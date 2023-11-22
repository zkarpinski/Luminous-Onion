package com.luminousonion;

import com.luminousonion.dto.ScanRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ScanControllerTest {

    @Test
    public void testRequestEndpoint() {
        String projectVersion = ConfigProvider.getConfig().getValue("quarkus.application.version", String.class);

        given()
            .when().get("/request")
            .then()
            .statusCode(200)
            .body(is(projectVersion));
    }

    @Test
    public void testPostRequestEndpoint() {
        String projectVersion = ConfigProvider.getConfig().getValue("quarkus.application.version", String.class);

        ScanRequest scanRequest = new ScanRequest();
        scanRequest.image = "test";
        scanRequest.tool = "trivy";

        given()
            .when().body(scanRequest).contentType(ContentType.JSON)
            .post("/request")
            .then()
            .statusCode(200)
            .body(is("OS not supported"));
    }
}
