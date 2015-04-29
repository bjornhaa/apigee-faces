package com.haakenstad.apigee.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Created by T746301 on 07.04.2015.
 */
public class SalesTipResponse {

    private List<SalesTip> salesTips;

    public List<SalesTip> getSalesTips() {
        return salesTips;
    }

    public void setSalesTips(List<SalesTip> salesTips) {
        this.salesTips = salesTips;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
