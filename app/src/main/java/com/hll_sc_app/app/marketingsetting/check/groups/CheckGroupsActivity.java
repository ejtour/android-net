package com.hll_sc_app.app.marketingsetting.check.groups;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.marketingsetting.check.shops.CheckShopsActivity;
import com.hll_sc_app.app.marketingsetting.helper.MarketingHelper;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.marketingsetting.CouponSendReq;
import com.hll_sc_app.bean.marketingsetting.MarketingCustomerBean;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/3/4
 */

@Route(path = RouterConfig.ACTIVITY_MARKETING_CHECK_GROUPS)
public class CheckGroupsActivity extends BaseLoadActivity implements ICheckGroupsContract.ICheckGroupsView {
    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;
    @Autowired(name = "parcelable")
    ArrayList<MarketingCustomerBean> mList;
    private CheckGroupsAdapter mAdapter;
    private ICheckGroupsContract.ICheckGroupsPresenter mPresenter;

    public static void start(ArrayList<MarketingCustomerBean> list) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_MARKETING_CHECK_GROUPS, list);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        mPresenter = new CheckGroupsPresenter();
        mPresenter.register(this);
        initView();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("集团");
        mAdapter = new CheckGroupsAdapter(MarketingHelper.convertCustomerToCouponBean(mList));
        mListView.setAdapter(mAdapter);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), ViewUtils.dip2px(this, 0.5f));
        decor.setLineMargin(UIUtils.dip2px(10), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CouponSendReq.GroupandShopsBean item = mAdapter.getItem(position);
            if (item == null) return;
            if (item.getScope() == 0) {
                handleData(new ArrayList<>(item.getShopNameList()));
            } else {
                mPresenter.reqShopList(item.getPurchaserID());
            }
        });
    }

    @Override
    public void handleData(ArrayList<String> shopNameList) {
        CheckShopsActivity.start(shopNameList);
    }
}
