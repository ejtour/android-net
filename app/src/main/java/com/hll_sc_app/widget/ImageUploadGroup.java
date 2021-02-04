package com.hll_sc_app.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.hll_sc_app.R;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.ImgShowDelBlock;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Upload;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/17
 */

public class ImageUploadGroup extends LinearLayout {
    private ImgUploadBlock mUpload;
    private List<String> mUploadImgUrls = new ArrayList<>();
    /**
     * 凭证最大数量
     */
    private final int MAX_IMG_NUMBER = 5;
    private int mItemSize;
    private int mPadding;
    private String mLabel;
    private OnClickListener mListener;
    private OnImageCountChangedListener mChangeListener;
    private boolean mEditable;

    public ImageUploadGroup(Context context) {
        this(context, null);
    }

    public ImageUploadGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageUploadGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        calcItemSize(attrs);
        mUpload = new ImgUploadBlock(context);
        mUpload.setIconResId(R.drawable.ic_camera);
        mUpload.setTitle(mLabel);
        mUpload.setSubTitle("0/5");
        mUpload.setOnTouchListener((v, event) -> {
            if (mListener != null) mListener.onClick(this);
            return false;
        });
        addView(mUpload, mItemSize, mItemSize);
    }

    public void setEditable(boolean editable) {
        mEditable = editable;
        mUpload.setVisibility(editable && mUploadImgUrls.size() < MAX_IMG_NUMBER ? VISIBLE : GONE);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof ImgShowDelBlock) {
                ((ImgShowDelBlock) view).showDel(editable);
            }
        }
    }

    private void calcItemSize(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImageUploadGroup);
        mPadding = typedArray.getDimensionPixelSize(R.styleable.ImageUploadGroup_iug_padding, UIUtils.dip2px(10));
        int margin = typedArray.getDimensionPixelSize(R.styleable.ImageUploadGroup_iug_margin, 0);
        mLabel = typedArray.getString(R.styleable.ImageUploadGroup_iug_label);
        if (TextUtils.isEmpty(mLabel)) mLabel = "上传凭证";
        typedArray.recycle();
        float sw = UIUtils.getScreenWidth(getContext());
        mItemSize = (int) ((sw - 2 * margin - 4 * mPadding) / 5);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null
                && requestCode == Constant.IMG_SELECT_REQ_CODE) {
            List<String> list = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(list)) {
                imageUpload(list.get(0));
            }
        }
    }

    private void imageUpload(String path) {
        if (!(getContext() instanceof ILoadView)) return;
        Upload.upload((ILoadView) (getContext()), path, this::showUploadedImg);
    }

    public void showImages(String[] urls) {
        reset();
        if (urls == null || urls.length == 0) return;
        for (String url : urls) {
            if (!TextUtils.isEmpty(url)) {
                showUploadedImg(url);
            }
        }
    }

    private void reset() {
        mUploadImgUrls.clear();
        mUpload.setSubTitle("0/5");
        if (getChildCount() > 1) {
            View upload = getChildAt(getChildCount() - 1);
            upload.setVisibility(mEditable ? VISIBLE : GONE);
            removeAllViews();
            addView(upload);
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mListener = l;
    }

    public void setChangedListener(OnImageCountChangedListener listener) {
        mChangeListener = listener;
    }

    private void showUploadedImg(String url) {
        mUploadImgUrls.add(url);
        if (mChangeListener != null) mChangeListener.onChanged(mUploadImgUrls);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mItemSize, mItemSize);
        layoutParams.setMargins(0, 0, mPadding, 0);
        ImgShowDelBlock del = new ImgShowDelBlock(getContext());
        del.setImgUrl(url);
        del.setLayoutParams(layoutParams);
        del.showDel(mEditable);
        addView(del, mUploadImgUrls.size() - 1);
        del.setTag(url);
        del.setDeleteListener(v -> {
            if (v.getTag() == null) return;
            int delIndex = mUploadImgUrls.indexOf(v.getTag().toString());
            mUploadImgUrls.remove(delIndex);
            if (mChangeListener != null) mChangeListener.onChanged(mUploadImgUrls);
            removeViewAt(delIndex);
            mUpload.setVisibility(View.VISIBLE);
            mUpload.setSubTitle(mUploadImgUrls.size() + "/" + MAX_IMG_NUMBER);
        });
        mUpload.setSubTitle(mUploadImgUrls.size() + "/" + MAX_IMG_NUMBER);
        //当图片为最多 则隐藏上传图片组件
        if (MAX_IMG_NUMBER == mUploadImgUrls.size())
            mUpload.setVisibility(View.GONE);
        //设置浏览时所需要的所有urls
        del.setUrls(mUploadImgUrls);
    }

    public List<String> getUploadImgUrls() {
        return mUploadImgUrls;
    }

    public interface OnImageCountChangedListener {
        void onChanged(List<String> urls);
    }
}
