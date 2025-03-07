package org.user;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.api.UserJSON;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class User implements SupportUser {

    // Endpoint создания пользователя
    public static final String CREATE_USER_API = "/api/auth/register";
    // Endpoint авторизации пользователя
    public static final String LOGIN_USER_API = "/api/auth/login";
    // Endpoint обновления данных пользователя
    public static final String UPDATE_DATE_API = "/api/auth/user";
    // Endpoint удаления пользователя
    public static final String DELETE_USER_API = "api/auth/user";


    // Создаём экземпляр класса с телом запроса
    private UserJSON userJSON;
    // Устанавливаем необходимые значения тела
    public void setUserBody(UserJSON userJSON) { this.userJSON = userJSON; }

    /// Регистрация пользователя____________________
    @Step("Создание пользователя")
    public Response createUser() {

        Response response = given()
                .header("Content-type", "application/json")
                .log().all()
                .body(userJSON)
                .when()
                .post(CREATE_USER_API);
        return response;
    }

    @Step("Получаем ответ true и статус код 200 при регистрации")
    public void getResponseUniqueUser(Response response) {
        response.then().assertThat().statusCode(200)
                .body("success",equalTo(true));
    }

    @Step("Получаем ответ false и статус код 403")
    public void getResponseDoubleUser(Response response) {
        response.then().assertThat().statusCode(403)
                .body("success",equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Step("Получаем ответ false и статус код 403")
    public void getResponseUserWithoutData(Response response) {
        response.then().assertThat().statusCode(403)
                .body("success",equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    /// Авторизация пользователя____________________
    @Step("Авторизация пользователя")
    public Response loginUser(UserJSON userJSON) {

        Response response = given()
                .header("Content-type", "application/json")
                .log().all()
                .body(userJSON)
                .when()
                .post(LOGIN_USER_API);
        return response;
    }

    @Step("олучаем ответ true и статус код 200 при авторизации")
    @Description("Авторизация пользователя с валидными кредами (позитивный сценарий)")
    public void loginValidUser (Response response) {
        response.then().assertThat().statusCode(200)
                .body("success",equalTo(true));
    }

    @Step("Получаем ответ false и статус код 401")
    @Description("Авторизация пользователя с не верным или отсутствующим полем (негативный сценарий)")
    public void getResponseWhenErrorCred(Response response) {
        response.then().assertThat().statusCode(401)
                .body("success",equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    /// Обновление данных пользователя___________________
    @Step("Обновление данных пользователя")
    public Response updateDataUser(UserJSON userJSON, String accessToken) {
        if (accessToken != null) {
        Response response = given()
                .header("Content-type", "application/json")
                .header("authorization", accessToken)
                .log().all()
                .body(userJSON)
                .when()
                .patch(UPDATE_DATE_API);
        return response;
        } else {
            Response response = given()
                    .header("Content-type", "application/json")
                    .log().all()
                    .body(userJSON)
                    .when()
                    .patch(UPDATE_DATE_API);
            return response;
        }
    }

    @Step("Получаем ответ true и статус код 200 после обновления данных")
    public void getUpdateDateUser(Response response, UserJSON userJSON) {
        response.then().assertThat().statusCode(200)
                .body("success",equalTo(true))
                .body("user.email",equalTo(userJSON.getEmail()))
                .body("user.name", equalTo(userJSON.getName()));
    }

    @Step("Получаем ответ true и статус код 403 на обновление данных")
    public void getUpdateWithDoubleEmail(Response response) {
        response.then().assertThat().statusCode(403)
                .body("success",equalTo(false))
                .body("message", equalTo("User with such email already exists"));
    }

    @Step("Получаем ответ false и статус код 401 на обновление данных")
    public void getErrorUpdateUser(Response response) {
        response.then().assertThat().statusCode(401)
                .body("success",equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    /// Удаление пользователя____________________
    public void deleteUser(String accessToken) {
            Response response = given()
                    .header("authorization", accessToken)
                    .log().all()
                    .when()
                    .delete(DELETE_USER_API);
            response.then().statusCode(202); // Проверяем статус код
    }
}
