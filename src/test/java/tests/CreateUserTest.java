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

    UserJSON user;
    GeneratorUser randomUser;
    String accessToken;
    final CreateUser createUser = new CreateUser();

    @Override
    public void setUp() {
        super.setUp();
    }

    @Test
    @DisplayName("Создание валидиного пользователя") // имя теста
    @Description("Создание уникального пользователя (позитивный сценарий)") // описание теста
    public void createUserTest() {
        user = randomUser.generateUser();
        createUser.setUserBody(user);
        Response userResponse = createUser.createUser();
        // получение токена пользователя для дальнейшей работы с ним
        accessToken = userResponse.path("accessToken");
        createUser.getResponseUniqueUser(userResponse);
    }

    @Test
    @DisplayName("Создание дублированного пользователя") // имя теста
    @Description("Создание пользователя с одинаковыми кредами (негативный сценарий)") // описание теста
    public void createDoubleUserTest() {
        user = randomUser.generateUser();
        createUser.setUserBody(user);
        Response userResponse = createUser.createUser();
        Response userDoubleResponse = createUser.createUser();
        // получение токена пользователя для дальнейшей работы с ним
        accessToken = userResponse.path("accessToken");
        createUser.getResponseDoubleUser(userDoubleResponse);
    }

    @Test
    @DisplayName("Создание пользователя без заполнения имени") // имя теста
    @Description("Создание пользователя с не полными кредами (негативный сценарий)") // описание теста
    public void createUserWithoutNameTest() {
        user = randomUser.generateUser();
        createUser.setUserBody(new UserJSON(null, user.getPassword(), user.getEmail()));
        Response userResponse = createUser.createUser();
        // получение токена пользователя для дальнейшей работы с ним
        accessToken = userResponse.path("accessToken");
        createUser.getResponseUserWithoutData(userResponse);
    }

    @Test
    @DisplayName("Создание пользователя без заполнения пароля") // имя теста
    @Description("Создание пользователя с не полными кредами (негативный сценарий)") // описание теста
    public void createUserWithoutPasswordTest() {
        user = randomUser.generateUser();
        createUser.setUserBody(new UserJSON(null, user.getPassword(), user.getEmail()));
        Response userResponse = createUser.createUser();
        // получение токена пользователя для дальнейшей работы с ним
        accessToken = userResponse.path("accessToken");
        createUser.getResponseUserWithoutData(userResponse);
    }

    @Test
    @DisplayName("Создание пользователя без заполнения email") // имя теста
    @Description("Создание пользователя с не полными кредами (негативный сценарий)") // описание теста
    public void createUserWithoutEmailTest() {
        user = randomUser.generateUser();
        createUser.setUserBody(new UserJSON(null, user.getPassword(), user.getEmail()));
        Response userResponse = createUser.createUser();
        // получение токена пользователя для дальнейшей работы с ним
        accessToken = userResponse.path("accessToken");
        createUser.getResponseUserWithoutData(userResponse);
    }

    @Override
    public void afterClass() throws Exception {
        super.afterClass();
    }
}
