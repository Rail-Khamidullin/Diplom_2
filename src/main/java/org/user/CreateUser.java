package org.user;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.api.UserJSON;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUser implements SupportCreateUser {

    // Endpoint создания пользователя
    public static final String CREATE_USER_API = "/api/auth/register";
    // Endpoint удаления пользователя
    public static final String DELETE_USER_API = "api/auth/user";

    // Создаём экземпляр класса с телом запроса
    private UserJSON userJSON;
    // Устанавливаем необходимые значения тела
    public void setUserBody(UserJSON userJSON) { this.userJSON = userJSON; }

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

    @Step("Получаем ответ true и статус код 200")
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
    @Description("Создание пользователя с не полными кредами (негативный сценарий)")
    public void getResponseUserWithoutData(Response response) {
        response.then().assertThat().statusCode(403)
                .body("success",equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    // Удаляем пользователя
    public void deleteUser(String accessToken) {
        if (accessToken != null) {
            Response response = given()
                    .header("authorization", accessToken)
                    .log().all()
                    .when()
                    .delete(DELETE_USER_API);
            response.then().statusCode(202); // Проверяем статус код
        } else {
            System.out.println("User is null !");
        }
    }
}
