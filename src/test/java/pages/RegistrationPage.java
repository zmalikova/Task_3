
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

    // Ссылка «Войти» в форме регистрации
    private final By loginLink = By.xpath(
            "//a[contains(normalize-space(.), 'Войти')]"
    );

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );
    }

    @Step("Нажать ссылку «Войти» в форме регистрации")
    public void clickLoginLink() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginLink)
        ).click();
    }
}
