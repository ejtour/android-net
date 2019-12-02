package com.hll_sc_app.app.setting.bill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.user.GroupParame;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author baitianqi baitianqi@hualala.com
 *
 * version 1.0.0
 * Copyright (C) 2017-2018 hualala
 *           This program is protected by copyright laws.
 *           Program Name:哗啦啦商城
 *
 * Description:
 *
 * CreateTime: 2019/7/12 18:11
 *
 * Change History:
 *
 *        Date             CR Number              Name              Description of change
 *
 */

@Route(path = RouterConfig.BILL_SETTING, extras = Constant.LOGIN_EXTRA)
public class BillSettingActivity extends BaseLoadActivity implements IBillSettingContract.IBillSettingView {

//    2-自动接单开关
    @BindView(R.id.switch_2)
    SwitchButton mSwitch_2;

//    3-自动发货开关
    @BindView(R.id.switch_3)
    SwitchButton mSwitch_3;

//    4-只允许合作采购商下单
    @BindView(R.id.switch_4)
    SwitchButton mSwitch_4;

//    7-是否仅接单(获取多个开关参数用逗号拼接)
    @BindView(R.id.switch_7)
    SwitchButton mSwitch_7;

//    8-供应商验货开关
    @BindView(R.id.switch_8)
    SwitchButton mSwitch_8;

//    9-供应商只允许通过协议价下单开关
    @BindView(R.id.switch_9)
    SwitchButton mSwitch_9;

//    11-非营业时间不接单开关
    @BindView(R.id.switch_11)
    SwitchButton mSwitch_11;

//    12-代仓订单自动接单模式
    @BindView(R.id.switch_12)
    SwitchButton mSwitch_12;

//    14-退换货自动审核开关
    @BindView(R.id.switch_14)
    SwitchButton mSwitch_14;

//    15-司机验货确认开关
    @BindView(R.id.switch_15)
    SwitchButton mSwitch_15;

//    21-设置销售端退货限制退货时效
    @BindView(R.id.switch_21)
    SwitchButton mSwitch_21;

//    25-导出配送单带价格
    @BindView(R.id.switch_25)
    SwitchButton mSwitch_25;

    private IBillSettingContract.IBillSettingPresenter mPresent;
    private Unbinder unbinder;
    private static Integer OPEN_VALUE = 2;

    private Map<Integer, SwitchButton> typeToSwitchMap;

