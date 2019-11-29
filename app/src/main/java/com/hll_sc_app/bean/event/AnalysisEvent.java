package com.hll_sc_app.bean.event;

import com.hll_sc_app.bean.operationanalysis.AnalysisBean;
import com.hll_sc_app.bean.operationanalysis.AnalysisResp;
import com.hll_sc_app.bean.operationanalysis.LostResp;
import com.hll_sc_app.bean.operationanalysis.TopTenResp;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/14
 */

public class AnalysisEvent {
    private AnalysisResp mAnalysisResp;
    private LostResp mLostResp;
    private TopTenResp mTopTenResp;
    private AnalysisEvent mBackup;
    private int timeType;

    public AnalysisResp getAnalysisResp() {
        return mAnalysisResp;
    }

    public void setAnalysisResp(AnalysisResp analysisResp) {
        if (analysisResp != null) {
            List<AnalysisBean> records = analysisResp.getRecords();
            if (records.size() > 0) {
                records.remove(0);
            }
        }
        mAnalysisResp = analysisResp;
    }

    public LostResp getLostResp() {
        return mLostResp;
    }

    public void setLostResp(LostResp lostResp) {
        mLostResp = lostResp;
    }

    public TopTenResp getTopTenResp() {
        return mTopTenResp;
    }

    public void setTopTenResp(TopTenResp topTenResp) {
        mTopTenResp = topTenResp;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public void reset() {
        backup();
        mAnalysisResp = null;
        mLostResp = null;
        mTopTenResp = null;
    }

    public boolean done() {
        return mAnalysisResp != null && mLostResp != null && mTopTenResp != null;
    }

    private void backup() {
        mBackup = new AnalysisEvent();
        mBackup.mAnalysisResp = mAnalysisResp;
        mBackup.mLostResp = mLostResp;
        mBackup.mTopTenResp = mTopTenResp;
    }

    public void revert() {
        if (mBackup != null) {
            mAnalysisResp = mBackup.getAnalysisResp();
            mLostResp = mBackup.getLostResp();
            mTopTenResp = mBackup.getTopTenResp();
            mBackup = null;
        }
    }
}
