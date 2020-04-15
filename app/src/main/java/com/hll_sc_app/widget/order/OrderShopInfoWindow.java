package com.hll_sc_app.widget.order;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BaseShadowPopupWindow;
import com.hll_sc_app.bean.order.shop.OrderShopBean;
import com.hll_sc_app.widget.TriangleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/6
 */

public class OrderShopInfoWindow extends BaseShadowPopupWindow {
    @BindView(R.id.osi_arrow)
    TriangleView mArrow;
    @BindView(R.id.osi_bg)
    View mBg;
    @BindView(R.id.osi_shop_name)
    TextView mShopName;
    @BindView(R.id.osi_contact_people)
    TextView mContactPeople;
    @BindView(R.id.osi_phone)
    TextView mPhone;
    @BindView(R.id.osi_address)
    TextView mAddress;
    private int mHeight;

    public OrderShopInfoWindow(Activity activity) {
        super(activity);
        initWindow();
        initView();
    }

    private void initView() {
        View view = View.inflate(mActivity, R.layout.window_order_shop_info, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        view.setOnClickListener(v -> dismiss());
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mHeight = view.getMeasuredHeight();
    }

    private void initWindow() {
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable());
    }

    @OnClick(R.id.osi_bg)
    public void onViewClicked() {
    }

    public OrderShopInfoWindow setData(OrderShopBean bean) {
        mShopName.setText(bean.getShopName());
        mContactPeople.setText(bean.getLinkman());
        mPhone.setText(PhoneUtil.formatPhoneNum(bean.getMobile()));
        mPhone.setTag(bean.getMobile());
        mAddress.setText(bean.getShopAddress());
        return this;
    }

    public void showAtLocationCompat(View anchor) {
        showAnim();
        int[] location = new int[2];
        anchor.getLocationOnScreen(location);
        if (location[1] + anchor.getHeight() + mHeight >= UIUtils.getScreenHeight(mActivity)) {
            directionBottom();
            showAtLocation(anchor, Gravity.NO_GRAVITY, 0, location[1] - mHeight);
        } else {
            directionTop();
            showAtLocation(anchor, Gravity.NO_GRAVITY, 0, location[1] + anchor.getHeight());
        }
    }

    private void directionBottom() {
        mArrow.update(TriangleView.BOTTOM, Color.WHITE);
        ConstraintLayout.LayoutParams arrowParams = (ConstraintLayout.LayoutParams) mArrow.getLayoutParams();
        arrowParams.topToTop = -1;
        arrowParams.topToBottom = R.id.osi_bg;
        ConstraintLayout.LayoutParams bgParams = (ConstraintLayout.LayoutParams) mBg.getLayoutParams();
        bgParams.topToBottom = -1;
        bgParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        mBg.setLayoutParams(bgParams);
    }

    private void directionTop() {
        mArrow.update(TriangleView.TOP, Color.WHITE);
        ConstraintLayout.LayoutParams arrowParams = (ConstraintLayout.LayoutParams) mArrow.getLayoutParams();
        arrowParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        arrowParams.topToBottom = -1;
        ConstraintLayout.LayoutParams bgParams = (ConstraintLayout.LayoutParams) mBg.getLayoutParams();
        bgParams.topToBottom = R.id.osi_arrow;
        bgParams.topToTop = -1;
        mBg.setLayoutParams(bgParams);
    }

    @OnClick(R.id.osi_phone)
    public void dial(View view) {
        if (view.getTag() != null) {
            UIUtils.callPhone(view.getTag().toString());
        }
    }
}
