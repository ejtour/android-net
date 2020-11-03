package com.hll_sc_app.app.order.statistic.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.statistic.OrderShopStatisticAdapter;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.statistic.OrderStatisticBean;
import com.hll_sc_app.bean.order.statistic.OrderStatisticShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/5
 */

@Route(path = RouterConfig.ORDER_SHOP_STATISTIC)
public class ShopOrderStatisticActivity extends BaseLoadActivity implements IShopOrderStatisticContract.IShopOrderStatisticView {
    @BindView(R.id.sos_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.sos_image)
    GlideImageView mImage;
    @BindView(R.id.sos_name)
    TextView mName;
    @BindView(R.id.sos_info)
    TextView mInfo;
    @BindView(R.id.sos_list_view)
    RecyclerView mListView;
    @Autowired(name = "parcelable")
    OrderStatisticBean mBean;
    private OrderShopStatisticAdapter mAdapter;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();

    public static void start(OrderStatisticBean bean, boolean notOrder, int timetype) {
        bean.setNotOrder(notOrder);
        bean.setTimeType(timetype);
        RouterUtil.goToActivity(RouterConfig.ORDER_SHOP_STATISTIC, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_shop_order_statistic);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        IShopOrderStatisticContract.IShopOrderStatisticPresenter presenter = ShopOrderStatisticPresenter.newInstance();
        presenter.register(this);
        if (mBean.isNotOrder()) {
            mReq.put("groupID", UserConfig.getGroupID());
            mReq.put("purchaserID", mBean.getPurchaserID());
            mReq.put("timetype", String.valueOf(mBean.getTimeType()));
            presenter.start();
        }
        initView();
    }

    private void initView() {
        mTitleBar.setHeaderTitle(mBean.isNotOrder() ? "未下单门店" : "下单门店");
        String source = String.format("合作门店：%s  |  %s：%s",
                CommonUtils.formatNum(mBean.getCoopShopNum()),
                mBean.isNotOrder() ? "未下单门店" : "已下单门店",
                CommonUtils.formatNum(mBean.getShopNum()));
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_999999)),
                0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_999999)),
                source.indexOf("|"), source.lastIndexOf("：") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mInfo.setText(ss);
        mName.setText(mBean.getPurchaserName());
        mImage.setImageURL(mBean.getGroupLogoUrl());

        mAdapter = new OrderShopStatisticAdapter();
        mListView.setAdapter(mAdapter);
        setData(mBean.getShopList());
    }

    @Override
    public void setData(List<OrderStatisticShopBean> list) {
        mAdapter.setNewData(list, getPrefix(), mBean.isNotOrder());
    }

    private String getPrefix() {
        if (mBean.isNotOrder()) {
            return mBean.getTimeType() == 1 ? "昨日" : mBean.getTimeType() == 2 ? "上周" : "上月";
        }
        return "";
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }
}
