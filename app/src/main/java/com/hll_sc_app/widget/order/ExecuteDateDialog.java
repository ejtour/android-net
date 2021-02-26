package com.hll_sc_app.widget.order;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/23
 */

public class ExecuteDateDialog extends BaseDialog {
    @BindView(R.id.ded_tip)
    TextView mTip;
    @BindView(R.id.ded_date)
    WheelView mDate;
    @BindView(R.id.ded_time)
    WheelView mTime;
    @BindView(R.id.ded_title)
    TextView mTitle;
    private WheelAdapter mDateAdapter;
    private WheelAdapter mTimeAdapter;
    private Map<String, List<String>> mMap;
    private int mSelectDay;
    private int mSelectTime;
    private IDayTimeCallback mCallback;

    public ExecuteDateDialog(@NonNull Activity context, String title, Map<String, List<String>> map) {
        super(context, R.style.date_picker_dialog);
        mMap = map;
        initView();
        mTitle.setText(title);
    }

    private void initView() {
        mDateAdapter = new WheelAdapter(getContext(), new ArrayList<>(mMap.keySet()));
        mDate.setViewAdapter(mDateAdapter);
        mDate.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                //no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                mSelectDay = wheel.getCurrentItem();
                mTimeAdapter.setList(mMap.get(String.valueOf(mDateAdapter.getItemText(wheel.getCurrentItem()))));
                mTime.setCurrentItem(0, true);
                mSelectTime = 0;
            }
        });
        String startDate = String.valueOf(mDateAdapter.getItemText(0));
        mTip.setText(String.format("您当前能够选择的最快配送日期为%s", startDate));
        mDate.setCurrentItem(0);

        mTimeAdapter = new WheelAdapter(getContext(), mMap.get(startDate));
        mTime.setViewAdapter(mTimeAdapter);
        mTime.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                //no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                mSelectTime = wheel.getCurrentItem();
            }
        });
        mTime.setCurrentItem(0);
    }

    public void setDayTimeCallback(IDayTimeCallback callback) {
        mCallback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_execute_date, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.BasePopupAnimation);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.gravity = Gravity.BOTTOM;
            window.setAttributes(attributes);
        }
    }

    @OnClick({R.id.ded_close, R.id.ded_ok, R.id.ded_dismiss_tip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ded_close:
                dismiss();
                break;
            case R.id.ded_ok:
                dismiss();
                if (mCallback != null) {
                    mCallback.callback(mSelectDay, mSelectTime);
                }
                break;
            case R.id.ded_dismiss_tip:
                mTip.setVisibility(View.GONE);
                break;
        }
    }

    public interface IDayTimeCallback {
        void callback(int day, int time);
    }

    private static class WheelAdapter extends AbstractWheelTextAdapter {
        private List<String> mList;

        WheelAdapter(Context context, List<String> list) {
            super(context, R.layout.window_date_select_item, R.id.txt_select_item);
            this.mList = list;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return mList.get(index);
        }

        @Override
        public int getItemsCount() {
            return mList == null ? 0 : mList.size();
        }

        public void setList(List<String> list) {
            this.mList = list;
            notifyDataInvalidatedEvent();
        }
    }
}
