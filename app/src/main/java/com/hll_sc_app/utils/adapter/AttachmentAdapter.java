package com.hll_sc_app.utils.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Environment;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.message.FileAttachment;
import com.hll_sc_app.bean.message.MultiUrlItem;
import com.hll_sc_app.citymall.App;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/16/20.
 */
public class AttachmentAdapter extends BaseMultiItemQuickAdapter<MultiUrlItem, BaseViewHolder> {
    private int mImageCount;
    private final int mRadius;
    private final String mPath;

    public AttachmentAdapter() {
        super(null);
        addItemType(MultiUrlItem.IMG, R.layout.item_image_attachment);
        addItemType(MultiUrlItem.OTH, R.layout.item_attachment);
        mRadius = UIUtils.dip2px(3);
        mPath = Environment.getExternalStoragePublicDirectory("Documents") + File.separator + App.INSTANCE.getString(R.string.app_name);
    }

    public String getPath() {
        return mPath;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        if (viewType == MultiUrlItem.OTH) {
            helper.addOnClickListener(R.id.ia_action);
        } else {
            ((GlideImageView) helper.getView(R.id.iia_image)).setScaleByWidth(true);
        }
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiUrlItem item) {
        if (helper.getItemViewType() == MultiUrlItem.IMG) {
            ((GlideImageView) helper.getView(R.id.iia_image)).setImageURL(item.getUrl());
        } else if (helper.getItemViewType() == MultiUrlItem.OTH) {
            int position = helper.getAdapterPosition() - getHeaderLayoutCount() - mImageCount;
            GradientDrawable drawable = (GradientDrawable) helper.itemView.getBackground();
            drawable.setColor(position % 2 == 0 ? Color.WHITE : Color.parseColor("#fafafa"));
            boolean isFirst = position == 0;
            ((ViewGroup.MarginLayoutParams) helper.itemView.getLayoutParams()).topMargin = isFirst ? mRadius * 2 : 0;
            boolean isLast = position == mData.size() - getHeaderLayoutCount() - mImageCount;
            int topRadius = isFirst ? mRadius : 0;
            int bottomRadius = isLast ? mRadius : 0;
            drawable.setCornerRadii(new float[]{topRadius, topRadius, topRadius, topRadius,
                    bottomRadius, bottomRadius, bottomRadius, bottomRadius});
            helper.itemView.setBackground(drawable);
            File file = new File(mPath, item.getName());
            helper.setText(R.id.ia_name, item.getName())
                    .setGone(R.id.ia_action, !file.exists())
                    .setGone(R.id.ia_done, file.exists())
                    .setImageResource(R.id.ia_icon, item.getTypeIcon());
        }
    }

    public void setData(List<String> urls) {
        List<MultiUrlItem> list = new ArrayList<>();
        List<MultiUrlItem> other = new ArrayList<>();
        for (String url : urls) {
            String s = url.toLowerCase();
            if (s.endsWith("jpg") || s.endsWith("jpeg") || s.endsWith("png")) {
                list.add(new MultiUrlItem(url));
            } else {
                other.add(new MultiUrlItem(url));
            }
        }
        mImageCount = list.size();
        list.addAll(other);
        setNewData(list);
    }

    public void setFiles(List<FileAttachment> files) {
        List<MultiUrlItem> list = new ArrayList<>();
        List<MultiUrlItem> other = new ArrayList<>();
        for (FileAttachment file : files) {
            String s = file.getFileUrl().toLowerCase();
            if (s.endsWith("jpg") || s.endsWith("jpeg") || s.endsWith("png")) {
                list.add(new MultiUrlItem(file.getFileUrl(), file.getFileName()));
            } else {
                other.add(new MultiUrlItem(file.getFileUrl(), file.getFileName()));
            }
        }
        mImageCount = list.size();
        list.addAll(other);
        setNewData(list);
    }
}
