
package helpers;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;

import java.time.Duration;

public class UserHelper {

    private final WebDriver driver;

    public UserHelper(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Зарегистрировать нового пользователя через интерфейс")
    public void registerUser(String name, String email, String password) {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        mainPage.clickRegistrationLink();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.enterName(name);
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.clickRegisterButton();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/login"));
    }
    @Step("Зарегистрировать пользователя и войти в аккаунт")
    public void registerAndLogin(String name, String email, String password) {
        registerUser(name, email, password);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);
    }
}
