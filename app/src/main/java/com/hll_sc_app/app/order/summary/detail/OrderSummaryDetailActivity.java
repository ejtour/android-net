package com.hll_sc_app.app.order.summary.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.summary.SummaryShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/28
 */
@Route(path = RouterConfig.ORDER_SUMMARY_DETAIL)
public class OrderSummaryDetailActivity extends BaseActivity {

    @BindView(R.id.osd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.osd_image)
    GlideImageView mImage;
    @BindView(R.id.osd_shop)
    TextView mShop;
    @BindView(R.id.osd_group)
    TextView mGroup;
    @BindView(R.id.osd_info)
    TextView mInfo;
    @BindView(R.id.osd_list_view)
    RecyclerView mListView;
    @Autowired(name = "parcelable", required = true)
    SummaryShopBean mBean;

    public static void start(SummaryShopBean shopBean) {
        RouterUtil.goToActivity(RouterConfig.ORDER_SUMMARY_DETAIL, shopBean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_order_summary_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
    }

    private void initView() {
        if (!TextUtils.isEmpty(mBean.getStallName())) {
            mTitleBar.setHeaderTitle("档口商品明细");
            mShop.setText(String.format("%s - %s", mBean.getStallName(), mBean.getShopName()));
        } else {
            mTitleBar.setHeaderTitle("门店商品明细");
            mShop.setText(mBean.getShopName());
        }
        mImage.setImageURL(mBean.getPurchaserLogo());
        mGroup.setText(mBean.getPurchaserName());
        String info = String.format("%s种商品，共计%s件 / ¥%s", CommonUtils.formatNum(mBean.getProductCount()),
                CommonUtils.formatNum(mBean.getProductNum()),
                CommonUtils.formatMoney(mBean.getProductAmount()));
        mInfo.setText(info);
        mListView.setAdapter(new OrderSummaryDetailAdapter(mBean.getProductList()));
    }
}
