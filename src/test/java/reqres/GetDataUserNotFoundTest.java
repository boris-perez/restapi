package reqres;

import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetDataUserNotFoundTest {

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
    public void getDataUserNotFound() throws URISyntaxException, IOException, InterruptedException {
        URIBuilder uri = new URIBuilder()
                .setScheme("https")
                .setHost("reqres.in")
                .setPath("api/users/23");

        System.out.println("Endpoint: " + uri.build());

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri.build())
                .build();

        HttpResponse<String> response = clientHttp.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response: " + response.body());

        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
        assertTrue(response.body().contains("{}"));
    }
}
