
package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ConstructorPage;

import static org.junit.Assert.assertTrue;

public class ConstructorTest {

    private WebDriver driver;
    private ConstructorPage constructorPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(
                "https://qa-stellarburgers.education-services.ru/"
        );

        constructorPage = new ConstructorPage(driver);
    }

    @Test
    public void shouldOpenBunsSection() {
        constructorPage.clickBunsTab();

        assertTrue(
                "Должен быть выбран раздел «Булки»",
                constructorPage.getActiveTabText().contains("Булки")
        );
    }

    @Test
    public void shouldOpenSaucesSection() {
        constructorPage.clickSaucesTab();

        assertTrue(
                "Должен быть выбран раздел «Соусы»",
                constructorPage.getActiveTabText().contains("Соусы")
        );
    }

    @Test
    public void shouldOpenFillingsSection() {
        constructorPage.clickFillingsTab();

        assertTrue(
                "Должен быть выбран раздел «Начинки»",
                constructorPage.getActiveTabText().contains("Начинки")
        );
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
