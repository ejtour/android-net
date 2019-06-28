package com.hll_sc_app.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;

/**
 * 搜索
 *
 * @author zhuyingsong
 * @date 2019-06-27
 */
public class SearchView extends FrameLayout {
    private TextView mTxtSearchContent;
    private ImageView mImgSearchClear;
    private ContentClickListener mListener;

    public SearchView(@NonNull Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setBackgroundResource(R.color.base_white);
        int padding = UIUtils.dip2px(10);
        setPadding(0, padding, 0, padding);
        View.inflate(context, R.layout.view_search, this);
        mTxtSearchContent = findViewById(R.id.txt_searchContent);
        mImgSearchClear = findViewById(R.id.img_searchClear);
        this.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.click(getSearchContent());
            }
        });
        mImgSearchClear.setOnClickListener(v -> showSearchContent(false, null));
    }

    /**
     * 获取搜索词
     *
     * @return 搜索词
     */
    private String getSearchContent() {
        String searchContent = "";
        if (mImgSearchClear.getVisibility() == View.VISIBLE) {
            searchContent = mTxtSearchContent.getText().toString();
        }
        return searchContent;
    }

    public void showSearchContent(boolean show, String content) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mTxtSearchContent.getLayoutParams();
        if (show) {
            mImgSearchClear.setVisibility(View.VISIBLE);
            mTxtSearchContent.setText(content);
            params.weight = 1;
        } else {
            mImgSearchClear.setVisibility(View.GONE);
            mTxtSearchContent.setText(content);
            params.weight = 0;
        }
        if (mListener != null) {
            mListener.toSearch(getSearchContent());
        }
    }

    public SearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setContentClickListener(ContentClickListener mListener) {
        this.mListener = mListener;
    }

    public interface ContentClickListener {
        /**
         * 点击搜索栏跳转搜索页面
         *
         * @param searchContent 搜索栏内的搜索词
         */
        void click(String searchContent);

        /**
         * 触发搜索
         *
         * @param searchContent 搜索栏内的搜索词
         */
        void toSearch(String searchContent);
    }
}
