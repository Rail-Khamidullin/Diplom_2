package org.user;

import io.restassured.response.Response;


public interface SupportCreateUser {

    // Создание пользователя
    public Response createUser();
    // Удаление пользователя
    public void deleteUser(String accessToken);
}
