import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.UserSteps;

import static org.hamcrest.Matchers.is;

public class TestLogInUser {
    private UserSteps userSteps = new UserSteps();
    private String email;
    private String password;
    private String name;

    @Before
    public void setUp() {
        email = UserSteps.returnRandomEmail();
        password = UserSteps.returnRandomPassword();
        name = UserSteps.returnRandomName();
        userSteps.createUser(email, password, name);
    }

    @After
    public void tearDown() {
        String accessToken = userSteps.loginUser(email, password).extract().path("accessToken");
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Проверка успешности авторизации")
    public void shouldReturnSuccessTrue() {
        userSteps
                .loginUser(email, password)
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Попытка авторизации без email")
    public void loginWithIncorrectEmail() {
        userSteps
                .loginUser("123"+email, password)
                .statusCode(401)
                .body("message", is("email or password are incorrect"));
    }

    @Test
    @DisplayName("Попытка авторизации без пароля")
    public void loginWithIncorrectPassword() {
        userSteps
                .loginUser(email,"123"+password)
                .statusCode(401)
                .body("message", is("email or password are incorrect"));
    }
}