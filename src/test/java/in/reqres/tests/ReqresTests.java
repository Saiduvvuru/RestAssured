package in.reqres.tests;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.Assertions.*;

public class ReqresTests extends CommonMethods {

 //   private static final Logger LOG = Logger.getLogger(ReqresTests.class);

    private static final int EXPECTED_SUCCESS_STATUS_CODE = 200;
    private static final int EXPECTED_CREATED_STATUS_CODE = 201;


    @Test
    public void GET_ListUsers() throws IOException {
        addDetailsToReport("SubSection1", "1", "GETting the list of all Users");
        test.assignCategory("REQRES", "Regression", "Total");

        requestSpecification.basePath("/users");
        Response response = performGETOperation(requestSpecification);
      /*  Response response = RestAssured
                .given()
                .spec(requestSpecification)
                .when()
                .get(PropertiesUtility.BASE_URI + "/users")
                .then().log().all()
                //  .assertThat().statusCode(200)
                .extract().response(); */

        verifyStatusCode(response, EXPECTED_SUCCESS_STATUS_CODE, response.statusCode());
    }


    @Test
    public void POST_CreateUsers() throws IOException {
        addDetailsToReport("subSection2", "1", "Creating the new user - POST ");
        test.assignCategory("REQRES", "Regression", "Total", "POST");

        requestSpecification.basePath("/users");
        String payload = getFileContent("src/test/resources/TestData/NewUser.json");
        Response response = performPOSTOperation(requestSpecification, payload);

       /* Response response = RestAssured.given()
                .spec(requestSpecification)
                .body(payload)
                .when()
                .post(PropertiesUtility.BASE_URI + "/users")
                .then().log().all()
                .extract().response();
    */
        verifyStatusCode(response, EXPECTED_CREATED_STATUS_CODE, response.statusCode());

        String jsonResponse = response.toString();
        LOG.info("Response is " + jsonResponse);

        JsonPath js = response.jsonPath();
        //Storing the response values in a variable., So, this can be either used
        // for assertions  or to forward it to other tests ( as parameter values or in other way)
        String lname = js.get("name");
        String job = js.get("job");
        System.out.println("Name is :" + lname + "        Job is :" + job);

        /* Other way of extracting the response ( String) values is
          String jsonResponse = response.toString();
              System.out.println(jsonResponse);
         JsonPath js = new JsonPath(jsonResponse)
         String name = js.getString("name");
        String job = js.getString("job");
          assertThat(lname).isNotNull();
          assertThat(job).isNotNull();
        */

    }


    @Test
    public void PUT_UpdateUsers() throws IOException {
        addDetailsToReport("SubSection2", "2", "Updating the new user - PUT ");
        test.assignCategory("REQRES", "Regression", "Total", "PUT");

        requestSpecification.basePath("/users/2");
        String payload = getFileContent("src/test/resources/TestData/UpdateUser.json");

        Response response = performPUTOperation(requestSpecification, payload);

    /*  Response response = RestAssured.given()
                .spec(requestSpecification)
                .body(payload)
                .when()
                .put(PropertiesUtility.BASE_URI + "/users/2")
                .then().log().all()
                .extract().response();  */

        verifyStatusCode(response, EXPECTED_SUCCESS_STATUS_CODE, response.statusCode());
    }


    @Ignore
    public void connectToDatabase() throws Exception {

        //NOT USING DBUtility  - BUT KEPT AS PLACEHOLDER
        //    addDetailsToReport("SubSection2", "2", "Updating the new user - PUT ");
        //   test.assignCategory("REQRES", "Regression", "Total", "Database");

        //    int actual_count = 0;
     /*   try {
            DBUtility.connectDB(DBUserName, DBPassword);
            String query = "Select count(*) from channel where id =?";
            ResultSet rs = DBUtility.executeQuery(query, NewChannelID);
            while (rs.next()) {
                actual_count = rs.getInt("COUNT(*)");
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "Error with Result Set ....." + e.getMessage());
        } finally {
            DBUtility.disconnect();
        }
        int expected_count = 0;
        if (expected_count == actual_count) {
            test.log(Status.PASS, MarkupHelper.createLabel("No Record (Count 0) in DB as expected", ExtentColor.GREEN));
        } else {
            test.log(Status.FAIL, MarkupHelper.createLabel("Error - DB Count doesn't match", ExtentColor.RED));
            Assert.fail();
        }  */
    }


}


