package com.hll_sc_app.app.wallet.authentication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BaseShadowPopupWindow;


public class DemoImgWindow extends BaseShadowPopupWindow {
    public static int TYPE_LICENSE = 1;//营业执照
    private View mRootView;
    private Activity mActivity;
    public DemoImgWindow(Activity activity) {
        super(activity);
        mActivity = activity;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = inflater.inflate(R.layout.window_wallet_demo_img, null);
        this.setContentView(mRootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.BasePopupAnimation);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#b3000000"));
        this.setBackgroundDrawable(dw);
        mRootView.setOnClickListener(v -> {
            dismiss();
        });
    }

    public void showDemo(int type){
        ImageView mDemo = mRootView.findViewById(R.id.img_demo);
        if (type == TYPE_LICENSE){
//            mDemo.setImageResource(R.drawable.ic_wallet_demo_license);
        }
        showAtLocation(mActivity.getWindow().getDecorView(), Gravity.END,0,0);
    }
}
