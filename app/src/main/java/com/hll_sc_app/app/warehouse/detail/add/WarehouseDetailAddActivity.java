package com.hll_sc_app.app.warehouse.detail.add;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.warehouse.detail.WarehouseShopAdapter;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.RefreshWarehouseShopList;
import com.hll_sc_app.bean.warehouse.WarehouseShopBean;
import com.hll_sc_app.bean.warehouse.WarehouseShopEditReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/24
 */
@Route(path = RouterConfig.WAREHOUSE_DETAIL_ADD)
public class WarehouseDetailAddActivity extends BaseLoadActivity implements IWarehouseDetailAddContract.IWarehouseDetailAddView {
    @BindView(R.id.wda_select_all)
    TextView mSelectAll;
    @BindView(R.id.wda_confirm)
    TextView mConfirm;
    @BindView(R.id.wda_list_view)
    RecyclerView mListView;
    @Autowired(name = "object0")
    String mGroupID;
    private WarehouseShopAdapter mAdapter;
    private IWarehouseDetailAddContract.IWarehouseDetailAddPresenter mPresenter;
    private EmptyView mEmptyView;

    /**
     * @param groupID     代仓公司id
     */
    public static void start(String groupID) {
        RouterUtil.goToActivity(RouterConfig.WAREHOUSE_DETAIL_ADD, groupID);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_detail_add);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = WarehouseDetailAddPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), ViewUtils.dip2px(this, 0.5f));
        decor.setLineMargin(UIUtils.dip2px(UIUtils.dip2px(105)), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mAdapter = new WarehouseShopAdapter(true);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            WarehouseShopBean item = mAdapter.getItem(position);
            if (item == null) return;
            item.setSelect(!item.isSelect());
            mAdapter.notifyItemChanged(position);
            updateBottomData();
        });
    }

    private void updateBottomData() {
        int num = 0;
        for (WarehouseShopBean bean : mAdapter.getData()) {
            if (bean.isSelect()) {
                num++;
            }
        }
        mSelectAll.setSelected(num == mAdapter.getData().size() && num > 0);
        mConfirm.setText(String.format("确认 (%s)", num));
        mConfirm.setEnabled(num > 0);
    }

    @OnClick(R.id.wda_select_all)
    public void selectAll(View view) {
        List<WarehouseShopBean> list = mAdapter.getData();
        if (CommonUtils.isEmpty(list)) return;
        for (WarehouseShopBean bean : list) {
            bean.setSelect(!view.isSelected());
        }
        updateBottomData();
    }

    @OnClick(R.id.wda_confirm)
    public void confirm() {
        List<String> shopIds = new ArrayList<>();
        for (WarehouseShopBean bean : mAdapter.getData()) {
            if (bean.isSelect()) {
                shopIds.add(bean.getId());
            }
        }
        WarehouseShopEditReq req = new WarehouseShopEditReq();
        req.setPurchaserID(mGroupID);
        req.setShopIds(shopIds);
        mPresenter.confirm(req);
    }

    @Override
    public void setData(List<WarehouseShopBean> list) {
        mAdapter.setNewData(list);
        if (CommonUtils.isEmpty(list)) {
            initEmptyView();
            mEmptyView.reset();
            mEmptyView.setTipsTitle("无数据");
        }
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    @Override
    public void success() {
        showToast("新增代仓门店成功");
        EventBus.getDefault().post(new RefreshWarehouseShopList());
        finish();
    }

    @Override
    public String getPurchaserID() {
        return mGroupID;
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }
}
