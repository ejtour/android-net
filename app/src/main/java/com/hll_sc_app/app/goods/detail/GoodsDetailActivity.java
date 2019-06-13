package com.hll_sc_app.app.goods.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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
import com.hll_sc_app.bean.goods.NicknamesBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @BindView(R.id.txt_productName)
    TextView mTxtProductName;
    @BindView(R.id.txt_nextDayDelivery)
    TextView mTxtNextDayDelivery;
    @BindView(R.id.txt_depositProduct)
    TextView mTxtDepositProduct;
    @BindView(R.id.flowLayout)
    TagFlowLayout mFlowLayout;
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
        showBanner(bean);
        showProductName(bean);
    }

    private void showBanner(GoodsBean bean) {
        List<String> list = new ArrayList<>();
        list.add(bean.getImgUrl());
        if (!TextUtils.isEmpty(bean.getImgUrlSub())) {
            list.addAll(Arrays.asList(bean.getImgUrlSub().split(",")));
        }
        mBanner.setImages(list).setImageLoader(new BannerImageLoader(list)).start();
    }

    private void showProductName(GoodsBean bean) {
        mTxtProductName.setText(bean.getProductName());
        mTxtNextDayDelivery.setVisibility(TextUtils.equals(bean.getNextDayDelivery(), "1") ? View.VISIBLE : View.GONE);
        mTxtDepositProduct.setVisibility(TextUtils.equals(bean.getDepositProductType(), "1") ? View.VISIBLE :
            View.GONE);
        List<NicknamesBean> nicknamesBeanList = bean.getNicknames();
        if (!CommonUtils.isEmpty(nicknamesBeanList)) {
            mFlowLayout.setVisibility(View.VISIBLE);
            List<NicknamesBean> list = new ArrayList<>();
            for (NicknamesBean nicknamesBean : nicknamesBeanList) {
                if (TextUtils.equals("2", nicknamesBean.getNicknameType())) {
                    list.add(nicknamesBean);
                }
            }
            mFlowLayout.setAdapter(new FlowAdapter(this, list));
        } else {
            mFlowLayout.setVisibility(View.GONE);
        }
    }

    private static class FlowAdapter extends TagAdapter<NicknamesBean> {
        private LayoutInflater mInflater;

        FlowAdapter(Context context, List<NicknamesBean> list) {
            super(list);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(FlowLayout flowLayout, int position, NicknamesBean s) {
            TextView tv = (TextView) mInflater.inflate(R.layout.view_item_goods_detail_name, flowLayout, false);
            tv.setText(s.getNickname());
            return tv;
        }
    }
}
