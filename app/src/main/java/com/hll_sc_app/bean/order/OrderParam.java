package com.hll_sc_app.bean.order;

import com.hll_sc_app.bean.order.search.OrderSearchBean;
import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/5
 */

public class OrderParam {
    private OrderSearchBean searchBean;
    private long createStart;
    private long createEnd;
    private long executeStart;
    private long executeEnd;
    private long signStart;
    private long signEnd;

    public void setSearchBean(OrderSearchBean searchBean) {
        this.searchBean = searchBean;
    }

    public String getSearchWords() {
        return searchBean == null ? "" : searchBean.getName();
    }

    public String getSearchShopID() {
        return searchBean == null ? "" : searchBean.getShopMallId();
    }

    public long getCreateStart() {
        return createStart;
    }

    public void setCreateStart(long createStart) {
        this.createStart = createStart;
    }

    public long getCreateEnd() {
        return createEnd;
    }

    public void setCreateEnd(long createEnd) {
        this.createEnd = createEnd;
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
        return createStart == 0 ? "" : CalendarUtils.format(new Date(createStart), format);
    }

    public String getFormatCreateEnd(String format) {
        return createEnd == 0 ? "" : CalendarUtils.format(new Date(createEnd), format);
    }

    public String getFormatExecuteStart(String format) {
        return executeStart == 0 ? "" : CalendarUtils.format(new Date(executeStart), format);
    }

    public String getFormatExecuteEnd(String format) {
        return executeEnd == 0 ? "" : CalendarUtils.format(new Date(executeEnd), format);
    }

    public String getFormatSignStart(String format) {
        return signStart == 0 ? "" : CalendarUtils.format(new Date(signStart), format);
    }

    public String getFormatSignEnd(String format) {
        return signEnd == 0 ? "" : CalendarUtils.format(new Date(signEnd), format);
    }

    public void cancelTimeInterval() {
        createStart = createEnd = executeStart = executeEnd = signStart = signEnd = 0;
    }
}