
package tests;

import config.Urls;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest extends BaseTest {

    private WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    @Description("Проверка успешной регистрации нового пользователя")
    public void shouldRegisterNewUser() {
        email = "user" + UUID.randomUUID()
                .toString().replace("-", "").substring(0, 10)
                + "@example.com";

        password = "Burger2026";

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        mainPage.clickRegistrationLink();

        RegistrationPage registrationPage = new RegistrationPage(driver);

        registrationPage.enterName("Little Alien");
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.clickRegisterButton();

        getWait().until(
                ExpectedConditions.urlToBe(Urls.LOGIN_URL)
        );

        assertTrue(
                new LoginPage(driver).isLoginPageDisplayed(),
                "После регистрации должна отображаться форма входа"
        );
    }

    @Test
    @Description("Проверка ошибки при регистрации с паролем короче шести символов")
    public void shouldShowErrorForShortPassword() {
        String invalidEmail = "user" + UUID.randomUUID()
                .toString().replace("-", "").substring(0, 10)
                + "@example.com";

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        mainPage.clickRegistrationLink();

        RegistrationPage registrationPage = new RegistrationPage(driver);

        registrationPage.enterName("Little Alien");
        registrationPage.enterEmail(invalidEmail);
        registrationPage.enterPassword("12345");
        registrationPage.clickRegisterButton();

        assertEquals(
                "Некорректный пароль",
                registrationPage.getErrorMessage(),
                "Должна отображаться ошибка некорректного пароля"
        );
    }
}
