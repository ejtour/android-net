package com.hll_sc_app.app.goods.add.customcategory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 自定义分类
 *
 * @author zhuyingsong
 * @date 2019/6/18
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_CUSTOM_CATEGORY, extras = Constant.LOGIN_EXTRA)
public class GoodsCustomCategoryActivity extends BaseLoadActivity implements GoodsCustomCategoryContract.IGoodsCustomCategoryView {
    private GoodsCustomCategoryPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_custom_category);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = GoodsCustomCategoryPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
    }


    @OnClick({R.id.img_close, R.id.txt_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_edit:
                // 编辑
                break;
            default:
                break;
        }
    }
}
