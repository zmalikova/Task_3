package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApi {

    private static final String BASE_URL =
            "https://qa-stellarburgers.education-services.ru/";

    @Step("Создать пользователя через API")
    public Response createUser(String email, String password, String name) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body("{"
                        + "\"email\":\"" + email + "\","
                        + "\"password\":\"" + password + "\","
                        + "\"name\":\"" + name + "\""
                        + "}")
                .when()
                .post("/api/auth/register");
    }

    @Step("Авторизоваться через API")
    public Response loginUser(String email, String password) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body("{"
                        + "\"email\":\"" + email + "\","
                        + "\"password\":\"" + password + "\""
                        + "}")
                .when()
                .post("/api/auth/login");
    }

    @Step("Удалить пользователя через API")
    public Response deleteUser(String accessToken) {
        return given()
                .baseUri(BASE_URL)
                .header("Authorization", accessToken)
                .when()
                .delete("/api/auth/user");
    }
}
