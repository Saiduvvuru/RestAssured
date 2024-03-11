package in.reqres.tests;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import in.reqres.utils.ReportsUtility;
import in.reqres.utils.PropertiesUtility;


import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;


public class BaseTest {

    private static final Logger LOG = Logger.getLogger(BaseTest.class);

    public static ReportsUtility reportsUtility = new ReportsUtility();
    public static PropertiesUtility props;

    public static ExtentReports report;
    public static ExtentTest test;

    public static ExtentTest subSection;


    @BeforeSuite
    public void setEnvironment() throws Exception {
        PropertiesUtility.getProperties();
        //    System.out.println("I am in SET ENVIRONMENT METHOD - Loaded Properties :" + PropertiesUtility.BASE_URI);
        //   System.out.println(PropertiesUtility.ENVIRONMENT);
        report = reportsUtility.setReport();


    }

    @AfterSuite
    public void endSuite() {
        //   System.out.println("Test Results available @: " + reportsUtility.resultsFolder);
    }

    @BeforeMethod()
    public void createTest(final Method m) {
        //   test = report.createTest(m.getName());

    }

    @AfterMethod
    public void endTest() throws IOException {
        reportsUtility.writeToReport();

    }

    @AfterTest
    public void tearDown() {
        //   report.flush();
    }

    protected void verifyStatusCode(Response response, int expectedStatusCode, int actualStatusCode) {
        if (expectedStatusCode == actualStatusCode) {
            test.log(Status.PASS, MarkupHelper.createLabel("RESPONSE STATUS:" + response.getStatusLine(), ExtentColor.GREEN));
            test.log(Status.PASS, MarkupHelper.createCodeBlock(response.asPrettyString()));
        } else {
            test.log(Status.FAIL, MarkupHelper.createLabel("RESPONSE STATUS:" + response.getStatusLine(), ExtentColor.RED));
            test.log(Status.FAIL, "Expected Status code: " + expectedStatusCode + ", Actual status code " + actualStatusCode);
        }
    }


  /*  public RequestSpecification decodeKeyStore(String certPath, String password) throws Exception {
        KeyStore keyStore = loadKeyStore(certPath, password.toCharArray(), KeyStore.getDefaultType());
        SSLSocketFactory clientAuthFactory = new SSLSocketFactory(keyStore, password);
        SSLConfig sslConfig = RestAssuredConfig.config().getSSLConfig().with().sslSocketFactory(clientAuthFactory);
        RestAssuredConfig config = RestAssuredConfig.config().sslConfig(sslConfig);
        return RestAssured.given().config(config);
    }

    public KeyStore loadKeyStore(String path, char[] password, String storeType) {
        KeyStore keyStore;
        try {
            keyStore = KeyStore.getInstance(storeType);
            keyStore.load(new FileInputStream(path), password);
        } catch (Exception e) {
            throw new RuntimeException("Error while extracting the keystore", e);
        }
        return keyStore;
    }

    // USAGE OF the decodeKeyStore() is as follows:
    //Response response = RestAssured.given().spec(decodeKeyStore(getCertPath(), getPassword())).log().all().........
    // You get the CertPath, NrtPath, Base_URI , Password from Properties file
*/

}






