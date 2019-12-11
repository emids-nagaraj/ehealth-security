package com.ehealth.security;


/**
 * @author nagaraj
 *
 */

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 120_000_000; // millisec (= 2 min)
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_IN_URL = "/auth/signin";
    public static final String SIGN_UP_URL = "/users/register";
    public static final String ISADMIN = "isAdmin"; //admin flag
    public static final String SWAGGER_UI_URL= "/swagger-ui.html";
    public static final String TEST_URL = "/test/india";
//    public static final String GET_PLANS_URL = "/plans";
//    public static final String GET_CUSTOM_PLANS_URL = "/plans/getPlanByID";
//    public static final String CREATE_SUBSCRIPTION = "/subscription";
//    public static final String TARGET_FILE = "/target.jsp";
//    public static final String REDIRECT_URL = "/redirecturl";
}