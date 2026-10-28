
package tests;

import api.UserApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserApiTest {

    @Test
    public void shouldCreateAndDeleteUser() {
        UserApi userApi = new UserApi();

        String email = "user" + UUID.randomUUID()
                .toString().replace("-", "")
                + "@example.com";
        String password = "Burger2026";
        String name = "Little Alien";

        Response createResponse = userApi.createUser(
                email, password, name
        );

        assertEquals(
                200,
                createResponse.statusCode(),
                "Пользователь должен создаться"
        );

        assertTrue(
                createResponse.jsonPath()
                        .getString("accessToken") != null,
                "В ответе должен быть accessToken"
        );

        String accessToken = createResponse.jsonPath()
                .getString("accessToken");

        assertNotNull("Токен не должен быть null", accessToken);

        Response deleteResponse = userApi.deleteUser(accessToken);

        assertEquals(
                202,
                deleteResponse.statusCode(),
                "Пользователь должен удалиться"
        );
    }
}
