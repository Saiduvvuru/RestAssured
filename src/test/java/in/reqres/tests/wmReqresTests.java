package in.reqres.tests;


import com.aventstack.extentreports.Status;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;


import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
//import static org.assertj.core.api.Assertions.*;

public class wmReqresTests extends CommonMethods {

    private static final Logger LOG = Logger.getLogger(wmReqresTests.class);

    private static final int EXPECTED_SUCCESS_STATUS_CODE = 200;
    private static final int EXPECTED_CREATED_STATUS_CODE = 201;

    WireMockServer wiremockServer;

    RequestSpecification requestSpecification;


    @Test
    public void GET_listUsersWithWiremock() throws IOException {
        addDetailsToReport("WiremockTests", "1", "GET the list of all Users");
        test.assignCategory("REQRES", "Regression", "Total");

        wiremockServer = new WireMockServer();
        configureFor("localhost", 8080);
        wiremockServer.start();
        //Providing the targetURL to tell where to record the data from
        //   wiremockServer.startRecording("https://reqres.in/api/users");

        Header contentHeader = new Header("Content-Type", "application/json");
        requestSpecification = RestAssured.given().log().all().header(contentHeader);
        //Providing localhost and port number , so request goes to wiremock server.
        requestSpecification.baseUri("http://localhost:" + wiremockServer.port());

        QueryableRequestSpecification qrs = SpecificationQuerier.query(requestSpecification);
        test.log(Status.INFO, "Endpoint is : GET  " + qrs.getBaseUri() + qrs.getBasePath());

        Response response = RestAssured.given().spec(requestSpecification)
                .when().get()
                .then().log().all()
                .extract().response();

        // Verification is as usual
        verifyStatusCode(response, 200, response.statusCode());

        //  wiremockServer.stopRecording();
        wiremockServer.stop();


    }
}



