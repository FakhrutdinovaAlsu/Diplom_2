import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.UserSteps;
import static org.hamcrest.Matchers.is;

public class TestCreateNewUser {
    private UserSteps userSteps = new UserSteps();
    private String email;
    private String password;
    private String name;

    @Before
    public void setUp() {
        email = UserSteps.returnRandomEmail();
        password = UserSteps.returnRandomPassword();
        name = UserSteps.returnRandomName();
    }

    @After
    public void tearDown() {
        String accessToken = userSteps.loginUser(email, password).extract().path("accessToken");
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Проверка на успешную регистрацию")
    public void shouldReturnSuccessTrue () {
        userSteps
            .createUser(email, password, name)
            .statusCode(200)
            .body("success", is(true));
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void shouldReturnUserAlreadyExists () {
    userSteps.createUser(email, password, name);
    userSteps.createUser(email, password, name)
            .statusCode(403)
            .body("message", is("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя без Email")
    public void registerWithOutEmail () {
        userSteps.createUser("", password, name)
            .statusCode(403)
            .body("message", is("Email, password and name are required fields"));
        }

    @Test
    @DisplayName("Создание пользователя без пароля")
    public void registerWithOutPassword () {
        userSteps.createUser(email, "", name)
            .statusCode(403)
            .body("message", is("Email, password and name are required fields"));
        }

    @Test
    @DisplayName("Создание пользователя без имени")
    public void registerWithOutName () {
        userSteps
            .createUser(email, password, "")
            .statusCode(403)
            .body("message", is("Email, password and name are required fields"));
        }
    }