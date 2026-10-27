
package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.MainPage;
import pages.RegistrationPage;

import static org.junit.Assert.assertTrue;

public class LoginTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa-stellarburgers.education-services.ru/");
    }

    @Test
    public void shouldOpenLoginPageFromMainButton() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickLoginButton();

        assertTrue(
                "После нажатия должна открыться страница входа",
                driver.getCurrentUrl().contains("/login")
        );
    }

    @Test
    public void shouldOpenLoginPageFromPersonalAccount() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickPersonalAccount();

        assertTrue(
                "Должна открыться страница входа",
                driver.getCurrentUrl().contains("/login")
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
                "После нажатия «Войти» должна открыться страница входа",
                driver.getCurrentUrl().contains("/login")
        );
    }



    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
