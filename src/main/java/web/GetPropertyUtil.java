package web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class GetPropertyUtil {

    public static String getValue(String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/java/resources/data.properties"));
        return properties.getProperty(key);
    }
}
