package com.hll_sc_app.widget.aptitude;

import android.view.View;

import com.hll_sc_app.bean.aptitude.AptitudeInfoReq;
import com.hll_sc_app.bean.aptitude.AptitudeInfoResp;

public interface IAptitudeInfoCallback {
    void withData(AptitudeInfoResp resp);

    AptitudeInfoReq getReq();

    void setEditable(boolean editable);


    View getView();
}