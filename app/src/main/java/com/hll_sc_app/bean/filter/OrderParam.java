package com.hll_sc_app.bean.filter;

import android.text.TextUtils;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.event.ShopSearchEvent;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/5
 */

public class OrderParam {
    private ShopSearchEvent searchBean;
    private String extraId;
    private int searchType; // 0 采购商名称 1 货主名称 2 订单号  3 汇总采购商集团  4 汇总采购商门店  5 汇总货主集团  6 汇总货主门店
    private long createStart;
    private long createEnd;
    private long executeStart;
    private long executeEnd;
    private long signStart;
    private long signEnd;
    private long tempCreateStart;
    private long tempCreateEnd;

    public void setSearchBean(ShopSearchEvent searchBean) {
        this.searchBean = searchBean;
        if (TextUtils.isEmpty(getSearchWords()) && !TextUtils.isEmpty(extraId)) {
            extraId = "";
        }
    }

    public String getSearchWords() {
        return searchBean == null ? "" : searchBean.getName();
    }

    public String getSearchShopID() {
        return searchBean == null ? "" : searchBean.getShopMallId();
    }

    public String getExtraId() {
        return extraId;
    }

    public void setExtraId(String extraId) {
        this.extraId = extraId;
    }

    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public long getCreateStart() {
        return createStart == 0 ? tempCreateStart : createStart;
    }

    public void setCreateStart(long createStart) {
        this.createStart = createStart;
    }

    public long getCreateEnd() {
        return createEnd == 0 ? tempCreateEnd : createEnd;
    }

    public void setCreateEnd(long createEnd) {
        this.createEnd = createEnd;
    }

    public void setTempCreateStart(long tempCreateStart) {
        this.tempCreateStart = tempCreateStart;
    }

    public void setTempCreateEnd(long tempCreateEnd) {
        this.tempCreateEnd = tempCreateEnd;
    }

    public long getExecuteStart() {
        return executeStart;
    }

    public void setExecuteStart(long executeStart) {
        this.executeStart = executeStart;
    }

    public long getExecuteEnd() {
        return executeEnd;
    }

    public void setExecuteEnd(long executeEnd) {
        this.executeEnd = executeEnd;
    }

    public long getSignStart() {
        return signStart;
    }

    public void setSignStart(long signStart) {
        this.signStart = signStart;
    }

    public long getSignEnd() {
        return signEnd;
    }

    public void setSignEnd(long signEnd) {
        this.signEnd = signEnd;
    }

    public String getFormatCreateStart(String format) {
        return getCreateStart() == 0 ? null : CalendarUtils.format(new Date(getCreateStart()), format);
    }

    public String getFormatCreateEnd(String format) {
        return getCreateEnd() == 0 ? null : CalendarUtils.format(new Date(getCreateEnd()), format);
    }

    public String getFormatExecuteStart(String format) {
        return executeStart == 0 ? null : CalendarUtils.format(new Date(executeStart), format);
    }

    public String getFormatExecuteEnd(String format) {
        return executeEnd == 0 ? null : CalendarUtils.format(new Date(executeEnd), format);
    }

    public String getFormatSignStart(String format) {
        return signStart == 0 ? null : CalendarUtils.format(new Date(signStart), format);
    }

    public String getFormatSignEnd(String format) {
        return signEnd == 0 ? null : CalendarUtils.format(new Date(signEnd), format);
    }

    public void cancelTimeInterval() {
        createStart = createEnd = executeStart = executeEnd = signStart = signEnd = 0;
    }

    public BaseMapReq.Builder toReqBuilder() {
        BaseMapReq.Builder builder = BaseMapReq.newBuilder()
                .put("subBillCreateTimeEnd", getFormatCreateEnd(Constants.UNSIGNED_YYYY_MM_DD))
                .put("subBillCreateTimeStart", getFormatCreateStart(Constants.UNSIGNED_YYYY_MM_DD))
                .put("subBillExecuteDateEnd", getFormatExecuteEnd(Constants.UNSIGNED_YYYY_MM_DD_HH))
                .put("subBillExecuteDateStart", getFormatExecuteStart(Constants.UNSIGNED_YYYY_MM_DD_HH))
                .put("subBillSignTimeEnd", getFormatSignEnd(Constants.UNSIGNED_YYYY_MM_DD_HH))
                .put("subBillSignTimeStart", getFormatSignStart(Constants.UNSIGNED_YYYY_MM_DD_HH));
        int searchType = getSearchType();
        String associatedID = getSearchShopID();
        String searchWords = !TextUtils.isEmpty(associatedID) ? "" : getSearchWords();
        if (searchType < 3) {
            builder.put(searchType == 2 ? "subBillNo" : searchType == 1 ? "shipperName" : "searchWords", searchWords);
        }
        if (searchType == 6) {
            builder.put("shipperID", getExtraId());
        }
        if (searchType == 0 || searchType == 4 || searchType == 6) { // 订单搜索采购商门店 || 汇总搜索采购商门店 || 汇总搜索货主门店
            builder.put("shopID", associatedID);
        } else if (searchType == 1 || searchType == 5) { // 订单搜索货主集团 || 汇总搜索货主集团
            builder.put("shipperID", associatedID);
        } else if (searchType == 3) { // 汇总搜索采购商集团
            builder.put("purchaserID", associatedID);
        }
        return builder;
    }
}