package org.generator;

import com.github.javafaker.Faker;
import org.api.UserJSON;

public class GeneratorUser {

    // Генерируем данные для создания пользователя
    public static UserJSON generateUser() {

        Faker faker = new Faker();
        faker.lordOfTheRings();

        String email = faker.internet().safeEmailAddress();
        String password = faker.internet().password(6, 10, true, true, true);
        String name = faker.lordOfTheRings().character();

        return new UserJSON(name, password, email);
    }
}
