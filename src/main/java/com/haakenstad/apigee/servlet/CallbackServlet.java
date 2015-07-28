package com.haakenstad.apigee.servlet;


import com.google.common.collect.ImmutableMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by t746301 on 31.03.2015.
 */
public class CallbackServlet extends HttpServlet {
    private ApiGeeClient apiGeeClient = new ApiGeeClient();

    public CallbackServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();
        if (req.getParameter("error") != null) {
            writer.println(req.getParameter("error"));
            return;
        }

        String code = req.getParameter("code");
        //String code = "UFkXH0UI";
        System.out.println("Callback med code: " + code);
        if (code != null) {

            // get the access token by post to Google
            ImmutableMap<String, String> formParameters = ImmutableMap.<String, String>builder()
                    .put("code", code)
                    .put("grant_type", "authorization_code").build();
            ENV env = (ENV) req.getSession().getAttribute("env");
            String tokenurl = Constants.getProperty(env,PropKeys.APIGEE_TOKEN_URL);
            String clientId = Constants.getProperty(env, PropKeys.CLIENT_ID);
            String clientSecret = Constants.getProperty(env, PropKeys.CLIENT_SECRET);
            String body = apiGeeClient.postAuthentication(tokenurl, formParameters, clientId,clientSecret);

            JSONObject jsonObject;

            // get the access token from json and request info from Google
            try {
                jsonObject = (JSONObject) new JSONParser().parse(body);
            } catch (ParseException e) {
                throw new RuntimeException("Unable to parse json " + body);
            }

            // google tokens expire after an hour, but since we requested offline access we can get a new token without user involvement via the refresh token
            String accessToken = (String) jsonObject.get("access_token");
            System.out.println("accessToken = " + accessToken);

            // you may want to store the access token in session
            req.getSession().setAttribute("access_token", accessToken);

        } else {
            //System.out.println("Ingen code, hva i svarte er dette ? "+req.getRequestURI());
        }
        resp.sendRedirect("mainPage.jsf");
    }
}
