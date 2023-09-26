package com.luminousonion.cucumber.definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringbootCucumberTestDefinitions {

    private final static String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;

    private ValidatableResponse validatableResponse;

    private void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    protected RequestSpecification requestSpecification() {
        configureRestAssured();
        return given();
    }

    @Given("I send a request to the URL {string} to get product details")
    public void iSendARequest(String endpoint) throws Throwable {
        validatableResponse = requestSpecification().contentType(ContentType.JSON)
                .when().get(endpoint).then();
        System.out.println("RESPONSE :"+validatableResponse.extract().asString());
    }

    @Then("the response will return status {int} and id {int} and names {string}")
    public void extractResponse(int status, int id, String projectName) {
        validatableResponse.assertThat().statusCode(equalTo(status))
                .body("id",hasItem(id))
                .body(containsString(projectName));

    }

}
