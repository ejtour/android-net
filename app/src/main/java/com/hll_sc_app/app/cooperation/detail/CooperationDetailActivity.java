package com.hll_sc_app.app.cooperation.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合作采购商详情
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_DETAIL, extras = Constant.LOGIN_EXTRA)
public class CooperationDetailActivity extends BaseLoadActivity implements CooperationDetailContract.IGoodsRelevancePurchaserView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Autowired(name = "object0")
    String mPurchaserId;
    @BindView(R.id.img_logoUrl)
    GlideImageView mImgLogoUrl;
    @BindView(R.id.txt_purchaserName)
    TextView mTxtName;
    @BindView(R.id.txt_defaultSettlementWay)
    TextView mTxtDefaultSettlementWay;
    @BindView(R.id.txt_defaultDeliveryWay)
    TextView mTxtDefaultDeliveryWay;
    @BindView(R.id.txt_shopCount)
    TextView mTxtShopCount;

    private CooperationDetailPresenter mPresenter;
    private PurchaserShopListAdapter mAdapter;
    private ContextOptionsWindow mOptionsWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_purchaser_detail);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = CooperationDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new PurchaserShopListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PurchaserShopBean bean = mAdapter.getItem(position);
            if (bean == null) {
                return;
            }
            if (view.getId() == R.id.txt_del) {
                showDelTipsDialog(bean);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    /**
     * 删除门店合作提示框
     *
     * @param bean 采购商
     */
    private void showDelTipsDialog(PurchaserShopBean bean) {
        SuccessDialog.newBuilder(this)
            .setImageTitle(R.drawable.ic_dialog_failure)
            .setImageState(R.drawable.ic_dialog_state_failure)
            .setMessageTitle("确认解除合作吗？")
            .setMessage("解除合作关系后，将不能再为该门店下单")
            .setButton((dialog, item) -> {
                dialog.dismiss();
                if (item == 1) {
                    mPresenter.delCooperationPurchaser(bean.getPurchaserID());
                } else {
                    SwipeItemLayout.closeAllItems(mRecyclerView);
                }
            }, "我再看看", "立即解除").create().show();
    }

    @Override
    public void showPurchaserDetail(CooperationPurchaserDetail resp) {
        mImgLogoUrl.setImageURL(resp.getLogoUrl());
        mTxtName.setText(resp.getName());
        mTxtDefaultDeliveryWay.setText(String.format("默认结算方式：%s", getDeliveryWay(resp.getDefaultDeliveryWay())));
        mTxtDefaultSettlementWay.setText(String.format("默认配送方式：%s", getSettlementWay(resp.getDefaultSettlementWay())));
        mTxtShopCount.setText(String.format("当前合作门店数量：%s", CommonUtils.formatNumber(resp.getShopCount())));
        mAdapter.setNewData(resp.getShopDetailList());
        mAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("还没有合作门店数据").create());
    }

    @Override
    public String getPurchaserId() {
        return mPurchaserId;
    }

    /**
     * 获取配送方式显示
     *
     * @param defaultDeliveryWay 配送方式数字表示
     * @return 配送方式
     */
    private String getDeliveryWay(String defaultDeliveryWay) {
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(defaultDeliveryWay)) {
            String[] strings = defaultDeliveryWay.split(",");
            for (String s : strings) {
                if (TextUtils.equals(s, "1")) {
                    builder.append("自有物流配送").append("/");
                } else if (TextUtils.equals(s, "2")) {
                    builder.append("上门自提").append("/");
                } else if (TextUtils.equals(s, "3")) {
                    builder.append("第三方配送公司").append("/");
                }
            }
        }
        if (builder.length() != 0) {
            builder.delete(builder.length() - 1, builder.length());
        } else {
            builder.append("无");
        }
        return builder.toString();
    }

    /**
     * 获取结算方式显示
     *
     * @param defaultSettlementWay 结算方式数字表示
     * @return 结算方式
     */
    private String getSettlementWay(String defaultSettlementWay) {
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(defaultSettlementWay)) {
            String[] strings = defaultSettlementWay.split(",");
            for (String s : strings) {
                if (TextUtils.equals(s, "1")) {
                    builder.append("货到付款").append("/");
                } else if (TextUtils.equals(s, "2")) {
                    builder.append("账期支付").append("/");
                } else if (TextUtils.equals(s, "3")) {
                    builder.append("线上支付").append("/");
                }
            }
        }
        if (builder.length() != 0) {
            builder.delete(builder.length() - 1, builder.length());
        } else {
            builder.append("无");
        }
        return builder.toString();
    }

    @OnClick({R.id.img_close, R.id.txt_options})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_options:
                showOptionsWindow(view);
                break;
            default:
                break;
        }
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_cooperation_detail_settlement,
                OptionType.OPTION_COOPERATION_DETAIL_SETTLEMENT));
            list.add(new OptionsBean(R.drawable.ic_cooperation_detail_salesman,
                OptionType.OPTION_COOPERATION_DETAIL_SALESMAN));
            list.add(new OptionsBean(R.drawable.ic_cooperation_detail_driver,
                OptionType.OPTION_COOPERATION_DETAIL_DRIVER));
            list.add(new OptionsBean(R.drawable.ic_cooperation_detail_driver,
                OptionType.OPTION_COOPERATION_DETAIL_DELIVERY));
            list.add(new OptionsBean(R.drawable.ic_cooperation_detail_shop, OptionType.OPTION_COOPERATION_DETAIL_SHOP));
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
        if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_DETAIL_SETTLEMENT)) {
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_DETAIL_SALESMAN)) {
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_DETAIL_DRIVER)) {
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_DETAIL_DELIVERY)) {
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_DETAIL_SHOP)) {
        }
        mOptionsWindow.dismiss();
    }

    private static class PurchaserShopListAdapter extends BaseQuickAdapter<PurchaserShopBean, BaseViewHolder> {

        PurchaserShopListAdapter() {
            super(R.layout.item_cooperation_purchaser_shop);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.addOnClickListener(R.id.txt_del);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserShopBean item) {
            helper.setText(R.id.txt_shopName, item.getShopName())
                .setText(R.id.txt_shopAdmin, "联系人：" + getString(item.getShopAdmin()) + " / "
                    + getString(PhoneUtil.formatPhoneNum(item.getShopPhone())))
                .setText(R.id.txt_shopAddress, "地址：" + getString(item.getShopAddress()))
                .setGone(R.id.txt_newShopNum, CommonUtils.getDouble(item.getStatus()) == 0);
            ((GlideImageView) helper.getView(R.id.img_imagePath)).setImageURL(item.getImagePath());
        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }
    }
}
