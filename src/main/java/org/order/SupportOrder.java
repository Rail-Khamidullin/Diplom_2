package org.order;

import io.restassured.response.Response;
import org.api.OrderJSON;

public interface SupportOrder {
    // Создание заказа с авторизацией и без
    public Response createOrder(OrderJSON orderJSON, String accessToken);
    // Получение заказа, с авторизацией и без, для конкретного пользователя
    public Response getOrderUser(String accessToken);
}
