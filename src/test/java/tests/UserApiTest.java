
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

        String accessToken = null;

        try {
            Response createResponse = userApi.createUser(
                    email, password, name
            );

            assertEquals(
                    200,
                    createResponse.statusCode(),
                    "Пользователь должен создаться"
            );

            accessToken = createResponse.jsonPath()
                    .getString("accessToken");

            assertNotNull(
                    accessToken,
                    "В ответе должен быть accessToken"
            );

            assertTrue(
                    !accessToken.isEmpty(),
                    "Токен не должен быть пустым"
            );
        } finally {
            if (accessToken != null && !accessToken.isEmpty()) {
                Response deleteResponse = userApi.deleteUser(accessToken);

                assertEquals(
                        202,
                        deleteResponse.statusCode(),
                        "Пользователь должен удалиться"
                );
            }
        }
    }

}
