package com.hll_sc_app.app.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.REPORT_CATEGORY_LIST)
public class ReportCategoryManagerActivity extends BaseLoadActivity {


    @BindView(R.id.report_product_sale)
    LinearLayout reportProductSale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_category_list);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.report_product_sale)
    public void onViewClicked() {
        RouterUtil.goToActivity(RouterConfig.REPORT_PRODUCT_AGGREGATION);
    }
}
