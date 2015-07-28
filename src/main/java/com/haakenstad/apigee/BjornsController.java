/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.haakenstad.apigee;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.haakenstad.apigee.model.*;
import com.haakenstad.apigee.servlet.ApiGeeClient;
import com.haakenstad.apigee.servlet.Constants;
import com.haakenstad.apigee.servlet.ENV;
import com.haakenstad.apigee.servlet.PropKeys;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.parser.ParseException;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "controller")
@RequestScoped
public class BjornsController {


    private static final String PROTOCOL = "https://";
    private String msisdn;
    private String changeId;
    private TreeNode tipsTree;
    private String json;
    private String action = "PRESENTED";
    private String id;
    private String page = "test";
    private String addonProducts;

    /**
     * default empty constructor
     */
    public BjornsController() {
    }


    public String salesTips() throws ParseException {

        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Object accessToken = sessionMap.get("access_token");
        // get some info about the user with the access token
        Map<String, String> headerParams = ImmutableMap.<String, String>builder()
                .put("Authorization", "Bearer " + accessToken)
                .put("Content-Type", "application/json")
                .put("Accept", "*")
                .put("X-Mona-Client-Name", "whatevah")
                .build();
        Map<String, String> params = ImmutableMap.<String, String>builder()
                .put("brand", "TELENOR")
                .put("timeout", "30000")
                //.put("Language", "EN") ignored
                .build();
        ApiGeeClient apiGeeClient = new ApiGeeClient();
        try {
            ENV env = (ENV) sessionMap.get("env");
            String baseUrl = Constants.getProperty(env, PropKeys.APIGEE_BASE_URL);

            String json = apiGeeClient.get(PROTOCOL+baseUrl + "/salestips/v1/", params, headerParams);
            System.out.println("json fra MS: "+json);
            SalesTipResponse salesTipResponse = new Gson().fromJson(json, SalesTipResponse.class);
            setJson(json);
            populateTree(salesTipResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "result.xhtml";
    }

    private void populateTree(SalesTipResponse salesTipResponse) {
        tipsTree = new DefaultTreeNode("Root",null);
        if (salesTipResponse != null && salesTipResponse.getSalesTips() != null && salesTipResponse.getSalesTips().size() > 0) {
            for (SalesTip salesTip : salesTipResponse.getSalesTips()) {
                TreeNode tipNode = new DefaultTreeNode(salesTip.getSalesTipId().getId(), tipsTree);
                Subscription subscription = salesTip.getSubscription();
                if (subscription != null) {
                    tipNode.getChildren().add(new DefaultTreeNode("SubscriptionId: "+ subscription.getSubscriptionId().getId()));
                }
                tipNode.getChildren().add(new DefaultTreeNode("actionType: "+salesTip.getSalesTipActionType(),tipNode));
                tipNode.getChildren().add(new DefaultTreeNode("salesTipCategory: "+salesTip.getSalesTipCategory(),tipNode));
                tipNode.getChildren().add(new DefaultTreeNode("salesTipProductType: "+salesTip.getSalesTipProductType(),tipNode));
                String url = salesTip.getExternalLink() != null ? salesTip.getExternalLink().getUrl() : "";
                tipNode.getChildren().add(new DefaultTreeNode("link: "+ url,tipNode));
                ProductInformation currentProductInformation = salesTip.getCurrentProductInformation();
                if (currentProductInformation != null) {
                    DefaultTreeNode currentNode = new DefaultTreeNode("currentProductInformation", tipNode);
                    BigDecimal amount = currentProductInformation.getPrice().getMonthlyCost().getAmount();
                    currentNode.getChildren().add(new DefaultTreeNode("Current subscription id: " + currentProductInformation.getSubscriptionProduct().getId()));
                    currentNode.getChildren().add(new DefaultTreeNode("Current subscription name: " + currentProductInformation.getSubscriptionProduct().getName()));
                    currentNode.getChildren().add(new DefaultTreeNode("Monthly cost: " + amount));
                    tipNode.getChildren().add(currentNode);
                }
                ProductInformation salesTipProductInformation = salesTip.getSalesTipProductInformation();
                if (salesTipProductInformation != null) {
                    DefaultTreeNode salesTipNode = new DefaultTreeNode("salestip", tipNode);
                    SubscriptionProduct subscriptionProduct = salesTipProductInformation.getSubscriptionProduct();
                    if (subscriptionProduct != null) {
                        DefaultTreeNode subscrNode = new DefaultTreeNode("Subscription product");
                        salesTipNode.getChildren().add(subscrNode);
                        subscrNode.getChildren().add(new DefaultTreeNode("Subscription id: " + subscriptionProduct.getId()));
                        subscrNode.getChildren().add(new DefaultTreeNode("Subscription name: " + subscriptionProduct.getName()));
                        subscrNode.getChildren().add(new DefaultTreeNode("Subscription mainSub: " + subscriptionProduct.getMainSubProductId()));
                    }
                    List<Product> bindings = salesTipProductInformation.getBindings();
                    if (bindings != null) {
                        DefaultTreeNode bindingNode = new DefaultTreeNode("Bindings: ");
                        salesTipNode.getChildren().add(bindingNode);
                        for (Product binding : bindings) {
                            bindingNode.getChildren().add(new DefaultTreeNode("id: "+binding.getId()));
                            bindingNode.getChildren().add(new DefaultTreeNode("name: "+binding.getName()));
                        }
                    }

                    TerminalProduct terminalProduct = salesTipProductInformation.getTerminalProduct();
                    if (terminalProduct != null) {
                        DefaultTreeNode terminalNode = new DefaultTreeNode("Terminal product");
                        salesTipNode.getChildren().add(terminalNode);
                        terminalNode.getChildren().add(new DefaultTreeNode("Terminal product id: "+terminalProduct.getId()));
                        terminalNode.getChildren().add(new DefaultTreeNode("Terminal product name: "+terminalProduct.getName()));


                    }
                }
            }
        }
    }

    public String validator() {

        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Object accessToken = sessionMap.get("access_token");
        // get some info about the user with the access token
        Map<String, String> headerParams = ImmutableMap.<String, String>builder()
                .put("Authorization", "Bearer " + accessToken)
                .put("Accept", "*")
                .build();
        ApiGeeClient apiGeeClient = new ApiGeeClient();
        try {
            ENV env = (ENV) sessionMap.get("env");
            String baseUrl = Constants.getProperty(env, PropKeys.APIGEE_BASE_URL);

            String url = PROTOCOL+baseUrl + "/mobile-subscription/v1/tel:47" + getMsisdn() + "/alternatives/validator?";
            String sep = "";
            if (!StringUtils.isEmpty(getChangeId())) {
                url += "changeId=" + getChangeId();
                sep = "&";
            }
            if (!StringUtils.isEmpty(getAddonProducts())) {
                url += sep+"addProducts=" + getAddonProducts();
            }
            String json = apiGeeClient.get(url,new HashMap<String, String>(), headerParams);
            setJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "json_raw.xhtml";
    }

    public String alternatives() {

        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Object accessToken = sessionMap.get("access_token");
        // get some info about the user with the access token
        Map<String, String> headerParams = ImmutableMap.<String, String>builder()
                .put("Authorization", "Bearer " + accessToken)
                .put("Accept", "*")
                .build();
        ApiGeeClient apiGeeClient = new ApiGeeClient();
        try {
            ENV env = (ENV) sessionMap.get("env");
            String baseUrl = Constants.getProperty(env, PropKeys.APIGEE_BASE_URL);
            String url = PROTOCOL+baseUrl + "/mobile-subscription/v1/tel:47" + getMsisdn() + "/alternatives";
            String json = apiGeeClient.get(url,new HashMap<String, String>(), headerParams);
            setJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("4");

        return "json_raw.xhtml";
    }

    public String offers() {

        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Object accessToken = sessionMap.get("access_token");
        // get some info about the user with the access token
        Map<String, String> headerParams = ImmutableMap.<String, String>builder()
                .put("Authorization", "Bearer " + accessToken)
                .put("Accept", "*")
                .build();
        ApiGeeClient apiGeeClient = new ApiGeeClient();
        try {
            ENV env = (ENV) sessionMap.get("env");
            String baseUrl = Constants.getProperty(env, PropKeys.APIGEE_BASE_URL);
            String url = PROTOCOL+baseUrl + "/mobile-product/v2/tel:47" + getMsisdn() + "/offers";
            String json = apiGeeClient.get(url,new HashMap<String, String>(), headerParams);
            setJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("4");

        return "json_raw.xhtml";
    }

    public String inventory() {

        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Object accessToken = sessionMap.get("access_token");
        // get some info about the user with the access token
        Map<String, String> headerParams = ImmutableMap.<String, String>builder()
                .put("Authorization", "Bearer " + accessToken)
                .put("Accept", "*")
                .build();
        ApiGeeClient apiGeeClient = new ApiGeeClient();
        try {
            ENV env = (ENV) sessionMap.get("env");
            String baseUrl = Constants.getProperty(env, PropKeys.APIGEE_BASE_URL);

            String url = PROTOCOL+baseUrl + "/mobile-product/v2/tel:47" + getMsisdn() + "/inventory";
            String json = apiGeeClient.get(url,new HashMap<String, String>(), headerParams);
            setJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("4");

        return "json_raw.xhtml";
    }

    public String included() {

        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Object accessToken = sessionMap.get("access_token");
        // get some info about the user with the access token
        Map<String, String> headerParams = ImmutableMap.<String, String>builder()
                .put("Authorization", "Bearer " + accessToken)
                .put("Accept", "*")
                .build();
        ApiGeeClient apiGeeClient = new ApiGeeClient();
        try {
            ENV env = (ENV) sessionMap.get("env");
            String baseUrl = Constants.getProperty(env, PropKeys.APIGEE_BASE_URL);

            String url = PROTOCOL+baseUrl + "/mobile-subscription/v1/tel:47" + getMsisdn() + "/included";
            String json = apiGeeClient.get(url,new HashMap<String, String>(), headerParams);
            setJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("4");

        return "json_raw.xhtml";
    }

    public String counters() {

        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Object accessToken = sessionMap.get("access_token");
        // get some info about the user with the access token
        Map<String, String> headerParams = ImmutableMap.<String, String>builder()
                .put("Authorization", "Bearer " + accessToken)
                .put("Accept", "*")
                .build();
        ApiGeeClient apiGeeClient = new ApiGeeClient();
        try {
            ENV env = (ENV) sessionMap.get("env");
            String baseUrl = Constants.getProperty(env, PropKeys.APIGEE_BASE_URL);

            String url = PROTOCOL+baseUrl + "/mobile-usage/v2/tel:47" + getMsisdn() + "/counters";
            String json = apiGeeClient.get(url,new HashMap<String, String>(), headerParams);
            setJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("4");

        return "json_raw.xhtml";
    }

    public String tracking() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Object accessToken = sessionMap.get("access_token");
        // get some info about the user with the access token
        Map<String, String> headerParams = ImmutableMap.<String, String>builder()
                .put("Authorization", "Bearer " + accessToken)
                .put("Content-Type", "application/json")
                .put("Accept", "*")
                .put("X-Mona-Client-Name", "whatevah")
                .build();
        Map<String, String> params = ImmutableMap.<String, String>builder()
                //.put("pageName", getPage())
                .build();
        ApiGeeClient apiGeeClient = new ApiGeeClient();
        try {
            String url = "/salestips/v1/";
            url += getId().replace(";","_");
            url += "/"+getAction();
            url += "?pageName="+getPage();

            ENV env = (ENV) sessionMap.get("env");
            String baseUrl = Constants.getProperty(env, PropKeys.APIGEE_BASE_URL);
            String json = apiGeeClient.post(PROTOCOL+baseUrl + url, params, headerParams);
            System.out.println("json fra MS: "+json);
            SalesTipResponse salesTipResponse = new Gson().fromJson(json, SalesTipResponse.class);
            setJson(json);
            populateTree(salesTipResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "json_raw.xhtml";

    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

    public TreeNode getTipsTree() {
        return tipsTree;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public void setAddonProducts(String addonProducts) {
        this.addonProducts = addonProducts;
    }

    public String getAddonProducts() {
        return addonProducts;
    }
}
