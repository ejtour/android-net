package com.hll_sc_app.bean.message;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hll_sc_app.R;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/16/20.
 */
public class MultiUrlItem implements MultiItemEntity {
    public static final int IMG = 0x1;
    public static final int OTH = 0x2;
    private String name;
    private final String url;

    public MultiUrlItem(String url) {
        this.url = url;
    }

    public MultiUrlItem(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return !TextUtils.isEmpty(name) ? name : url.substring(url.lastIndexOf("/") + 1);
    }

    public int getTypeIcon() {
        String ext = url.substring(url.lastIndexOf(".") + 1);
        switch (ext.toLowerCase()) {
            case "doc":
            case "docx":
                return R.drawable.ic_word;
            case "xls":
            case "xlsx":
                return R.drawable.ic_excel;
            case "pdf":
                return R.drawable.ic_pdf;
            case "zip":
                return R.drawable.ic_zip;
            case "rar":
                return R.drawable.ic_rar;
            default:
                return 0;
        }
    }

    @Override
    public int getItemType() {
        String s = url.toLowerCase();
        return s.endsWith("jpg") || s.endsWith("jpeg") || s.endsWith("png") ? IMG : OTH;
    }
}
