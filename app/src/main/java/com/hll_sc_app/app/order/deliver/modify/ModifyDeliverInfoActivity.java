package com.hll_sc_app.app.order.deliver.modify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/13
 */

@Route(path = RouterConfig.ROOT_ORDER_MODIFY_DELIVER)
public class ModifyDeliverInfoActivity extends BaseLoadActivity implements IModifyDeliverInfoContract.IModifyDeliverInfoView {
    public static final int REQ_KEY = 0x366;
    public static final String RESP_LIST_KEY = "resp_list_key";

    /**
     * @param list      商品明细列表
     * @param subBillID 订单 id
     */
    public static void start(Activity context, ArrayList<OrderDetailBean> list, String subBillID) {
        ARouter.getInstance()
                .build(RouterConfig.ROOT_ORDER_MODIFY_DELIVER)
                .withParcelableArrayList("parcelable", list)
                .withString("object", subBillID)
                .setProvider(new LoginInterceptor())
                .navigation(context, REQ_KEY);
    }

    @BindView(R.id.mdi_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.mdi_list_view)
    RecyclerView mListView;
    @Autowired(name = "parcelable", required = true)
    ArrayList<OrderDetailBean> mList;
    @Autowired(name = "object", required = true)
    String mSubBillID;
    private IModifyDeliverInfoContract.IModifyDeliverInfoPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_modify_deliver_info);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = ModifyDeliverInfoPresenter.newInstance(mList, mSubBillID);
        mPresenter.register(this);
    }

    private void initView() {
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(90), 0, 0, 0);
        mListView.addItemDecoration(decor);
        mListView.setAdapter(new ModifyDeliverInfoAdapter(mList));
        mTitleBar.setRightBtnClick(v -> mPresenter.modifyDeliverInfo());
    }

    @Override
    public void modifySuccess() {
        Intent intent = new Intent();
        intent.putExtra(RESP_LIST_KEY, mList);
        setResult(RESULT_OK, intent);
        finish();
    }
}
