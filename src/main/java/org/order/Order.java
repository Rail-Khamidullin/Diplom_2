package org.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.api.OrderJSON;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class Order implements SupportOrder {

    // Endpoint получения ингридиентов
    public static final String INGREDIENT_API = "/api/ingredients";
    // Endpoint создания заказа
    public static final String ORDER_API = "/api/orders";

    /// Получение данных об ингредиентах____________________
    @Step("Получение хэш ингредиента")
    public Response getIngridient() {
        Response response = given()
                .log().all()
                .when()
                .get(INGREDIENT_API);
        return response;
    }

    /// Создание заказа____________________
    @Step("Создание заказа с авторизацией и без")
    public Response createOrder(OrderJSON orderJSON, String accessToken) {
        if (accessToken != null) {
            Response response = given()
                    .header("Content-type", "application/json")
                    .header("authorization", accessToken)
                    .log().all()
                    .body(orderJSON)
                    .when()
                    .post(ORDER_API);
            return response;
        } else {
            Response response = given()
                    .header("Content-type", "application/json")
                    .log().all()
                    .body(orderJSON)
                    .when()
                    .post(ORDER_API);
            return response;
        }
    }

    @Step("Получаем ответ true и статус код 200 на создание заказа")
    public void getOrderWithIngredient(Response response) {
        response.then().assertThat().statusCode(200)
                .body("success",equalTo(true));
        String nameOrder = response.path("name");
        System.out.println(nameOrder);
    }

    @Step("Получаем ответ false и статус код 400 на создание заказа без ингредиентов")
    public void getOrderWithoutIngredient(Response response) {
        response.then().assertThat().statusCode(400)
                .body("success",equalTo(false))
                .body("message",equalTo("Ingredient ids must be provided"));
    }

    @Step("Получаем статус код 500 на создание заказа с не верным хэшем")
    public void getOrderBagIngredient(Response response) {
        response.then().assertThat().statusCode(500);
    }

    /// Получение заказа____________________
    @Step("Получение заказа, с авторизацией и без, для конкретного пользователя")
    public Response getOrderUser(String accessToken) {
        if (accessToken != null) {
            Response response = given()
                    .header("authorization", accessToken)
                    .log().all()
                    .when()
                    .get(ORDER_API);
            return response;
        } else {
            Response response = given()
                    .log().all()
                    .when()
                    .get(ORDER_API);
            return response;
        }
    }

    @Step("Получаем ответ true и статус код 200 на создание заказа с авторизацией")
    public void getOrderWithAuth(Response response) {
        response.then().assertThat().statusCode(200)
                .body("success",equalTo(true))
                .body("orders", notNullValue());
    }

    @Step("Получаем ответ false и статус код 401 на создание заказа без авторизации")
    public void getOrderWithoutAuth(Response response) {
        response.then().assertThat().statusCode(401)
                .body("success",equalTo(false))
                .body("message",equalTo("You should be authorised"));
    }
}
