package com.hll_sc_app.bean.home;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/20
 */

public class VisitResp {
    private int activeVisitPlanCount;
    private int activeVisitRecordCount;
    private int visitPlanCount;
    private int visitRecordCount;

    public int getActiveVisitPlanCount() {
        return activeVisitPlanCount;
    }

    public void setActiveVisitPlanCount(int activeVisitPlanCount) {
        this.activeVisitPlanCount = activeVisitPlanCount;
    }

    public int getActiveVisitRecordCount() {
        return activeVisitRecordCount;
    }

    public void setActiveVisitRecordCount(int activeVisitRecordCount) {
        this.activeVisitRecordCount = activeVisitRecordCount;
    }

    public int getVisitPlanCount() {
        return visitPlanCount;
    }

    public void setVisitPlanCount(int visitPlanCount) {
        this.visitPlanCount = visitPlanCount;
    }

    public int getVisitRecordCount() {
        return visitRecordCount;
    }

    public void setVisitRecordCount(int visitRecordCount) {
        this.visitRecordCount = visitRecordCount;
    }
}
