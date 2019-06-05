package com.hll_sc_app.bean.order;

import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/5
 */

public class OrderParam {
    private int flag;
    private String searchWords;
    private long createStart;
    private long createEnd;
    private long executeStart;
    private long executeEnd;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getSearchWords() {
        return searchWords;
    }

    public void setSearchWords(String searchWords) {
        this.searchWords = searchWords;
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

    public void cancelTimeInterval() {
        createStart = createEnd = executeStart = executeEnd = 0;
    }
}