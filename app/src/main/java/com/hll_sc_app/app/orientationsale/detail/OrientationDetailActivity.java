package com.hll_sc_app.app.orientationsale.detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.orientation.OrientationDetailBean;
import com.hll_sc_app.bean.orientation.OrientationListBean;
import com.hll_sc_app.bean.orientation.OrientationProductSpecBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.ORIENTATION_DETAIL, extras = Constant.LOGIN_EXTRA)
public class OrientationDetailActivity extends BaseLoadActivity implements IOrientationDetailContract.IOrientationDetailView {
    @BindView(R.id.aod_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aod_copy)
    TextView mCopy;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.img_logoUrl)
    GlideImageView mImageView;
    @BindView(R.id.txt_cooperation_name)
    TextView mCooperationNameView;
    @BindView(R.id.rl_add_product)
    FrameLayout mAddProductView;
    @BindView(R.id.txt_change_tip)
    TextView mShopNumView;
    @Autowired(name = "parcelable")
    OrientationListBean mOrientationListBean;
    private IOrientationDetailContract.IOrientationDetailPresenter mPresenter;
    private OrientationDetailAdapter mAdapter;
    private View mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation_detail);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = OrientationDetailPresenter.newInstance();
        mPresenter.register(this);
        initView();
        initData();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::setOrientation);
        mAddProductView.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new OrientationDetailAdapter();
        mAdapter.setOnDelClickListener((productIndex, specIndex) -> {
            List<OrientationDetailBean> productList = mOrientationListBean.getProductList();
            productList.get(productIndex).getSpecs().remove(specIndex);
            if (productList.get(productIndex).getSpecs().size() == 0) {//清掉所选的商品
                productList.remove(productIndex);
                mAdapter.notifyDataSetChanged();
            }
            if (productList.size() == 0) {
                mAddProductView.setVisibility(View.GONE);
            }
        });
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.view_orientation_detail_empty, mRecyclerView, false);
        mEmptyView.findViewById(R.id.txt_add_product).setOnClickListener(v -> addProduct());
        mAdapter.setEmptyView(mEmptyView);
        mRecyclerView.setAdapter(mAdapter);
        mCooperationNameView.setText(mOrientationListBean.getPurchaserName());
        mImageView.setImageURL(mOrientationListBean.getPurchaserImgUrl());
        mShopNumView.setText("已选择" + mOrientationListBean.getPurchaserShopIDs().split(",").length + "个门店");
        mCopy.setVisibility(!TextUtils.isEmpty(getMainID()) ? View.VISIBLE : View.GONE);
    }

    private void initData() {
        if (!TextUtils.isEmpty(getMainID())) {
            mPresenter.getOrientation();
        }
        if (mOrientationListBean.getPurchaserImgUrl() == null) {
            mPresenter.getGroupInfo(mOrientationListBean.getPurchaserID());
        }
        List<OrientationDetailBean> productList = mOrientationListBean.getProductList();
        if (productList != null && productList.size() != 0) {
            setView(productList);
        }
    }

    private void addProduct() {
        ARouter.getInstance().build(RouterConfig.ORIENTATION_PRODUCT)
                .withParcelableArrayList("parcelable", (ArrayList<OrientationDetailBean>) mAdapter.getData())
                .setProvider(new LoginInterceptor()).navigation();
    }

    @Override
    public void setView(List<OrientationDetailBean> list) {
        for (OrientationDetailBean orientationDetailBean : list) {
            for (OrientationProductSpecBean specBean : orientationDetailBean.getSpecs()) {
                specBean.setSelect(true);
            }
        }
        mAdapter.setNewData(list);
        mAdapter.setEmptyView(mEmptyView);
        mAddProductView.setVisibility(CommonUtils.isEmpty(list) ? View.GONE : View.VISIBLE);
        mOrientationListBean.setProductList(list);
    }

    @Override
    public String getMainID() {
        return mOrientationListBean.getId();
    }

    @Override
    public void setPurchaserUrl(String url) {
        mOrientationListBean.setPurchaserImgUrl(url);
        mImageView.setImageURL(mOrientationListBean.getPurchaserImgUrl());
    }

    @Override
    public void addSuccess() {
        showToast("设置成功");
        ARouter.getInstance()
                .build(RouterConfig.ORIENTATION_LIST)
                .withBoolean("reload", true)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .setProvider(new LoginInterceptor())
                .navigation(this);
        finish();
    }

    private void setCooperation() {
        if (mOrientationListBean.getFrom() != null && mOrientationListBean.getFrom() == 0) {
            //来源是新增
            finish();
        } else {
            mOrientationListBean.setFrom(1);
            RouterUtil.goToActivity(RouterConfig.ORIENTATION_COOPERATION_PURCHASER, mOrientationListBean);
        }
    }

    private void setOrientation(View view) {
        if (CommonUtils.isEmpty(mOrientationListBean.getProductList())) {
            showToast("商品不能为空");
            return;
        }
        mPresenter.setOrientation(mOrientationListBean.getProductList(), mOrientationListBean);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        boolean reload = intent.getBooleanExtra("reload", false);
        if (reload) {
            initData();
        }
        Parcelable parcelable = intent.getParcelableExtra("parcelable");
        if (parcelable instanceof OrientationListBean) {
            this.mOrientationListBean = (OrientationListBean) parcelable;
            mPresenter.getGroupInfo(mOrientationListBean.getPurchaserID());
            mImageView.setImageURL(mOrientationListBean.getPurchaserImgUrl());
            mCooperationNameView.setText(mOrientationListBean.getPurchaserName());
            mShopNumView.setText("已选择" + mOrientationListBean.getPurchaserShopIDs().split(",").length + "个门店");
        }
    }

    @OnClick({R.id.txt_add_product, R.id.rl_cooperation_info, R.id.aod_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_add_product:
                addProduct();
                break;
            case R.id.rl_cooperation_info:
                setCooperation();
                break;
            case R.id.aod_copy:
                OrientationListBean bean = mOrientationListBean.deepCopy();
                bean.setFrom(1);
                bean.setId(null);
                RouterUtil.goToActivity(RouterConfig.ORIENTATION_DETAIL, bean);
                finish();
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onEvent(List<OrientationDetailBean> event) {
        // 商品列表
        mOrientationListBean.setProductList(event);
        filterNoSellList(event);
        mAdapter.setNewData(event);
        if (!CommonUtils.isEmpty(event)) {
            mAddProductView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 过滤掉没有设置的规格
     * 反向循环
     * @param list
     */
    private void filterNoSellList(List<OrientationDetailBean> list) {
        for (OrientationDetailBean orientationDetailBean : list) {
            for (int i = orientationDetailBean.getSpecs().size() - 1; i >= 0; i--) {
                OrientationProductSpecBean specBean = orientationDetailBean.getSpecs().get(i);
                if (!specBean.isSelect()) {
                    orientationDetailBean.getSpecs().remove(specBean);
                }
            }
        }
    }
}
