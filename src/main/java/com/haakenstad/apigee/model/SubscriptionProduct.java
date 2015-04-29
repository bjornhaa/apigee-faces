package com.haakenstad.apigee.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by T746301 on 07.04.2015.
 */
public class SubscriptionProduct {

    private String mainSubProductId;
    private TrafficQuotas trafficQuotas;
    private String id;
    private String type;
    private String name;

    public String getMainSubProductId() {
        return mainSubProductId;
    }

    public void setMainSubProductId(String mainSubProductId) {
        this.mainSubProductId = mainSubProductId;
    }

    public TrafficQuotas getTrafficQuotas() {
        return trafficQuotas;
    }

    public void setTrafficQuotas(TrafficQuotas trafficQuotas) {
        this.trafficQuotas = trafficQuotas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
