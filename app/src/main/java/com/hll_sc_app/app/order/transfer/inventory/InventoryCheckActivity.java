package com.hll_sc_app.app.order.transfer.inventory;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.transfer.InventoryBean;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/4
 */

@Route(path = RouterConfig.ORDER_INVENTORY_CHECK)
public class InventoryCheckActivity extends BaseLoadActivity {
    public static final int REQ_KEY = 0x956;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_check);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initView();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::sure);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
    }

    private void sure(View view) {
        showToast("确定");
    }
}
