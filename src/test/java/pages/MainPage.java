
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

    // Ссылка «Зарегистрироваться»
    private final By registrationLink =
            By.linkText("Зарегистрироваться");

    // Оверлей модального окна
    private final By modalOverlay =
            By.cssSelector("[class*='Modal_modal_overlay']");

    // Логотип Stellar Burgers
    private final By logo = By.xpath(
            "//header//a[.//*[name()='svg']]"
    );

    // Кнопка «Конструктор»
    private final By constructorButton =
            By.xpath("//header//nav//ul/li[1]/a");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(15)
        );
    }

    // Ждём, пока модальное окно перестанет перекрывать страницу
    private void waitForModalToDisappear() {
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        modalOverlay
                )
        );
    }

//    @Step("Нажать кнопку «Войти в аккаунт» на главной странице")
//    public void clickLoginButton() {
//        waitForModalToDisappear();
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(loginButton)
//        ).click();
//    }

    @Step("Нажать кнопку «Войти в аккаунт» на главной странице")
    public void clickLoginButton() {
        wait.until(ExpectedConditions.presenceOfElementLocated(loginButton));

        wait.until(ExpectedConditions.elementToBeClickable(loginButton))
                .click();
    }


    @Step("Нажать кнопку «Личный Кабинет»")
    public void clickPersonalAccount() {
        waitForModalToDisappear();

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        personalAccountButton
                )
        ).click();
    }

    @Step("Открыть форму регистрации")
    public void clickRegistrationLink() {
        waitForModalToDisappear();

        wait.until(
                ExpectedConditions.elementToBeClickable(registrationLink)
        ).click();
    }

    @Step("Нажать на логотип Stellar Burgers")
    public void clickLogo() {
        wait.until(
                ExpectedConditions.elementToBeClickable(logo)
        ).click();
    }

    @Step("Нажать кнопку «Конструктор»")
    public void clickConstructorButton() {
        waitForModalToDisappear();

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        constructorButton
                )
        ).click();
    }
}
