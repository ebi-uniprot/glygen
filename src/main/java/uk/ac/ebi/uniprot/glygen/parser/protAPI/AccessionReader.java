package uk.ac.ebi.uniprot.glygen.parser.protAPI;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;

public class AccessionReader {
    private String entryEndpoint = "https://www.ebi.ac.uk/proteins/api/proteins/";

    public JsonObject getEntry(String entry) {

        Client client = ClientBuilder.newClient();
        WebTarget webTarget =
        client.register(JsonProcessingFeature.class).target(this.entryEndpoint);
        Invocation.Builder builder = webTarget.path(entry).request(MediaType.APPLICATION_JSON_TYPE);
        JsonObject jsonEntry = builder.get(JsonObject.class);
        return (jsonEntry);
    }
}
