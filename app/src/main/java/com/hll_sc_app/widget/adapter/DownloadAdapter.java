package com.hll_sc_app.widget.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
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
    public DownloadAdapter(@Nullable List<DownLoadBean> data) {
        super(R.layout.list_item_download, data);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.img_operation);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, DownLoadBean item) {
        ImageView imageType = helper.getView(R.id.img_type);
        GlideImageView imageJPG = helper.getView(R.id.img_jpg);
        ImageView imageOperation = helper.getView(R.id.img_operation);
        helper.setText(R.id.txt_name, item.getName());
        String type = getFileType(item.getUrl());
        imageType.setVisibility(View.GONE);
        imageJPG.setVisibility(View.GONE);
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
            case "jpg":
                imageJPG.setVisibility(View.VISIBLE);
                imageJPG.setImageURL(item.getUrl());
                imageOperation.setImageResource(R.drawable.ic_download);
        }
        helper.setBackgroundColor(R.id.list_item, helper.getLayoutPosition() % 2 == 0 ? 0x80f1f3f7 : Color.WHITE);
    }

    private String getFileType(String url) {
        String[] types = url.split(".");
        return types.length == 2 ? types[1] : "";
    }


}
