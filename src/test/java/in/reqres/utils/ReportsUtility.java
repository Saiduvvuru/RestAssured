package in.reqres.utils;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportsUtility {

    public static ExtentReports report;
    // public static ExtentTest test;
    public static String resultsFolder = "Results/";

    public ExtentReports setReport() {
        //  resultsFolder = "//Results//" + getCurrentDateAndTime();
        System.out.println(resultsFolder);
        File file = new File(resultsFolder);
        file.mkdirs();
        setSparkReporter();
        return report;


    }

    public static ExtentReports setSparkReporter() {
        //  ExtentSparkReporter sparkReporter = new ExtentSparkReporter(resultsFolder + getCurrentDateAndTime() + "/TestReport.html");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(resultsFolder + "/TestReport.html");

        report = new ExtentReports();
        report.attachReporter(sparkReporter);
        sparkReporter.config().setTheme(Theme.STANDARD);
        //     report.setSystemInfo("Author:", "Sai Duvvuru");
        report.setSystemInfo("Environemnt: ", PropertiesUtility.ENVIRONMENT);
        report.setSystemInfo("Base URL :  ", PropertiesUtility.BASE_URI);

        report.setSystemInfo("OS: ", System.getProperty("os.name"));
        report.setSystemInfo("OS Architecture: ", System.getProperty("os.arch"));
        //   report.setSystemInfo("Browser: ", PropertiesUtility.BROWSER_NAME);
        report.setSystemInfo("Host Name: ", System.getProperty("host"));
        report.setSystemInfo("Project: ", "RESTAssured Sample");
        //   report.setSystemInfo("Application: ", PropertiesUtility.APPLICATION);

        System.out.println("YOU are in Set Spark Reporter Method");
        return report;
    }

    public void writeToReport() {
        report.flush();
    }

    public static String getCurrentDateAndTime() {
        String str = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS");
            Date date = new Date();
            str = dateFormat.format(date);
            str = str.replace(" ", "").replaceAll("/", "").replaceAll(":", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}



