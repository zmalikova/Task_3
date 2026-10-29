
package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import pages.RegistrationPage;
import pages.LoginPage;
import api.UserApi;
import io.restassured.response.Response;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.PageLoadStrategy;

import java.time.Duration;


public class LoginTest {

    private WebDriver driver;
    private String email;
    private String password;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(30));
        driver.get("https://qa-stellarburgers.education-services.ru/");
    }

    @Test
    public void shouldOpenLoginPageFromMainButton() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickLoginButton();

        assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "После нажатия должна открыться страница входа"
                );
    }

    @Test
    public void shouldOpenLoginPageFromPersonalAccount() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickPersonalAccount();

        assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "Должна открыться страница входа"
                );
    }


    @Test
    public void shouldOpenLoginPageFromRegistrationForm() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        mainPage.clickRegistrationLink();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.clickLoginLink();

        assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "После нажатия «Войти» должна открыться страница входа"
                );
    }


    @Test
    public void shouldReturnToLoginFromPasswordRecovery() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(15)
        );

        wait.until(
                ExpectedConditions.urlContains("/login")
        );

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickForgotPasswordLink();

        wait.until(
                ExpectedConditions.urlContains("/forgot-password")
        );

        loginPage.clickLoginLink();

        wait.until(
                ExpectedConditions.urlContains("/login")
        );

        assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "После нажатия «Войти» должна открыться страница входа"
        );
    }

    @Test
    public void shouldLoginWithValidCredentials() {
        email = "user" + java.util.UUID.randomUUID()
                .toString().replace("-", "").substring(0, 10)
                + "@example.com";
        password = "Burger2026";

        String name = "Little Alien";
        UserApi userApi = new UserApi();

        // Создаём тестового пользователя через API
        Response createResponse =
                userApi.createUser(email, password, name);

        assertTrue(
                createResponse.statusCode() == 200,
                "Тестовый пользователь должен создаться через API"
        );

        // Переходим ко входу и авторизуемся
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        // Проверяем успешный вход
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(
                        "https://qa-stellarburgers.education-services.ru/"
                ));

        assertTrue(
                driver.getCurrentUrl().equals(
                        "https://qa-stellarburgers.education-services.ru/"
                ),
                "После входа должна открыться главная страница"
        );
    }

    @Test
    public void shouldLogoutFromPersonalAccount() {
        email = "user" + java.util.UUID.randomUUID()
                .toString().replace("-", "").substring(0, 10)
                + "@example.com";

        password = "Burger2026";
        String name = "Little Alien";

        helpers.UserHelper userHelper = new helpers.UserHelper(driver);
        userHelper.registerUser(name, email, password);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();

        new org.openqa.selenium.support.ui.WebDriverWait(
                driver, java.time.Duration.ofSeconds(10)
        ).until(
                org.openqa.selenium.support.ui.ExpectedConditions.urlToBe(
                        "https://qa-stellarburgers.education-services.ru/"
                )
        );

        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();

        pages.AccountPage accountPage = new pages.AccountPage(driver);
        accountPage.clickLogoutButton();

        new org.openqa.selenium.support.ui.WebDriverWait(
                driver, java.time.Duration.ofSeconds(10)
        ).until(
                org.openqa.selenium.support.ui.ExpectedConditions.or(
                        org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/login"),
                        org.openqa.selenium.support.ui.ExpectedConditions.urlToBe(
                                "https://qa-stellarburgers.education-services.ru/"
                        )
                )
        );

        assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "После выхода должна открыться страница входа"
                );
    }


    @AfterEach
    public void tearDown() {
        try {
            if (email != null && password != null) {
                try {
                    UserApi userApi = new UserApi();

                    Response loginResponse = userApi.loginUser(email, password);

                    if (loginResponse.statusCode() == 200) {
                        String accessToken = loginResponse.jsonPath()
                                .getString("accessToken");

                        if (accessToken != null && !accessToken.isEmpty()) {
                            Response deleteResponse =
                                    userApi.deleteUser(accessToken);

                            System.out.println(
                                    "Результат удаления пользователя: "
                                            + deleteResponse.statusCode()
                            );
                        }
                    }
                } catch (Exception e) {
                    System.out.println(
                            "Не удалось удалить тестового пользователя через API: "
                                    + e.getMessage()
                    );
                }
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

}
