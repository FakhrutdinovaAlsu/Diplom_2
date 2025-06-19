package ru.praktikum;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import static io.restassured.RestAssured.given;

public class UserSteps {
    @Step("Создание радномного Email '{email}'")
    public static String returnRandomEmail() {
        return RandomStringUtils.randomAlphabetic(10) + "@gmail.com";
    }

    @Step("Создание радномного пароля '{password}' ")
    public static String returnRandomPassword() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    @Step("Создание радномного имени '{name}'")
    public static String returnRandomName() {
        return  RandomStringUtils.randomAlphabetic(10);
    }

    @Step("Создание нового пользователя с почтой '{email}', паролем '{password}' и именем '{name}'")
    public ValidatableResponse createUser(String email, String password, String name) {
        LogInDetails logInDetails = new LogInDetails(email, password,name);
        return (ValidatableResponse) given()
                .contentType(ContentType.JSON)
                .body(logInDetails)
                .when()
                .post(ApiConfig.getRegisterPath())
                .then();
    }

    @Step("Авторизация пользователя с почтой '{email}' и паролем '{password}'")
    public ValidatableResponse loginUser(String login, String password) {
        LogInDetails logInDetails = new LogInDetails(login, password);
        return given()
                .contentType(ContentType.JSON)
                .body(logInDetails)
                .when()
                .post(ApiConfig.getLoginPath())
                .then();
    }

    @Step("Удаление Пользователя с accessToken '{accessToken}'")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .when()
                .delete(ApiConfig.getRDeliteUserPath())
                .then();
    }
}