package com.hll_sc_app.widget.order;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.order.statistic.OrderStatisticResp;
import com.hll_sc_app.widget.EasingTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.ViewCollections;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/19
 */

public class OrderStatisticHeader extends ConstraintLayout {
    private static final String[] LABEL_ARRAY = {"今日", "本周", "本月"};
    @BindView(R.id.osh_tab_layout)
    CommonTabLayout mTabLayout;
    @BindView(R.id.osh_shop_num)
    EasingTextView mShopNum;
    @BindView(R.id.osh_order_num)
    EasingTextView mOrderNum;
    @BindView(R.id.osh_order_amount)
    EasingTextView mOrderAmount;
    @BindViews({R.id.osh_shop_num, R.id.osh_order_num, R.id.osh_order_amount})
    List<EasingTextView> mCountViews;

    public OrderStatisticHeader(Context context) {
        this(context, null);
    }

    public OrderStatisticHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderStatisticHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_order_statistic_header, this);
        ButterKnife.bind(this, view);
        setBackgroundResource(R.drawable.bg_white_radius_8_solid);
        int space = UIUtils.dip2px(22);
        setPadding(0, space, 0, space);
        initView();
    }

    private void initView() {
        initTab();
        ViewCollections.run(mCountViews, (view, index) -> view.setProcessor(rawText -> {
            int end = rawText.indexOf("\n");
            if (end == 0) return rawText;
            SpannableString ss = new SpannableString(rawText);
            ss.setSpan(new StyleSpan(Typeface.BOLD), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.color_666666))
                    , 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new RelativeSizeSpan(2f), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return ss;
        }));
    }

    public void setOnTabSelectListener(OnTabSelectListener listener) {
        mTabLayout.setOnTabSelectListener(listener);
    }

    public void setCurrentTab(int currentTab) {
        mTabLayout.setCurrentTab(currentTab);
    }

    private void initTab() {
        ArrayList<CustomTabEntity> arrayList = new ArrayList<>();
        for (String bean : LABEL_ARRAY) {
            arrayList.add(new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return bean;
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
        }
        mTabLayout.setTabData(arrayList);
    }

    public void setData(OrderStatisticResp resp) {
        post(() -> {
            mShopNum.easingText(resp.getShopNum(), EasingTextView.INTEGER);
            mOrderNum.easingText(resp.getTotalNum(), EasingTextView.INTEGER);
            mOrderAmount.easingText(resp.getTotalAmount(), EasingTextView.MONEY);
        });
    }

    public void showSummary(boolean showCount) {
        int space = UIUtils.dip2px(22);
        setPadding(0, space, 0, showCount ? 0 : space);
        ViewCollections.run(mCountViews, (view, index) -> view.setVisibility(showCount ? View.VISIBLE : View.GONE));
    }
}
