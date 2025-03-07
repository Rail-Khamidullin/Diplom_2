package tests.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.api.UserJSON;
import org.generator.GeneratorUser;
import org.junit.After;
import org.junit.Test;
import org.user.User;
import tests.BaseTest;

public class LoginUserTest extends BaseTest {

    private UserJSON user;
    private GeneratorUser randomUser;
    private String accessToken;
    final User createUser = new User();

    @Override
    public void setUp() {
        super.setUp();
        user = randomUser.generateUser();
        createUser.setUserBody(user);
        Response userResponse = createUser.createUser();
        accessToken = userResponse.path("accessToken");
    }

    @Test
    @DisplayName("Авторизация валидиного пользователя") // имя теста
    @Description("Авторизация пользователя с верными кредами (позитивный сценарий)") // описание теста
    public void loginUserTest() {
        Response loginUserResponse = createUser.loginUser(user);
        createUser.loginValidUser(loginUserResponse);
    }

    @Test
    @DisplayName("Авторизация НЕ валидиного пользователя") // имя теста
    @Description("Авторизация пользователя с не верным логином (негативный сценарий)") // описание теста
    public void authorizeWithErrorLoginTest() {
        Response loginUserResponse = createUser
                .loginUser(new UserJSON("NameTest", user.getPassword(), user.getEmail()));
        createUser.getResponseWhenErrorCred(loginUserResponse);
    }

    @Test
    @DisplayName("Авторизация НЕ валидиного пользователя") // имя теста
    @Description("Авторизация пользователя с не верным паролем (негативный сценарий)") // описание теста
    public void authorizeWithErrorPasswordTest() {
        Response loginUserResponse = createUser
                .loginUser(new UserJSON (user.getName(), "1111", user.getEmail()));
        createUser.getResponseWhenErrorCred(loginUserResponse);
    }

    @Test
    @DisplayName("Авторизация НЕ валидиного пользователя") // имя теста
    @Description("Авторизация пользователя с не верным email (негативный сценарий)") // описание теста
    public void authorizeWithErrorEmailTest() {
        Response loginUserResponse = createUser
                .loginUser(new UserJSON(user.getName(), user.getPassword(), "test@email.ru"));
        createUser.getResponseWhenErrorCred(loginUserResponse);
    }

    @Test
    @DisplayName("Авторизация НЕ валидиного пользователя") // имя теста
    @Description("Авторизация пользователя без логина (негативный сценарий)") // описание теста
    public void authorizeWithoutLoginTest() {
        Response loginUserResponse = createUser
                .loginUser(new UserJSON (null, user.getPassword(), user.getEmail()));
        createUser.getResponseWhenErrorCred(loginUserResponse);
    }

    @Test
    @DisplayName("Авторизация НЕ валидиного пользователя") // имя теста
    @Description("Авторизация пользователя без пароля (негативный сценарий)") // описание теста
    public void authorizeWithoutPasswordTest() {
        Response loginUserResponse = createUser
                .loginUser(new UserJSON (user.getName(), null, user.getEmail()));
        createUser.getResponseWhenErrorCred(loginUserResponse);
    }

    @Test
    @DisplayName("Авторизация НЕ валидиного пользователя") // имя теста
    @Description("Авторизация пользователя без email (негативный сценарий)") // описание теста
    public void authorizeWithoutEmailTest() {
        Response loginUserResponse = createUser
                .loginUser(new UserJSON (user.getName(), "1111", null));
        createUser.getResponseWhenErrorCred(loginUserResponse);
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
