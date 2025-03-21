package tests.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.api.UserJSON;
import org.generator.GeneratorUser;
import org.junit.After;
import org.junit.Test;
import org.order.Order;
import org.user.User;
import tests.BaseTest;


public class GetOrderTest extends BaseTest {
    Order order = new Order();

    private UserJSON user;
    private GeneratorUser randomUser;
    String accessToken;
    User createUser = new User();

    @Override
    public void setUp() {
        super.setUp();
    }

    @Test
    @DisplayName("Получение заказов пользователя") // имя теста
    @Description("Получение заказа с авторизацией (позитивный сценарий)") // описание теста
    public void getOrderWithAuthTest() {
        user = randomUser.generateUser();  // генерация данных
        createUser.setUserBody(user);  // добавление данных в тело запроса
        Response getAccess = createUser.createUser();  // создание пользователя
        accessToken = getAccess.path("accessToken");  // получение токена пользователя для дальнейшей работы с ним

        Response responseOrder = order.getOrderUser(accessToken);
        order.getOrderWithAuth(responseOrder);
    }

    @Test
    @DisplayName("Получение заказов пользователя") // имя теста
    @Description("Получение заказа без авторизации (негативный сценарий)") // описание теста
    public void getOrderWithoutAuthTest() {
        Response responseOrder = order.getOrderUser(null);
        order.getOrderWithoutAuth(responseOrder);
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
