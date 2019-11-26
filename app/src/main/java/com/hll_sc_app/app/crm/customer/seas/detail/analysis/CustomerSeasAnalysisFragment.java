package com.hll_sc_app.app.crm.customer.seas.detail.analysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.widget.EmptyView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/26
 */

public class CustomerSeasAnalysisFragment extends BaseLazyFragment {

    public static CustomerSeasAnalysisFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CustomerSeasAnalysisFragment fragment = new CustomerSeasAnalysisFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return EmptyView.newBuilder(requireActivity())
                .setImage(R.drawable.ic_work_in_progress)
                .setTipsTitle("程序员正在夜以继日开发中...")
                .setTips("敬请期待！").create();
    }

    @Override
    protected void initData() {

    }
}
