package com.hll_sc_app.bean.report.credit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/18
 */

public class CreditBean {
    private List<CreditItemBean> list;
    private double receiveAmount; // 总销售额
    private double receiveAmount01; // 应收额
    private double receiveAmount02;
    private double receiveAmount03;
    private double receiveAmount04;
    private double receiveAmount05;
    private double receiveAmount06;
    private double receiveAmount07;
    private double receiveAmount08;
    private double receiveAmount09;
    private double receiveAmount10;
    private double receiveAmount11;
    private double receiveAmount12;
    private double payAmount; // 总回款额
    private double payAmount01; // 回款额
    private double payAmount02;
    private double payAmount03;
    private double payAmount04;
    private double payAmount05;
    private double payAmount06;
    private double payAmount07;
    private double payAmount08;
    private double payAmount09;
    private double payAmount10;
    private double payAmount11;
    private double payAmount12;
    private double unPayAmount; // 未回款额
    private double unPayAmount01; // 未收款
    private double unPayAmount02;
    private double unPayAmount03;
    private double unPayAmount04;
    private double unPayAmount05;
    private double unPayAmount06;
    private double unPayAmount07;
    private double unPayAmount08;
    private double unPayAmount09;
    private double unPayAmount10;
    private double unPayAmount11;
    private double unPayAmount12;
    private String imagePath;
    private String shopName;
    private String purchaserName;
    private int shopID;

    public void preProcess() {
        list = new ArrayList<>(12);
        list.add(new CreditItemBean(payAmount01, receiveAmount01, unPayAmount01));
        list.add(new CreditItemBean(payAmount02, receiveAmount02, unPayAmount02));
        list.add(new CreditItemBean(payAmount03, receiveAmount03, unPayAmount03));
        list.add(new CreditItemBean(payAmount04, receiveAmount04, unPayAmount04));
        list.add(new CreditItemBean(payAmount05, receiveAmount05, unPayAmount05));
        list.add(new CreditItemBean(payAmount06, receiveAmount06, unPayAmount06));
        list.add(new CreditItemBean(payAmount07, receiveAmount07, unPayAmount07));
        list.add(new CreditItemBean(payAmount08, receiveAmount08, unPayAmount08));
        list.add(new CreditItemBean(payAmount09, receiveAmount09, unPayAmount09));
        list.add(new CreditItemBean(payAmount10, receiveAmount10, unPayAmount10));
        list.add(new CreditItemBean(payAmount11, receiveAmount11, unPayAmount11));
        list.add(new CreditItemBean(payAmount12, receiveAmount12, unPayAmount12));
    }

    public List<CreditItemBean> getList() {
        return list;
    }

    public double getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public double getReceiveAmount01() {
        return receiveAmount01;
    }

    public void setReceiveAmount01(double receiveAmount01) {
        this.receiveAmount01 = receiveAmount01;
    }

    public double getReceiveAmount02() {
        return receiveAmount02;
    }

    public void setReceiveAmount02(double receiveAmount02) {
        this.receiveAmount02 = receiveAmount02;
    }

    public double getReceiveAmount03() {
        return receiveAmount03;
    }

    public void setReceiveAmount03(double receiveAmount03) {
        this.receiveAmount03 = receiveAmount03;
    }

    public double getReceiveAmount04() {
        return receiveAmount04;
    }

    public void setReceiveAmount04(double receiveAmount04) {
        this.receiveAmount04 = receiveAmount04;
    }

    public double getReceiveAmount05() {
        return receiveAmount05;
    }

    public void setReceiveAmount05(double receiveAmount05) {
        this.receiveAmount05 = receiveAmount05;
    }

    public double getReceiveAmount06() {
        return receiveAmount06;
    }

    public void setReceiveAmount06(double receiveAmount06) {
        this.receiveAmount06 = receiveAmount06;
    }

    public double getReceiveAmount07() {
        return receiveAmount07;
    }

    public void setReceiveAmount07(double receiveAmount07) {
        this.receiveAmount07 = receiveAmount07;
    }

    public double getReceiveAmount08() {
        return receiveAmount08;
    }

    public void setReceiveAmount08(double receiveAmount08) {
        this.receiveAmount08 = receiveAmount08;
    }

    public double getReceiveAmount09() {
        return receiveAmount09;
    }

    public void setReceiveAmount09(double receiveAmount09) {
        this.receiveAmount09 = receiveAmount09;
    }

    public double getReceiveAmount10() {
        return receiveAmount10;
    }

    public void setReceiveAmount10(double receiveAmount10) {
        this.receiveAmount10 = receiveAmount10;
    }

    public double getReceiveAmount11() {
        return receiveAmount11;
    }

