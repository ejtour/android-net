package com.hll_sc_app.app.goods.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.glide.BannerImageLoader;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品详情
 *
 * @author zhuyingsong
 * @date 2019/6/13
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_DETAIL)
public class GoodsDetailActivity extends BaseLoadActivity implements GoodsDetailContract.IGoodsDetailView {
    @BindView(R.id.banner)
    Banner mBanner;
    @Autowired(name = "object0")
    String mProductID;
    private GoodsDetailPresenter mPresenter;

    public static void start(String productID) {
        RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_DETAIL, productID);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = GoodsDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        mPresenter.queryGoodsDetail(mProductID);
    }

    private void initView() {
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setImageLoader(new BannerImageLoader());
        mBanner.setBannerAnimation(Transformer.Default);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBanner.stopAutoPlay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBanner.startAutoPlay();
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void showDetail(GoodsBean bean) {
        showBanner(bean.getImgUrlSub());
    }

    private void showBanner(String imgUrlSub) {
        if (TextUtils.isEmpty(imgUrlSub)) {
            return;
        }
        mBanner.update(Arrays.asList(imgUrlSub.split(",")));
    }
}
