package com.hll_sc_app.app.deliverymanage.range;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.delivery.ProvinceListBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.GirdSimpleDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 配送管理-配送范围
 *
 * @author zhuyingsong
 * @date 2019/08/01
 */
@Route(path = RouterConfig.DELIVERY_RANGE, extras = Constant.LOGIN_EXTRA)
public class DeliveryRangeActivity extends BaseLoadActivity implements DeliveryRangeContract.IDeliveryRangeView {
    @BindView(R.id.recyclerView_select)
    RecyclerView mRecyclerViewSelect;
    @BindView(R.id.recyclerView_area)
    RecyclerView mRecyclerViewArea;

    private AreaListAdapter mAreaAdapter;
    private DeliveryRangePresenter mPresenter;
    private SelectListAdapter mSelectAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_range);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = DeliveryRangePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mAreaAdapter = new AreaListAdapter();
        mAreaAdapter.setOnItemClickListener((adapter, view, position) -> {
            // TODO:点击监听
        });
        mRecyclerViewArea.addItemDecoration(new GirdSimpleDecoration(4));
        mRecyclerViewArea.setAdapter(mAreaAdapter);

        mSelectAdapter = new SelectListAdapter();
        mSelectAdapter.setOnItemClickListener((adapter, view, position) -> {
            // TODO:点击监听
        });
        mRecyclerViewSelect.addItemDecoration(new GirdSimpleDecoration(4));
        mRecyclerViewSelect.setAdapter(mSelectAdapter);
    }

    @Subscribe
    public void onEvent(ProvinceListBean bean) {
        // 根据地区设置
        if (bean == null) {
            return;
        }
        List<ProvinceListBean> beans = mAreaAdapter.getData();
        if (!CommonUtils.isEmpty(beans)) {
            for (int position = 0; position < beans.size(); position++) {
                ProvinceListBean provinceListBean = beans.get(position);
                if (TextUtils.equals(provinceListBean.getProvinceCode(), bean.getProvinceCode())) {
                    provinceListBean.setCityList(bean.getCityList());
                    provinceListBean.setOptionalNum(bean.getOptionalNum());
                    provinceListBean.setSelectedNum(bean.getSelectedNum());
                    provinceListBean.setSelect(bean.getSelectedNum() != 0);
                    mAreaAdapter.notifyItemChanged(position);
                    break;
                }
            }
        }
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        finish();
    }

    @Override
    public void showAreaList(List<ProvinceListBean> list) {
        mAreaAdapter.setNewData(list);
    }

    @Override
    public void showSelectAreaList(List<ProvinceListBean> list) {
        mSelectAdapter.setNewData(list);
    }

    @Override
    public Context getContext() {
        return this;
    }

    class SelectListAdapter extends BaseQuickAdapter<ProvinceListBean, BaseViewHolder> {
        SelectListAdapter() {
            super(R.layout.item_delivery_range_select_area);
        }

        @Override
        protected void convert(BaseViewHolder helper, ProvinceListBean item) {
            helper.setText(R.id.txt_provinceName, item.getProvinceName())
                .setText(R.id.txt_selectedNum, String.valueOf(item.getSelectedNum()));
        }
    }

    class AreaListAdapter extends BaseQuickAdapter<ProvinceListBean, BaseViewHolder> {
        AreaListAdapter() {
            super(R.layout.item_delivery_minimum_detail_area);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.setGone(R.id.txt_selectedNum, false)
                .setGone(R.id.txt_optionalNum, false);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, ProvinceListBean item) {
            helper.setText(R.id.txt_provinceName, item.getProvinceName());
        }
    }
}
