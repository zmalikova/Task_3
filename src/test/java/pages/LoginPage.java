
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

    private final By emailInput = By.xpath(
            "//fieldset[1]//input"
    );

    private final By passwordInput = By.xpath(
            "//input[@type='password']"
    );

    private final By loginButton = By.xpath(
            "//button[contains(normalize-space(.), 'Войти')]"
    );

    @Step("Ввести электронную почту")
    public void enterEmail(String email) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailInput)
        ).sendKeys(email);
    }

    @Step("Ввести пароль")
    public void enterPassword(String password) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordInput)
        ).sendKeys(password);
    }

    @Step("Нажать кнопку «Войти»")
    public void clickLoginButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
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
                "//a[text()='Войти']"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(loginLink)
        ).click();
    }

    @Step("Авторизоваться под пользователем")
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();

        wait.until(
                ExpectedConditions.not(
                        ExpectedConditions.urlContains("/login")
                )
        );
    }

    @Step("Проверить отображение страницы входа")
    public boolean isLoginPageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailInput)
        ).isDisplayed();
    }
}
