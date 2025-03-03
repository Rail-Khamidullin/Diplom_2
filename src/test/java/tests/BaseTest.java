package tests;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.user.CreateUser;

public class BaseTest {

    public static final String BURGERS_URL = "https://stellarburgers.nomoreparties.site";
    private CreateUser createUser = new CreateUser();
    protected String accessToken;

    // Повторяющуюся для разных ручек часть URL лучше записать в переменную в методе Before
    @Before
    public void setUp() { RestAssured.baseURI = BURGERS_URL; }
}
