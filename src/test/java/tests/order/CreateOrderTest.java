package tests.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.api.OrderJSON;
import org.api.UserJSON;
import org.generator.GeneratorUser;
import org.junit.After;
import org.junit.Test;
import org.order.Order;
import org.user.User;
import tests.BaseTest;
import java.util.ArrayList;
import java.util.List;

public class CreateOrderTest extends BaseTest {

    OrderJSON orderJSON = new OrderJSON();
    Order order = new Order();
    List<String> ingredients = new ArrayList<>();
    Response response;

    private UserJSON user;
    private GeneratorUser randomUser;
    String accessToken;
    User createUser = new User();

    @Override
    public void setUp() {
        super.setUp();
        response = order.getIngridient();
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
        Response getRequest = order.createOrder(orderJSON, null);
        order.getOrderWithIngredient(getRequest);
    }

    @Test
    @DisplayName("Создание заказа") // имя теста
    @Description("Создание заказа с авторизацией (позитивный сценарий)") // описание теста
    public void createOrderWithAuthTest() {
        String ingredFirst = response.path("data[0]._id");
        String ingredSecond = response.path("data[1]._id");
        ingredients.add(ingredFirst);
        ingredients.add(ingredSecond);
        orderJSON = new OrderJSON(ingredients);

        user = randomUser.generateUser();  // генерация данных
        createUser.setUserBody(user);  // добавление данных в тело запроса
        Response getAccess = createUser.createUser();  // создание пользователя
        accessToken = getAccess.path("accessToken");  // получение токена пользователя для дальнейшей работы с ним

        Response getRequest = order.createOrder(orderJSON, accessToken);
        order.getOrderWithIngredient(getRequest);
    }

    @Test
    @DisplayName("Создание заказа") // имя теста
    @Description("Создание заказа без ингредиентов (негативый сценарий)") // описание теста
    public void createOrderWithoutIngredientTest() {
        Response getRequest = order.createOrder(orderJSON, null);
        order.getOrderWithoutIngredient(getRequest);
    }

    @Test
    @DisplayName("Создание заказа") // имя теста
    @Description("Создание заказа с не верными ингредиентами (негативый сценарий)") // описание теста
    public void createOrderBagIngredientTest() {
        ingredients.add("3123");
        orderJSON = new OrderJSON(ingredients);
        Response getRequest = order.createOrder(orderJSON, null);
        order.getOrderBagIngredient(getRequest);
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
