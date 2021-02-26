package com.hll_sc_app.app.goodsdemand.special.detail;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.detail.GoodsDemandDetailAdapter;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goodsdemand.SpecialDemandBean;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.goodsdemand.GoodsSpecialDemandHeader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/11
 */
@Route(path = RouterConfig.GOODS_SPECIAL_DEMAND_DETAIL)
public class SpecialDemandDetailActivity extends BaseLoadActivity {

    @Autowired(name = "parcelable")
    SpecialDemandBean mBean;
    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;

    public static void start(SpecialDemandBean bean) {
        RouterUtil.goToActivity(RouterConfig.GOODS_SPECIAL_DEMAND_DETAIL, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
    }

    private void initView() {
        mTitleBar.setHeaderTitle(mBean.getPurchaserName());
        GoodsSpecialDemandHeader header = new GoodsSpecialDemandHeader(this);
        header.setData(mBean);
        GoodsDemandDetailAdapter adapter = new GoodsDemandDetailAdapter(mBean.getDemandList());
        adapter.setHeaderView(header);
        mListView.setAdapter(adapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
    }
}
