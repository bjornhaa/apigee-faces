package com.haakenstad.apigee.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by T746301 on 07.04.2015.
 */
public class TrafficQuotas {

    private TrafficQuota includedVoiceQuota;
    private TrafficQuota includedSmsQuota;
    private TrafficQuota includedMmsQuota;
    private TrafficQuota includedDataQuota;
    private TrafficQuota dataChokeQuota;
    private String dataDownloadBandwidth;
    private String dataUploadBandwidth;

    public TrafficQuota getIncludedVoiceQuota() {
        return includedVoiceQuota;
    }

    public void setIncludedVoiceQuota(TrafficQuota includedVoiceQuota) {
        this.includedVoiceQuota = includedVoiceQuota;
    }

    public TrafficQuota getIncludedSmsQuota() {
        return includedSmsQuota;
    }

    public void setIncludedSmsQuota(TrafficQuota includedSmsQuota) {
        this.includedSmsQuota = includedSmsQuota;
    }

    public TrafficQuota getIncludedMmsQuota() {
        return includedMmsQuota;
    }

    public void setIncludedMmsQuota(TrafficQuota includedMmsQuota) {
        this.includedMmsQuota = includedMmsQuota;
    }

    public TrafficQuota getIncludedDataQuota() {
        return includedDataQuota;
    }

    public void setIncludedDataQuota(TrafficQuota includedDataQuota) {
        this.includedDataQuota = includedDataQuota;
    }

    public TrafficQuota getDataChokeQuota() {
        return dataChokeQuota;
    }

    public void setDataChokeQuota(TrafficQuota dataChokeQuota) {
        this.dataChokeQuota = dataChokeQuota;
    }

    public String getDataDownloadBandwidth() {
        return dataDownloadBandwidth;
    }

    public void setDataDownloadBandwidth(String dataDownloadBandwidth) {
        this.dataDownloadBandwidth = dataDownloadBandwidth;
    }

    public String getDataUploadBandwidth() {
        return dataUploadBandwidth;
    }

    public void setDataUploadBandwidth(String dataUploadBandwidth) {
        this.dataUploadBandwidth = dataUploadBandwidth;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
