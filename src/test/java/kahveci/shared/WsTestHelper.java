package kahveci.shared;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URI;
import java.net.URL;

public final class WsTestHelper {

    public static WebTarget initClient(URL base, String localPath) {
        Client client = ClientBuilder.newClient();
        return client.target(URI.create(base.toString()) + localPath);
    }

    private WsTestHelper() {
    }
}
