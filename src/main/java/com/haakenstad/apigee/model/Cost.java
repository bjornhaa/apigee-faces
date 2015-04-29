package com.haakenstad.apigee.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * Created by T746301 on 07.04.2015.
 */
public class Cost {

    private BigDecimal amount;
    private Boolean discounted;
    private int discountedMonths;
    private Long discountedListAmount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getDiscounted() {
        return discounted;
    }

    public void setDiscounted(Boolean discounted) {
        this.discounted = discounted;
    }

    public int getDiscountedMonths() {
        return discountedMonths;
    }

    public void setDiscountedMonths(int discountedMonths) {
        this.discountedMonths = discountedMonths;
    }

    public Long getDiscountedListAmount() {
        return discountedListAmount;
    }

    public void setDiscountedListAmount(Long discountedListAmount) {
        this.discountedListAmount = discountedListAmount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
