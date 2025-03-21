package org.user;

import io.restassured.response.Response;
import org.api.UserJSON;


public interface SupportUser {

    // Создание пользователя
    public Response createUser();
    // Авторизация пользователя
    public Response loginUser(UserJSON userJSON);
    // Удаление пользователя
    public void deleteUser(String accessToken);
    // Обновление данных пользователя
    public Response updateDataUser(UserJSON userJSON, String accessToken);
}
