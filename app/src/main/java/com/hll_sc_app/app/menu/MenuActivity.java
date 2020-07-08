package com.hll_sc_app.app.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/12
 */
@Route(path = RouterConfig.MENU)
public class MenuActivity extends BaseLoadActivity {

    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;
    protected MenuAdapter mAdapter;
    private IMenuStrategy mMenuStrategy;

    public static void start(String key) {
        RouterUtil.goToActivity(RouterConfig.MENU, key);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_simple_list);
        ButterKnife.bind(this);
        mMenuStrategy = MenuFactory.getMenuStrategy(getIntent().getStringExtra("object0"));
        initView();
    }

    private void initView() {
        mListView.setPadding(0, UIUtils.dip2px(10), 0, UIUtils.dip2px(10));
        mTitleBar.setHeaderTitle(mMenuStrategy.getTitle());
        mAdapter = new MenuAdapter(mMenuStrategy.getList(), getItemClickListener());
        mListView.setAdapter(mAdapter);
        View footerView = getFooterView();
        if (footerView != null) {
            mAdapter.setFooterView(footerView);
        }
    }

    protected BaseQuickAdapter.OnItemClickListener getItemClickListener() {
        return null;
    }

    protected View getFooterView() {
        return null;
    }
}
