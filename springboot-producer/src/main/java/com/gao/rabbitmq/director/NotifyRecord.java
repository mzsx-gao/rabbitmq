package com.gao.rabbitmq.director;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 名称: NotifyRecord.java
 * 描述: TODO
 *
 * @author gaoshudian
 * @date 2019/4/23 11:17 AM
 */
public class NotifyRecord implements Serializable {

    private Long id;
    private String merchantCode;
    private String applicationCode;
    private String buCode;
    private String interfaceId;
    private String orderNo;
    private Integer notifyTimes;
    private Integer maxNotifyTimes;
    private String notifyUrl;
    private String reqData;
    private String cryptorData;
    private Date nextNotifyTime;
    private Date lastNotifyTime;
    private Integer status;
    private Integer businessCode;
    private String reqDataDigest;
    private Date createTime;
    private Date updateTime;
    private Map<String, String> extra = new HashMap();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public String getBuCode() {
        return buCode;
    }

    public void setBuCode(String buCode) {
        this.buCode = buCode;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getNotifyTimes() {
        return notifyTimes;
    }

    public void setNotifyTimes(Integer notifyTimes) {
        this.notifyTimes = notifyTimes;
    }

    public Integer getMaxNotifyTimes() {
        return maxNotifyTimes;
    }

    public void setMaxNotifyTimes(Integer maxNotifyTimes) {
        this.maxNotifyTimes = maxNotifyTimes;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReqData() {
        return reqData;
    }

    public void setReqData(String reqData) {
        this.reqData = reqData;
    }

    public String getCryptorData() {
        return cryptorData;
    }

    public void setCryptorData(String cryptorData) {
        this.cryptorData = cryptorData;
    }

    public Date getNextNotifyTime() {
        return nextNotifyTime;
    }

    public void setNextNotifyTime(Date nextNotifyTime) {
        this.nextNotifyTime = nextNotifyTime;
    }

    public Date getLastNotifyTime() {
        return lastNotifyTime;
    }

    public void setLastNotifyTime(Date lastNotifyTime) {
        this.lastNotifyTime = lastNotifyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(Integer businessCode) {
        this.businessCode = businessCode;
    }

    public String getReqDataDigest() {
        return reqDataDigest;
    }

    public void setReqDataDigest(String reqDataDigest) {
        this.reqDataDigest = reqDataDigest;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "NotifyRecord{" +
                "id=" + id +
                ", merchantCode='" + merchantCode + '\'' +
                ", applicationCode='" + applicationCode + '\'' +
                ", buCode='" + buCode + '\'' +
                ", interfaceId='" + interfaceId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", notifyTimes=" + notifyTimes +
                ", maxNotifyTimes=" + maxNotifyTimes +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", reqData='" + reqData + '\'' +
                ", cryptorData='" + cryptorData + '\'' +
                ", nextNotifyTime=" + nextNotifyTime +
                ", lastNotifyTime=" + lastNotifyTime +
                ", status=" + status +
                ", businessCode=" + businessCode +
                ", reqDataDigest='" + reqDataDigest + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", extra=" + extra +
                '}';
    }
}
