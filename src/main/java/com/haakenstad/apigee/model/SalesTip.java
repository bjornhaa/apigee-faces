package com.haakenstad.apigee.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by T746301 on 07.04.2015.
 */
public class SalesTip {

    private String salesTipCategory;
    private String salesTipActionType;
    private boolean orderable;
    private String descriptionBody;
    private String teaserBody;
    private String teaserHeader;
    private String descriptionHeader;
    private String salesTipProductType;
    private SalesTipId salesTipId;
    private ProductInformation currentProductInformation;
    private ProductInformation salesTipProductInformation;
    private Link externalLink;
    private Subscription subscription;


    public String getSalesTipCategory() {
        return salesTipCategory;
    }

    public void setSalesTipCategory(String salesTipCategory) {
        this.salesTipCategory = salesTipCategory;
    }

    public String getSalesTipActionType() {
        return salesTipActionType;
    }

    public void setSalesTipActionType(String salesTipActionType) {
        this.salesTipActionType = salesTipActionType;
    }

    public boolean isOrderable() {
        return orderable;
    }

    public void setOrderable(boolean orderable) {
        this.orderable = orderable;
    }

    public String getDescriptionBody() {
        return descriptionBody;
    }

    public void setDescriptionBody(String descriptionBody) {
        this.descriptionBody = descriptionBody;
    }

    public String getTeaserBody() {
        return teaserBody;
    }

    public void setTeaserBody(String teaserBody) {
        this.teaserBody = teaserBody;
    }

    public String getTeaserHeader() {
        return teaserHeader;
    }

    public void setTeaserHeader(String teaserHeader) {
        this.teaserHeader = teaserHeader;
    }

    public String getDescriptionHeader() {
        return descriptionHeader;
    }

    public void setDescriptionHeader(String descriptionHeader) {
        this.descriptionHeader = descriptionHeader;
    }

    public String getSalesTipProductType() {
        return salesTipProductType;
    }

    public void setSalesTipProductType(String salesTipProductType) {
        this.salesTipProductType = salesTipProductType;
    }

    public SalesTipId getSalesTipId() {
        return salesTipId;
    }

    public void setSalesTipId(SalesTipId salesTipId) {
        this.salesTipId = salesTipId;
    }

    public ProductInformation getCurrentProductInformation() {
        return currentProductInformation;
    }

    public void setCurrentProductInformation(ProductInformation currentProductInformation) {
        this.currentProductInformation = currentProductInformation;
    }

    public ProductInformation getSalesTipProductInformation() {
        return salesTipProductInformation;
    }

    public void setSalesTipProductInformation(ProductInformation salesTipProductInformation) {
        this.salesTipProductInformation = salesTipProductInformation;
    }

    public Link getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(Link externalLink) {
        this.externalLink = externalLink;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
