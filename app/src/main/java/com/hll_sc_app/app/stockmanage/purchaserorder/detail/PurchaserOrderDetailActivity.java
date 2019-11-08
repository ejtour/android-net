package com.hll_sc_app.app.stockmanage.purchaserorder.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderDetailRecord;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderDetailResp;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderRecord;
import com.hll_sc_app.bean.stockmanage.purchaserorder.UrlObject;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.ShareDialog;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 采购单详情查询
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.STOCK_PURCHASER_ORDER_DETAIL)
public class PurchaserOrderDetailActivity extends BaseLoadActivity implements PurchaserOrderDetailContract.IPurchaserOrderDetailView {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Autowired(name = "object0")
    String mPurchaserBillID = null;
    @BindView(R.id.purchaser_order_comp_name)
    TextView purchaserOrderCompName;
    @BindView(R.id.purchaser_order_status)
    TextView purchaserOrderStatus;
    @BindView(R.id.purchaser_order_no)
    TextView purchaserOrderNo;
    @BindView(R.id.purchaser_order_create_time)
    TextView purchaserOrderCreateTime;
    @BindView(R.id.purchaser_order_orig)
    TextView purchaserOrderOrig;
    @BindView(R.id.purchaser_order_arrival_date)
    TextView purchaserOrderArrivalDate;
    @BindView(R.id.purchaser_order_mark)
    TextView purchaserOrderMark;
    @BindView(R.id.purchaser_order_category_num)
    TextView purchaserOrderCategoryNum;
    @BindView(R.id.purchaser_order_num)
    TextView purchaserOrderNum;
    @BindView(R.id.purchaser_order_amount)
    TextView purchaserOrderAmount;
    private ShareDialog mShareDialog;

    private String supplyerName;

    private PurchaserOrderDetailListAdapter mAdapter;
    private PurchaserOrderDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_purchaser_order_detail);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = PurchaserOrderDetailPresenter.newInstance();
        mAdapter = new PurchaserOrderDetailListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.register(this);
        mPresenter.start();
    }

    public static void start(String purchaserBillID) {
        RouterUtil.goToActivity(RouterConfig.STOCK_PURCHASER_ORDER_DETAIL, purchaserBillID);
    }

    @Override
    protected void onDestroy() {
        if (mShareDialog != null) mShareDialog.release();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.img_back,R.id.shared_purchaser_order_detail_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.shared_purchaser_order_detail_btn:
                if (mShareDialog == null) {
                    mShareDialog = new ShareDialog(this);
                    UrlObject urlObject = new UrlObject();
                    urlObject.setUrl(HttpConfig.getHost() + HttpConfig.URL);
                    UrlObject.UrlData body = new UrlObject.UrlData();
                    body.setPurchaserBillID(mPurchaserBillID);
                    body.setGroupID(UserConfig.getGroupID());
                    urlObject.setBody(body);
                    String url = "http://172.16.32.222:3001/client/sharePurchase?shareData="
                            + Base64.encodeToString(JsonUtil.toJson(urlObject).getBytes(), Base64.NO_WRAP);
                    mShareDialog.setData(ShareDialog.ShareParam.createWebShareParam(
                            "采购单分享",
                            "http://res.hualala.com/group3/M02/11/E4/wKgVbV3FKiGutZpqAABNhltbYDI135.png",
                            "二十二城采购单分享",
                            "二十二城的生鲜食材很棒棒呦，快来看看吧~",
                            url
                    ).setShareTimeLine(false).setShareQzone(false));
                }
                mShareDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mShareDialog != null) mShareDialog.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showPurchaserOrderDetail(PurchaserOrderDetailResp purchaserOrderDetailResp) {
        if (!CommonUtils.isEmpty(purchaserOrderDetailResp.getRecords())) {
            PurchaserOrderRecord record = purchaserOrderDetailResp.getRecord();
            supplyerName = record.getSupplierName();
            purchaserOrderCompName.setText(supplyerName);
            purchaserOrderStatus.setText(record.getStatusDesc());
            purchaserOrderNo.setText(String.format("%s : %s", "采购单号", record.getBillNo()));
            purchaserOrderCreateTime.setText(String.format("%s : %s", "采购日期",
                    CalendarUtils.getDateFormatString(record.getBillDate(), CalendarUtils.FORMAT_LOCAL_DATE, Constants.SLASH_YYYY_MM_DD)));
            purchaserOrderOrig.setText(String.format("%s : %s", "采购组织", record.getSupplierName()));
            purchaserOrderArrivalDate.setText(String.format("%s : %s", "到货日期",
                    CalendarUtils.getDateFormatString(record.getBillExecuteDate(), CalendarUtils.FORMAT_LOCAL_DATE, Constants.SLASH_YYYY_MM_DD)));
            purchaserOrderMark.setText(String.format("%s : %s", "备注信息", StringUtil.isEmpty(record.getBillRemark()) ? "无" : record.getBillRemark()));
            purchaserOrderCategoryNum.setText(purchaserOrderDetailResp.getRecords().size() + "");
            BigDecimal totalNum = BigDecimal.ZERO;
            for (PurchaserOrderDetailRecord r : purchaserOrderDetailResp.getRecords()) {
                totalNum = totalNum.add(new BigDecimal(r.getGoodsNum()));
            }
            purchaserOrderNum.setText(totalNum.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
            purchaserOrderAmount.setText(String.format("%s %s", "¥", CommonUtils.formatMoney(new BigDecimal(record.getTotalPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())));
            mAdapter.addData(purchaserOrderDetailResp.getRecords());
        }
    }

    @Override
    public String getPurchaserBillID() {
        return mPurchaserBillID;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    class PurchaserOrderDetailListAdapter extends BaseQuickAdapter<PurchaserOrderDetailRecord, BaseViewHolder> {

        PurchaserOrderDetailListAdapter() {
            super(R.layout.item_stock_purchaser_order_detail);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserOrderDetailRecord bean) {
            helper.setText(R.id.txt_purchaser_order_goods_name, bean.getGoodsName())
                    .setText(R.id.txt_purchaser_order_goods_desc, String.format("%s/%s", bean.getGoodsDesc(), bean.getPurchaseUnit()))
                    .setText(R.id.txt_purchaser_order_goods_num, String.format("%s/%s", new BigDecimal(bean.getGoodsNum()).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString(), bean.getPurchaseUnit()));
        }
    }

}
