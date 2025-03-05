package org.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.api.OrderJSON;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrder {

    public static final String INGREDIENT_API = "/api/ingredients";
    public static final String ORDER_API = "/api/orders";

    /// Получение данных об ингридиентах____________________
    @Step("Получение хэш ингридиента")
    public Response getIngridient() {

        Response response = given()
                .log().all()
                .when()
                .get(INGREDIENT_API);
        return response;
    }

    @Step("Создание заказа")
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

    @Step("Получаем ответ false и статус код 400 на создание заказа")
    public void getOrderWithoutIngredient(Response response) {
        response.then().assertThat().statusCode(400)
                .body("success",equalTo(false))
                .body("message",equalTo("Ingredient ids must be provided"));
    }

    @Step("Получаем статус код 500 на создание заказа")
    public void getOrderBagIngredient(Response response) {
        response.then().assertThat().statusCode(500);
    }
}
