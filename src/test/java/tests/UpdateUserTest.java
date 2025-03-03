package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.api.UserJSON;
import org.generator.GeneratorUser;
import org.junit.After;
import org.junit.Test;
import org.user.CreateUser;

public class UpdateUserTest extends BaseTest {

    private UserJSON user;
    private GeneratorUser randomUser;
    private String accessToken;
    final CreateUser createUser = new CreateUser();

    @Override
    public void setUp() {
        super.setUp();
        user = randomUser.generateUser();
        createUser.setUserBody(user);
        Response userResponse = createUser.createUser();
        accessToken = userResponse.path("accessToken");
    }

    @Test
    @DisplayName("Изменение данных пользователя после авторизации") // имя теста
    @Description("Смена пароля (позитивный сценарий)") // описание теста
    public void updateUPasswordAfterloginTest() {
        // новый пароль
        String newPassword = randomUser.generateUser().getPassword();
        // добавление нового пароля в тело запроса
        user.setPassword(newPassword);
        // обновление данных пользователя
        Response newDataUser = createUser.updateDataUser(user, accessToken);
        // проверка соответствия тела запроса и ответа
        createUser.getUpdateDateUser(newDataUser, user);
    }

    @Test
    @DisplayName("Изменение данных пользователя после авторизации") // имя теста
    @Description("Смена логина (позитивный сценарий)") // описание теста
    public void updateNameAfterloginTest() {
        String newName = randomUser.generateUser().getName();
        user.setName(newName);
        Response newDataUser = createUser.updateDataUser(user, accessToken);
        createUser.getUpdateDateUser(newDataUser, user);
    }

    @Test
    @DisplayName("Изменение данных пользователя после авторизации") // имя теста
    @Description("Смена email (позитивный сценарий)") // описание теста
    public void updateEmailAfterloginTest() {
        String newEmail = randomUser.generateUser().getEmail();
        user.setEmail(newEmail);
        Response newDataUser = createUser.updateDataUser(user, accessToken);
        createUser.getUpdateDateUser(newDataUser, user);
    }

    @Test
    @DisplayName("Изменение данных пользователя после авторизации") // имя теста
    @Description("Смена email на такой же (негативный сценарий)") // описание теста
    public void updateSameEmailAfterloginTest() {
        Response newDataUser = createUser.updateDataUser(user, accessToken);
        createUser.getUpdateDateUser(newDataUser, user);
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации") // имя теста
    @Description("Смена email (негативный сценарий)") // описание теста
    public void updateEmailBeforeloginTest() {
        String newEmail = randomUser.generateUser().getEmail();
        user.setEmail(newEmail);
        Response newDataUser = createUser.updateDataUser(user, null);
        createUser.getErrorUpdateUser(newDataUser);
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации") // имя теста
    @Description("Смена логина (негативный сценарий)") // описание теста
    public void updateNameBeforeloginTest() {
        String newName = randomUser.generateUser().getName();
        user.setName(newName);
        Response newDataUser = createUser.updateDataUser(user, null);
        createUser.getErrorUpdateUser(newDataUser);
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации") // имя теста
    @Description("Смена пароля (негативный сценарий)") // описание теста
    public void updateUPasswordBeforeloginTest() {
        String newPassword = randomUser.generateUser().getPassword();
        user.setPassword(newPassword);
        Response newDataUser = createUser.updateDataUser(user, null);
        createUser.getErrorUpdateUser(newDataUser);
    }

    @After
    public void afterClass() throws Exception {
        createUser.deleteUser(accessToken);
    }
}
