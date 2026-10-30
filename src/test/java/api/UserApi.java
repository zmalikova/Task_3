
package api;

import config.Urls;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApi {

    @Step("Создать пользователя через API")
    public Response createUser(String email, String password, String name) {
        UserRequest user = new UserRequest(email, password, name);

        return given()
                .baseUri(Urls.BASE_URL)
                .contentType("application/json")
                .body(user)
                .when()
                .post(Urls.API_REGISTER);
    }

    @Step("Авторизоваться через API")
    public Response loginUser(String email, String password) {
        LoginRequest user = new LoginRequest(email, password);

        return given()
                .baseUri(Urls.BASE_URL)
                .contentType("application/json")
                .body(user)
                .when()
                .post(Urls.API_LOGIN);
    }

    @Step("Удалить пользователя через API")
    public Response deleteUser(String accessToken) {
        return given()
                .baseUri(Urls.BASE_URL)
                .header("Authorization", accessToken)
                .when()
                .delete(Urls.API_USER);
    }
}
