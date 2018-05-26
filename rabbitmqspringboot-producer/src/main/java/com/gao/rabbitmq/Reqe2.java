package com.gao.rabbitmq;

/**
 *   名称: Reqe2.java
 *   描述:
 *   类型: JAVA
 *   最近修改时间:2018/5/22 17:14
 *   @version [版本号, V1.0]
 *   @since 2018/5/22 17:14
 *   @author gaoshudian
 */
public class Reqe2 {

    private String serial_no;
    private String cif_deposit_no;
    private String name;
    private String license_no;
    private String account_no;
    private String account_category;
    private String back_no;
    private String bank_branch_name;
    private String bank_province_no;
    private String bank_city_no;
    private String bank_zone_no;
    private String bank_branch_addr;

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getAccount_category() {
        return account_category;
    }

    public void setAccount_category(String account_category) {
        this.account_category = account_category;
    }

    public String getBack_no() {
        return back_no;
    }

    public void setBack_no(String back_no) {
        this.back_no = back_no;
    }

    public String getBank_branch_name() {
        return bank_branch_name;
    }

    public void setBank_branch_name(String bank_branch_name) {
        this.bank_branch_name = bank_branch_name;
    }

    public String getBank_province_no() {
        return bank_province_no;
    }

    public void setBank_province_no(String bank_province_no) {
        this.bank_province_no = bank_province_no;
    }

    public String getBank_city_no() {
        return bank_city_no;
    }

    public void setBank_city_no(String bank_city_no) {
        this.bank_city_no = bank_city_no;
    }

    public String getBank_zone_no() {
        return bank_zone_no;
    }

    public void setBank_zone_no(String bank_zone_no) {
        this.bank_zone_no = bank_zone_no;
    }

    public String getBank_branch_addr() {
        return bank_branch_addr;
    }

    public void setBank_branch_addr(String bank_branch_addr) {
        this.bank_branch_addr = bank_branch_addr;
    }

    @Override
    public String toString() {
        return "Reqe2{" +
                "serial_no='" + serial_no + '\'' +
                ", cif_deposit_no='" + cif_deposit_no + '\'' +
                ", name='" + name + '\'' +
                ", license_no='" + license_no + '\'' +
                ", account_no='" + account_no + '\'' +
                ", account_category='" + account_category + '\'' +
                ", back_no='" + back_no + '\'' +
                ", bank_branch_name='" + bank_branch_name + '\'' +
                ", bank_province_no='" + bank_province_no + '\'' +
                ", bank_city_no='" + bank_city_no + '\'' +
                ", bank_zone_no='" + bank_zone_no + '\'' +
                ", bank_branch_addr='" + bank_branch_addr + '\'' +
                '}';
    }
}