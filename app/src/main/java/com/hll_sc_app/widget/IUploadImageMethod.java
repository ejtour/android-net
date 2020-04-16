package com.hll_sc_app.widget;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.ImgShowDelBlock;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Upload;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 多张图片上传的方法
 */
public interface IUploadImageMethod {

    /**
     * 上传图片网络请求
     *
     * @param data
     * @param activity
     */
    default void uploadImg(Intent data, BaseLoadActivity activity) {
        List<String> list = Matisse.obtainPathResult(data);
        if (!CommonUtils.isEmpty(list)) {
            Upload.upload(activity, list.get(0), this::addImgUrlDetail);
        }
    }

    /**
     * 显示图片
     *
     * @param url
     */
    default void addImgUrlDetail(String url) {
        if (getImageContainer().getTag() == null) {
            getImageContainer().setTag(new ArrayList<String>());
        }
        ArrayList<String> urls = (ArrayList<String>) getImageContainer().getTag();
        urls.add(url);
        ImgShowDelBlock block = new ImgShowDelBlock(getImageActivity());
        block.setImgUrl(url);
        block.setDeleteListener(v -> {
            urls.remove(block.getImageUrl());
            getImageContainer().removeView(block);
            getUploadBlock().setSubTitle(String.format("%s/%s", getImageContainer().getChildCount() - 1, getMaxImageNum()));
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(60),
                UIUtils.dip2px(60));
        params.rightMargin = UIUtils.dip2px(10);

        int curChildCount = getImageContainer().getChildCount();
        getImageContainer().addView(block, curChildCount - 1, params);
        getUploadBlock().setSubTitle(String.format("%s/%s", getImageContainer().getChildCount() - 1, getMaxImageNum()));
        getUploadBlock().setVisibility(curChildCount == getMaxImageNum() ? View.GONE : View.VISIBLE);
    }

    /**
     * 存储图片的容器
     *
     * @return
     */
    LinearLayout getImageContainer();

    /**
     * 继承该类的activity
     *
     * @return
     */
    Activity getImageActivity();

    /**
     * 上传图片的组件
     */
    ImgUploadBlock getUploadBlock();

    /**
     * 上传图片的数量
     *
     * @return
     */
    int getMaxImageNum();

    /**
     * 获取所有上传的图片的url
     *
     * @return
     */
    default List<String> getImageUrls() {
        Object o = getImageContainer().getTag();
        if (o == null) {
            return Collections.singletonList("");
        }
        return (List<String>) o;
    }
}
