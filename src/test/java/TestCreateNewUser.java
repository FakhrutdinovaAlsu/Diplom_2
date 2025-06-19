import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.ApiConfig;
import ru.praktikum.UserSteps;
import static org.hamcrest.Matchers.is;

public class TestCreateNewUser {
    private UserSteps userSteps = new UserSteps();
    private String email;
    private String password;
    private String name;

    @Before
    public void setUp() {
        RestAssured.baseURI = ApiConfig.getBaseUrl();
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
    @Description("Проводится проверка на успешную регитсрацию нового пользователя")
    public void shouldReturnSuccessTrue () {
        userSteps
            .createUser(email, password, name)
            .statusCode(200)
            .body("success", is(true));
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Проводится проверка на создание пользователя, который уже зарегистрирован")
    public void shouldReturnUserAlreadyExists () {
    userSteps.createUser(email, password, name);
    userSteps.createUser(email, password, name)
            .statusCode(403)
            .body("message", is("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя без Email")
    @Description("Проводится проверка на создание пользователя без указания Email")
    public void registerWithOutEmail () {
        userSteps.createUser("", password, name)
            .statusCode(403)
            .body("message", is("Email, password and name are required fields"));
        }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Проводится проверка на создание пользователя без указания пароля")
    public void registerWithOutPassword () {
        userSteps.createUser(email, "", name)
            .statusCode(403)
            .body("message", is("Email, password and name are required fields"));
        }

    @Test
    @DisplayName("Создание пользователя без имени")
    @Description("Проводится проверка на создание пользователя без указания имени")
    public void registerWithOutName () {
        userSteps
            .createUser(email, password, "")
            .statusCode(403)
            .body("message", is("Email, password and name are required fields"));
        }
    }