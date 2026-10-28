package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.MainPage;

import static org.junit.Assert.assertTrue;

public class NavigationTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa-stellarburgers.education-services.ru/");
    }

    @Test
    public void shouldNavigateToConstructorFromPersonalAccount() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickPersonalAccount();

        assertTrue(
                "Должна открыться страница входа",
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