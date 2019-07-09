package com.hll_sc_app.widget.aftersales;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.widget.TriangleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/9
 */

public class PurchaserShopSelectWindow extends BasePopupWindow {
    private static final String GROUP = "集团";
    private static final String SHOP = "门店";
    @BindView(R.id.pss_search_type)
    TextView mSearchType;
    @BindView(R.id.pss_search_edit)
    EditText mSearchEdit;
    @BindView(R.id.pss_left_list)
    RecyclerView mLeftList;
    @BindView(R.id.pss_right_list)
    RecyclerView mRightList;
    @BindView(R.id.pss_top_bar_bg)
    View mPssTopBarBg;
    @BindView(R.id.pss_toggle_btn)
    View mPssToggleBtn;
    @BindView(R.id.pss_triangle)
    TriangleView mPssTriangle;
    @BindView(R.id.pss_ver_div)
    View mPssVerDiv;
    @BindView(R.id.pss_search_btn)
    TextView mPssSearchBtn;
    @BindView(R.id.pss_reset)
    TextView mPssReset;
    @BindView(R.id.pss_confirm)
    TextView mPssConfirm;

    public PurchaserShopSelectWindow(Activity activity) {
        super(activity);
        View rootView = View.inflate(mActivity, R.layout.window_purchaser_shop_select, null);
        rootView.setOnClickListener(v -> dismiss());
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xbb000000));
    }

    @OnClick({R.id.pss_toggle_btn, R.id.pss_search_btn, R.id.pss_reset, R.id.pss_confirm, R.id.pss_bg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pss_toggle_btn:
                CharSequence text = mSearchType.getText();
                if (GROUP.contentEquals(text)) mSearchType.setText(SHOP);
                else mSearchType.setText(GROUP);
                break;
            case R.id.pss_search_btn:
                break;
            case R.id.pss_reset:
                break;
            case R.id.pss_confirm:
                break;
        }
    }
}
