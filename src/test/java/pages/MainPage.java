
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Кнопка «Войти в аккаунт» на главной странице
    private final By loginButton = By.xpath(
            "//button[contains(normalize-space(.), 'Войти в аккаунт')]"
    );

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );
    }

    public void clickLoginButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }
}
