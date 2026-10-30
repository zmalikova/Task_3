package tests;

import api.UserApi;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AccountPage;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import config.Urls;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {

    private WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    @Description("Проверка перехода на страницу входа по кнопке «Войти в аккаунт»")
    public void shouldOpenLoginPageFromMainButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        getWait().until(
                ExpectedConditions.urlToBe(Urls.LOGIN_URL)
        );

        LoginPage loginPage = new LoginPage(driver);

        assertTrue(loginPage.isLoginPageDisplayed(),
                "Должно отображаться поле Email");
    }

    @Test
    @Description("Проверка перехода на страницу входа через личный кабинет")
    public void shouldOpenLoginPageFromPersonalAccount() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();

        getWait().until(
                ExpectedConditions.urlToBe(Urls.LOGIN_URL)
        );

        LoginPage loginPage = new LoginPage(driver);

        assertTrue(loginPage.isLoginPageDisplayed(),
                "Должно отображаться поле Email");
    }

    @Test
    @Description("Проверка перехода на страницу входа из формы регистрации")
    public void shouldOpenLoginPageFromRegistrationForm() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        mainPage.clickRegistrationLink();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.clickLoginLink();

        getWait().until(
                ExpectedConditions.urlToBe(Urls.LOGIN_URL)
        );

        LoginPage loginPage = new LoginPage(driver);

        assertTrue(loginPage.isLoginPageDisplayed(),
                "Должно отображаться поле Email");
    }

    @Test
    @Description("Проверка возврата на страницу входа со страницы восстановления пароля")
    public void shouldReturnToLoginFromPasswordRecovery() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickForgotPasswordLink();

        getWait().until(
                ExpectedConditions.urlToBe(Urls.FORGOT_PASSWORD_URL)
        );

        loginPage.clickLoginLink();

        getWait().until(
                ExpectedConditions.urlToBe(Urls.LOGIN_URL)
        );

        assertTrue(loginPage.isLoginPageDisplayed(),
                "Должно отображаться поле Email");
    }

    @Test
    @Description("Проверка успешной авторизации зарегистрированного пользователя")
    public void shouldLoginWithValidCredentials() {
        createTestUser();

        UserApi userApi = new UserApi();
        Response response = userApi.createUser(email, password, "Little Alien");

        assertEquals(200, response.statusCode(),
                "Пользователь должен создаться через API");

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        getWait().until(
                ExpectedConditions.urlToBe(Urls.BASE_URL)
        );

        assertTrue(mainPage.isLogoDisplayed(),
                "После авторизации должен отображаться логотип");
    }

    @Test
    @Description("Проверка выхода авторизованного пользователя из личного кабинета")
    public void shouldLogoutFromPersonalAccount() {
        createTestUser();

        UserApi userApi = new UserApi();
        Response response = userApi.createUser(email, password, "Little Alien");

        assertEquals(200, response.statusCode(),
                "Пользователь должен создаться через API");

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        getWait().until(
                ExpectedConditions.urlToBe(Urls.BASE_URL)
        );

        assertTrue(mainPage.isLogoDisplayed(),
                "После авторизации должен отображаться логотип");

        mainPage.clickPersonalAccount();

        AccountPage accountPage = new AccountPage(driver);
        accountPage.clickLogoutButton();

        getWait().until(
                ExpectedConditions.urlToBe(Urls.LOGIN_URL)
        );

        assertTrue(new LoginPage(driver).isLoginPageDisplayed(),
                "После выхода должно отображаться поле Email");
    }

    @Step("Подготовить данные тестового пользователя")
    private void createTestUser() {
        email = "user" + UUID.randomUUID()
                .toString().replace("-", "")
                .substring(0, 10) + "@example.com";

        password = "Burger2026";
    }

}