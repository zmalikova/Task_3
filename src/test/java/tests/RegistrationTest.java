
package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.MainPage;
import pages.RegistrationPage;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class RegistrationTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa-stellarburgers.education-services.ru/");
    }

    @Test
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
                "После успешной регистрации должна открыться страница входа",
                driver.getCurrentUrl().contains("/login")
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
                "Должна отображаться ошибка для пароля короче 6 символов",
                registrationPage.getErrorMessage().length() > 0
        );
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
