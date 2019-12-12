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
    public static final String TEST_URL = "/test/india";
    
    //swagger urls
    public static final String SWAG_API_DOC_URL =  "/v2/api-docs";
    public static final String SWAG_CONFIG_UI_URL = "/configuration/ui";
    public static final String SWAG_RESOURCE_URL = "/swagger-resources/**" ;
    public static final String SWAG_SECURITY_URL = "/configuration/security";
    public static final String SWAG_UI_URL =  "/swagger-ui.html";
    public static final String SWAG_JARS_URL =  "/webjars/**";
}