package com.hll_sc_app.app.warehouse.shipper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.CloseShipper;
import com.hll_sc_app.bean.user.FollowQRResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.WXFollowDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 代仓管理-我是货主页面
 *
 * @author zhuyingsong
 * @date 2019/8/7
 */
@Route(path = RouterConfig.WAREHOUSE_SHIPPER, extras = Constant.LOGIN_EXTRA)
public class ShipperActivity extends BaseLoadActivity implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.aws_title_bar)
    TitleBar mTitleBar;
    private ContextOptionsWindow mOptionsWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_shipper);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mTitleBar.setRightBtnClick(this::showAddWindow);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(CloseShipper event) {
        finish();
    }

    @OnClick({R.id.cons_shipper_goods, R.id.cons_shipper_shop,
            R.id.cons_shipper_warehouse_company, R.id.cons_follow_wx})
    public void onViewClicked(View view) {
        int i = view.getId();
        switch (i) {
            case R.id.cons_shipper_goods:
                RouterUtil.goToActivity(RouterConfig.WAREHOUSE_SHIPPER_GOODS);
                break;
            case R.id.cons_shipper_shop:
                RouterUtil.goToActivity(RouterConfig.WAREHOUSE_SHIPPER_SHOP);
                break;
            case R.id.cons_shipper_warehouse_company:
                RouterUtil.goToActivity(RouterConfig.WAREHOUSE_LIST);
                break;
            case R.id.cons_follow_wx:
                User.queryFollowQR(null, "2", new SimpleObserver<FollowQRResp>(this) {
                    @Override
                    public void onSuccess(FollowQRResp followQRResp) {
                        new WXFollowDialog(ShipperActivity.this)
                                .show(followQRResp.getQrcodeUrl());
                    }
                });
                break;
            default:
                break;
        }
    }

    private void showAddWindow(View view) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_warehouse_add, OptionType.OPTION_WAREHOUSE_ADD));
            list.add(new OptionsBean(R.drawable.ic_cooperation_receive, OptionType.OPTION_COOPERATION_RECEIVE));
            list.add(new OptionsBean(R.drawable.ic_cooperation_send, OptionType.OPTION_COOPERATION_SEND));
            mOptionsWindow = new ContextOptionsWindow(this).setListener(this).refreshList(list);
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.END);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null) {
            return;
        }
        if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_WAREHOUSE_ADD)) {
            RouterUtil.goToActivity(RouterConfig.WAREHOUSE_ADD);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_RECEIVE)) {
            RouterUtil.goToActivity(RouterConfig.WAREHOUSE_APPLICATION);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_SEND)) {
            RouterUtil.goToActivity(RouterConfig.WAREHOUSE_INVITE);
        }
        mOptionsWindow.dismiss();
    }
}
