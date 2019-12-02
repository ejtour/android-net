package com.hll_sc_app.app.cooperation.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.shopadd.CooperationSelectShopActivity;
import com.hll_sc_app.app.cooperation.detail.shopsettlement.CooperationShopSettlementActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.PurchaserShopSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.bean.cooperation.CooperationShopReq;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class CooperationDetailActivity extends BaseLoadActivity implements CooperationDetailContract.ICooperationDetailView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
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
    @Autowired(name = "object0")
    String mPurchaserId;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_options)
    ImageView mImgOption;
    @BindView(R.id.cpd_search_view)
    SearchView mSearchView;
    private PurchaserShopListAdapter mAdapter;
    private CooperationPurchaserDetail mDetail;
    private ContextOptionsWindow mOptionsWindow;
    private CooperationDetailPresenter mPresenter;
    private SwipeItemLayout.OnSwipeItemTouchListener swipeItemTouchListener;

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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMorePurchaserDetail();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryPurchaserDetail(false);
            }
        });
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
            } else if (view.getId() == R.id.content) {
                bean.setPurchaserID(mDetail.getPurchaserID());
                bean.setCooperationActive(mDetail.getCooperationActive());
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_DETAIL, bean);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        swipeItemTouchListener = new SwipeItemLayout.OnSwipeItemTouchListener(this);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(CooperationDetailActivity.this, searchContent, PurchaserShopSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
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
                        toDelShop(bean);
                    } else {
                        SwipeItemLayout.closeAllItems(mRecyclerView);
                    }
                }, "我再看看", "立即解除")
                .create().show();
    }

    private void toDelShop(PurchaserShopBean bean) {
        if (mDetail == null) {
            return;
        }
        CooperationShopReq req = new CooperationShopReq();
        req.setActionType("delete");
        req.setGroupID(UserConfig.getGroupID());
        req.setOriginator("1");
        req.setPurchaserID(mDetail.getPurchaserID());
        req.setPurchaserName(mDetail.getName());
        List<CooperationShopReq.ShopBean> list = new ArrayList<>();
        list.add(new CooperationShopReq.ShopBean(bean.getShopID(), bean.getShopName()));
        req.setShopList(list);
        mPresenter.editCooperationPurchaserShop(req);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    /**
     * 门店新增
     *
     * @param listBean 新增的门店数据
     */
    @Subscribe
    public void onEvent(List<PurchaserShopBean> listBean) {
        if (CommonUtils.isEmpty(listBean) || mDetail == null) {
            return;
        }
        CooperationShopReq req = new CooperationShopReq();
        req.setActionType("insert");
        req.setGroupID(UserConfig.getGroupID());
        req.setOriginator("1");
        req.setPurchaserID(mDetail.getPurchaserID());
        req.setPurchaserName(mDetail.getName());
        List<CooperationShopReq.ShopBean> list = new ArrayList<>();
        for (PurchaserShopBean bean : listBean) {
            list.add(new CooperationShopReq.ShopBean(bean.getShopID(), bean.getShopName()));
        }
        req.setShopList(list);
        mPresenter.editCooperationPurchaserShop(req);
    }

    @Override
    public void showPurchaserDetail(CooperationPurchaserDetail resp, boolean append) {
        if (!append) {
            mDetail = resp;
            if (TextUtils.equals(resp.getGroupActiveLabel(), "1")) {
                // 禁用
                mImgLogoUrl.setDisableImageUrl(resp.getLogoUrl(), GlideImageView.GROUP_BLOCK_UP);
                mTxtName.setTextColor(Color.parseColor("#999999"));
            } else if (TextUtils.equals(resp.getGroupActiveLabel(), "2")) {
                // 注销
                mImgLogoUrl.setDisableImageUrl(resp.getLogoUrl(), GlideImageView.GROUP_LOG_OUT);
                mTxtName.setTextColor(Color.parseColor("#999999"));
            } else {
                mImgLogoUrl.setImageURL(resp.getLogoUrl());
                mTxtName.setTextColor(Color.parseColor("#222222"));
            }
            mTxtName.setText(resp.getName());
            mTxtDefaultDeliveryWay.setText(String.format("默认配送方式：%s", getDeliveryWay(resp.getDefaultDeliveryWay())));
            mTxtDefaultSettlementWay.setText(String.format("默认结算方式：%s",
                    getSettlementWay(resp.getDefaultSettlementWay())));
            mTxtShopCount.setText(String.format("当前合作门店数量：%s", CommonUtils.formatNumber(resp.getShopCount())));
        }
        List<PurchaserShopBean> shopBeans = resp.getShopDetailList();
        if (append) {
            if (!CommonUtils.isEmpty(shopBeans)) {
                mAdapter.addData(shopBeans);
            }
        } else {
            if (resp.getCooperationActive() == 0) {
                mRecyclerView.addOnItemTouchListener(swipeItemTouchListener);
            } else {
                mRecyclerView.removeOnItemTouchListener(swipeItemTouchListener);
            }
            mAdapter.setNewData(shopBeans);
        }
        mAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("还没有合作门店数据").create());
        mRefreshLayout.setEnableLoadMore(shopBeans != null && shopBeans.size() == 20);

        mImgOption.setVisibility(resp.getCooperationActive() == 1 || UserConfig.crm() ? View.GONE : View.VISIBLE);
    }

    /**
     * 获取配送方式显示
     *
     * @param defaultDeliveryWay 配送方式数字表示
     * @return 配送方式
     */
    public static String getDeliveryWay(String defaultDeliveryWay) {
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
    public static String getSettlementWay(String defaultSettlementWay) {
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(defaultSettlementWay)) {
            String[] strings = defaultSettlementWay.split(",");
            for (String s : strings) {
                if (TextUtils.equals(s, CooperationShopSettlementActivity.PAY_CASH)) {
                    builder.append("货到付款").append("/");
                } else if (TextUtils.equals(s, CooperationShopSettlementActivity.PAY_ACCOUNT)) {
                    builder.append("账期支付").append("/");
                } else if (TextUtils.equals(s, CooperationShopSettlementActivity.PAY_ONLINE)) {
                    builder.append("线上支付").append("/");
                } else if (TextUtils.equals(s, CooperationShopSettlementActivity.PAY_CARD)) {
                    builder.append("卡支付").append("/");
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

    @Override
    public String getPurchaserId() {
        return mPurchaserId;
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    @OnClick({R.id.img_close, R.id.txt_options, R.id.cons_details})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_options:
                showOptionsWindow(view);
                break;
            case R.id.cons_details:
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_DETAILS, mDetail.getPurchaserID());
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
            toSelectShop(CooperationSelectShopActivity.TYPE_SETTLEMENT);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_DETAIL_SALESMAN)) {
            toSelectShop(CooperationSelectShopActivity.TYPE_SALESMAN);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_DETAIL_DRIVER)) {
            toSelectShop(CooperationSelectShopActivity.TYPE_DRIVER);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_DETAIL_DELIVERY)) {
            toSelectShop(CooperationSelectShopActivity.TYPE_DELIVERY);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_DETAIL_SHOP)) {
            RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_ADD_SHOP, mDetail.getPurchaserID());
        }
        mOptionsWindow.dismiss();
    }

    private void toSelectShop(String type) {
        if (CommonUtils.isEmpty(mAdapter.getData()) || mDetail == null) {
            showToast("当前没有门店");
            return;
        }
        ShopSettlementReq req = new ShopSettlementReq();
        req.setActionType(type);
        req.setChangeAllShops("0");
        req.setGroupID(UserConfig.getGroupID());
        req.setPurchaserID(mDetail.getPurchaserID());
        CooperationSelectShopActivity.start(this, mAdapter.getData(), req);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.start();
    }

    public static class PurchaserShopListAdapter extends BaseQuickAdapter<PurchaserShopBean, BaseViewHolder> {
        private boolean mIsAdd;
        private Map<String, PurchaserShopBean> mSelectMap;

        public PurchaserShopListAdapter() {
            super(R.layout.item_cooperation_purchaser_shop);
        }

        public PurchaserShopListAdapter(Map<String, PurchaserShopBean> map) {
            super(R.layout.item_cooperation_purchaser_shop);
            this.mIsAdd = true;
            this.mSelectMap = map;
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.addOnClickListener(R.id.txt_del).addOnClickListener(R.id.content);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserShopBean item) {
            helper.setText(R.id.txt_shopName, item.getShopName())
                    .setText(R.id.txt_shopAdmin, "联系人：" + getString(item.getShopAdmin()) + " / "
                            + getString(PhoneUtil.formatPhoneNum(item.getShopPhone())))
                    .setText(R.id.txt_shopAddress, "地址：" + getString(item.getShopAddress()))
                    .setGone(R.id.txt_newShopNum, !mIsAdd && CommonUtils.getDouble(item.getStatus()) == 0)
                    .setGone(R.id.img_select, mIsAdd)
                    .getView(R.id.img_select).setSelected(mSelectMap != null && mSelectMap.containsKey(item.getShopID()));
            GlideImageView imageView = helper.getView(R.id.img_imagePath);
            if (TextUtils.equals(item.getIsActive(), "0")) {
                imageView.setDisableImageUrl(item.getImagePath(), GlideImageView.DISABLE_SHOP);
            } else {
                imageView.setImageURL(item.getImagePath());
            }
        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }
    }
}
