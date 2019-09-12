package com.hll_sc_app.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.hll_sc_app.R;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.ImgShowDelBlock;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Upload;
import com.zhihu.matisse.Matisse;

import java.io.File;
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
    private ILoadView mView;
    private int mPadding;

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
        mUpload.setTitle("上传凭证");
        mUpload.setSubTitle("0/5");
        addView(mUpload, mItemSize, mItemSize);
    }

    private void calcItemSize(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImageUploadGroup);
        mPadding = typedArray.getDimensionPixelSize(R.styleable.ImageUploadGroup_iug_padding, UIUtils.dip2px(10));
        int margin = typedArray.getDimensionPixelSize(R.styleable.ImageUploadGroup_iug_margin, 0);
        typedArray.recycle();
        float sw = UIUtils.getScreenWidth(getContext());
        mItemSize = (int) ((sw - 2 * margin - 4 * mPadding) / 5);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null
                && requestCode == ImgUploadBlock.REQUEST_CODE_CHOOSE) {
            List<String> list = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(list)) {
                imageUpload(new File(list.get(0)));
            }
        }
    }

    public void register(ILoadView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    protected void onDetachedFromWindow() {
        mView = null;
        super.onDetachedFromWindow();
    }

    public void imageUpload(File imageFile) {
        Upload.imageUpload(imageFile, new SimpleObserver<String>(mView) {
            @Override
            public void onSuccess(String s) {
                showUploadedImg(s);
            }
        });
    }

    public void showImages(String[] urls) {
        if (urls == null || urls.length == 0) return;
        for (String url : urls) {
            showUploadedImg(url);
        }
    }

    private void showUploadedImg(String url) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mItemSize, mItemSize);
        layoutParams.setMargins(0, 0, mPadding, 0);
        ImgShowDelBlock del = new ImgShowDelBlock(getContext());
        del.setImgUrl(url);
        del.setLayoutParams(layoutParams);
        addView(del, mUploadImgUrls.size());
        del.setDeleteListener(v -> {
            int delIndex = mUploadImgUrls.indexOf(url);
            removeViewAt(delIndex);
            mUploadImgUrls.remove(delIndex);
            mUpload.setVisibility(View.VISIBLE);
            mUpload.setSubTitle(mUploadImgUrls.size() + "/" + MAX_IMG_NUMBER);
        });
        mUploadImgUrls.add(url);
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
}
