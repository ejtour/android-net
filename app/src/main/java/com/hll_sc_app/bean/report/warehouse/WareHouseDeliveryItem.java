package com.hll_sc_app.bean.report.warehouse;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WareHouseDeliveryItem implements IStringArrayGenerator {

    private AtomicInteger atomicInteger = new AtomicInteger(0);
    /**
     * 发货金额
     */
    private String deliveryAmount;
    /**
     * 发货单数
     */
    private String deliveryBillNum;
    /**
     * 发货商品量
     */
    private String deliveryGoodsNum;
    /**
     * 发货商品数
     */
    private String deliveryGoodsSpecNum;
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
    private String wareHouseDeliveryGoodsAmount;


    public String getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(String deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public String getDeliveryBillNum() {
        return deliveryBillNum;
    }

    public void setDeliveryBillNum(String deliveryBillNum) {
        this.deliveryBillNum = deliveryBillNum;
    }

    public String getDeliveryGoodsNum() {
        return deliveryGoodsNum;
    }

    public void setDeliveryGoodsNum(String deliveryGoodsNum) {
        this.deliveryGoodsNum = deliveryGoodsNum;
    }

    public String getDeliveryGoodsSpecNum() {
        return deliveryGoodsSpecNum;
    }

    public void setDeliveryGoodsSpecNum(String deliveryGoodsSpecNum) {
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

    public String getWareHouseDeliveryGoodsAmount() {
        return wareHouseDeliveryGoodsAmount;
    }

    public void setWareHouseDeliveryGoodsAmount(String wareHouseDeliveryGoodsAmount) {
        this.wareHouseDeliveryGoodsAmount = wareHouseDeliveryGoodsAmount;
    }

    @Override
    public List<CharSequence> convertToRowData() {
        List<CharSequence> list = new ArrayList<>();
        list.add(atomicInteger.incrementAndGet()+"");
        list.add(getShipperGroup()); // 货主集团
        list.add(getShipperShop()); // 货主门店
        list.add(getDeliveryBillNum()); // 发货单数
        list.add(getDeliveryGoodsSpecNum()); // 发货种数
        list.add(getDeliveryGoodsNum()); //发货件数
        list.add(CommonUtils.formatMoney(Double.parseDouble(getDeliveryAmount()))); // 发货金额
        list.add(CommonUtils.formatNumber(getWareHouseDeliveryGoodsAmount())); // 发货货值
        return list;
    }
}
