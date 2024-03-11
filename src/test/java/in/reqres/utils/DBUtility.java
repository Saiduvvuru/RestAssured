package in.reqres.utils;

import com.aventstack.extentreports.Status;
import in.reqres.tests.BaseTest;
import org.testng.Assert;

import java.sql.*;

public class DBUtility extends BaseTest {

    public static String oracleDriver = "oracle.jdbc.driver.OracleDriver";
    public static String connectionString = "jdbc:oracle:thin@(DESCRIPTION=................";
    public static Connection connection = null;

    public static ResultSet resultSet = null;

    //Establish DB Connection
    public static void connectDB(String DBUserName, String DBPassword) {
        try {
            //Load the ORacle JDBC Driver
            Class.forName(oracleDriver);
            //create a connection to DB
            connection = DriverManager.getConnection(connectionString, DBUserName, DBPassword);

        } catch (Exception e) {
            test.log(Status.FAIL, "Database Conneection Failed ......." + e.getMessage());
            test.log(Status.SKIP, "Terminating the test");
            Assert.fail("Failed test and so exiting test");
        }
    }

    //Execute an SQL Query and return a ResultSet Object
    public static ResultSet executeQuery(String strQuery, int intChannelID) throws SQLException {
        try {
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(strQuery);
                ps.setInt(1, intChannelID);
                resultSet = ps.executeQuery();
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "Query Error ......." + e.getMessage());
            test.log(Status.SKIP, "Terminating the test");
            Assert.fail("Query Error, exiting the test");
        }
        return resultSet;
    }

    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }


}
