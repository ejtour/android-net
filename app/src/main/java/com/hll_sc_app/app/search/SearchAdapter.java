package com.hll_sc_app.app.search;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.utils.ColorStr;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */

public class SearchAdapter extends BaseQuickAdapter<NameValue, BaseViewHolder> {
    private Pattern mPattern;

    SearchAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, NameValue item) {
        helper.setText(R.id.search_name, processItem(item.getName()));
    }

    /**
     * 将匹配到的文本高亮显示
     */
    private SpannableString processItem(String name) {
        SpannableString spannableString = SpannableString.valueOf(name);
        if (mPattern != null) {
            Matcher matcher = mPattern.matcher(name);
            while (matcher.find()) {
                spannableString.setSpan(
                        new ForegroundColorSpan(Color.parseColor(ColorStr.COLOR_222222)),
                        matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }

    /**
     * @param regex 表达式
     */
    void setNewData(@Nullable List<NameValue> data, String regex) {
        mPattern = Pattern.compile(regex);
        super.setNewData(data);
    }
}