
package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import config.Urls;

import java.time.Duration;

public class AccountPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By logoutButton = By.xpath("//nav/ul/li[3]/button");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );
    }

    @Step("Нажать кнопку «Выйти» в личном кабинете")
    public void clickLogoutButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(logoutButton)
        ).click();

        wait.until(
                ExpectedConditions.urlToBe(Urls.LOGIN_URL)
        );
    }

    @Step("Проверить отображение кнопки «Выйти»")
    public boolean isLogoutButtonDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(logoutButton)
        ).isDisplayed();
    }
}
