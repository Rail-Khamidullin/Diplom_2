package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.api.OrderJSON;
import org.api.UserJSON;
import org.generator.GeneratorUser;
import org.junit.After;
import org.junit.Test;
import org.order.CreateOrder;
import org.user.CreateUser;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderTest extends BaseTest {

    OrderJSON orderJSON;
    CreateOrder createOrder = new CreateOrder();
    List<String> ingredients = new ArrayList<>();
    Response response;

    private UserJSON user;
    private GeneratorUser randomUser;
    String accessToken;
    CreateUser createUser = new CreateUser();

    @Override
    public void setUp() {
        super.setUp();
        response = createOrder.getIngridient();
    }

    @Test
    @DisplayName("Создание заказа") // имя теста
    @Description("Создание заказа без авторизации (позитивный сценарий)") // описание теста
    public void createOrderWithoutAuthTest() {
        // достаём id ингридиентов и добавляем сначала в массив, а потом в тело запроса
        String ingredFirst = response.path("data[0]._id");
        String ingredSecond = response.path("data[1]._id");
        ingredients.add(ingredFirst);
        ingredients.add(ingredSecond);
        orderJSON = new OrderJSON(ingredients);
        Response getRequest = createOrder.createOrder(orderJSON, null);
        createOrder.getOrderWithIngredient(getRequest);
    }

    @Test
    @DisplayName("Создание заказа") // имя теста
    @Description("Создание заказа с авторизации (позитивный сценарий)") // описание теста
    public void createOrderWithAuthTest() {
        // достаём id ингридиентов и добавляем сначала в массив, а потом в тело запроса
        String ingredFirst = response.path("data[0]._id");
        String ingredSecond = response.path("data[1]._id");
        ingredients.add(ingredFirst);
        ingredients.add(ingredSecond);
        orderJSON = new OrderJSON(ingredients);

        user = randomUser.generateUser();  // генерация данных
        createUser.setUserBody(user);  // добавление данных в тело запроса
        Response getAccess = createUser.createUser();  // создание пользователя
        accessToken = getAccess.path("accessToken");  // получение токена пользователя для дальнейшей работы с ним

        Response getRequest = createOrder.createOrder(orderJSON, accessToken);
        createOrder.getOrderWithIngredient(getRequest);
    }

    @Test
    @DisplayName("Создание заказа") // имя теста
    @Description("Создание заказа без ингридиентов (негативый сценарий)") // описание теста
    public void createOrderWithoutIngredientTest() {
        orderJSON = new OrderJSON();
        Response getRequest = createOrder.createOrder(orderJSON, null);
        createOrder.getOrderWithoutIngredient(getRequest);
    }

    @Test
    @DisplayName("Создание заказа") // имя теста
    @Description("Создание заказа с не верными ингридиентами (негативый сценарий)") // описание теста
    public void createOrderWithoutBodyTest() {
        orderJSON = new OrderJSON();
        Response getRequest = createOrder.createOrder(orderJSON, null);
        createOrder.getOrderWithoutIngredient(getRequest);
    }

    @Test
    @DisplayName("Создание заказа") // имя теста
    @Description("Создание заказа с не верными ингридиентами (негативый сценарий)") // описание теста
    public void createOrderBagIngredientTest() {
        ingredients.add("3123");
        orderJSON = new OrderJSON(ingredients);
        Response getRequest = createOrder.createOrder(orderJSON, null);
        createOrder.getOrderBagIngredient(getRequest);
    }

    @After
    public void afterClass() throws Exception {
        if (accessToken != null) {
            createUser.deleteUser(accessToken);
        } else {
            System.out.println("User is null !");
        }
    }
}
