
package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ConstructorPage;


public class ConstructorTest {

    private WebDriver driver;
    private ConstructorPage constructorPage;

    @BeforeEach
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
                constructorPage.getActiveTabText().contains("Булки"),
                "Должен быть выбран раздел «Булки»"
                );
    }

    @Test
    public void shouldOpenSaucesSection() {
        constructorPage.clickSaucesTab();

        assertTrue(
                constructorPage.getActiveTabText().contains("Соусы"),
                "Должен быть выбран раздел «Соусы»"
                );
    }

    @Test
    public void shouldOpenFillingsSection() {
        constructorPage.clickFillingsTab();

        assertTrue(
                constructorPage.getActiveTabText().contains("Начинки"),
                "Должен быть выбран раздел «Начинки»"
                );
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
