package ua.training.util;

import java.util.*;

public class Message {
    public static final String LOGIN_ERROR = "LOGIN_ERROR";
    public static final String REGISTRATION_ERROR = "REGISTRATION_ERROR";
    public static final String PASSWORD_MISMATCH_ERROR = "PASSWORD_MISMATCH_ERROR";
    public static final String ILLEGAL_ACCESS_ERROR = "ILLEGAL_ACCESS_ERROR";
    public static final String SUBSCRIPTION_ERROR = "SUBSCRIPTION_ERROR";
    public static final String USER_SUBSCRIBED = "USER_SUBSCRIBED";

    private static final String BUNDLE_NAME = "messages";

    private static Locale locale;
    private static ResourceBundle resource;

    static {
        locale = Locale.getDefault();
        resource = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }


    public static void setLocale(String locale) {
        switch(locale) {
            case "ua":
               Message.locale = new Locale("uk", "UA");
                break;
            default:
                Message.locale = Locale.ENGLISH;
        }
    }

    public static String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
