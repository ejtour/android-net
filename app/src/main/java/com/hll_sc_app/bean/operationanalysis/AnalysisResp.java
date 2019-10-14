package com.hll_sc_app.bean.operationanalysis;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/14
 */

public class AnalysisResp {
    private AnalysisDataBean analysisData;
    private List<AnalysisBean> records;

    public AnalysisDataBean getAnalysisData() {
        return analysisData;
    }

    public void setAnalysisData(AnalysisDataBean analysisData) {
        this.analysisData = analysisData;
    }

    public List<AnalysisBean> getRecords() {
        return records;
    }

    public void setRecords(List<AnalysisBean> records) {
        this.records = records;
    }
}
