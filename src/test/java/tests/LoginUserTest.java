package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.api.UserJSON;
import org.generator.GeneratorUser;
import org.junit.After;
import org.junit.Test;
import org.user.CreateUser;

public class LoginUserTest extends BaseTest {

    UserJSON user;
    GeneratorUser randomUser;
    String accessToken;
    final CreateUser createUser = new CreateUser();

    @Override
    public void setUp() {
        super.setUp();
    }

    @Test
    @DisplayName("Авторизация валидиного пользователя") // имя теста
    @Description("Создание уникального пользователя (позитивный сценарий)") // описание теста
    public void loginUserTest() {
        user = randomUser.generateUser();
        createUser.setUserBody(user);
        Response userResponse = createUser.createUser();
        accessToken = userResponse.path("accessToken");
        Response loginUserResponse = createUser.loginUser(user);
        createUser.loginValidUser(loginUserResponse);
    }

    @Test
    @DisplayName("Авторизация НЕ валидиного пользователя") // имя теста
    @Description("Авторизация пользователя с не верным логином (негативный сценарий)") // описание теста
    public void authorizeWithErrorLoginTest() {
        user = randomUser.generateUser();
        createUser.setUserBody(user);
        Response userResponse = createUser.createUser();
        accessToken = userResponse.path("accessToken");
        Response loginUserResponse = createUser
                .loginUser(new UserJSON("NameTest", user.getPassword(), user.getEmail()));
        createUser.getResponseWhenErrorCred(loginUserResponse);
    }

    @Test
    @DisplayName("Авторизация НЕ валидиного пользователя") // имя теста
    @Description("Авторизация пользователя с не верным паролем (негативный сценарий)") // описание теста
    public void authorizeWithErrorPasswordTest() {
        user = randomUser.generateUser();
        createUser.setUserBody(user);
        Response userResponse = createUser.createUser();
        accessToken = userResponse.path("accessToken");
        Response loginUserResponse = createUser
                .loginUser(new UserJSON (user.getName(), "1111", user.getEmail()));
        createUser.getResponseWhenErrorCred(loginUserResponse);
    }

    @Test
    @DisplayName("Авторизация НЕ валидиного пользователя") // имя теста
    @Description("Авторизация пользователя с не верным email (негативный сценарий)") // описание теста
    public void authorizeWithErrorEmailTest() {
        user = randomUser.generateUser();
        createUser.setUserBody(user);
        Response userResponse = createUser.createUser();
        accessToken = userResponse.path("accessToken");
        Response loginUserResponse = createUser
                .loginUser(new UserJSON(user.getName(), user.getPassword(), "test@email.ru"));
        createUser.getResponseWhenErrorCred(loginUserResponse);
    }

    @Test
    @DisplayName("Авторизация НЕ валидиного пользователя") // имя теста
    @Description("Авторизация пользователя без логина (негативный сценарий)") // описание теста
    public void authorizeWithoutLoginTest() {
        user = randomUser.generateUser();
        createUser.setUserBody(user);
        Response userResponse = createUser.createUser();
        accessToken = userResponse.path("accessToken");
        Response loginUserResponse = createUser
                .loginUser(new UserJSON (null, user.getPassword(), user.getEmail()));
        createUser.getResponseWhenErrorCred(loginUserResponse);
    }

    @Test
    @DisplayName("Авторизация НЕ валидиного пользователя") // имя теста
    @Description("Авторизация пользователя без пароля (негативный сценарий)") // описание теста
    public void authorizeWithoutPasswordTest() {
        user = randomUser.generateUser();
        createUser.setUserBody(user);
        Response userResponse = createUser.createUser();
        accessToken = userResponse.path("accessToken");
        Response loginUserResponse = createUser
                .loginUser(new UserJSON (user.getName(), null, user.getEmail()));
        createUser.getResponseWhenErrorCred(loginUserResponse);
    }

    @Test
    @DisplayName("Авторизация НЕ валидиного пользователя") // имя теста
    @Description("Авторизация пользователя без email (негативный сценарий)") // описание теста
    public void authorizeWithoutEmailTest() {
        user = randomUser.generateUser();
        createUser.setUserBody(user);
        Response userResponse = createUser.createUser();
        accessToken = userResponse.path("accessToken");
        Response loginUserResponse = createUser
                .loginUser(new UserJSON (user.getName(), "1111", null));
        createUser.getResponseWhenErrorCred(loginUserResponse);
    }

    @After
    public void afterClass() throws Exception {
        createUser.deleteUser(accessToken);
    }
}
