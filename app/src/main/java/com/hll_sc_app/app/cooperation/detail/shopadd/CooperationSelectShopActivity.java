package com.hll_sc_app.app.cooperation.detail.shopadd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.CooperationDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合作采购商详情-批量修改选择门店
 *
 * @author zhuyingsong
 * @date 2019/7/18
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_DETAIL_SELECT_SHOP, extras = Constant.LOGIN_EXTRA)
public class CooperationSelectShopActivity extends BaseLoadActivity {
    public static final String TYPE_SETTLEMENT = "settlementWay";
    public static final String TYPE_DELIVERY = "deliveryWay";
    public static final String TYPE_SALESMAN = "salesRepresentative";
    public static final String TYPE_DRIVER = "driver";
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.img_allCheck)
    ImageView mImgAllCheck;
    @BindView(R.id.txt_checkNum)
    TextView mTxtCheckNum;
    @BindView(R.id.txt_confirm)
    TextView mTxtConfirm;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @Autowired(name = "parcelable1", required = true)
    ShopSettlementReq mReq;
    @Autowired(name = "parcelable", required = true)
    ArrayList<PurchaserShopBean> mData;
    private Map<String, PurchaserShopBean> mSelectMap;
    private CooperationDetailActivity.PurchaserShopListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_select_shop);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mSelectMap = new HashMap<>();
        initView();
    }

    private void initView() {
        showTitle(mReq.getActionType());
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new CooperationDetailActivity.PurchaserShopListAdapter(mSelectMap);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PurchaserShopBean bean = (PurchaserShopBean) adapter.getItem(position);
            if (bean != null) {
                addOrRemove(bean);
                checkSelectAll();
                adapter.notifyItemChanged(position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(mData);
        mAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("您还没有合作采购商门店数据").create());
    }

    private void showTitle(String type) {
        switch (type) {
            case TYPE_SETTLEMENT:
                mTxtConfirm.setText("去选择结算方式");
                mTxtTitle.setText("批量修改结算方式");
                break;
            case TYPE_SALESMAN:
                mTxtConfirm.setText("去选择销售");
                mTxtTitle.setText("批量指派销售");
                break;
            case TYPE_DRIVER:
                mTxtConfirm.setText("去选择司机");
                mTxtTitle.setText("批量指派司机");
                break;
            case TYPE_DELIVERY:
                mTxtConfirm.setText("去选择配送方式");
                mTxtTitle.setText("批量修改配送方式");
                break;
            default:
                break;
        }
    }

    private void addOrRemove(PurchaserShopBean shopBean) {
        if (shopBean == null) {
            return;
        }
        String shopId = shopBean.getShopID();
        if (mSelectMap.containsKey(shopId)) {
            mSelectMap.remove(shopId);
        } else {
            mSelectMap.put(shopId, shopBean);
        }
    }

    private void checkSelectAll() {
        if (mAdapter == null || CommonUtils.isEmpty(mAdapter.getData())) {
            return;
        }
        List<PurchaserShopBean> list = mAdapter.getData();
        boolean select = true;
        for (PurchaserShopBean bean : list) {
            if (!mSelectMap.containsKey(bean.getShopID())) {
                select = false;
                break;
            }
        }
        mImgAllCheck.setSelected(select);
        mTxtCheckNum.setText(String.format(Locale.getDefault(), "已选：%d", mSelectMap.size()));
        mTxtConfirm.setEnabled(!mSelectMap.isEmpty());
    }

    @OnClick({R.id.img_close, R.id.txt_confirm, R.id.img_allCheck, R.id.txt_allCheck})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                toClose();
                break;
            case R.id.txt_confirm:
                toConfirm();
                break;
            case R.id.img_allCheck:
            case R.id.txt_allCheck:
                mImgAllCheck.setSelected(!mImgAllCheck.isSelected());
                selectAll(mImgAllCheck.isSelected());
                break;
            default:
                break;
        }
    }

    private void toClose() {
        if (mSelectMap.isEmpty()) {
            finish();
            return;
        }
        // 填写过数据后进行提示
        showTipsDialog();
    }

    private void toConfirm() {
        List<String> listShopIds = new ArrayList<>();
        List<PurchaserShopBean> listSelectShop = new ArrayList<>(mSelectMap.values());
        if (!CommonUtils.isEmpty(listSelectShop)) {
            for (PurchaserShopBean bean : listSelectShop) {
                listShopIds.add(bean.getShopID());
            }
        }
        mReq.setShopIds(listShopIds);
        switch (mReq.getActionType()) {
            case TYPE_SETTLEMENT:
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_SETTLEMENT, mReq);
                break;
            case TYPE_DELIVERY:
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_DELIVERY, mReq);
                break;
            case TYPE_SALESMAN:
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_SALES, mReq);
                break;
            default:
                break;
        }
    }

    private void selectAll(boolean select) {
        if (mAdapter == null || CommonUtils.isEmpty(mAdapter.getData())) {
            return;
        }
        if (select) {
            List<PurchaserShopBean> list = mAdapter.getData();
            for (PurchaserShopBean bean : list) {
                if (!mSelectMap.containsKey(bean.getShopID())) {
                    mSelectMap.put(bean.getShopID(), bean);
                }
            }
        } else {
            mSelectMap.clear();
        }
        mAdapter.notifyDataSetChanged();
        mTxtCheckNum.setText(String.format(Locale.getDefault(), "已选：%d", mSelectMap.size()));
        mTxtConfirm.setEnabled(!mSelectMap.isEmpty());
    }

    private void showTipsDialog() {
        SuccessDialog.newBuilder(this)
            .setImageTitle(R.drawable.ic_dialog_failure)
            .setImageState(R.drawable.ic_dialog_state_failure)
            .setMessageTitle("确认要离开么")
            .setMessage("您已经填写了部分数据，离开会\n丢失当前已填写的数据")
            .setCancelable(false)
            .setButton((dialog, item) -> {
                if (item == 0) {
                    finish();
                }
                dialog.dismiss();
            }, "确认离开", "我再想想").create().show();
    }

    @Override
    public void onBackPressed() {
        toClose();
    }
}
