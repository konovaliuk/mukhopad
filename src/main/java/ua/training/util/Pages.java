package ua.training.util;

import java.util.ResourceBundle;

public class Pages {

    private static Pages instance;
    private ResourceBundle resource;
    private static final String BUNDLE_NAME = "pages";

    public static final String MAIN = "MAIN_PAGE";
    public static final String CHECKOUT = "CHECKOUT_PAGE";
    public static final String REGISTRATION = "REGISTRATION_PAGE";
    public static final String USER = "USER_PAGE";
    public static final String LOGIN = "LOGIN_PAGE";

    public static Pages getInstance() {
        if (instance == null) {
            instance = new Pages();
            instance.resource = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String get(String key) {
        return (String) resource.getObject(key);
    }
}
