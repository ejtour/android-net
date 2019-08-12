package com.hll_sc_app.app.orientationsale.detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
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
import com.hll_sc_app.widget.SimpleDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.ORIENTATION_DETAIL, extras = Constant.LOGIN_EXTRA)
public class OrientationDetailActivity extends BaseLoadActivity implements IOrientationDetailContract.IOrientationDetailView {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.img_logoUrl)
    GlideImageView mImageView;
    @BindView(R.id.txt_cooperation_name)
    TextView mCooperationNameView;
    @BindView(R.id.txt_delete_tip)
    TextView mDeleteView;
    @BindView(R.id.rl_add_product)
    RelativeLayout mAddProductView;
    @BindView(R.id.txt_change_tip)
    TextView mShopNumView;
    @Autowired(name = "parcelable")
    OrientationListBean mOrientationListBean;
    private IOrientationDetailContract.IOrientationDetailPresenter mPresenter;
    private OrientationDetailAdapter mAdapter;
    private List<OrientationDetailBean> productList;
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
        mPresenter.start();
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
        mAddProductView.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new OrientationDetailAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.img_delete) {
                mAdapter.getData().remove(position);
                mAdapter.notifyDataSetChanged();
                if (mAdapter.getData().size() == 0) {
                    mAddProductView.setVisibility(View.GONE);
                }
            }
        });
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.view_orientation_detail_empty, mRecyclerView, false);
        mEmptyView.findViewById(R.id.txt_add_product).setOnClickListener(v -> addProduct());
        mAdapter.setEmptyView(mEmptyView);
        mRecyclerView.setAdapter(mAdapter);
        mCooperationNameView.setText(mOrientationListBean.getPurchaserName());
        mImageView.setImageURL(mOrientationListBean.getPurchaserImgUrl());
        mShopNumView.setText("已选择" + mOrientationListBean.getPurchaserShopIDs().split(",").length + "个门店");
    }

    private void initData() {
        if (mOrientationListBean.getId() != null && mOrientationListBean.getId() != "") {
            mPresenter.getOrientation();
        }
        if (mOrientationListBean.getPurchaserImgUrl() == null) {
            mPresenter.getGroupInfo(mOrientationListBean.getPurchaserID());
        }
    }

    private void addProduct() {
        List<OrientationDetailBean> data = mAdapter.getData();
        ArrayList<OrientationDetailBean> list = new ArrayList<>();
        for (OrientationDetailBean datum : data) {
            OrientationDetailBean bean = new OrientationDetailBean();
            bean.setProductName(datum.getProductName());
            bean.setSupplierName(datum.getSupplierName());
            bean.setProductID(datum.getProductID());
            bean.setImgUrl(datum.getImgUrl());
            ArrayList<OrientationProductSpecBean> specList = new ArrayList<>();
            for (OrientationProductSpecBean spec : datum.getSpecs()) {
                OrientationProductSpecBean specBean = new OrientationProductSpecBean();
                specBean.setProductPrice(spec.getProductPrice());
                specBean.setSaleUnitName(spec.getSaleUnitName());
                specBean.setSaleUnitID(spec.getSaleUnitID());
                specBean.setSpecContent(spec.getSpecContent());
                specList.add(specBean);
            }
            bean.setSpecs(specList);
            list.add(bean);
        }
        ARouter.getInstance().build(RouterConfig.ORIENTATION_PRODUCT)
            .withParcelableArrayList("parcelable", list)
            .setProvider(new LoginInterceptor()).navigation();
    }

    @Override
    public void setView(List<OrientationDetailBean> list) {
        mAdapter.setNewData(list);
        mAdapter.setEmptyView(mEmptyView);
        if (list == null || list.size() == 0) {
            mAddProductView.setVisibility(View.GONE);
        } else {
            mAddProductView.setVisibility(View.VISIBLE);
        }
        productList = list;
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
            .withBoolean("item", true)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .setProvider(new LoginInterceptor())
            .navigation(this);
        finish();
    }

    @Subscribe
    public void onEvent(List<OrientationDetailBean> event) {
        // 商品列表
        productList = event;
        mAdapter.setNewData(productList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        boolean reload = intent.getBooleanExtra("reload", false);
        if (reload) {
            initData();
        }
        Parcelable parcelable = intent.getParcelableExtra("parcelable");
        if (parcelable != null) {
            this.mOrientationListBean = (OrientationListBean) parcelable;
            mPresenter.getGroupInfo(mOrientationListBean.getPurchaserID());
            mImageView.setImageURL(mOrientationListBean.getPurchaserImgUrl());
            mCooperationNameView.setText(mOrientationListBean.getPurchaserName());
            mShopNumView.setText("已选择" + mOrientationListBean.getPurchaserShopIDs().split(",").length + "个门店");
        }
    }

    @OnClick({R.id.img_close, R.id.txt_add_product, R.id.rl_cooperation_info, R.id.txt_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_add_product:
                addProduct();
                break;
            case R.id.rl_cooperation_info:
                setCooperation();
                break;
            case R.id.txt_set:
                setOrientation();
                break;
            default:
                break;
        }
    }

    private void setCooperation() {
        mOrientationListBean.setFrom(1);
        RouterUtil.goToActivity(RouterConfig.ORIENTATION_COOPERATION_PURCHASER, mOrientationListBean);
    }

    private void setOrientation() {
        mPresenter.setOrientation(productList, mOrientationListBean);
    }
}
