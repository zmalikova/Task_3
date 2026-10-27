
package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Поле имени
    private final By nameInput = By.xpath(
            "//label[contains(., 'Имя')]/following-sibling::input"
    );

    // Поле электронной почты
    private final By emailInput = By.xpath(
            "//label[contains(., 'Email')]/following-sibling::input"
    );

    // Поле пароля
    private final By passwordInput = By.xpath(
            "//input[@type='password']"
    );

    // Кнопка регистрации
    private final By registerButton = By.xpath(
            "//button[contains(., 'Зарегистрироваться')]"
    );

    // Ссылка «Войти»
    private final By loginLink = By.xpath(
            "//a[contains(normalize-space(.), 'Войти')]"
    );

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );
    }

    @Step("Ввести имя")
    public void enterName(String name) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(nameInput)
        ).sendKeys(name);
    }

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

    @Step("Нажать кнопку «Зарегистрироваться»")
    public void clickRegisterButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(registerButton)
        ).click();

        wait.until(
                ExpectedConditions.or(
                        ExpectedConditions.urlContains("/login"),
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(@class, 'input__error')]")
                        )
                )
        );
    }

    @Step("Перейти ко входу из формы регистрации")
    public void clickLoginLink() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginLink)
        ).click();
    }

    @Step("Получить текст ошибки регистрации")
    public String getErrorMessage() {
        By errorMessage = By.xpath(
                "//*[contains(@class, 'input__error') or "
                        + "contains(@class, 'error') or "
                        + "contains(@class, 'Error')]"
        );

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).getText();
    }
}
