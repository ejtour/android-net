package com.hll_sc_app.app.order.summary.detail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.summary.SummaryShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ShareDialog;
import com.hll_sc_app.widget.TitleBar;

import org.json.JSONException;
import org.json.JSONObject;

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
    @BindView(R.id.osd_tag)
    TextView mTag;
    @BindView(R.id.osd_group)
    TextView mGroup;
    @BindView(R.id.osd_info)
    TextView mInfo;
    @BindView(R.id.osd_status)
    TextView mStatus;
    @BindView(R.id.osd_list_view)
    RecyclerView mListView;
    @Autowired(name = "parcelable", required = true)
    SummaryShopBean mBean;
    private ShareDialog mShareDialog;

    public static void start(SummaryShopBean shopBean, int subBillStatus) {
        shopBean.setSubBillStatus(subBillStatus);
        RouterUtil.goToActivity(RouterConfig.ORDER_SUMMARY_DETAIL, shopBean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
    }

    private void initView() {
        mTitleBar.setHeaderTitle(getTitleContent());
        mTitleBar.setRightBtnClick(this::share);
        mTag.setVisibility(mBean.getSubbillCategory() == 2 ? View.VISIBLE : View.GONE);
        mStatus.setText(mBean.getSubBillStatus() == 1 ? "待接单" : "待发货");
        mShop.setText(!TextUtils.isEmpty(mBean.getStallID()) ? String.format("%s - %s", mBean.getStallName(), mBean.getShopName()) : mBean.getShopName());
        mImage.setImageURL(mBean.getPurchaserLogo());
        mGroup.setText(mBean.getPurchaserName());
        String info = String.format("%s种商品，共计%s件 / ¥%s", CommonUtils.formatNum(mBean.getProductCount()),
                CommonUtils.formatNum(mBean.getProductNum()),
                CommonUtils.formatMoney(mBean.getProductAmount()));
        mInfo.setText(info);
        mListView.setAdapter(new OrderSummaryDetailAdapter(mBean.getProductList()));
    }

    private String getTitleContent() {
        return !TextUtils.isEmpty(mBean.getStallID()) ? "档口商品详情" : "门店商品详情";
    }

    private void share(View view) {
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(this);
            JSONObject json = new JSONObject();
            try {
                json.put("groupID", UserConfig.getGroupID());
                json.put("purchaserID", mBean.getPurchaserID());
                json.put("shopID", mBean.getShopID());
                json.put("stallID", mBean.getStallID());
                json.put("subBillStatus", mBean.getSubBillStatus());
                json.put("subbillCategory", mBean.getSubbillCategory());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String url = getString(R.string.share_domain)
                    + "/client/orderSummary?shareData="
                    + Base64.encodeToString(json.toString().getBytes(), Base64.NO_WRAP);
            mShareDialog.setData(ShareDialog.ShareParam.createWebShareParam(
                    "分享",
                    getString(R.string.share_icon),
                    getTitleContent(),
                    BuildConfig.ODM_NAME + "的生鲜食材很棒棒呦，快来看看吧~",
                    url)
                    .setShareQzone(false)
                    .setShareTimeLine(false));
        }
        mShareDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mShareDialog != null) mShareDialog.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        if (mShareDialog != null) mShareDialog.release();
        super.onDestroy();
    }
}
