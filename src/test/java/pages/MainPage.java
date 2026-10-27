
package pages;

import io.qameta.allure.Step;
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

    // Ссылка «Личный Кабинет» в шапке сайта
    private final By personalAccountButton = By.xpath(
            "//header//nav//a[p[contains(normalize-space(.), 'Личный Кабинет')]]"
    );

    // Ссылка «Зарегистрироваться» на странице входа
    private final By registrationLink = By.linkText("Зарегистрироваться");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );
    }

    @Step("Нажать кнопку «Войти в аккаунт» на главной странице")
    public void clickLoginButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    @Step("Нажать кнопку «Личный Кабинет»")
    public void clickPersonalAccount() {
        wait.until(
                ExpectedConditions.elementToBeClickable(personalAccountButton)
        ).click();
    }

    @Step("Открыть форму регистрации")
    public void clickRegistrationLink() {
        wait.until(
                ExpectedConditions.elementToBeClickable(registrationLink)
        ).click();
    }
}