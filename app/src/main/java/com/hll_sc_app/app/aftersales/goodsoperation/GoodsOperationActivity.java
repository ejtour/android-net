package com.hll_sc_app.app.aftersales.goodsoperation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.aftersales.AfterSalesDetailsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/15
 */

@Route(path = RouterConfig.AFTER_SALES_GOODS_OPERATION)
public class GoodsOperationActivity extends BaseLoadActivity implements IGoodsOperationContract.IGoodsOperationView {
    public static final int REQ_CODE = 0x295;
    public static final String PREF_DRIVER = "司机";
    public static final String PREF_WAREHOUSE = "仓库";

    @Autowired(name = "parcelable")
    AfterSalesBean mResp;
    @BindView(R.id.sgo_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.sgo_sum_amount)
    TextView mSumAmount;
    @BindView(R.id.sgo_list_view)
    RecyclerView mListView;
    private GoodsOperationAdapter mAdapter;
    private IGoodsOperationContract.IGoodsOperationPresenter mPresenter;

    public static void start(Activity context, AfterSalesBean resp) {
        RouterUtil.goToActivity(RouterConfig.AFTER_SALES_GOODS_OPERATION, context, REQ_CODE, resp);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sales_goods_operation);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initView();
        initData();
    }

    private void initData() {
        mPresenter = GoodsOperationPresenter.newInstance(mResp);
        mPresenter.register(this);
    }

    private void initView() {
        mTitleBar.setHeaderTitle(String.format("%s收货", mResp.getRefundBillStatus() == 2 ? PREF_DRIVER : PREF_WAREHOUSE));
        mAdapter = new GoodsOperationAdapter(mResp.getDetailList(), mResp.getRefundBillType() == 3, this::calcTotalAmount);
        mListView.setAdapter(mAdapter);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(90), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        calcTotalAmount();
    }

    private void calcTotalAmount() {
        double total = 0;
        for (AfterSalesDetailsBean bean : mAdapter.getData()) {
            total = CommonUtils.addDouble(0, total, CommonUtils.mulDouble(bean.getProductPrice(), bean.getRefundNum(), 4).doubleValue()).doubleValue();
        }
        String source = "¥" + CommonUtils.formatMoney(total);
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(1.6f), source.indexOf("¥") + 1, source.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSumAmount.setText(ss);
    }

    @OnClick(R.id.sgo_confirm)
    public void confirm() {
        mPresenter.doAction(mAdapter.getReqList(mResp.getRefundBillStatus() == 2));
    }

    @Override
    public void handleStatusChange() {
        setResult(RESULT_OK);
        finish();
    }
}
