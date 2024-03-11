package in.reqres.tests;


import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import in.reqres.utils.PropertiesUtility;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;


public class CommonMethods extends BaseTest {

    //    public static String propertiesFile = "application.properties";
    private static final Logger LOG = Logger.getLogger(CommonMethods.class);
    RequestSpecification requestSpecification;

    @BeforeMethod
    public void specSetUp() {

        // work on this - this should return request specification.
        Header contentHeader = new Header("Content-Type", "application/json");
        requestSpecification = RestAssured.given().log().all().header(contentHeader);
        requestSpecification.baseUri(PropertiesUtility.BASE_URI);


    }


    protected Response performGETOperation(RequestSpecification requestSpecification) {

        QueryableRequestSpecification qrs = SpecificationQuerier.query(requestSpecification);
        test.log(Status.INFO, "Endpoint is : GET  " + qrs.getBaseUri() + qrs.getBasePath());

        Response response = RestAssured.given()
                .spec(requestSpecification)
                .when()
                .get()
                .then().log().all()
                .extract().response();
        return response;
    }

    protected Response performPUTOperation(RequestSpecification requestSpecification, String payload) {
        Response response = RestAssured.given()
                .spec(requestSpecification)
                .body(payload)
                .when()
                .put()
                .then().log().all()
                .extract().response();
        return response;

    }

    protected Response performPOSTOperation(RequestSpecification requestSpecification, String payload) {
        Response response = RestAssured.given()
                .spec(requestSpecification)
                .body(payload)
                .when()
                .post()
                .then().log().all()
                .extract().response();
        return response;

    }

    protected String getFileContent(String filePath) throws IOException {

        File payloadInFile = new File(filePath);
        String content = FileUtils.readFileToString(payloadInFile, "UTF-8");
        test.log(Status.INFO, MarkupHelper.createLabel("Request Payload is :", ExtentColor.BLUE));
        test.log(Status.INFO, content);
        return content;
        /*      Another way of reading json file
        byte[] payloadfInFile = Files.readAllBytes(Paths.get(filePath));
        String content = new String(payloadfInFile);
       */


    }

    public void addDetailsToReport(String category, String testNumber, String testNameDescription) {
        //Service Name = Under Project, there could be more than 1 services like Rates, deal and Quote
        // testName is the description of the test
        if (testNumber.equals("1")) {
            subSection = report.createTest(category);
        }
        test = subSection.createNode(testNameDescription);
    }
}





