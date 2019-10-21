package com.hll_sc_app.app.analysis;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.bean.event.AnalysisEvent;
import com.hll_sc_app.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.NumberFormat;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/15
 */

public abstract class BaseAnalysisFragment extends BaseLazyFragment {
    protected AnalysisEvent mAnalysisEvent;
    protected int mHighlightColor;
    protected NumberFormat mPercentInstance;

    {
        mPercentInstance = NumberFormat.getPercentInstance();
        mPercentInstance.setMaximumFractionDigits(2);
        mPercentInstance.setMinimumFractionDigits(2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mHighlightColor = ContextCompat.getColor(requireContext(), R.color.color_f99c04);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(sticky = true)
    public void handleAnalysisEvent(AnalysisEvent event) {
        mAnalysisEvent = event;
        setForceLoad(true);
        lazyLoad();
    }

    protected double getRate(double current, double last) {
        return last == 0 ? current == 0 ? 0 : 1 : (current - last) / last;
    }
}
