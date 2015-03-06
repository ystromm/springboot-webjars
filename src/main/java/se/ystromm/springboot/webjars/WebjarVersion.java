package se.ystromm.springboot.webjars;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.google.common.base.Optional;
import com.google.common.io.Resources;

final class WebjarVersion {

    public static final String[] DEFAULT_MAVEN_GROUPS = { "org.webjars" };
    private final static String POM_PROPERTIES_PATH = "META-INF/maven/%s/%s/pom.properties";
    
    // hidden
    private WebjarVersion() {
        // empty
    }
        
    /**
     * Get the version.
     * @param group For example 'org.webjars'.
     * @param library For example 'angularjs'.
     * @return An optional version.
     */
    static Optional<String> webjarVersion(String group, String library) {
        final String pomPropertiesPath = String.format(POM_PROPERTIES_PATH, group, library);
        try {
        final URL pomPropertiesURL = Resources.getResource(pomPropertiesPath);
            final InputStream pomPropertiesInputStream = pomPropertiesURL.openStream();                
            try {
                final Properties properties = new Properties();
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
