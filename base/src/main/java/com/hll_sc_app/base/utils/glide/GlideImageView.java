package com.hll_sc_app.base.utils.glide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.hll_sc_app.base.R;
import com.hll_sc_app.base.utils.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Glide ImageView
 *
 * @author zhuyingsong
 * @date 2018/12/20
 */
public class GlideImageView extends android.support.v7.widget.AppCompatImageView {
    public static final String DISABLE_IMAGE = "DISABLE_IMAGE";
    public static final String DISABLE_SHOP = "DISABLE_SHOP";
    public static final String GROUP_BLOCK_UP = "GROUP_BLOCK_UP";
    public static final String GROUP_LOG_OUT = "GROUP_LOG_OUT";
    /**
     * 是否按宽度等比例显示
     */
    private boolean isScaleByWidth;
    /**
     * 占位图
     */
    private Drawable mPlaceholder;
    /**
     * 失败图
     */
    private Drawable mError;
    /**
     * 圆角
     */
    private int mRoundingRadius;
    private boolean mCircle;
    private boolean mCenterInside;
    private Context mContext;
    private String mUrl;
    private boolean mNeedLoad;
    private int mWidth, mHeight;
    /**
     * 开启预览模式
     */
    private boolean mPreview;
    /**
     * 所有url 用于预览时，提供所有url，实现多图的滚动浏览
     */
    private List<String> mUrls;
    private String mType;
    private GlideRequests mReq;

    public GlideImageView(Context context) {
        super(context);
        mContext = context;
    }

