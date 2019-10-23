package com.hll_sc_app.app.order.transfer.check;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.transfer.InventoryCheckReq;
import com.hll_sc_app.bean.order.transfer.OrderResultResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.adapter.ViewPagerAdapter;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/4
 */

@Route(path = RouterConfig.ORDER_COMMODITY_CHECK)
public class CommodityCheckActivity extends BaseLoadActivity implements ICommodityCheckContract.ICommodityCheckView {
    public static final int REQ_KEY = 0x956;

    /**
     * @param resp 下单失败返回值
     */
    public static void start(Activity context, OrderResultResp resp) {
        RouterUtil.goToActivity(RouterConfig.ORDER_COMMODITY_CHECK, context, REQ_KEY, resp);
    }

    @BindView(R.id.stp_title_bar)
    TitleBar mTitleBar;
    @Autowired(name = "parcelable", required = true)
    OrderResultResp mResp;
    @BindView(R.id.stp_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.stp_view_pager)
    ViewPager mViewPager;
    private CommodityCheckPresenter mPresenter;
    private InventoryCheckAdapter mAAdapter;
    private FallShelfCheckAdapter mBAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab_pager);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initView();
        initData();
    }

    private void initData() {
        mPresenter = CommodityCheckPresenter.newInstance();
        mPresenter.register(this);
    }

    private void initView() {
        mTitleBar.setRightText("确定");
        mTitleBar.setHeaderTitle("商品检查");
        mTitleBar.setRightBtnClick(this::sure);
        List<RecyclerView> list = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        if (!CommonUtils.isEmpty(mResp.getRecords())) {
            RecyclerView aList = createListView();
            mAAdapter = new InventoryCheckAdapter(mResp.getRecords());
            aList.setAdapter(mAAdapter);
            list.add(aList);
            titles.add("库存检查");
        }
        if (!CommonUtils.isEmpty(mResp.getShelfFlowRecords())) {
            RecyclerView bList = createListView();
            mBAdapter = new FallShelfCheckAdapter(mResp.getShelfFlowRecords());
            bList.setAdapter(mBAdapter);
            list.add(bList);
            titles.add("下架商品");
        }
        if (titles.size() == 0) {
            return;
        }
        RecyclerView[] array = new RecyclerView[0];
        String[] titleArray = {};
        mViewPager.setAdapter(new ViewPagerAdapter(list.toArray(array)));
        mTabLayout.setViewPager(mViewPager, titles.toArray(titleArray));
        if (titles.size() == 1) {
            mTitleBar.setHeaderTitle(titles.get(0));
            mTabLayout.setVisibility(View.GONE);
        } else {
            mTitleBar.setHeaderTitle("商品检查");
            mTabLayout.setVisibility(View.VISIBLE);
        }
    }

    private RecyclerView createListView() {
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setClipToPadding(false);
        recyclerView.setPadding(0, UIUtils.dip2px(5), 0, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        return recyclerView;
    }

    private void sure(View view) {
        List<InventoryCheckReq.InventoryCheckBean> reqList = new ArrayList<>();
        if (mAAdapter != null) {
            List<InventoryCheckReq.InventoryCheckBean> aReqList = mAAdapter.getReqList();
            for (InventoryCheckReq.InventoryCheckBean bean : aReqList) {
                if (bean.getFlag() == 0) {
                    showToast("存在未处理库存不足商品 请确认是否全部处理完成");
                    return;
                }
            }
            reqList.addAll(aReqList);
        }
        if (mBAdapter != null) {
            reqList.addAll(mBAdapter.getReqList());
        }
        if (reqList.size() == 0) {
            finish();
            return;
        }
        mPresenter.commitCheck(reqList);
    }

    @Override
    public void commitSuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
