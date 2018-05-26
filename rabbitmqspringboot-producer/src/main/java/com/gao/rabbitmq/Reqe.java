package com.gao.rabbitmq;

import java.util.List;

/**
 *   名称: Reqe.java
 *   描述:
 *   类型: JAVA
 *   最近修改时间:2018/5/22 17:13
 *   @version [版本号, V1.0]
 *   @since 2018/5/22 17:13
 *   @author gaoshudian
 */
public class Reqe{

    private String batch_id;
    private String interface_id;
    private String tenantCode;
    private String appCode;
    private String token;
    private String buCode;
    private List<Reqe2> list;

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getInterface_id() {
        return interface_id;
    }

    public void setInterface_id(String interface_id) {
        this.interface_id = interface_id;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBuCode() {
        return buCode;
    }

    public void setBuCode(String buCode) {
        this.buCode = buCode;
    }

    public List<Reqe2> getList() {
        return list;
    }

    public void setList(List<Reqe2> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Reqe{" +
                "batch_id='" + batch_id + '\'' +
                ", interface_id='" + interface_id + '\'' +
                ", tenantCode='" + tenantCode + '\'' +
                ", appCode='" + appCode + '\'' +
                ", token='" + token + '\'' +
                ", buCode='" + buCode + '\'' +
                ", list=" + list +
                '}';
    }
}