package com.hll_sc_app.app.goods.stick;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.widget.SearchView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品置顶管理
 *
 * @author zhuyingsong
 * @date 2019/7/1
 */
@Route(path = RouterConfig.GOODS_STICK_MANAGE, extras = Constant.LOGIN_EXTRA)
public class GoodsStickActivity extends BaseLoadActivity implements GoodsStickContract.IGoodsStickView {
    @BindView(R.id.img_close)
    ImageView mImgClose;
    @BindView(R.id.rl_toolbar)
    RelativeLayout mRlToolbar;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.recyclerView_level1)
    RecyclerView mRecyclerViewLevel1;
    @BindView(R.id.recyclerView_product)
    RecyclerView mRecyclerViewProduct;
    @BindView(R.id.txt_checkNum)
    TextView mTxtCheckNum;
    @BindView(R.id.text_save)
    TextView mTextSave;
    @BindView(R.id.fl_bottom)
    RelativeLayout mFlBottom;
    private GoodsStickPresenter mPresenter;
    private CustomCategoryAdapter mCategoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_stick);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        mPresenter = GoodsStickPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mRecyclerViewLevel1.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showCustomCategoryList(CustomCategoryResp resp) {

    }

    @OnClick({R.id.img_close, R.id.text_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                break;
            case R.id.text_save:
                break;
            default:
                break;
        }
    }

    class CustomCategoryAdapter extends BaseQuickAdapter<CustomCategoryBean, BaseViewHolder> {

        CustomCategoryAdapter() {
            super(R.layout.item_goods_custom_category_top);
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomCategoryBean item) {
            helper.setText(R.id.txt_categoryName, item.getCategoryName());
        }

    }
}
