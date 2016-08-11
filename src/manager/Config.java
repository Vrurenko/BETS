package manager;

import java.util.ResourceBundle;

public class Config {

    private static Config instance;
    private ResourceBundle resource;
    private static final String BUNDLE_NAME = "config";
    public static final String CLIENT = "CLIENT";
    public static final String ADMIN = "ADMIN";
    public static final String BOOKMAKER = "BOOKMAKER";
    public static final String ERROR = "ERROR";
    public static final String LOGIN = "LOGIN";
    public static final String REGISTRATION = "REGISTRATION";

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
            instance.resource = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