    public void setReceiveAmount11(double receiveAmount11) {
        this.receiveAmount11 = receiveAmount11;
    }

    public double getReceiveAmount12() {
        return receiveAmount12;
    }

    public void setReceiveAmount12(double receiveAmount12) {
        this.receiveAmount12 = receiveAmount12;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public double getPayAmount01() {
        return payAmount01;
    }

    public void setPayAmount01(double payAmount01) {
        this.payAmount01 = payAmount01;
    }

    public double getPayAmount02() {
        return payAmount02;
    }

    public void setPayAmount02(double payAmount02) {
        this.payAmount02 = payAmount02;
    }

    public double getPayAmount03() {
        return payAmount03;
    }

    public void setPayAmount03(double payAmount03) {
        this.payAmount03 = payAmount03;
    }

    public double getPayAmount04() {
        return payAmount04;
    }

    public void setPayAmount04(double payAmount04) {
        this.payAmount04 = payAmount04;
    }

    public double getPayAmount05() {
        return payAmount05;
    }

    public void setPayAmount05(double payAmount05) {
        this.payAmount05 = payAmount05;
    }

    public double getPayAmount06() {
        return payAmount06;
    }

    public void setPayAmount06(double payAmount06) {
        this.payAmount06 = payAmount06;
    }

    public double getPayAmount07() {
        return payAmount07;
    }

    public void setPayAmount07(double payAmount07) {
        this.payAmount07 = payAmount07;
    }

    public double getPayAmount08() {
        return payAmount08;
    }

    public void setPayAmount08(double payAmount08) {
        this.payAmount08 = payAmount08;
    }

    public double getPayAmount09() {
        return payAmount09;
    }

    public void setPayAmount09(double payAmount09) {
        this.payAmount09 = payAmount09;
    }

    public double getPayAmount10() {
        return payAmount10;
    }

    public void setPayAmount10(double payAmount10) {
        this.payAmount10 = payAmount10;
    }

    public double getPayAmount11() {
        return payAmount11;
    }

    public void setPayAmount11(double payAmount11) {
        this.payAmount11 = payAmount11;
    }

    public double getPayAmount12() {
        return payAmount12;
    }

    public void setPayAmount12(double payAmount12) {
        this.payAmount12 = payAmount12;
    }

    public double getUnPayAmount() {
        return unPayAmount;
    }

    public void setUnPayAmount(double unPayAmount) {
        this.unPayAmount = unPayAmount;
    }

    public double getUnPayAmount01() {
        return unPayAmount01;
    }

    public void setUnPayAmount01(double unPayAmount01) {
        this.unPayAmount01 = unPayAmount01;
    }

    public double getUnPayAmount02() {
        return unPayAmount02;
    }

    public void setUnPayAmount02(double unPayAmount02) {
        this.unPayAmount02 = unPayAmount02;
    }

    public double getUnPayAmount03() {
        return unPayAmount03;
    }

    public void setUnPayAmount03(double unPayAmount03) {
        this.unPayAmount03 = unPayAmount03;
    }

    public double getUnPayAmount04() {
        return unPayAmount04;
    }

    public void setUnPayAmount04(double unPayAmount04) {
        this.unPayAmount04 = unPayAmount04;
    }

    public double getUnPayAmount05() {
        return unPayAmount05;
    }

    public void setUnPayAmount05(double unPayAmount05) {
        this.unPayAmount05 = unPayAmount05;
    }

    public double getUnPayAmount06() {
        return unPayAmount06;
    }

    public void setUnPayAmount06(double unPayAmount06) {
        this.unPayAmount06 = unPayAmount06;
    }

    public double getUnPayAmount07() {
        return unPayAmount07;
    }

    public void setUnPayAmount07(double unPayAmount07) {
        this.unPayAmount07 = unPayAmount07;
    }

    public double getUnPayAmount08() {
        return unPayAmount08;
    }

    public void setUnPayAmount08(double unPayAmount08) {
        this.unPayAmount08 = unPayAmount08;
    }

    public double getUnPayAmount09() {
        return unPayAmount09;
    }

    public void setUnPayAmount09(double unPayAmount09) {
        this.unPayAmount09 = unPayAmount09;
    }

    public double getUnPayAmount10() {
        return unPayAmount10;
    }

    public void setUnPayAmount10(double unPayAmount10) {
        this.unPayAmount10 = unPayAmount10;
    }

    public double getUnPayAmount11() {
        return unPayAmount11;
    }

    public void setUnPayAmount11(double unPayAmount11) {
        this.unPayAmount11 = unPayAmount11;
    }

    public double getUnPayAmount12() {
        return unPayAmount12;
    }

    public void setUnPayAmount12(double unPayAmount12) {
        this.unPayAmount12 = unPayAmount12;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }
}
