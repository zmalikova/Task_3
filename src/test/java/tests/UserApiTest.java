
package tests;

import api.UserApi;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
                "Пользователь должен создаться",
                200,
                createResponse.statusCode()
        );

        assertTrue(
                "В ответе должен быть accessToken",
                createResponse.jsonPath()
                        .getString("accessToken") != null
        );

        String accessToken = createResponse.jsonPath()
                .getString("accessToken");

        assertNotNull("Токен не должен быть null", accessToken);

        Response deleteResponse = userApi.deleteUser(accessToken);

        assertEquals(
                "Пользователь должен удалиться",
                202,
                deleteResponse.statusCode()
        );
    }
}
