
package tests;

import api.UserApi;
import helpers.UserHelper;
import io.restassured.response.Response;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import pages.LoginPage;
import factory.DriverFactory;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationTest {

    private WebDriver driver;
    private String email;
    private String password;
    private final String baseUrl =
            "https://qa-stellarburgers.education-services.ru/";

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");

        driver = DriverFactory.createDriver(browser);
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @Test
    public void shouldOpenLoginFromPersonalAccountWhenLoggedOut() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();

        assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "Неавторизованный пользователь должен попасть на страницу входа"
        );
    }

    @Test
    public void shouldNavigateToConstructorByConstructorButton() {
        loginAndOpenAccount();

        MainPage mainPage = new MainPage(driver);
        mainPage.clickConstructorButton();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(baseUrl));

        assertTrue(
                driver.getCurrentUrl().equals(baseUrl),
                "После нажатия «Конструктор» должна открыться главная страница"
        );
    }

    @Test
    public void shouldNavigateToConstructorByLogo() {
        loginAndOpenAccount();

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLogo();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(baseUrl));

        assertTrue(
                driver.getCurrentUrl().equals(baseUrl),
                "После нажатия на логотип должна открыться главная страница"
        );
    }

    @Step("Создать пользователя, войти в аккаунт и открыть личный кабинет")
    private void loginAndOpenAccount() {
        email = "user" + UUID.randomUUID()
                .toString().replace("-", "")
                .substring(0, 12) + "@example.com";
        password = "Burger2026";
        String name = "Little Alien";

        UserHelper userHelper = new UserHelper(driver);
        userHelper.registerUser(name, email, password);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/account"));
    }

    @AfterEach
    public void tearDown() {
        try {
            if (email != null && password != null) {
                UserApi userApi = new UserApi();
                Response response = userApi.loginUser(email, password);

                if (response.statusCode() == 200) {
                    String token = response.jsonPath()
                            .getString("accessToken");

                    if (token != null && !token.isEmpty()) {
                        userApi.deleteUser(token);
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
