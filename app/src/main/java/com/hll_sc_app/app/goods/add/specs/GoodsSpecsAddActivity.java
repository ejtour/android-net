package com.hll_sc_app.app.goods.add.specs;

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
 * 新增商品规格
 *
 * @author zhuyingsong
 * @date 2019/6/19
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_SPECS, extras = Constant.LOGIN_EXTRA)
public class GoodsSpecsAddActivity extends BaseLoadActivity implements GoodsSpecsAddContract.IGoodsAddView {
    private GoodsSpecsAddPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_specs);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = GoodsSpecsAddPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }


    private void initView() {

    }


    @OnClick({R.id.img_close, R.id.rl_categoryName, R.id.rl_shopProductCategorySubName, R.id.txt_categoryName_copy,
        R.id.txt_specs_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            default:
                break;
        }
    }
}
