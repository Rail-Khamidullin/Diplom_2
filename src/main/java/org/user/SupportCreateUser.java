package org.user;

import io.restassured.response.Response;
import org.api.UserJSON;


public interface SupportCreateUser {

    // Создание пользователя
    public Response createUser();
    // Авторизация пользователя
    public Response loginUser(UserJSON userJSON);
    // Удаление пользователя
    public void deleteUser(String accessToken);
}
