package com.hll_sc_app.app.cooperation.detail.details.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.CooperationDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合作采购商详情-需合作门店
 *
 * @author zhuyingsong
 * @date 2019/7/18
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOPS, extras = Constant.LOGIN_EXTRA)
public class CooperationShopListActivity extends BaseLoadActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Autowired(name = "parcelable", required = true)
    ArrayList<PurchaserShopBean> mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_shops);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        CooperationDetailActivity.PurchaserShopListAdapter adapter =
            new CooperationDetailActivity.PurchaserShopListAdapter();
        mRecyclerView.setAdapter(adapter);
        adapter.setNewData(mData);
        adapter.setEmptyView(EmptyView.newBuilder(this).setTips("您还没有合作采购商门店数据").create());
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        finish();
    }
}