    public GlideImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GlideImageView);
        mPlaceholder = array.getDrawable(R.styleable.GlideImageView_base_placeholder);
        if (mPlaceholder == null) {
            mPlaceholder = ContextCompat.getDrawable(context, R.drawable.ic_placeholder);
        }
        mError = array.getDrawable(R.styleable.GlideImageView_base_error);
        if (mError == null) {
            mError = mPlaceholder;
        }
        mRoundingRadius = array.getInt(R.styleable.GlideImageView_base_RoundingRadius, 0);
        mCircle = array.getBoolean(R.styleable.GlideImageView_base_circle, false);
        mPreview = array.getBoolean(R.styleable.GlideImageView_base_preview, false);
        array.recycle();
        onClickEventListener();
    }

    private GlideRequests req() {
        if (mReq == null) {
            mReq = GlideApp.with(this);
        }
        return mReq;
    }

    private void onClickEventListener() {
        if (mPreview) {
            this.setOnClickListener(v -> {
                ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, GlideImageView.this,
                        "image");
                Intent intent = new Intent(mContext, ImageViewActivity.class);
                intent.putExtra("url", mUrl);
                intent.putStringArrayListExtra("urls", (ArrayList<String>) mUrls);
                ActivityCompat.startActivity(mContext, intent, options.toBundle());
            });
        }
    }

    public void setLocalImage(Drawable drawable) {
        setOptions(req().load(drawable)).into(this);
    }

    private GlideRequest setOptions(GlideRequest request) {
        request = request.error(mError).placeholder(mPlaceholder);
        if (mRoundingRadius != 0) {
            request = request.transform(new MultiTransformation<>(new CenterCrop(),
                new RoundedCorners(UIUtils.dip2px(mRoundingRadius))));
        } else {
            request = request.centerCrop();
        }
        if (mCircle) {
            request = request.circleCrop();
        }
        if (mCenterInside) {
            request = request.centerInside();
        }
        if (isScaleByWidth) {
            // 商品详情页面
            request = request.fitCenter();
        }
        return request;
    }

    /**
     * 显示商品禁用的标识
     *
     * @param url 商品URL
     */
    public void setDisableImageUrl(String url, String type) {
        mUrl = TextUtils.isEmpty(url) ? "" : url.trim();
        mType = type;
        loadUrl();
    }

    private void loadUrl() {
        if (mNeedLoad && mUrl != null) {
            StringBuilder sb = new StringBuilder(mUrl);
            if (mWidth > 0 && mHeight > 0) {
                if (mUrl.startsWith("group")) {
                    sb.insert(mUrl.lastIndexOf("."), "=" + mWidth + "x" + mHeight);
                } else {
                    sb.append(String.format("?x-oss-process=image/resize,m_fill,h_%s,w_%s", mHeight, mWidth));
                }
            }
            String myUrl = "http://res.hualala.com/" + sb.toString();
            if (!TextUtils.isEmpty(mType)) {
                setOptions(req().load(myUrl)).into(new ActivityCustomViewTarget(this, mType));
            } else {
                setOptions(req().load(myUrl)).into(this);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mWidth != w || mHeight != h) {
            mNeedLoad = true;
            mWidth = w;
            mHeight = h;
            loadUrl();
        }
    }

    /**
     * 在看大图的组件中 imageviewactivity中 设置为false，请求原图
     *
     * @param url         url
     * @param isSmallSize 是否根据imageview的大小去请求对应大小的图片 设置false 请求原图
     */
    public void setImageURL(String url, boolean isSmallSize) {
        if (isSmallSize) {
            setImageURL(url);
        } else {
            url = TextUtils.isEmpty(url) ? "" : url.trim();
            setOptions(req().load("http://res.hualala.com/" + url)).into(this);
        }
    }

    /**
     * 请求符合ImageView大小的图片 减少图片缓存大小 和 网络请求时间
     *
     * @param url url
     */
    public void setImageURL(String url) {
        mUrl = TextUtils.isEmpty(url) ? "" : url.trim();
        mType = null;
        loadUrl();
    }

    public void setImageURL(int resID) {
        setOptions(req().load(resID)).into(this);
    }

    public void setRadius(int radius) {
        mRoundingRadius = radius;
    }

    public void setPlaceholder(Drawable placeholder) {
        mPlaceholder = placeholder;
    }

    public GlideImageView setScaleByWidth(boolean scaleByWidth) {
        isScaleByWidth = scaleByWidth;
        return this;
    }

    public void isPreview(boolean isPreview) {
        mPreview = isPreview;
        onClickEventListener();
    }

    public void setUrls(List<String> urls) {
        this.mUrls = urls;
    }

    public void setUrls(String[] urls) {
        this.mUrls = new ArrayList<>(Arrays.asList(urls));
    }

    /**
     * 设置图片的裁剪模式为 centerInside
     *
     * @param centerInside true
     */
    public void setCenterInside(boolean centerInside) {
        this.mCenterInside = centerInside;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if (d != null && isScaleByWidth) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            // 高度根据使得图片的宽度充满屏幕计算而得
            int height =
                (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public static class Builder {
        /**
         * 是否按宽度等比例显示
         */
        private boolean isScaleByWidth;
        /**
         * 占位图
         */
        private Drawable mPlaceholder;

        /**
         * 圆角
         */
        private int mRoundingRadius;
        private boolean mCircle;
        private boolean mCenterInside;

        private Context mContext;

        private String mUrl;

        /**
         * 所有url 用于预览时，提供所有url，实现多图的滚动浏览
         */
        private List<String> urls;
        /**
         * 开启预览模式
         */
        private boolean mPreview;


        private Builder(Context context) {
            mContext = context;
        }

        public static Builder create(Context context) {
            return new Builder(context);
        }

        public Builder setScaleByWidth(boolean scaleByWidth) {
            isScaleByWidth = scaleByWidth;
            return this;
        }

        public Builder setPlaceholder(Drawable mPlaceholder) {
            this.mPlaceholder = mPlaceholder;
            return this;
        }


        public Builder setRoundingRadius(int mRoundingRadius) {
            this.mRoundingRadius = mRoundingRadius;
            return this;
        }

        public Builder setCircle(boolean mCircle) {
            this.mCircle = mCircle;
            return this;
        }

        public Builder setCenterInside(boolean mCenterInside) {
            this.mCenterInside = mCenterInside;
            return this;
        }


        public Builder setUrl(String mUrl) {
            this.mUrl = mUrl;
            return this;
        }

        public Builder setPreview(boolean mPreview) {
            this.mPreview = mPreview;
            return this;
        }

        public Builder setUrls(List<String> urls) {
            this.urls = urls;
            return this;
        }

        public GlideImageView create() {
            GlideImageView glideImageView = new GlideImageView(mContext);
            glideImageView.isScaleByWidth = isScaleByWidth;
            glideImageView.mCircle = mCircle;
            glideImageView.setPlaceholder(mPlaceholder);
            glideImageView.setRadius(mRoundingRadius);
            glideImageView.setCenterInside(mCenterInside);
            glideImageView.isPreview(mPreview);
            glideImageView.setScaleByWidth(isScaleByWidth);
            glideImageView.setUrls(urls);
            glideImageView.setImageURL(mUrl);
            return glideImageView;
        }
    }
}