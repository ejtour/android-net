package com.hll_sc_app.app.blacklist.detail;

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
import com.hll_sc_app.app.blacklist.list.BlackListActivity;
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

@Route(path = RouterConfig.BLACK_DETAIL, extras = Constant.LOGIN_EXTRA)
public class BlackDetailActivity extends BaseLoadActivity implements IBlackDetailContract.IDetailView {

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
    private IBlackDetailContract.IPresent mPresenter;
    private BlackDetailAdapter mAdapter;
    private List<OrientationDetailBean> productList;
    private View mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list_detail);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = BlackDetailPresenter.newInstance();
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
        mAdapter = new BlackDetailAdapter();
        mAdapter.setOnDelClickListener((productIndex, specIndex) -> {
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
        ((TextView) mEmptyView.findViewById(R.id.txt_alert)).setText("点击下面按钮添加要加入黑名单的商品");
        ((TextView) mEmptyView.findViewById(R.id.txt_add_product)).setText("选择品相黑名单商品");
        mAdapter.setEmptyView(mEmptyView);
        mRecyclerView.setAdapter(mAdapter);
        mCooperationNameView.setText(mOrientationListBean.getPurchaserName());
        mImageView.setImageURL(mOrientationListBean.getPurchaserImgUrl());
        mShopNumView.setText("已选择" + mOrientationListBean.getPurchaserShopIDs().split(",").length + "个门店");
        if (BlackListActivity.getOrientationDetailBeans() != null && BlackListActivity.getOrientationDetailBeans().size() != 0) {
            productList = BlackListActivity.getOrientationDetailBeans();
        }
    }

    private void initData() {
        if (mOrientationListBean.getId() != null && mOrientationListBean.getId() != "") {
            mPresenter.getOrientation();
        }
        if (mOrientationListBean.getPurchaserImgUrl() == null) {
            mPresenter.getGroupInfo(mOrientationListBean.getPurchaserID());
        }
        if (productList != null && productList.size() != 0) {
            setView(productList);
        }
    }

    private void addProduct() {

          /* List<OrientationDetailBean> data = mAdapter.getData();
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
                specBean.setSpecID(spec.getSpecID());
                specList.add(specBean);
            }
            bean.setSpecs(specList);
            list.add(bean);
        }*/

        ARouter.getInstance().build(RouterConfig.BLACK_PRODUCT)
                .withParcelableArrayList("parcelable", (ArrayList<OrientationDetailBean>) mAdapter.getData())
                .setProvider(new LoginInterceptor()).navigation();


    }

    @Override
    public void setView(List<OrientationDetailBean> list) {
        selectAll(list);
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
                .build(RouterConfig.BLACK_LIST)
                .withBoolean("reload", true)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .setProvider(new LoginInterceptor())
                .navigation(this);
        finish();
    }

    /**
     * 将所选的商品下的部分规格置为选中状态
     *
     * @param list
     */
    private void selectAll(List<OrientationDetailBean> list) {
        for (OrientationDetailBean orientationDetailBean : list) {
            for (OrientationProductSpecBean specBean : orientationDetailBean.getSpecs()) {
                specBean.setSelect(true);
            }
        }
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
        if (mOrientationListBean.getFrom() != null && mOrientationListBean.getFrom() == 0) {
            //来源是新增
            BlackListActivity.setOrientationDetailBeans(productList);
            finish();
        } else {
            mOrientationListBean.setFrom(1);
            RouterUtil.goToActivity(RouterConfig.BLACK_COOPERATION_PURCHASER, mOrientationListBean);
        }
    }

    private void setOrientation() {
        if (productList == null || productList.size() == 0) {
            showToast("商品不能为空");
            return;
        }
        mPresenter.setOrientation(productList, mOrientationListBean);
    }

    @Subscribe
    public void onEvent(List<OrientationDetailBean> event) {
        // 商品列表
        productList = event;
        filterNoSellList(productList);
        mAdapter.setNewData(productList);
        mAdapter.notifyDataSetChanged();
        if (productList != null && productList.size() != 0) {
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
