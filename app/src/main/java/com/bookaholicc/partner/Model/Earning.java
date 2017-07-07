package com.bookaholicc.partner.Model;

import java.security.Timestamp;

/**
 * Created by nandhu on 23/6/17.
 *
 */

public class Earning {

    public String pName;
    public String duraion;
    public String amount;
    public String productImage;

    public String timeStamp;

    public Earning(String pName, String amount, String imageURL, String timeStamp, String windowId) {
        this.pName = pName;
        this.duraion = windowId;
        this.amount = amount;
        this.productImage = imageURL;
        this.timeStamp = timeStamp;

    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
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


    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
