package com.hll_sc_app.app.cooperation.detail.details.shop;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.CooperationDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.event.CooperationEvent;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 合作采购商详情-需合作门店
 *
 * @author zhuyingsong
 * @date 2019/7/18
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOPS, extras = Constant.LOGIN_EXTRA)
public class CooperationShopListActivity extends BaseLoadActivity implements ICooperationShopListContract.ICooperationShopListView {
    @BindView(R.id.asl_list_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @Autowired(name = "parcelable", required = true)
    ArrayList<PurchaserShopBean> mData;
    private CooperationDetailActivity.PurchaserShopListAdapter mAdapter;
    private ICooperationShopListContract.ICooperationShopListPresenter mPresenter;
    private boolean mHasChanged;
    private PurchaserShopBean mBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        EventBus.getDefault().register(this);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = new CooperationShopListPresenter();
        mPresenter.register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void handleEvent(CooperationEvent event) {
        if (event.getMessage().equals(CooperationEvent.SHOP_CHANGED) && mBean != null) {
            success();
        }
    }

    private void initView() {
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mTitleBar.setHeaderTitle("待同意合作门店");
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
                , UIUtils.dip2px(1)));
        mAdapter = new CooperationDetailActivity.PurchaserShopListAdapter();
        mAdapter.setOnItemChildClickListener((adapter1, view, position) -> {
            mBean = mAdapter.getItem(position);
            if (mBean == null || TextUtils.isEmpty(mBean.getPurchaserID())) return;
            if (view.getId() == R.id.content) {
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_DETAIL, mBean);
            } else if (view.getId() == R.id.txt_agree) {
                mPresenter.agree(mBean);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(mData);
        mAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("您还没有合作客户门店数据").create());
    }

    @Override
    public void onBackPressed() {
        if (mHasChanged) {
            EventBus.getDefault().post(new CooperationEvent(CooperationEvent.SHOP_NUM_CHANGED));
        }
        super.onBackPressed();
    }

    @Override
    public void success() {
        mHasChanged = true;
        if (mAdapter.getData().size() > 1) {
            mAdapter.remove(mAdapter.getData().indexOf(mBean));
        } else {
            onBackPressed();
        }
    }
}
