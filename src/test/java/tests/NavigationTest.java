
package tests;

import api.UserApi;
import config.Urls;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import pages.AccountPage;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationTest extends BaseTest {

    private WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    @Description("Проверка перехода на страницу входа через личный кабинет")
    public void shouldOpenLoginFromPersonalAccountWhenLoggedOut() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();

        getWait().until(
                ExpectedConditions.urlToBe(Urls.LOGIN_URL)
        );

        assertTrue(
                new LoginPage(driver).isLoginPageDisplayed(),
                "Должна отображаться форма входа"
        );
    }

    @Test
    @Description("Проверка перехода в конструктор по кнопке «Конструктор»")
    public void shouldNavigateToConstructorByConstructorButton() {
        loginAndOpenAccount();

        MainPage mainPage = new MainPage(driver);
        mainPage.clickConstructorButton();

        getWait().until(
                ExpectedConditions.urlToBe(Urls.BASE_URL)
        );

        assertTrue(
                mainPage.isLogoDisplayed(),
                "Должен отображаться логотип"
        );
    }

    @Test
    @Description("Проверка перехода в конструктор по логотипу")
    public void shouldNavigateToConstructorByLogo() {
        loginAndOpenAccount();

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLogo();

        getWait().until(
                ExpectedConditions.urlToBe(Urls.BASE_URL)
        );

        assertTrue(
                mainPage.isLogoDisplayed(),
                "Должен отображаться логотип"
        );
    }

    @Step("Создать пользователя через API, войти и открыть личный кабинет")
    private void loginAndOpenAccount() {
        email = "user" + UUID.randomUUID()
                .toString().replace("-", "")
                .substring(0, 12) + "@example.com";

        password = "Burger2026";

        UserApi userApi = new UserApi();

        Response response = userApi.createUser(
                email, password, "Little Alien"
        );

        assertEquals(
                200,
                response.statusCode(),
                "Пользователь должен создаться через API"
        );

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        getWait().until(
                ExpectedConditions.urlToBe(Urls.BASE_URL)
        );

        assertTrue(
                mainPage.isLogoDisplayed(),
                "После авторизации должен отображаться логотип"
        );

        mainPage.clickPersonalAccount();

        getWait().until(
                ExpectedConditions.urlToBe(Urls.ACCOUNT_URL)
        );

        assertTrue(
                new AccountPage(driver).isLogoutButtonDisplayed(),
                "В личном кабинете должна отображаться кнопка «Выйти»"
        );
    }
}
