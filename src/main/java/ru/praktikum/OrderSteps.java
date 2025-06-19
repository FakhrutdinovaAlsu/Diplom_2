package ru.praktikum;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Получение списка заказов с сервера")
    public static ValidatableResponse getOrderList() {
        return given()
                .when()
                .get("api/orders/all")
                .then();
    }

    @Step("Получение списка ингредиентов с сервера")
    public static ValidatableResponse getIngredientsList() {
        return given()
                .when()
                .get(ApiConfig.getIngredientsPath())
                .then();
    }

    @Step("Получение ID ингредиента")
    public String getIngredientId() {
        return getIngredientsList().extract()
                .body().path("data[0]._id");
    }

    @Step("Создание заказа c ингредиентами")
    public static ValidatableResponse createOrder(CreateOrderData createOrderData,String accessToken) {
        return (ValidatableResponse) given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(createOrderData)
                .when()
                .post(ApiConfig.getAddOrderPath())
                .then();
    }
}