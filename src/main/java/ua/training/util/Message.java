package ua.training.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

public class Message {
    public static final String LOGIN_ERROR = "LOGIN_ERROR";
    public static final String REGISTRATION_ERROR = "REGISTRATION_ERROR";
    public static final String PERIODICAL_INSERTION_ERROR = "PERIODICAL_INSERTION_ERROR";
    public static final String PERIODICAL_INSERTION_SUCCESS = "PERIODICAL_INSERTION_SUCCESS";
    public static final String PERIODICAL_UPDATE_SUCCESS = "PERIODICAL_UPDATE_SUCCESS";
    public static final String PASSWORD_MISMATCH_ERROR = "PASSWORD_MISMATCH_ERROR";
    public static final String ILLEGAL_ACCESS_ERROR = "ILLEGAL_ACCESS_ERROR";
    public static final String SUBSCRIPTION_ERROR = "SUBSCRIPTION_ERROR";
    public static final String USER_SUBSCRIBED = "USER_SUBSCRIBED";
    public static final String PERIODICAL_DELETE_ERROR = "PERIODICAL_DELETE_ERROR";
    public static final String PERIODICAL_DELETE_SUCCESS = "PERIODICAL_DELETE_SUCCESS";

    public static final String EDITION_PLAN_ONE = "EDITION_PLAN_ONE";
    public static final String EDITION_PLAN_THREE = "EDITION_PLAN_THREE";
    public static final String EDITION_PLAN_SIX = "EDITION_PLAN_SIX";
    public static final String EDITION_PLAN_YEAR = "EDITION_PLAN_YEAR";

    public static final Locale ENGLISH = Locale.ENGLISH;
    public static final Locale UKRAINIAN = new Locale("uk", "UA");

    public static final String REGISTRATION_SUCCESS = "REGISTRATION_SUCCESS";

    private static final String PARAM_ERROR = "error";
    private static final String PARAM_SUCCESS = "success";


    private static final String BUNDLE_NAME = "messages";

    private static ResourceBundle resource = ResourceBundle.getBundle(BUNDLE_NAME);

    public static void setDefaultLocale() {
        setLocale(Locale.getDefault());
    }

    public static void setLocale(Locale locale) {
        resource = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }

    public static String error(HttpServletRequest request, String message, String redirectPage) {
        request.setAttribute(PARAM_ERROR, Message.getProperty(message));
        return Page.get(redirectPage);
    }

    public static String success(HttpServletRequest request, String message, String redirectPage) {
        request.setAttribute(PARAM_SUCCESS, Message.getProperty(message));
        return Page.get(redirectPage);
    }

    public static String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
