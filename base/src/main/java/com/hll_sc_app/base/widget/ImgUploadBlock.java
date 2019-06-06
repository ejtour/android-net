package com.hll_sc_app.base.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hll_sc_app.base.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.Glide4Engine;
import com.hll_sc_app.base.utils.permission.RequestPermissionUtils;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils;

import java.util.EnumSet;
import java.util.Set;


/**
 * 上传图片的组件
 *
 * @author zc
 * @date 20190605
 */
public class ImgUploadBlock extends RelativeLayout {
    public static final int REQUEST_CODE_CHOOSE = 104;
    private static final String[] PERMISSIONS = {Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE,
        Permission.WRITE_EXTERNAL_STORAGE};
    private TextView mTitle;
    private TextView mSubTitle;
    private ImgShowDelBlock mImgShow;
    /**
     * 字节单位 上传图片的最大大小
     */
    private int maxSize;

    public ImgUploadBlock(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        setBackgroundResource(R.drawable.base_bg_border_radius_3);
        View.inflate(context, R.layout.base_view_upload_img, this);
        mTitle = findViewById(R.id.txt_title);
        mSubTitle = findViewById(R.id.txt_sub_title);
        mImgShow = findViewById(R.id.img_show);
        mImgShow.setVisibility(GONE);
        setOnClickListener(v -> new RequestPermissionUtils(getContext(), PERMISSIONS, this::selectPhoto).requestPermission());
    }

    private void selectPhoto() {
        Matisse.from(((Activity) getContext()))
            .choose(MimeType.ofImage())
            .theme(R.style.Matisse_Dracula)
            .countable(false)
            .addFilter(new MiniSizeFilter(maxSize))
            .maxSelectable(1)
            .capture(true)
            .captureStrategy(new CaptureStrategy(true, ((Activity) getContext()).getApplication().getPackageName() +
                ".fileprovider"))
            .gridExpectedSize(UIUtils.dip2px(120))
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .thumbnailScale(0.85f)
            .imageEngine(new Glide4Engine())
            .forResult(REQUEST_CODE_CHOOSE);
    }

    public ImgUploadBlock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BaseUploadImgBlock);
        setTitle(array.getString(R.styleable.BaseUploadImgBlock_base_title));
        setSubTitle(array.getString(R.styleable.BaseUploadImgBlock_base_subTitle));
        setIconResId(array.getResourceId(R.styleable.BaseUploadImgBlock_base_imgIcon, R.drawable.base_ic_img_add));
        array.recycle();
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setSubTitle(String subTitle) {
        mSubTitle.setText(subTitle);
        mSubTitle.setVisibility(TextUtils.isEmpty(subTitle) ? GONE : VISIBLE);
    }

    public void setIconResId(int resId) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), resId);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTitle.setCompoundDrawables(null, drawable, null, null);
        }
    }

    public void showImage(String url, OnClickListener listener) {
        mImgShow.setImgUrl(url);
        mImgShow.setDeleteListener(listener);
        mImgShow.setVisibility(VISIBLE);
        mSubTitle.setVisibility(GONE);
        mTitle.setVisibility(GONE);
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

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public static class MiniSizeFilter extends Filter {
        private int maxSize;

        MiniSizeFilter(int maxSize) {
            this.maxSize = maxSize;
        }

        @Override
        protected Set<MimeType> constraintTypes() {
            return EnumSet.of(MimeType.JPEG, MimeType.PNG, MimeType.BMP, MimeType.WEBP);
        }

        @Override
        public IncapableCause filter(Context context, Item item) {
            if (!needFiltering(context, item)) {
                return null;
            }
            if (maxSize > 0 && (item.size > maxSize)) {
                return new IncapableCause(IncapableCause.TOAST, "上传图片的大小不能超过%s兆",
                    String.valueOf(PhotoMetadataUtils.getSizeInMB(maxSize)));
            }
            return null;
        }
    }
}
