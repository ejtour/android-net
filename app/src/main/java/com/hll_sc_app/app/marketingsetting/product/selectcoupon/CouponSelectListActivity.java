package com.hll_sc_app.app.marketingsetting.product.selectcoupon;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.marketingsetting.CouponListBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 选择优惠券
 */

@Route(path = RouterConfig.ACTIVITY_SELECT_COUPON_LIST, extras = Constant.LOGIN_EXTRA)
public class CouponSelectListActivity extends BaseLoadActivity implements ICouponSelectListContract.IView {
    public static String RESULT_NAME = "coupon";
    @BindView(R.id.recyclerView)
    RecyclerView mList;
    @Autowired(name = "parcelable")
    CouponListBean mSelectBean;
    private Unbinder unbinder;

    private CouponAdapter mAdapter;

    private ICouponSelectListContract.IPresenter mPresenter;

    public static void start(Activity activity, int requestCode, CouponListBean selectBean) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_SELECT_COUPON_LIST, activity, requestCode, selectBean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coupon);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new CouponAdapter(null);
        mList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mSelectBean = mAdapter.getItem(position);
            mAdapter.notifyDataSetChanged();
            Intent intent = new Intent();
            intent.putExtra(RESULT_NAME, mSelectBean);
            setResult(RESULT_OK, intent);
            finish();
        });
        mPresenter = CouponSelectListPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.getCouponList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    public void showList(List<CouponListBean> couponListBeans) {
        if (couponListBeans.size() == 0) {
            mAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("您还没有可选择的优惠券").create());
        }
        mAdapter.setNewData(couponListBeans);

    }

    private String transformCouponCondition(CouponListBean bean) {
        switch (bean.getCouponCondition()) {
            case 0:
                return "无使用限制";
            case 1:
                return String.format("满%s可使用", bean.getCouponConditionValue());
            default:
                return "";
        }
    }

    public class CouponAdapter extends BaseQuickAdapter<CouponListBean, BaseViewHolder> {
        public CouponAdapter(@Nullable List<CouponListBean> data) {
            super(R.layout.list_item_select_coupon, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CouponListBean item) {
            boolean isSelected = false;
            helper.setText(R.id.txt_name, item.getDiscountName())
                    .setText(R.id.txt_rule, transformCouponCondition(item))
                    .setText(R.id.txt_price, "¥" + CommonUtils.formatMoney(item.getCouponValue()));

            if (mSelectBean != null) {
                isSelected = TextUtils.equals(mSelectBean.getDiscountID(), item.getDiscountID());
            }
            helper.setTextColor(R.id.txt_name, Color.parseColor(isSelected ? "#5695D2" : "#222222"))
                    .setTextColor(R.id.txt_price, Color.parseColor(isSelected ? "#5695D2" : "#222222"))
                    .setTextColor(R.id.txt_rule, Color.parseColor(isSelected ? "#5695D2" : "#999999"));
        }
    }

}
