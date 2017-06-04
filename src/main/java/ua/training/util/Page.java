package ua.training.util;

import java.util.ResourceBundle;

public class Page {
    public static final String MAIN = "MAIN_PAGE";
    public static final String CHECKOUT = "CHECKOUT_PAGE";
    public static final String REGISTRATION = "REGISTRATION_PAGE";
    public static final String USER = "USER_PAGE";
    public static final String LOGIN = "LOGIN_PAGE";
    public static final String EDIT = "EDIT_PAGE";

    private static final ResourceBundle resource = ResourceBundle.getBundle("pages");

    public static String get(String key) {
        return (String) resource.getObject(key);
    }
}
