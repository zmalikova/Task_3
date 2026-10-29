
package tests;

import api.UserApi;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import pages.RegistrationPage;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest {

    private WebDriver driver;
    private String email;
    private String password;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(
                "https://qa-stellarburgers.education-services.ru/"
        );
    }

    @Test
    public void shouldRegisterNewUser() {
        email = "user" + UUID.randomUUID()
                .toString().replace("-", "").substring(0, 10)
                + "@example.com";
        password = "Burger2026";

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        mainPage.clickRegistrationLink();

        RegistrationPage registrationPage =
                new RegistrationPage(driver);

        registrationPage.enterName("Little Alien");
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.clickRegisterButton();

        new WebDriverWait(
                driver, Duration.ofSeconds(10)
        ).until(
                ExpectedConditions.urlContains("/login")
        );

        assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "После успешной регистрации должна открыться страница входа"
        );
    }

    @Test
    public void shouldShowErrorForShortPassword() {
        String invalidEmail = "user" + UUID.randomUUID()
                .toString().replace("-", "").substring(0, 10)
                + "@example.com";

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        mainPage.clickRegistrationLink();

        RegistrationPage registrationPage =
                new RegistrationPage(driver);

        registrationPage.enterName("Little Alien");
        registrationPage.enterEmail(invalidEmail);
        registrationPage.enterPassword("12345");
        registrationPage.clickRegisterButton();

        assertTrue(
                registrationPage.getErrorMessage().length() > 0,
                "Должна отображаться ошибка для пароля короче 6 символов"
        );
    }

    @AfterEach
    public void tearDown() {
        try {
            if (email != null && password != null) {
                UserApi userApi = new UserApi();
                Response loginResponse =
                        userApi.loginUser(email, password);

                if (loginResponse.statusCode() == 200) {
                    String accessToken = loginResponse.jsonPath()
                            .getString("accessToken");

                    if (accessToken != null
                            && !accessToken.isEmpty()) {
                        userApi.deleteUser(accessToken);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(
                    "Не удалось удалить тестового пользователя: "
                            + e.getMessage()
            );
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
