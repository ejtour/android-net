package com.hll_sc_app.widget.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.DownLoadBean;

import java.util.List;

public class DownloadAdapter extends BaseQuickAdapter<DownLoadBean, BaseViewHolder> {
    private boolean isDeleteModal;
    public DownloadAdapter(@Nullable List<DownLoadBean> data,boolean isDeleteModal) {
        super(R.layout.list_item_download, data);
        this.isDeleteModal = isDeleteModal;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.img_operation);
        if (this.isDeleteModal){
            ((ImageView)helper.getView(R.id.img_operation)).setImageResource(R.drawable.ic_custom_category_del);
        }
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, DownLoadBean item) {
        ImageView imageType = helper.getView(R.id.img_type);
        GlideImageView imageJPG = helper.getView(R.id.img_jpg);
        imageJPG.isPreview(true);
        ImageView imageOperation = helper.getView(R.id.img_operation);
        helper.setText(R.id.txt_name, item.getName());
        String type = getFileType(item.getName());
        imageType.setVisibility(View.GONE);
        imageJPG.setVisibility(View.GONE);
        imageOperation.setTag(type);
        if (!this.isDeleteModal) {
            switch (type) {
                case "rar":
                    imageType.setVisibility(View.VISIBLE);
                    imageType.setImageResource(R.drawable.ic_rar);
                    imageOperation.setImageResource(R.drawable.ic_download);
                    break;
                case "zip":
                    imageType.setVisibility(View.VISIBLE);
                    imageType.setImageResource(R.drawable.ic_zip);
                    imageOperation.setImageResource(R.drawable.ic_download);
                    break;
                case "doc":
                    imageType.setVisibility(View.VISIBLE);
                    imageType.setImageResource(R.drawable.ic_word);
                    imageOperation.setImageResource(R.drawable.ic_download);
                    break;
                case "jpg"://todo 图片类型需要扩展
                    imageJPG.setVisibility(View.VISIBLE);
                    imageJPG.setImageURL(item.getUrl());
                    imageOperation.setImageResource(R.drawable.ic_download);
                    break;
            }
        }
        helper.setBackgroundColor(R.id.list_item, helper.getLayoutPosition() % 2 == 0 ? Color.WHITE : 0x80f1f3f7);
    }

    private String getFileType(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        String[] types = url.split("\\.");
        return types.length == 2 ? types[1] : "";
    }


}
