package reqres;

import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.JsonSchemaValidator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetListUserPage2Test {

    HttpClient clientHttp;

    @BeforeEach
    public void setup() {
        System.out.println("Inicio de la Configuracion de la Prueba REST API");
        clientHttp = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Test
    public void getListUserPage2() throws URISyntaxException, IOException, InterruptedException {
        URIBuilder uri = new URIBuilder()
                .setScheme("https")
                .setHost("reqres.in")
                .setPath("api/users")
                .addParameter("page", "2");

        System.out.println("Endpoint: " + uri.build());

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri.build())
                .build();

        HttpResponse<String> response = clientHttp.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response: " + response.body());

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertTrue(response.body().contains("\"page\":2"));

        String validationSchema = JsonSchemaValidator.validateJsonAgainstSchema(response.body(), "UserSchema.json");

        System.out.println("Resultado de la validacion del Schema: " + validationSchema);

        assertEquals("", validationSchema, "Resultado de la validacion del schema");
    }
}
