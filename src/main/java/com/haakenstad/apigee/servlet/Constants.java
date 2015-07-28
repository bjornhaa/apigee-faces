package com.haakenstad.apigee.servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by t746301 on 09.06.2015.
 */
public class Constants {



    private static Map<PropKeys, String> props_test = new HashMap<PropKeys, String>();
    private static Map<PropKeys, String> props_prod = new HashMap<PropKeys, String>();

    private static final String APIGEE_BASE_URL_PROD = "apigw.telenor.no";
    private static final String APIGEE_BASE_URL_TEST = "test-" + APIGEE_BASE_URL_PROD;

    static {
        final String clientId_test = "F5zZoOvDLINAe8GmaQdQubd82F2k7kta";
        final String clientId_prod = "nJO85bbBLCkR7rQdO4AFb1rF7Xk0BqHV";
        final String clientSecret_test = "2MGiU9a5wQcyPPH6";
        final String clientSecret_prod = "YPHX4c3EGKxPU5fY";
        final String APIGEE_AUTHORIZE_URL_TEST = "https://" + APIGEE_BASE_URL_TEST + "/oauth/v2/authorize";
        final String APIGEE_AUTHORIZE_URL_PROD = "https://" + APIGEE_BASE_URL_PROD + "/oauth/v2/authorize";
        final String APIGEE_TOKEN_URL_TEST = "https://" + APIGEE_BASE_URL_TEST + "/oauth/v2/token";
        final String APIGEE_TOKEN_URL_PROD = "https://" + APIGEE_BASE_URL_PROD + "/oauth/v2/token";

        props_test.put(PropKeys.CLIENT_ID, clientId_test);
        props_test.put(PropKeys.CLIENT_SECRET, clientSecret_test);
        props_test.put(PropKeys.APIGEE_AUTHORIZE_URL, APIGEE_AUTHORIZE_URL_TEST);
        props_test.put(PropKeys.APIGEE_TOKEN_URL, APIGEE_TOKEN_URL_TEST);
        props_test.put(PropKeys.APIGEE_BASE_URL, APIGEE_BASE_URL_TEST);

        props_prod.put(PropKeys.CLIENT_ID, clientId_prod);
        props_prod.put(PropKeys.CLIENT_SECRET, clientSecret_prod);
        props_prod.put(PropKeys.APIGEE_AUTHORIZE_URL, APIGEE_AUTHORIZE_URL_PROD);
        props_prod.put(PropKeys.APIGEE_TOKEN_URL, APIGEE_TOKEN_URL_PROD);
        props_prod.put(PropKeys.APIGEE_BASE_URL, APIGEE_BASE_URL_PROD);

    }

    public static String getProperty(ENV env, PropKeys key) {
        switch (env) {
            case PROD:
                return props_prod.get(key);
            case TEST:
                return props_test.get(key);
        }
        throw new RuntimeException("invalid env "+env);
    }
}
