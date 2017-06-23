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

    public Earning(String pid, String duraion, String amount, String productImage, String productName) {
        this.pid = pid;
        this.duraion = duraion;
        this.amount = amount;
        this.productImage = productImage;
        this.productName = productName;
    }
}
