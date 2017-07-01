package com.bookaholicc.partner.Model;

/**
 * Created by nandhu on 23/6/17.
 *
 */

public class Earning {

    public String pid;
    public String duraion;
    public String amount;
    public String productImage;
    public String productName;

    public Earning(String pid, String duraion, String amount) {
        this.pid = pid;
        this.duraion = duraion;
        this.amount = amount;

    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDuraion() {
        return duraion;
    }

    public void setDuraion(String duraion) {
        this.duraion = duraion;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
