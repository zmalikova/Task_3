
package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Ссылка «Восстановить пароль» на странице входа
    private final By forgotPasswordLink = By.xpath(
            "//a[contains(., 'Восстановить пароль')]"
    );

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );
    }

    @Step("Нажать ссылку «Восстановить пароль»")
    public void clickForgotPasswordLink() {
        wait.until(
                ExpectedConditions.elementToBeClickable(forgotPasswordLink)
        ).click();
    }

    @Step("Вернуться ко входу из формы восстановления пароля")
    public void clickLoginLink() {
        By loginLink = By.xpath(
                "//a[contains(normalize-space(.), 'Войти')]"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(loginLink)
        ).click();
    }

}
