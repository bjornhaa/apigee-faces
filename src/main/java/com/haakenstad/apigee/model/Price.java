package com.haakenstad.apigee.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by T746301 on 07.04.2015.
 */
public class Price {

    private boolean freeOfCharge;
    private  Cost totalCostFirstYear;
    private  Cost monthlyCost;
    private  Cost initialCost;
    private  Cost deliveryCost;
    private boolean totalPriceDisclaimer;

    public boolean isFreeOfCharge() {
        return freeOfCharge;
    }

    public void setFreeOfCharge(boolean freeOfCharge) {
        this.freeOfCharge = freeOfCharge;
    }

    public Cost getTotalCostFirstYear() {
        return totalCostFirstYear;
    }

    public void setTotalCostFirstYear(Cost totalCostFirstYear) {
        this.totalCostFirstYear = totalCostFirstYear;
    }

    public Cost getMonthlyCost() {
        return monthlyCost;
    }

    public void setMonthlyCost(Cost monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    public Cost getInitialCost() {
        return initialCost;
    }

    public void setInitialCost(Cost initialCost) {
        this.initialCost = initialCost;
    }

    public Cost getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Cost deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public boolean isTotalPriceDisclaimer() {
        return totalPriceDisclaimer;
    }

    public void setTotalPriceDisclaimer(boolean totalPriceDisclaimer) {
        this.totalPriceDisclaimer = totalPriceDisclaimer;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
