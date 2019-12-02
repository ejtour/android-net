package com.hll_sc_app.app.setting.cooperation;

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
import com.hll_sc_app.base.utils.router.RightConfig;
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
 * CreateTime: 2019/7/17 10:59
 *
 * Change History:
 *
 *        Date             CR Number              Name              Description of change
 * 
 */
@Route(path = RouterConfig.COOPERATION_SETTING, extras = Constant.LOGIN_EXTRA)
public class CooperationSettingActivity extends BaseLoadActivity implements ICooperationSettingContract.ICooperationSettingView {

    //    6-添加我为合作供应商时需要验证开关
    @BindView(R.id.switch_6)
    SwitchButton mSwitch_6;

    //    18-新增合作门店时需要验证开关
    @BindView(R.id.switch_18)
    SwitchButton mSwitch_18;

    private ICooperationSettingContract.ICooperationSettingPresenter mPresent;
    private Unbinder unbinder;
    private static Integer OPEN_VALUE = 2;

    private Map<Integer, SwitchButton> typeToSwitchMap;

    private static Map<String, String> messageTitleMap;
    private static Map<String, String> messageMap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_setting);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void  initData() {
        typeToSwitchMap = new HashMap<>();
        typeToSwitchMap.put(6, mSwitch_6);
        typeToSwitchMap.put(18, mSwitch_18);

        mPresent = CooperationSettingPresenter.newInstance();
        mPresent.register(this);
        StringBuffer types = new StringBuffer();
        for (Map.Entry<Integer, SwitchButton> entry : typeToSwitchMap.entrySet()) {
            types.append(entry.getKey() + ",");
        }
        if(types != null && types.length() != 0) {
            types.deleteCharAt(types.length() - 1);
        }
        mPresent.getCooperationSetting(types.toString());
    }

    private void initView() {
        for (Map.Entry<Integer, SwitchButton> entry : typeToSwitchMap.entrySet()) {
            entry.getValue().setOnCheckedChangeListener((buttonView, isChecked) -> {
                toggleCooperationSettingStatus(isChecked, entry.getKey());
            });
        }
    }

    @Override
    public void showCooperationSetting(List<GroupParame> groupParameList) {
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
    public void toggleCooperationSettingStatus(boolean isChecked, Integer type) {
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
        if (!RightConfig.checkRight(getString(R.string.right_workingMealSetting_update))) {
            returnCheckStatus(!isChecked, type);
            showToast(getString(R.string.right_tips));
            return;
        }
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle(messageTitleMap.get(type + "_" + isChecked))
                .setMessage(messageMap.get(type + "_" + isChecked))
                .setCancelable(false)
                .setButton(((dialog, item) -> {
                    if (1 == item) {
                        mPresent.changeCooperationSetting(isChecked, type);
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
        messageTitleMap.put("6_true", "添加合作供应商需要验证？");
        messageMap.put("6_true", "");
        messageTitleMap.put("6_false", "添加合作供应商不需验证？");
        messageMap.put("6_false", "");
        //非营业时间不接单开关
        messageTitleMap.put("18_true", "添加合作门店需要验证？");
        messageMap.put("18_true", "");
        messageTitleMap.put("18_false", "添加合作门店不需要验证？");
        messageMap.put("18_false", "");
    }
}
