package com.hll_sc_app.app.complainmanage.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 投诉详情
 */
public class ComplainMangeDetailActivity extends BaseLoadActivity implements IComplainMangeDetailContract.IView {
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_manage_detail);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
