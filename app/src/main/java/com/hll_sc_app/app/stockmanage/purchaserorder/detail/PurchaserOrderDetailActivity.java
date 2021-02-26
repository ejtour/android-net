package com.hll_sc_app.app.stockmanage.purchaserorder.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderBean;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderDetailBean;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderDetailResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.ShareDialog;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 采购单详情查询
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.STOCK_PURCHASER_ORDER_DETAIL)
public class PurchaserOrderDetailActivity extends BaseLoadActivity implements IPurchaserOrderDetailContract.IPurchaserOrderDetailView {
    @Autowired(name = "object0")
    String mPurchaserBillID;
    @BindView(R.id.pod_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.pod_status)
    TextView mStatus;
    @BindView(R.id.pod_name)
    TextView mName;
    @BindView(R.id.pod_no)
    TextView mNo;
    @BindView(R.id.pod_purchase_date)
    TextView mPurchaseDate;
    @BindView(R.id.pod_arrive_date)
    TextView mArriveDate;
    @BindView(R.id.pod_org)
    TextView mOrg;
    @BindView(R.id.pod_remark)
    TextView mRemark;
    @BindView(R.id.pod_category)
    TextView mCategory;
    @BindView(R.id.pod_num)
    TextView mNum;
    @BindView(R.id.pod_amount)
    TextView mAmount;
    @BindView(R.id.pod_list_view)
    RecyclerView mListView;
    private ShareDialog mShareDialog;
    private PurchaserOrderDetailAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_purchaser_order_detail);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        IPurchaserOrderDetailContract.IPurchaserOrderDetailPresenter presenter = PurchaserOrderDetailPresenter.newInstance();
        presenter.register(this);
        presenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showShareDialog);
        mAdapter = new PurchaserOrderDetailAdapter();
        mListView.setAdapter(mAdapter);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), ViewUtils.dip2px(this, 0.5f));
        decor.setLineMargin(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0, Color.WHITE);
        mListView.addItemDecoration(decor);
    }

    public static void start(String purchaserBillID) {
        RouterUtil.goToActivity(RouterConfig.STOCK_PURCHASER_ORDER_DETAIL, purchaserBillID);
    }

    @Override
    protected void onDestroy() {
        if (mShareDialog != null) mShareDialog.release();
        super.onDestroy();
    }

    private void showShareDialog(View view) {
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(this);
            JSONObject json = new JSONObject();
            try {
                json.put("url", HttpConfig.getHost() + HttpConfig.URL);
                JSONObject obj = new JSONObject();
                obj.put("purchaserBillID", mPurchaserBillID);
                obj.put("groupID", UserConfig.getGroupID());
                json.put("body", obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String odmId = com.hll_sc_app.base.BuildConfig.ODM_ID;
            String url = getString(R.string.share_domain)
                    + "/client/sharePurchase?shareData="
                    + Base64.encodeToString(json.toString().getBytes(), Base64.NO_WRAP)
                    + "&odmId=" + odmId;
            mShareDialog.setData(ShareDialog.ShareParam.createWebShareParam(
                    "采购单分享",
                    getString(R.string.share_icon),
                    BuildConfig.ODM_NAME + "采购单分享",
                    BuildConfig.ODM_NAME + "的生鲜食材很棒棒呦，快来看看吧~",
                    url
            ).setShareTimeLine(false).setShareQzone(false));
        }
        mShareDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mShareDialog != null) mShareDialog.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setData(PurchaserOrderDetailResp resp) {
        PurchaserOrderBean record = resp.getRecord();
        mName.setText(record.getSupplierName());
        mStatus.setText(record.getStatusDesc());
        mNo.setText(String.format("采购单号：%s", record.getBillNo()));
        mPurchaseDate.setText(String.format("采购日期：%s", DateUtil.getReadableTime(record.getBillDate(), Constants.SLASH_YYYY_MM_DD)));
        mArriveDate.setText(String.format("到货日期：%s", DateUtil.getReadableTime(record.getBillExecuteDate(), Constants.SLASH_YYYY_MM_DD)));
        mOrg.setText(String.format("采购组织：%s/%s", record.getDemandName(), record.getBillCreateBy()));
        mRemark.setText(String.format("备注信息：%s", TextUtils.isEmpty(record.getBillRemark()) ? "无" : record.getBillRemark()));
        mNum.setText(String.valueOf(0));
        mCategory.setText(String.valueOf(0));
        mAmount.setText(String.format("¥%s", CommonUtils.formatMoney(record.getTotalPrice())));
        List<PurchaserOrderDetailBean> records = resp.getRecords();
        if (!CommonUtils.isEmpty(records)) {
            double totalNum = 0;
            for (PurchaserOrderDetailBean bean : records) {
                totalNum = CommonUtils.addDouble(totalNum, bean.getGoodsNum(), 0).doubleValue();
            }
            mNum.setText(CommonUtils.formatNum(totalNum));
            mCategory.setText(CommonUtils.formatNum(records.size()));
            mAdapter.setNewData(records);
        }
    }

    @Override
    public String getPurchaserBillID() {
        return mPurchaserBillID;
    }

    static class PurchaserOrderDetailAdapter extends BaseQuickAdapter<PurchaserOrderDetailBean, BaseViewHolder> {

        PurchaserOrderDetailAdapter() {
            super(R.layout.item_stock_purchaser_order_detail);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserOrderDetailBean bean) {
            helper.setText(R.id.pod_name, bean.getGoodsName())
                    .setText(R.id.pod_spec, String.format("%s/%s", bean.getGoodsDesc(), bean.getPurchaseUnit()))
                    .setText(R.id.pod_num, String.format("%s%s", CommonUtils.formatNum(bean.getGoodsNum()), bean.getPurchaseUnit()));
        }
    }
}
