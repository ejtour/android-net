package com.hll_sc_app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.aftersales.AfterSalesDetailsBean;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class ThumbnailView extends LinearLayout {

    private int thumbnailSize;
    private int picPadding;
    private boolean mPreviewEnable;

    public ThumbnailView(Context context) {
        this(context, null);
    }

    public ThumbnailView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThumbnailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 获取xml传入的属性
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ThumbnailView);
        picPadding = typedArray.getDimensionPixelSize(R.styleable.ThumbnailView_ctv_padding, UIUtils.dip2px(5)); // 图片之间的间距
        int margin = typedArray.getDimensionPixelSize(R.styleable.ThumbnailView_ctv_margin, UIUtils.dip2px(10)); // 组件距离屏幕两边的距离
        typedArray.recycle();
        // 空白大小
        int space = 4 * picPadding + getPaddingLeft() + getPaddingRight() + 2 * margin;
        // 计算每个图片的宽高
        thumbnailSize = (UIUtils.getScreenWidth(getContext()) - space) / 5;
        // 水平布局
        setOrientation(LinearLayout.HORIZONTAL);
        if (getBackground() == null) {
            setBackgroundColor(Color.rgb(250, 250, 250));
        }
    }

    public void setOrderDetailListData(List<OrderDetailBean> list) {
        List<ThumbnailBean> beans = new ArrayList<>();
        for (OrderDetailBean bean : list) {
            double num = bean.getSubBillStatus() == 4 || bean.getSubBillStatus() == 6 ? bean.getInspectionNum() : bean.getAdjustmentNum();
            beans.add(new ThumbnailBean(bean.getImgUrl(), num));
        }
        setData(beans);
    }

    public void setThumbnailListData(List<AfterSalesDetailsBean> list) {
        List<ThumbnailBean> beans = new ArrayList<>();
        for (AfterSalesDetailsBean data : list) {
            beans.add(new ThumbnailBean(data.getImgUrl(), data.getRefundNum()));
        }
        setData(beans);
    }

    public void enablePreview(boolean enable) {
        mPreviewEnable = enable;
    }

    public void setData(List<ThumbnailBean> list) {
        // 清空
        this.removeAllViews();

        // 不为空
        if (!CommonUtils.isEmpty(list)) {
            // 图片容器
            int max = Math.min(list.size(), 5);
            List<String> urls = new ArrayList<>();
            if (mPreviewEnable) {
                for (int i = 0; i < max; i++) {
                    urls.add(list.get(i).getImgUrl());
                }
            }
            for (int i = 0; i < max; i++) {
                ThumbnailBean item = list.get(i);
                LayoutParams layoutParams = new LayoutParams(thumbnailSize, thumbnailSize);
                layoutParams.setMargins(i == 0 ? 0 : picPadding, 0, 0, 0);
                if (item.isShowNum()) {
                    // 包含图片和商品数量TextView的RelativeLayout
                    FrameLayout wrapper = new FrameLayout(getContext());
                    // 图片
                    GlideImageView image = new GlideImageView(getContext());
                    image.setPlaceholder(getResources().getDrawable(R.drawable.ic_placeholder));
                    image.setRadius(2);
                    image.setImageURL(item.getImgUrl());
                    image.isPreview(mPreviewEnable);
                    image.setUrls(urls);
                    wrapper.addView(image, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                    // 设置文本
                    TextView textView = new TextView(getContext());
                    FrameLayout.LayoutParams tvLayoutParams =
                            new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, thumbnailSize / 3);
                    textView.setBackgroundResource(R.drawable.bg_thumbnail_text);
                    tvLayoutParams.gravity = Gravity.BOTTOM;
                    textView.setLayoutParams(tvLayoutParams);
                    textView.setPadding(0, 0, UIUtils.dip2px(3), 0);
                    textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                    textView.setTextColor(Color.argb(255, 255, 255, 255));
                    textView.setTextSize(10);
                    textView.setText(String.format("x%s", CommonUtils.formatNum(item.getNum())));
                    wrapper.addView(textView);
                    this.addView(wrapper, layoutParams);
                } else {
                    GlideImageView image = new GlideImageView(getContext());
                    image.setPlaceholder(getResources().getDrawable(R.drawable.ic_placeholder));
                    image.setRadius(2);
                    image.setImageURL(item.getImgUrl());
                    image.isPreview(mPreviewEnable);
                    image.setUrls(urls);
                    this.addView(image, layoutParams);
                }
            }
        }
    }

    public void setData(String[] array) {
        List<ThumbnailBean> beans = new ArrayList<>();
        for (String s : array) {
            beans.add(new ThumbnailBean(s));
        }
        setData(beans);
    }

    static class ThumbnailBean {
        private String imgUrl;
        private double num;
        private boolean showNum;

        ThumbnailBean(String imgUrl) {
            this(imgUrl, 0, false);
        }

        ThumbnailBean(String imgUrl, double num) {
            this(imgUrl, num, true);
        }

        ThumbnailBean(String imgUrl, double num, boolean showNum) {
            this.imgUrl = imgUrl;
            this.num = num;
            this.showNum = showNum;
        }

        String getImgUrl() {
            return imgUrl;
        }

        double getNum() {
            return num;
        }

        boolean isShowNum() {
            return showNum;
        }
    }
}

