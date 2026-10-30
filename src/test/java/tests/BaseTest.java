
package tests;

import api.UserApi;
import config.Urls;
import factory.DriverFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected String email;
    protected String password;

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");

        driver = DriverFactory.createDriver(browser);
        driver.manage().window().maximize();
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(30));

        driver.get(Urls.BASE_URL);
    }

    @AfterEach
    public void tearDown() {
        try {
            deleteTestUser();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    protected void deleteTestUser() {
        if (email == null || password == null) {
            return;
        }

        UserApi userApi = new UserApi();

        Response response = userApi.loginUser(email, password);

        if (response.statusCode() == 200) {
            String token = response.jsonPath()
                    .getString("accessToken");

            if (token != null && !token.isEmpty()) {
                Response deleteResponse = userApi.deleteUser(token);

                if (deleteResponse.statusCode() != 202) {
                    throw new AssertionError(
                            "Не удалось удалить тестового пользователя"
                    );
                }
            }
        }
    }
}
