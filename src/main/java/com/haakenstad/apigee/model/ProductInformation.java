package com.haakenstad.apigee.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Created by T746301 on 07.04.2015.
 */
public class ProductInformation {

    private Price price;
    private TerminalProduct terminalProduct;
    private SubscriptionProduct subscriptionProduct;
    private List<Product> addOnProducts;
    private List<Product> campaign;
    private List<Product> bindings;

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public TerminalProduct getTerminalProduct() {
        return terminalProduct;
    }

    public void setTerminalProduct(TerminalProduct terminalProduct) {
        this.terminalProduct = terminalProduct;
    }

    public SubscriptionProduct getSubscriptionProduct() {
        return subscriptionProduct;
    }

    public void setSubscriptionProduct(SubscriptionProduct subscriptionProduct) {
        this.subscriptionProduct = subscriptionProduct;
    }

    public List<Product> getAddOnProducts() {
        return addOnProducts;
    }

    public void setAddOnProducts(List<Product> addOnProducts) {
        this.addOnProducts = addOnProducts;
    }

    public List<Product> getCampaign() {
        return campaign;
    }

    public void setCampaign(List<Product> campaign) {
        this.campaign = campaign;
    }

    public List<Product> getBindings() {
        return bindings;
    }

    public void setBindings(List<Product> bindings) {
        this.bindings = bindings;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
