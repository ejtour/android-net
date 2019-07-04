package com.hll_sc_app.app.order.transfer.inventory;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.transfer.InventoryBean;
import com.hll_sc_app.bean.order.transfer.InventoryCheckReq;
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

@Route(path = RouterConfig.ORDER_INVENTORY_CHECK)
public class InventoryCheckActivity extends BaseLoadActivity implements IInventoryCheckContract.IInventoryCheckView {
    public static final int REQ_KEY = 0x956;
    private InventoryCheckPresenter mPresenter;

    /**
     * @param list 库存明细列表
     */
    public static void start(Activity context, ArrayList<InventoryBean> list) {
        RouterUtil.goToActivity(RouterConfig.ORDER_INVENTORY_CHECK, context, REQ_KEY, list);
    }

    @BindView(R.id.aic_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aic_list_view)
    RecyclerView mListView;
    @Autowired(name = "parcelable", required = true)
    ArrayList<InventoryBean> mList;
    private InventoryCheckAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_check);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initView();
        initData();
    }

    private void initData() {
        mPresenter = InventoryCheckPresenter.newInstance();
        mPresenter.register(this);
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::sure);
        mAdapter = new InventoryCheckAdapter(mList);
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void sure(View view) {
        List<InventoryCheckReq.InventoryCheckBean> reqList = mAdapter.getReqList();
        for (InventoryCheckReq.InventoryCheckBean bean : reqList) {
            if (bean.getFlag() == 0) {
                showToast("存在未处理库存不足商品 请确认是否全部处理完成");
                return;
            }
        }
        mPresenter.commitCheck(reqList);
    }

    @Override
    public void commitSuccess() {
        showToast("处理成功");
        setResult(RESULT_OK);
        finish();
    }
}
