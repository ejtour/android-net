package com.hll_sc_app.app.marketingsetting.helper;

import com.hll_sc_app.bean.marketingsetting.CouponSendReq;
import com.hll_sc_app.bean.marketingsetting.MarketingCustomerBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/3/4
 */

public class MarketingHelper {
    /**
     * 将营销客户列表转换为优惠券客户列表
     *
     * @param marketingCustomerBeans 营销客户列表
     * @return 优惠券客户列表
     */
    public static ArrayList<CouponSendReq.GroupandShopsBean> convertCustomerToCouponBean(ArrayList<MarketingCustomerBean> marketingCustomerBeans) {
        ArrayList<CouponSendReq.GroupandShopsBean> list = new ArrayList<>();
        for (MarketingCustomerBean bean : marketingCustomerBeans) {
            if (bean.getType() == 1) {
                CouponSendReq.GroupandShopsBean shopsBean = new CouponSendReq.GroupandShopsBean();
                shopsBean.setPurchaserID(bean.getPurchaserID());
                shopsBean.setPurchaserName(bean.getPurchaserName());
                shopsBean.setScope(1);
                list.add(shopsBean);
            } else {
                boolean find = false;
                for (CouponSendReq.GroupandShopsBean shopsBean : list) {
                    if (shopsBean.getPurchaserID().equals(bean.getPurchaserID())) {
                        find = true;
                        shopsBean.getShopIDList().add(bean.getShopID());
                        shopsBean.getShopNameList().add(bean.getShopName());
                        break;
                    }
                }
                if (!find) {
                    CouponSendReq.GroupandShopsBean shopsBean = new CouponSendReq.GroupandShopsBean();
                    shopsBean.setPurchaserID(bean.getPurchaserID());
                    shopsBean.setPurchaserName(bean.getPurchaserName());
                    List<String> shopIdList = new ArrayList<>();
                    shopIdList.add(bean.getShopID());
                    shopsBean.setShopIDList(shopIdList);
                    List<String> shopNameList = new ArrayList<>();
                    shopNameList.add(bean.getShopName());
                    shopsBean.setShopNameList(shopNameList);
                    list.add(shopsBean);
                }
            }
        }
        return list;
    }

    /**
     * 将优惠券客户列表转换为营销客户列表
     *
     * @param customers 优惠券客户列表
     * @return 营销客户列表
     */
    public static ArrayList<MarketingCustomerBean> convertCouponBeanToCustomer(ArrayList<CouponSendReq.GroupandShopsBean> customers) {
        ArrayList<MarketingCustomerBean> list = new ArrayList<>();
        if (!CommonUtils.isEmpty(customers)) {
            for (CouponSendReq.GroupandShopsBean shopsBean : customers) {
                if (shopsBean.getScope() == 1) {
                    MarketingCustomerBean bean = new MarketingCustomerBean();
                    bean.setPurchaserID(shopsBean.getPurchaserID());
                    bean.setPurchaserName(shopsBean.getPurchaserName());
                    bean.setShopCount(shopsBean.getShopCount());
                    bean.setType(1);
                    list.add(bean);
                } else {
                    if (!CommonUtils.isEmpty(shopsBean.getShopIDList())) {
                        for (String id : shopsBean.getShopIDList()) {
                            MarketingCustomerBean bean = new MarketingCustomerBean();
                            bean.setPurchaserID(shopsBean.getPurchaserID());
                            bean.setPurchaserName(shopsBean.getPurchaserName());
                            bean.setShopID(id);
                            bean.setShopCount(1);
                            list.add(bean);
                        }
                    }
                }
            }
        }
        return list;
    }
}
