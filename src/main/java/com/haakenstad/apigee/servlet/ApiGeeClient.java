package com.haakenstad.apigee.servlet;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ApiGeeClient {

    public final String clientId = "F5zZoOvDLINAe8GmaQdQubd82F2k7kta";
    public final String clientSecret = "zJpDtrqk7is9OwjDNWi5CzOK";


    // makes a GET request to url and returns body as a string
    public String get(String url, Map<String,String> params, Map<String,String> headerParams) throws ClientProtocolException, IOException {
        HttpGet request = new HttpGet(url);
        List<Header> headers = new ArrayList<Header>();
        for (String headerKey : headerParams.keySet()) {
            String headerValue = headerParams.get(headerKey);
            Header header = new BasicHeader(headerKey,headerValue);
            headers.add(header);
        }
        request.setHeaders(headers.toArray(new Header[headers.size()]));
        HttpParams requestParams = new BasicHttpParams();
        for (String paramKey : params.keySet()) {
            String paramValue = params.get(paramKey);
            requestParams.setParameter(paramKey, paramValue);
        }
        request.setParams(requestParams);
        return execute(request);
    }

    // makes a POST request to url with form parameters and returns body as a string
    public String postAuthentication(String url, Map<String, String> formParameters) throws IOException {
        HttpPost request = new HttpPost(url);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        for (String key : formParameters.keySet()) {
            nvps.add(new BasicNameValuePair(key, formParameters.get(key)));
        }

        request.setEntity(new UrlEncodedFormEntity(nvps));

        String userpass = "F5zZoOvDLINAe8GmaQdQubd82F2k7kta:2MGiU9a5wQcyPPH6";
        String basicAuth = "Basic " + DatatypeConverter.printBase64Binary(userpass.getBytes());
        request.setHeader("Authorization", basicAuth);

        return execute(request);
    }

    // makes a POST request to url with form parameters and returns body as a string
    public String post(String url, Map<String, String> formParameters, Map<String, String> headerParams) throws IOException {
        HttpPost request = new HttpPost(url);
        List<Header> headers = new ArrayList<Header>();
        for (String headerKey : headerParams.keySet()) {
            String headerValue = headerParams.get(headerKey);
            Header header = new BasicHeader(headerKey,headerValue);
            headers.add(header);
        }
        request.setHeaders(headers.toArray(new Header[headers.size()]));
        HttpParams requestParams = new BasicHttpParams();
        for (String paramKey : formParameters.keySet()) {
            String paramValue = formParameters.get(paramKey);
            requestParams.setParameter(paramKey, paramValue);
        }
        request.setParams(requestParams);

        return execute(request);
    }

    // makes request and checks response code for 200
    private String execute(HttpRequestBase request) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(request);
        System.out.println("execute med request "+request);

        Integer statusCode = response.getStatusLine().getStatusCode();
        String body;
        if (statusCode == 204) {
            body = "{}";
        } else {
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity);
        }

        if (!(statusCode == 204 || statusCode == 200)) {
            throw new RuntimeException("Expected 200 but got " + statusCode + ", with body " + body);
        }


        return body;
    }
}