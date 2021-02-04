package com.hll_sc_app.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.citymall.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;


public class TitleBar extends ConstraintLayout {

    @BindView(R.id.vtb_left_image)
    ImageView mLeftImage;
    @BindView(R.id.vtb_right_image)
    ImageView mRightImage;
    @BindView(R.id.vtb_title)
    TextView mTitle;
    @BindView(R.id.vtb_right_text)
    TextView mRightText;
    /***
     * 右侧按钮是否显示
     */
    private boolean rightButtonVisible;
    /***
     * 头部文字
     */
    private String headerTitle;

    /***
     * 右侧文字
     */
    private String rightButtonText;

    /**
     * 右侧图片
     */
    private int rightImgResId;
    private float mMaxDistance;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        attrsInit(context, attrs);
        init(context);
    }

    private void attrsInit(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        headerTitle = array.getString(R.styleable.TitleBar_tb_title);
        rightButtonVisible = array.getBoolean(R.styleable.TitleBar_tb_rightVisibility, false);
        rightButtonText = array.getString(R.styleable.TitleBar_tb_rightText);
        rightImgResId = array.getResourceId(R.styleable.TitleBar_tb_rightImage, R.drawable.ic_options);
        array.recycle();
    }

    private void init(Context context) {
        ViewGroup rootView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_title_bar, this, true);
        ButterKnife.bind(this, rootView);
        if (getBackground() == null) {
            setBackgroundResource(R.drawable.base_bg_shadow_top_bar);
        }
        bindDefaultClick();
        setHeaderTitle(headerTitle);
        setRightButtonImg(rightImgResId);
        setRightText(rightButtonText);
        setRightBtnVisible(rightButtonVisible);
    }

    private void bindDefaultClick() {
        //返回上一层页面
        mLeftImage.setOnClickListener(this::close);
    }

    public void setHeaderTitle(String title) {
        mTitle.setText(title);
    }

    public void setRightButtonImg(int resId) {
        mRightText.setVisibility(View.GONE);
        mRightImage.setVisibility(VISIBLE);
        mRightImage.setImageResource(resId);
    }

    /**
     * 设置显示右侧文字
     */
    public void setRightText(String text) {
        rightButtonText = text;
        mRightText.setText(text);
        mRightImage.setVisibility(View.GONE);
        mRightText.setVisibility(View.VISIBLE);
    }

    /***
     * 设置右侧是否显示
     */
    public void setRightBtnVisible(boolean visible) {
        if (visible) {
            if (!TextUtils.isEmpty(rightButtonText)) mRightText.setVisibility(VISIBLE);
            else mRightImage.setVisibility(VISIBLE);
        } else {
            mRightImage.setVisibility(GONE);
            mRightText.setVisibility(GONE);
        }
    }

    private void close(View v) {
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).finish();
        }
    }

    /**
     * 设置右侧的按钮触发函数
     */
    public void setRightBtnClick(OnClickListener listener) {
        mRightImage.setOnClickListener(listener);
        mRightText.setOnClickListener(listener);
    }

    /**
     * 设置左侧按钮触发函数
     */
    public void setLeftBtnClick(OnClickListener listener) {
        mLeftImage.setOnClickListener(listener);
    }

    /**
     * 设置中间的点击触发函数
     */
    public void setTextClick(OnClickListener listener) {
        mTitle.setOnClickListener(listener);
    }

    public void setGradientDistance(int distance) {
        mMaxDistance = distance;
    }

    public void gradientWithRecyclerView(RecyclerView listView) {
        setAlphaSelf(0);
        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int distance = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mMaxDistance == 0) mMaxDistance = getHeight();
                if (mTitle == null || mMaxDistance == 0) return;
                distance = distance + dy;
                setAlphaSelf(distance <= mMaxDistance ? distance / mMaxDistance : 1);
            }
        });
    }

    private void setAlphaSelf(float alpha) {
        getBackground().mutate().setAlpha((int) (255 * alpha));
        mTitle.setAlpha(alpha);
    }

    @OnLongClick(R.id.vtb_title)
    boolean onLongClick() {
        if ("设置".equals(mTitle.getText().toString()) && HttpConfig.isVip()) {
            ToastUtils.showShort(HttpConfig.getHost());
            return true;
        }
        return false;
    }
}
