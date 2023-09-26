package com.luminousonion;

import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ScanControllerTest {

    @Test
    public void testHelloEndpoint() {
        String projectVersion = ConfigProvider.getConfig().getValue("quarkus.application.version", String.class);

        given()
            .when().get("/request")
            .then()
            .statusCode(200)
            .body(is(projectVersion));
    }
}
