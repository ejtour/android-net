package com.hll_sc_app.base.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hll_sc_app.base.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;

import java.util.List;

/**
 * 展示上传后的图片的组件
 *
 * @author zc
 * @date 2019/2/13
 */
public class ImgShowDelBlock extends RelativeLayout {
    private GlideImageView mImgShow;
    private ImageView mImgDel;
    private TextView mTxtContent;
    private String mUrl;
    private boolean mShowDel = true;

    public ImgShowDelBlock(Context context) {
        super(context);
        initView(context);
    }

    public static boolean isImageFile(String url) {
        return url.endsWith(".jpg") || url.endsWith(".png");
    }

    public ImgShowDelBlock(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ImgShowDelBlock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.base_view_img_show_del, this);
        mImgShow = findViewById(R.id.img_content);
        mImgDel = findViewById(R.id.img_del);
        mTxtContent = findViewById(R.id.txt_content);
    }

    public void setFileUrl(String url, String fileName) {
        if (mShowDel) {
            mImgDel.setVisibility(View.VISIBLE);
        }
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        mImgShow.setImageURL(getFileResource(fileType));
        mTxtContent.setText(fileName);
        mImgShow.setVisibility(View.VISIBLE);
        mTxtContent.setVisibility(View.VISIBLE);
        mTxtContent.setTag(url);
        setOnClickListener(v -> {
            //todo 下载文件

        });
    }

    /**
     * 根据文件类型返回图标
     *
     * @param fileType
     * @return
     */
    private int getFileResource(String fileType) {
        fileType = fileType.toLowerCase();
        switch (fileType) {
            case "rar":
                return R.drawable.ic_rar;
            case "zip":
                return R.drawable.ic_zip;
            case "doc":
            case "docx":
                return R.drawable.ic_word;
            case "pdf":
                return R.drawable.ic_pdf;
            default:
                return -1;
        }
    }

    public String getImageUrl() {
        return mUrl;
    }

    public void setDeleteListener(OnClickListener listener) {
        mImgDel.setOnClickListener(listener);
    }

    public void setImgUrl(String url) {
        this.mUrl = url;
        mTxtContent.setVisibility(View.GONE);
        if (TextUtils.isEmpty(url)) {
            mImgShow.setVisibility(View.GONE);
            mImgDel.setVisibility(View.GONE);
        } else {
            if (mShowDel) {
                mImgDel.setVisibility(View.VISIBLE);
            }
            mImgShow.setVisibility(View.VISIBLE);
            mImgShow.isPreview(true);
            mImgShow.setImageURL(url);
        }
    }


    /**
     * 设置删除图标的显示隐藏
     *
     * @param isShow true
     */
    public void showDel(boolean isShow) {
        mShowDel = isShow;
        if (mImgDel != null) {
            mImgDel.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置浏览时所有的url；
     *
     * @param urls url
     */
    public void setUrls(List<String> urls) {
        mImgShow.setUrls(urls);
    }

    @Override
    public void setTag(Object tag) {
        super.setTag(tag);
        mImgDel.setTag(tag);
    }
}
