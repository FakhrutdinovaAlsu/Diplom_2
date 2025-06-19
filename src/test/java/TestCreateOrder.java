import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.ApiConfig;
import ru.praktikum.CreateOrderData;
import ru.praktikum.OrderSteps;
import ru.praktikum.UserSteps;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TestCreateOrder {
    private final UserSteps userSteps = new UserSteps();
    private final OrderSteps orderSteps = new OrderSteps();
    private String email;
    private String password;
    private String name;
    private String accessToken;
    private String[] ingredients;


    @Before
    public void setUp() {
        RestAssured.baseURI = ApiConfig.getBaseUrl();
        email = UserSteps.returnRandomEmail();
        password = UserSteps.returnRandomPassword();
        name = UserSteps.returnRandomName();
        userSteps.createUser(email, password, name);
        accessToken = userSteps.loginUser(email, password).extract().path("accessToken");
        String ingredient = orderSteps.getIngredientId();
        ingredients = new String[1];
        ingredients[0] = ingredient;
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void testCreateOrderWithAuthAndBurgerInfo() {
        OrderSteps.createOrder(new CreateOrderData(ingredients),accessToken)
                .statusCode(200)
                .body("success", is(true))
                .body("order.number", notNullValue())
                .body("name", notNullValue());;
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void testCreateOrderWithBurgerInfoWithOutAuth() {
        OrderSteps.createOrder(new CreateOrderData(ingredients),"").statusCode(401);
    }

    @Test
    @DisplayName("Создание заказа с информацией о наполнении")
    public void testCreateOrderWithBurgerInfo() {
        OrderSteps.createOrder(new CreateOrderData(ingredients),accessToken)
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Создание заказа без информации о бургере")
        public void testCreateOrderWithOutBurgerInfo() {
        OrderSteps.createOrder(new CreateOrderData(new String[0]), accessToken)
                    .statusCode(400)
                    .body("success", is(false))
                    .body("message", is("Ingredient ids must be provided"));
        }

    @Test
    @DisplayName("Создание заказа с неверным хэш ингредиентов")
    public void testCreateOrderWithIncorrectBurgerInfo() {
        String[] incorrectId = new String[1];
        incorrectId[0] = "123456";
        OrderSteps.createOrder(new CreateOrderData(incorrectId),accessToken)
                .statusCode(500);
    }
}