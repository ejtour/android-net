package com.hll_sc_app.bean.report.warehouse;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

public class WareHouseDeliveryBean implements IStringArrayGenerator {

    private int sequenceNo;
    /**
     * 发货金额
     */
    private double deliveryAmount;
    /**
     * 发货单数
     */
    private int deliveryBillNum;
    /**
     * 发货商品量
     */
    private double deliveryGoodsNum;
    /**
     * 发货商品数
     */
    private int deliveryGoodsSpecNum;
    /**
     * 货主集团
     */
    private String shipperGroup;
    /**
     * 货主门店
     */
    private String shipperShop;
    /**
     * 代仓货值
     */
    private double wareHouseDeliveryGoodsAmount;

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public double getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(double deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public int getDeliveryBillNum() {
        return deliveryBillNum;
    }

    public void setDeliveryBillNum(int deliveryBillNum) {
        this.deliveryBillNum = deliveryBillNum;
    }

    public double getDeliveryGoodsNum() {
        return deliveryGoodsNum;
    }

    public void setDeliveryGoodsNum(double deliveryGoodsNum) {
        this.deliveryGoodsNum = deliveryGoodsNum;
    }

    public int getDeliveryGoodsSpecNum() {
        return deliveryGoodsSpecNum;
    }

    public void setDeliveryGoodsSpecNum(int deliveryGoodsSpecNum) {
        this.deliveryGoodsSpecNum = deliveryGoodsSpecNum;
    }

    public String getShipperGroup() {
        return shipperGroup;
    }

    public void setShipperGroup(String shipperGroup) {
        this.shipperGroup = shipperGroup;
    }

    public String getShipperShop() {
        return shipperShop;
    }

    public void setShipperShop(String shipperShop) {
        this.shipperShop = shipperShop;
    }

    public double getWareHouseDeliveryGoodsAmount() {
        return wareHouseDeliveryGoodsAmount;
    }

    public void setWareHouseDeliveryGoodsAmount(double wareHouseDeliveryGoodsAmount) {
        this.wareHouseDeliveryGoodsAmount = wareHouseDeliveryGoodsAmount;
    }

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(String.valueOf(sequenceNo));
        list.add(shipperGroup); // 货主集团
        list.add(shipperShop); // 货主门店
        list.add(CommonUtils.formatNumber(deliveryBillNum)); // 发货单数
        list.add(CommonUtils.formatNumber(deliveryGoodsSpecNum)); // 发货种数
        list.add(CommonUtils.formatNumber(deliveryGoodsNum)); //发货件数
        list.add(CommonUtils.formatMoney(deliveryAmount)); // 发货金额
        list.add(CommonUtils.formatMoney(wareHouseDeliveryGoodsAmount)); // 发货货值
        return list;
    }
}
