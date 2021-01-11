package com.hll_sc_app.app.order.deliver.modify;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.order.ModifyUnitDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/13
 */

@Route(path = RouterConfig.ORDER_MODIFY_DELIVER)
public class ModifyDeliverInfoActivity extends BaseLoadActivity implements IModifyDeliverInfoContract.IModifyDeliverInfoView {
    public static final int REQ_KEY = 0x366;
    private OrderDetailBean mCurBean;

    /**
     * @param resp 订单信息
     */
    public static void start(Activity context, OrderResp resp) {
        RouterUtil.goToActivity(RouterConfig.ORDER_MODIFY_DELIVER, context, REQ_KEY, resp);
    }

    @BindView(R.id.mdi_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.mdi_list_view)
    RecyclerView mListView;
    @BindView(R.id.mdi_shop)
    TextView mShop;
    @BindView(R.id.mdi_group)
    TextView mGroup;
    @BindView(R.id.mdi_image)
    GlideImageView mImage;
    @Autowired(name = "parcelable", required = true)
    OrderResp mResp;
    private IModifyDeliverInfoContract.IModifyDeliverInfoPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_modify_deliver_info);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = ModifyDeliverInfoPresenter.newInstance(mResp.getBillDetailList(), mResp.getSubBillID());
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mShop.setText(mResp.getShopName());
        mGroup.setText(mResp.getPurchaserName());
        mImage.setImageURL(mResp.getImgUrl());
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(90), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        ModifyDeliverInfoAdapter adapter = new ModifyDeliverInfoAdapter(mResp.getBillDetailList());
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            mCurBean = ((OrderDetailBean) adapter1.getItem(position));
            if (mCurBean == null) {
                return;
            }
            ((ViewGroup) mTitleBar.getParent()).requestFocus();
            ModifyUnitDialog.create(this,
                    mCurBean.getAuxiliaryUnit(),
                    mCurBean.getSaleUnitName(),
                    mCurBean.getDeliverUnit(),
                    unit -> {
                        mCurBean.setDeliverUnit(unit);
                        ModifyDeliverInfoAdapter adapter2 = (ModifyDeliverInfoAdapter) mListView.getAdapter();
                        int pos = adapter2.getData().indexOf(mCurBean);
                        adapter2.notifyItemChanged(pos);
                    }).show();
        });
        mListView.setAdapter(adapter);
        mTitleBar.setRightBtnClick(v -> mPresenter.modifyDeliverInfo());
    }

    @Override
    public void modifySuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void modifyPrice() {
        ((ModifyDeliverInfoAdapter) mListView.getAdapter()).modifyPrice();
    }
}
