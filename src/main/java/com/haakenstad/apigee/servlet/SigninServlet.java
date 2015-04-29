package com.haakenstad.apigee.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by t746301 on 31.03.2015.
 */
public class SigninServlet extends HttpServlet {
    private ApiGeeClient apiGeeClient = new ApiGeeClient();

    public SigninServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        // https://test-apigw.telenor.no/oauth/v2/authorize?client_id=F5zZoOvDLINAe8GmaQdQubd82F2k7kta
        StringBuilder oauthUrl = new StringBuilder().append("https://test-apigw.telenor.no/oauth/v2/authorize")
                .append("?client_id=").append(apiGeeClient.clientId)
                .append("&response_type=code")
                .append("&state=this_can_be_anything_to_help_correlate_the_response%3Dlike_session_id")
                .append("&access_type=offline") // here we are asking to access to user's data while they are not signed in
                .append("&approval_prompt=force"); // this requires them to verify which account to use, if they are already signed in

        resp.sendRedirect(oauthUrl.toString());
    }
}
