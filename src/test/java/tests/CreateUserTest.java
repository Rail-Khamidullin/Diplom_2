package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.api.UserJSON;
import org.generator.GeneratorUser;
import org.junit.After;
import org.junit.Test;
import org.user.CreateUser;

public class CreateUserTest extends BaseTest {

    private UserJSON user;
    private GeneratorUser randomUser;
    private String accessToken;
    final CreateUser createUser = new CreateUser();

    @Override
    public void setUp() {
        super.setUp();
        // генерация данных
        user = randomUser.generateUser();
    }

    @Test
    @DisplayName("Создание валидиного пользователя") // имя теста
    @Description("Создание уникального пользователя (позитивный сценарий)") // описание теста
    public void createUserTest() {
        // добавление данных в тело запроса
        createUser.setUserBody(user);
        // создание пользователя
        Response userResponse = createUser.createUser();
        // получение токена пользователя для дальнейшей работы с ним
        accessToken = userResponse.path("accessToken");
        createUser.getResponseUniqueUser(userResponse);
    }

    @Test
    @DisplayName("Создание дублированного пользователя") // имя теста
    @Description("Создание пользователя с одинаковыми кредами (негативный сценарий)") // описание теста
    public void createDoubleUserTest() {
        createUser.setUserBody(user);
        Response firstUser = createUser.createUser();
        Response secondUser = createUser.createUser();
        accessToken = firstUser.path("accessToken");
        createUser.getResponseDoubleUser(secondUser);
    }

    @Test
    @DisplayName("Создание пользователя без заполнения имени") // имя теста
    @Description("Создание пользователя с не полными кредами (негативный сценарий)") // описание теста
    public void createUserWithoutNameTest() {
        createUser.setUserBody(new UserJSON(null, user.getPassword(), user.getEmail()));
        Response userResponse = createUser.createUser();
        accessToken = userResponse.path("accessToken");
        createUser.getResponseUserWithoutData(userResponse);
    }

    @Test
    @DisplayName("Создание пользователя без заполнения пароля") // имя теста
    @Description("Создание пользователя с не полными кредами (негативный сценарий)") // описание теста
    public void createUserWithoutPasswordTest() {
        createUser.setUserBody(new UserJSON(null, user.getPassword(), user.getEmail()));
        Response userResponse = createUser.createUser();
        accessToken = userResponse.path("accessToken");
        createUser.getResponseUserWithoutData(userResponse);
    }

    @Test
    @DisplayName("Создание пользователя без заполнения email") // имя теста
    @Description("Создание пользователя с не полными кредами (негативный сценарий)") // описание теста
    public void createUserWithoutEmailTest() {
        createUser.setUserBody(new UserJSON(null, user.getPassword(), user.getEmail()));
        Response userResponse = createUser.createUser();
        accessToken = userResponse.path("accessToken");
        createUser.getResponseUserWithoutData(userResponse);
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
