package se.ystromm.springboot.webjars;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.google.common.base.Optional;
import com.google.common.io.Resources;


public class Versions {

    public static final String[] DEFAULT_MAVEN_GROUPS = { "org.webjars" };
    private final static String POM_PROPERTIES_PATH = "META-INF/maven/%s/%s/pom.properties";
    
    // group would be org.webjars and library 'angularjs'
    Optional<String> version(String group, String library) {
        final String pomPropertiesPath = String.format(POM_PROPERTIES_PATH, group, library);
        try {
        final URL pomPropertiesURL = Resources.getResource(pomPropertiesPath);
            final InputStream pomPropertiesInputStream = pomPropertiesURL.openStream();                
            try {
                Properties properties = new Properties();
                properties.load(pomPropertiesInputStream);
                return Optional.fromNullable(properties.getProperty("version"));
            }
            finally {
                pomPropertiesInputStream.close();
            }
        } catch (IllegalArgumentException | IOException e) {
            return Optional.absent();
        }        
    }
}
