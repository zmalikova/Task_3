
package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.MainPage;
import pages.RegistrationPage;


import java.util.UUID;


public class RegistrationTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa-stellarburgers.education-services.ru/");
    }

    @Test
    @io.qameta.allure.Description("Успешная регистрация нового пользователя")
    public void shouldRegisterNewUser() {
        String email = "user" + UUID.randomUUID()
                .toString().replace("-", "").substring(0, 10)
                + "@example.com";

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        mainPage.clickRegistrationLink();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.enterName("Little Alien");
        registrationPage.enterEmail(email);
        registrationPage.enterPassword("Burger2026");
        registrationPage.clickRegisterButton();

        assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "После успешной регистрации должна открыться страница входа"
                );
    }

    @Test
    public void shouldShowErrorForShortPassword() {
        String email = "user" + UUID.randomUUID()
                .toString().replace("-", "").substring(0, 10)
                + "@example.com";

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        mainPage.clickRegistrationLink();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.enterName("Little Alien");
        registrationPage.enterEmail(email);
        registrationPage.enterPassword("12345");
        registrationPage.clickRegisterButton();

        assertTrue(
                registrationPage.getErrorMessage().length() > 0,
                "Должна отображаться ошибка для пароля короче 6 символов"
                );
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
