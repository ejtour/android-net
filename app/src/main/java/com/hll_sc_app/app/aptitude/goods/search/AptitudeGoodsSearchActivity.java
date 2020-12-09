package com.hll_sc_app.app.aptitude.goods.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;

import java.util.ArrayList;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/11/25
 */
@Route(path = RouterConfig.APTITUDE_GOODS_SEARCH)
public class AptitudeGoodsSearchActivity extends SearchActivity {

    private CommonTabLayout mTabLayout;

    public static void start(Activity context, String searchWords, String key, int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putBoolean("enableReq", index == 0);
        SearchActivity.start(RouterConfig.APTITUDE_GOODS_SEARCH, context, searchWords, key, bundle);
    }

    @Override
    protected void beforeInitView() {
        int index = mExtra.getInt("index", 0);
        mTabLayout = (CommonTabLayout) View.inflate(this, R.layout.view_aptitude_goods_tab, null);
        ArrayList<CustomTabEntity> list = new ArrayList<>();
        list.add(new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return "资质";
            }

            @Override
            public int getTabSelectedIcon() {
                return 0;
            }

            @Override
            public int getTabUnselectedIcon() {
                return 0;
            }
        });
        list.add(new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return "商品";
            }

            @Override
            public int getTabSelectedIcon() {
                return 0;
            }

            @Override
            public int getTabUnselectedIcon() {
                return 0;
            }
        });
        mTabLayout.setTabData(list);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mExtra.putBoolean("enableReq", position == 0);
                mTitleBar.onTextChanged(mTitleBar.getSearchContent());
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        LinearLayout parent = (LinearLayout) mTitleBar.getParent();
        ViewGroup.MarginLayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                UIUtils.dip2px(45));
        layoutParams.bottomMargin = UIUtils.dip2px(1);
        parent.addView(mTabLayout, parent.getChildCount() - 1, layoutParams);
        mTabLayout.setCurrentTab(index);
        super.beforeInitView();
    }

    @Override
    protected void beforeFinish(Intent intent) {
        intent.putExtra("index", mTabLayout.getCurrentTab());
    }
}
