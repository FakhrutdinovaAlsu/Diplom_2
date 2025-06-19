package ru.praktikum;

public class ApiConfig {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getOrdersPath() {
        return "api/orders/all";
    }

    public static String getIngredientsPath() {
        return "api/ingredients";
    }

    public static String getAddOrderPath() {
        return "api/orders";
    }

    public static String getRegisterPath() {
        return "api/auth/register";
    }

    public static String getLoginPath() {
        return "api/auth/login";
    }

    public static String getRDeliteUserPath() {
        return "api/auth/user";
    }


}
