package com.hll_sc_app.base.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hll_sc_app.base.R;
import com.hll_sc_app.base.utils.UIUtils;


/**
 * 上传图片的组件
 *
 * @author zc
 * @date 20190605
 */
public class ImgUploadBlock extends RelativeLayout {
    private TextView mTitle;
    private TextView mSubTitle;
    private ImgShowDelBlock mImgShow;
    private OnClickListener mDeleteListener;
    private UploadImgListener mUploadImgListener;

    public ImgUploadBlock(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        if (getBackground() == null)
            setBackgroundResource(R.drawable.base_bg_border_radius_3);
        View.inflate(context, R.layout.base_view_upload_img, this);
        mTitle = findViewById(R.id.txt_title);
        mSubTitle = findViewById(R.id.txt_sub_title);
        mImgShow = findViewById(R.id.img_show);
        mImgShow.setVisibility(GONE);
        mImgShow.setDeleteListener(this::delete);
        setOnClickListener(v -> {
            if (this.mUploadImgListener != null) {
                if (!this.mUploadImgListener.beforeOpenUpload(this)) {
                    return;
                }
            }
            UIUtils.selectPhoto((Activity) getContext());
        });
    }

    private void delete(View view) {
        deleteImage();
        if (mDeleteListener != null)
            mDeleteListener.onClick(view);
    }

    public ImgUploadBlock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.UploadImgBlock);
        setTitle(array.getString(R.styleable.UploadImgBlock_base_title));
        setSubTitle(array.getString(R.styleable.UploadImgBlock_base_subTitle));
        setIconResId(array.getResourceId(R.styleable.UploadImgBlock_base_imgIcon, R.drawable.base_ic_img_add));
        array.recycle();
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setSubTitle(String subTitle) {
        mSubTitle.setText(subTitle);
        mSubTitle.setVisibility(TextUtils.isEmpty(subTitle) ? GONE : VISIBLE);
    }

    public void setEditable(boolean editable) {
        mImgShow.showDel(editable);
    }

    public void setIconResId(int resId) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), resId);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTitle.setCompoundDrawables(null, drawable, null, null);
        }
    }

    public void showImage(String url) {
        if (TextUtils.isEmpty(url)){
            if (!TextUtils.isEmpty(getImgUrl())) {
                deleteImage();
            }
            return;
        }
        mImgShow.setImgUrl(url);
        mImgShow.setVisibility(VISIBLE);
        mSubTitle.setVisibility(GONE);
        mTitle.setVisibility(GONE);
    }

    public void setOnDeleteListener(OnClickListener listener) {
        mDeleteListener = listener;
    }

    public void deleteImage() {
        mImgShow.setImgUrl(null);
        mImgShow.setVisibility(GONE);
        mSubTitle.setVisibility(TextUtils.isEmpty(mSubTitle.getText()) ? GONE : VISIBLE);
        mTitle.setVisibility(VISIBLE);
    }

    public String getImgUrl() {
        return mImgShow != null ? mImgShow.getImageUrl() : null;
    }

    public void setmUploadImgListener(UploadImgListener uploadImgListener) {
        this.mUploadImgListener = uploadImgListener;
    }


    public interface UploadImgListener {
        /**
         * 打开图片库页面之前
         */
        boolean beforeOpenUpload(ImgUploadBlock imgUploadBlock);
    }
}
