package com.hll_sc_app.app.warehouse.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.warehouse.detail.add.WarehouseDetailAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.RefreshWarehouseShopList;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.warehouse.WarehouseDetailResp;
import com.hll_sc_app.bean.warehouse.WarehouseShopBean;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.WXFollowDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    @BindView(R.id.img_title_arrow)
    ImageView mImgTitleArrow;
    @BindView(R.id.txt_add)
    TextView mAdd;
    @BindView(R.id.txt_make_qr)
    TextView mMakeQr;
    private WarehouseShopAdapter mAdapter;
    private EmptyView mEmptyView;

    //在自营（代仓管理）且 代仓公司为已停止状态 则不进一步查看详情
    private boolean isAllowCheckDetail = true;
    private WarehouseDetailPresenter mPresenter;
    private WarehouseDetailResp mResp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_detail);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        mPresenter = WarehouseDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.queryCooperationWarehouseDetail(mGroupId);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void refreshList(RefreshWarehouseShopList event) {
        mPresenter.queryCooperationWarehouseDetail(mGroupId);
    }

    private void initView() {
        mEmptyView = EmptyView.newBuilder(this).setTips("暂无门店").create();
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
                , UIUtils.dip2px(1)));
        mAdapter = new WarehouseShopAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (!isAllowCheckDetail) {
                return;
            }
            WarehouseShopBean shopBean = (WarehouseShopBean) adapter.getItem(position);
            if (shopBean != null) {
                shopBean.setPurchaserId(mGroupId);
                shopBean.setIsWarehouse(mResp.getIsWarehouse());
                RouterUtil.goToActivity(RouterConfig.WAREHOUSE_SHOP_DETAIL, shopBean);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.img_close, R.id.cons_details, R.id.txt_add, R.id.txt_make_qr})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        } else if (view.getId() == R.id.cons_details) {
            if (mImgTitleArrow.getVisibility() == View.VISIBLE)
                RouterUtil.goToActivity(RouterConfig.WAREHOUSE_DETAILS, mGroupId, "formalSigned");
        } else if (view.getId() == R.id.txt_add) {
            WarehouseDetailAddActivity.start(mGroupId);
        } else if (view.getId() == R.id.txt_make_qr) {
            if (mResp != null)
                mPresenter.queryFollowQR(mResp.getPurchaserInfo().getGroupID(), mResp.getIsWarehouse());
        }
    }

    @Override
    public void showDetail(WarehouseDetailResp resp) {
        mResp = resp;
        PurchaserBean info = UserConfig.isSelfOperated() ? resp.getPurchaserInfo() : resp.getGroupInfo();
        if (info != null) {
            mImgLogoUrl.setImageURL(info.getLogoUrl());
            mTxtGroupName.setText(info.getGroupName());
            mTxtLinkman.setText("");
            mTxtLinkman.setText(String.format("联系人：%s / %s", getString(info.getLinkman()),
                    getString(PhoneUtil.formatPhoneNum(info.getMobile()))));
            mTxtGroupArea.setText(String.format("所在地区：%s", TextUtils.isEmpty(info.getGroupArea()) ? "无" :
                    info.getGroupArea()));

            //自营则为代仓管理
            if (UserConfig.isSelfOperated()) {
                if (resp.getWarehouseActive() == null || resp.getWarehouseActive() != 0) { // 代仓公司为已停止合作状态
                    isAllowCheckDetail = false;
                    mImgTitleArrow.setVisibility(View.GONE);
                    mMakeQr.setVisibility(View.GONE);
                    mAdd.setVisibility(View.GONE);
                } else {
                    mMakeQr.setVisibility(View.VISIBLE);
                    mAdd.setVisibility(View.VISIBLE);
                }
            } else {
                mMakeQr.setVisibility(View.GONE);
                mAdd.setVisibility(View.GONE);
            }
        }
        mAdapter.setNewData(resp.getShops());
        mAdapter.setEmptyView(mEmptyView);
    }

    @Override
    public void showFollowDialog(String qrcodeUrl) {
        new WXFollowDialog(this)
                .show(qrcodeUrl,
                        "关注微信公众号，让货主实时接收库存信息",
                        "点击保存下方二维码，分享给货主，当可用库存\n小于等于库存预警值时，推送公众号消息给货主",
                        false);
    }

    private String getString(String str) {
        if (TextUtils.isEmpty(str)) {
            return "无";
        }
        return str;
    }
}
