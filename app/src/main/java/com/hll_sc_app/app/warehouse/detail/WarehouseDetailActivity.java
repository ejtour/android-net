package com.hll_sc_app.app.warehouse.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.warehouse.WarehouseDetailResp;
import com.hll_sc_app.bean.warehouse.WarehouseShopBean;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 代仓货主详情
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
@Route(path = RouterConfig.WAREHOUSE_DETAIL, extras = Constant.LOGIN_EXTRA)
public class WarehouseDetailActivity extends BaseLoadActivity implements WarehouseDetailContract.IWarehouseDetailView {
    @Autowired(name = "object0")
    String mGroupId;
    @BindView(R.id.img_logoUrl)
    GlideImageView mImgLogoUrl;
    @BindView(R.id.txt_purchaserName)
    TextView mTxtGroupName;
    @BindView(R.id.txt_linkman)
    TextView mTxtLinkman;
    @BindView(R.id.txt_groupArea)
    TextView mTxtGroupArea;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ShopListAdapter mAdapter;
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_detail);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        WarehouseDetailPresenter presenter = WarehouseDetailPresenter.newInstance();
        presenter.register(this);
        presenter.queryCooperationWarehouseDetail(mGroupId);
    }

    private void initView() {
        mEmptyView = EmptyView.newBuilder(this).setTips("暂无门店").create();
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new ShopListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            WarehouseShopBean shopBean = (WarehouseShopBean) adapter.getItem(position);
            if (shopBean != null) {
                shopBean.setPurchaserId(mGroupId);
                RouterUtil.goToActivity(RouterConfig.WAREHOUSE_SHOP_DETAIL, shopBean);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.img_close, R.id.cons_details})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        } else if (view.getId() == R.id.cons_details) {
            RouterUtil.goToActivity(RouterConfig.WAREHOUSE_DETAILS, mGroupId, "formalSigned");
        }
    }

    @Override
    public void showDetail(WarehouseDetailResp resp) {
        PurchaserBean bean = resp.getPurchaserInfo();
        if (bean != null) {
            mTxtGroupName.setText(bean.getGroupName());
            mTxtLinkman.setText("");
            mTxtLinkman.setText(String.format("联系人：%s / %s", getString(bean.getLinkman()),
                getString(PhoneUtil.formatPhoneNum(bean.getMobile()))));
            mTxtGroupArea.setText(String.format("所在地区：%s", TextUtils.isEmpty(bean.getGroupArea()) ? "无" :
                bean.getGroupArea()));
        }
        mAdapter.setNewData(resp.getShops());
        mAdapter.setEmptyView(mEmptyView);
    }

    private String getString(String str) {
        if (TextUtils.isEmpty(str)) {
            return "无";
        }
        return str;
    }

    public static class ShopListAdapter extends BaseQuickAdapter<WarehouseShopBean, BaseViewHolder> {

        public ShopListAdapter() {
            super(R.layout.item_cooperation_purchaser_shop);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.setGone(R.id.txt_newShopNum, false).setGone(R.id.img_select, false)
                .addOnClickListener(R.id.content);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, WarehouseShopBean item) {
            helper.setText(R.id.txt_shopName, item.getShopName())
                .setText(R.id.txt_shopAdmin, "联系人：" + getString(item.getLinkman()) + " / "
                    + getString(PhoneUtil.formatPhoneNum(item.getMobile())))
                .setText(R.id.txt_shopAddress, "地址：" + getString(item.getShopAddress()));
            GlideImageView imageView = helper.getView(R.id.img_imagePath);
            if (TextUtils.equals(item.getIsActive(), "0")) {
                imageView.setShopDisableImageUrl(item.getLogoUrl());
            } else {
                imageView.setImageURL(item.getLogoUrl());
            }
        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }
    }
}
