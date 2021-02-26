package com.hll_sc_app.app.analysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.bean.event.AnalysisEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/15
 */

public abstract class BaseAnalysisFragment extends BaseLazyFragment {
    protected AnalysisEvent mAnalysisEvent;
    protected int mHighlightColor;

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

    protected String absRate(String rate) {
        return rate.replace("-", "");
    }
}
