package com.hll_sc_app.app.deliverymanage.minimum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.delivery.DeliveryMinimumBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 起送金额列表
 *
 * @author zhuyingsong
 * @date 2019/7/30
 */
@Route(path = RouterConfig.DELIVERY_MINIMUM, extras = Constant.LOGIN_EXTRA)
public class DeliveryMinimumActivity extends BaseLoadActivity implements DeliveryMinimumContract.IDeliveryMinimumView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private EmptyView mEmptyView;
    private MinimumListAdapter mAdapter;
    private DeliveryMinimumPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_minimum);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = DeliveryMinimumPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mEmptyView = EmptyView.newBuilder(this)
            .setTipsTitle("您还没有设置配送时效哦").setTips("点击右上角新增添加").create();
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this,
            R.color.base_color_divider), UIUtils.dip2px(5)));
        mAdapter = new MinimumListAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.start();
    }

    @OnClick({R.id.img_close, R.id.txt_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_add:
                break;
            default:
                break;
        }
    }

    @Override
    public void showDeliveryList(List<DeliveryMinimumBean> list) {
        mAdapter.setNewData(list);
        mAdapter.setEmptyView(mEmptyView);
    }

    class MinimumListAdapter extends BaseQuickAdapter<DeliveryMinimumBean, BaseViewHolder> {
        MinimumListAdapter() {
            super(R.layout.item_delivery_minimum);
        }

        @Override
        protected void convert(BaseViewHolder helper, DeliveryMinimumBean item) {
            helper.setText(R.id.txt_settings, TextUtils.equals(item.getSettings(), "0") ? getString(R.string.area) :
                "采购商")
                .setBackgroundRes(R.id.txt_settings, TextUtils.equals(item.getSettings(), "0") ?
                    R.drawable.bg_tag_primary_solid : R.drawable.bg_tag_red_solid)
                .setText(R.id.txt_divideName, item.getDivideName())
                .setText(R.id.txt_areaNum, TextUtils.equals(item.getSettings(), "0") ? "包含" + item.getAreaNum() +
                    "地区" : "包含" + item.getAreaNum() + "门店")
                .setText(R.id.txt_sendPrice,
                    "¥" + CommonUtils.formatMoney(CommonUtils.getDouble(item.getSendPrice())) + "起")
            ;
        }
    }
}
