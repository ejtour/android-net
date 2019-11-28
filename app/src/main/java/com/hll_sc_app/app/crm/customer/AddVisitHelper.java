package com.hll_sc_app.app.crm.customer;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.Gravity;

import com.hll_sc_app.app.crm.customer.search.CustomerSearchActivity;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.bean.customer.VisitPlanBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.impl.IStringListener;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/28
 */

public class AddVisitHelper {
    private Activity mContext;
    private VisitPlanBean mPlanBean;
    private SingleSelectionDialog mTypeDialog;
    private SingleSelectionDialog mLevelDialog;
    private DateWindow mTimeWindow;
    private SingleSelectionDialog mWayDialog;
    private SingleSelectionDialog mGoalDialog;

    public AddVisitHelper(VisitPlanBean planBean, Activity context) {
        mPlanBean = planBean;
        mContext = context;
    }

    public void onActivityResult(int resultCode, Intent data, IStringListener listener) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Parcelable parcelable = data.getParcelableExtra("parcelable");
                mPlanBean.inflateData(parcelable);
                listener.callback(mPlanBean.getCustomerName());
            }
        }
    }

    public void selectType(IStringListener listener) {
        if (mTypeDialog == null) {
            List<NameValue> nameValues = new ArrayList<>();
            for (int i = 1; i <= 2; i++) {
                nameValues.add(new NameValue(CustomerHelper.getVisitCustomerType(i), String.valueOf(i)));
            }
            mTypeDialog = SingleSelectionDialog.newBuilder(mContext, NameValue::getName)
                    .setTitleText("选择客户类型")
                    .refreshList(nameValues)
                    .setOnSelectListener(value -> {
                        int type = Integer.parseInt(value.getValue());
                        if (mPlanBean.getCustomerType() != type) {
                            mPlanBean.setCustomerType(type);
                            listener.callback(value.getName());
                        }
                    })
                    .create();
        }
        mTypeDialog.show();
    }

    public void selectLevel(IStringListener listener) {
        if (mLevelDialog == null) {
            List<NameValue> nameValues = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                nameValues.add(new NameValue(CustomerHelper.getCustomerMaintainLevel(i), String.valueOf(i)));
            }
            mLevelDialog = SingleSelectionDialog.newBuilder(mContext, NameValue::getName)
                    .setTitleText("选择客户级别")
                    .select(nameValues.get(0))
                    .refreshList(nameValues)
                    .setOnSelectListener(value -> {
                        int level = Integer.parseInt(value.getValue());
                        if (mPlanBean.getMaintainLevel() != level) {
                            mPlanBean.setMaintainLevel(Integer.parseInt(value.getValue()));
                            listener.callback(value.getName());
                        }
                    })
                    .create();
        }
        mLevelDialog.show();
    }

    public void selectCustomer() {
        CustomerSearchActivity.start(mContext, TextUtils.isEmpty(mPlanBean.getCustomerID()) ? mPlanBean.getPurchaserID() : mPlanBean.getCustomerID(),
                mPlanBean.getCustomerType() == 1 ? 0 : mPlanBean.getMaintainLevel() == 0 ? 1 : 2);
    }

    public void selectTime(IStringListener listener) {
        if (mTimeWindow == null) {
            mTimeWindow = new DateWindow(mContext);
            mTimeWindow.setSelectListener(date -> {
                mPlanBean.setVisitTime(CalendarUtils.toLocalDate(date));
                listener.callback(CalendarUtils.format(date, CalendarUtils.FORMAT_DATE_TIME));
            });
        }
        mTimeWindow.showAtLocation(mContext.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    public void selectWay(IStringListener listener) {
        if (mWayDialog == null) {
            List<NameValue> nameValues = new ArrayList<>();
            for (int i = 1; i <= 2; i++) {
                nameValues.add(new NameValue(CustomerHelper.getVisitWay(i), String.valueOf(i)));
            }
            mWayDialog = SingleSelectionDialog.newBuilder(mContext, NameValue::getName)
                    .setTitleText("选择拜访方式")
                    .refreshList(nameValues)
                    .setOnSelectListener(value -> {
                        mPlanBean.setVisitWay(Integer.parseInt(value.getValue()));
                        listener.callback(value.getName());

                    })
                    .create();
        }
        mWayDialog.show();
    }

    public void selectGoal(IStringListener listener) {
        if (mGoalDialog == null) {
            List<NameValue> nameValues = new ArrayList<>();
            for (int i = 1; i <= 4; i++) {
                nameValues.add(new NameValue(CustomerHelper.getVisitGoal(i), String.valueOf(i)));
            }
            mGoalDialog = SingleSelectionDialog.newBuilder(mContext, NameValue::getName)
                    .setTitleText("选择拜访目的")
                    .refreshList(nameValues)
                    .setOnSelectListener(value -> {
                        mPlanBean.setVisitGoal(Integer.parseInt(value.getValue()));
                        listener.callback(value.getName());
                    })
                    .create();
        }
        mGoalDialog.show();
    }

    public void saveSuccess() {
        ToastUtils.showShort("保存成功");
        Intent intent = new Intent();
        intent.putExtra(CustomerHelper.VISIT_KEY, mPlanBean);
        mContext.setResult(Activity.RESULT_OK, intent);
        mContext.finish();
    }
}
