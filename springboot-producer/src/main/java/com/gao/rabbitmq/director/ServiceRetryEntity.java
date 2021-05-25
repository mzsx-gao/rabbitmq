package com.gao.rabbitmq.director;

/**
 * 名称: ServiceRetryEntity.java
 * 描述: lenderb各个服务回调失败发送信息到lenderb-tss项目
 *
 * @author gaoshudian
 * @date   2019/4/24 6:18 PM
*/
public class ServiceRetryEntity {

     // 商户号
    private String merchantCode;
    
     // 业务条线编码
    private String buCode;
    
     // 应用编号
    private String applicationCode;
    
     // 接口编号
    private String interfaceId;

     // 交易编号
    private String tradeNo;
    
     // 目标服务名称
    private String targetServiceName;
    
     // 延迟时间:单位 分钟 默认10分钟
    private int delay = 10;
    
     // json
    private String data;
    
     // 是否自动触发
    private boolean autoTrigger = true;
    
     // 日切时间
    private String settleDate;
    
     // 配注
    private String remark;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getBuCode() {
        return buCode;
    }

    public void setBuCode(String buCode) {
        this.buCode = buCode;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTargetServiceName() {
        return targetServiceName;
    }

    public void setTargetServiceName(String targetServiceName) {
        this.targetServiceName = targetServiceName;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isAutoTrigger() {
        return autoTrigger;
    }

    public void setAutoTrigger(boolean autoTrigger) {
        this.autoTrigger = autoTrigger;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