    private static Map<String, String> messageTitleMap;
    private static Map<String, String> messageMap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_setting);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void  initData() {
        typeToSwitchMap = new HashMap<>();
        typeToSwitchMap.put(2, mSwitch_2);
        typeToSwitchMap.put(3, mSwitch_3);
        typeToSwitchMap.put(4, mSwitch_4);
        typeToSwitchMap.put(7, mSwitch_7);
        typeToSwitchMap.put(8, mSwitch_8);
        typeToSwitchMap.put(9, mSwitch_9);
        typeToSwitchMap.put(11, mSwitch_11);
        typeToSwitchMap.put(12, mSwitch_12);
        typeToSwitchMap.put(14, mSwitch_14);
        typeToSwitchMap.put(15, mSwitch_15);
        typeToSwitchMap.put(21, mSwitch_21);
        typeToSwitchMap.put(25, mSwitch_25);

        mPresent = BillSettingPresenter.newInstance();
        mPresent.register(this);
        StringBuffer types = new StringBuffer();
        for (Map.Entry<Integer, SwitchButton> entry : typeToSwitchMap.entrySet()) {
            types.append(entry.getKey() + ",");
        }
        if(types != null && types.length() != 0) {
            types.deleteCharAt(types.length() - 1);
        }
        mPresent.getBillSetting(types.toString());
    }

    private void initView() {
        for (Map.Entry<Integer, SwitchButton> entry : typeToSwitchMap.entrySet()) {
            entry.getValue().setOnCheckedChangeListener((buttonView, isChecked) -> {
                toggleBillSettingStatus(isChecked, entry.getKey());
            });
        }
    }

    @Override
    public void showBillSetting(List<GroupParame> groupParameList) {
        if (groupParameList.size() > 0) {
            for (GroupParame groupParame : groupParameList) {
                SwitchButton switchButton = typeToSwitchMap.get(groupParame.getParameType());
                if(switchButton != null) {
                    switchButton.setCheckedNoEvent(OPEN_VALUE == groupParame.getParameValue());
                }
            }
        }
    }

    @Override
    public void toggleBillSettingStatus(boolean isChecked, Integer type) {
        showAlertDialog(isChecked, type);
    }

    @Override
    public void returnCheckStatus(boolean oldChecked, Integer type) {
        SwitchButton switchButton = typeToSwitchMap.get(type);
        if(switchButton != null) {
            switchButton.setCheckedNoEvent(oldChecked);
        }
    }

    @Override
    public void showAlertDialog(boolean isChecked, Integer type) {
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle(messageTitleMap.get(type + "_" + isChecked))
                .setMessage(messageMap.get(type + "_" + isChecked))
                .setCancelable(false)
                .setButton(((dialog, item) -> {
                    if (1 == item) {
                        mPresent.changeBillSetting(isChecked, type);
                    }else {
                        returnCheckStatus(!isChecked, type);
                    }
                    dialog.dismiss();
                }), "取消", "确定")
                .create()
                .show();
    }

    public SwitchButton getSettingButton(Integer type) {
        return typeToSwitchMap.get(type);
    }

    //提示语初始化
    static {
        messageMap = new HashMap<>();
        messageTitleMap = new HashMap<>();
        //司机验货确认开关
        messageTitleMap.put("15_true", "确认开启验货模式？");
        messageMap.put("15_true", "");
        messageTitleMap.put("15_false", "确认关闭验货模式？");
        messageMap.put("15_false", "");
        //非营业时间不接单开关
        messageTitleMap.put("11_true", "开启非营业时间不接单？");
        messageMap.put("11_true", "开启后，除营业时间外不接单");
        messageTitleMap.put("11_false", "关闭非营业时间不接单？");
        messageMap.put("11_false", "关闭后，任何时间下单都可接单");
        //代仓订单自动接单模式
        messageTitleMap.put("12_true", "开启代仓自动接单模式？");
        messageMap.put("12_true", "开启后，代仓订单无需手动接单");
        messageTitleMap.put("12_false", "关闭代仓自动接单模式？");
        messageMap.put("12_false", "关闭后，代仓订单需要手动接单");
        //自动接单开关
        messageTitleMap.put("2_true", "确认开启自动接单模式？");
        messageMap.put("2_true", "开启后，新的订单无需手动接单");
        messageTitleMap.put("2_false", "确认关闭自动接单模式？");
        messageMap.put("2_false", "关闭后，新的订单需要手动接单");
        //自动发货开关
        messageTitleMap.put("3_true", "确认开启自动发货开关？");
        messageMap.put("3_true", "开启后，新的已接订单无需手动发货");
        messageTitleMap.put("3_false", "确认关闭自动发货开关？");
        messageMap.put("3_false", "关闭后，新的已接订单需要手动发货");
        //只允许合作采购商下单
        messageTitleMap.put("4_true", "只允许合作采购商下单？");
        messageMap.put("4_true", "开启后，非合作采购商不能下单");
        messageTitleMap.put("4_false", "允许所有采购商下单？");
        messageMap.put("4_false", "关闭后，非合作采购商可以下单");
        //仅接单
        messageTitleMap.put("7_true", "确认开启仅接单？");
        messageMap.put("7_true", "");
        messageTitleMap.put("7_false", "确认关闭仅接单？");
        messageMap.put("7_false", "");
        //退换货自动审核开关
        messageTitleMap.put("14_true", "开启退换货自动审核？");
        messageMap.put("14_true", "开启后，新退货订单无需手动审核");
        messageTitleMap.put("14_false", "关闭退换货自动审核？");
        messageMap.put("14_false", "关闭后，新退货订单需要手动审核");
        //供应商只允许通过协议价下单开关
        messageTitleMap.put("9_true", "开启只允许协议价下单？");
        messageMap.put("9_true", "开启后，采购商必须由生效协议报价时，才能下单");
        messageTitleMap.put("9_false", "关闭只允许协议价下单？");
        messageMap.put("9_false", "关闭后，采购商可在没有协议报价时下单");
        //供应商验货开关
        messageTitleMap.put("8_true", "开启由供应商验货？");
        messageMap.put("8_true", "开启后，验货数量由供应商填写");
        messageTitleMap.put("8_false", "关闭由供应商验货？");
        messageMap.put("8_false", "关闭后，验货数量由采购商填写");
        //销售端退货限制退货时效
        messageTitleMap.put("21_true", "确认开启么？");
        messageMap.put("21_true", "开启后，销售CRM申请退货不判断退货时效");
        messageTitleMap.put("21_false", "确认关闭么？");
        messageMap.put("21_false", "关闭后，销售CRM申请退货需要根据退货时效判断");
        //导出配送单带价格
        messageTitleMap.put("25_true", "确认开启么？");
        messageMap.put("25_true", "开启后，导出配送单带价格");
        messageTitleMap.put("25_false", "确认关闭么？");
        messageMap.put("25_false", "关闭后，导出配送单隐藏价格");


    }
}
