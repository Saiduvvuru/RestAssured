package in.reqres.utils;


import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtility {

    private static final Logger LOG = Logger.getLogger(PropertiesUtility.class);
    private static final String PROPERTIES_FILE = "src/test/resources/application.properties";
    public static String BASE_URI = null;
    public static String ENVIRONMENT = null;
    public static Properties props;


    public static Properties getProperties() throws IOException {
        props = new Properties();
        InputStream input = new FileInputStream(PROPERTIES_FILE);
        props.load(input);
        LOG.info("     Loaded Properties  " + props);
        ENVIRONMENT = props.getProperty("environment");
        BASE_URI = props.getProperty(ENVIRONMENT + "." + "base.url");
        System.out.println();
        //   System.out.println("Following are printed from getProperties method");
        //   System.out.println(ENVIRONMENT);
        //  System.out.println(BASE_URI);
        // System.out.println(props);
        return props;


    }


}
