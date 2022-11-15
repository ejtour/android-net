package com.hll_sc_app.app.simple;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.inquiry.detail.InquiryDetailActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.SimpleSearch;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/11
 */

@Route(path = RouterConfig.SEARCH_LIST)
public class SearchListActivity extends BaseActivity {
    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.asl_search_view)
    SearchView mSearchView;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;
    @Autowired(name = "title")
    String mTitle;
    @Autowired(name = "list")
    ArrayList<String> mList;
    @Autowired(name = "enableSearch")
    boolean mEnableSearch;
    private SimpleStringAdapter mAdapter;

    public static void start(String title, ArrayList<String> list, boolean enableSearch) {
        ARouter.getInstance()
                .build(RouterConfig.SEARCH_LIST)
                .withString("title", title)
                .withStringArrayList("list", list)
                .withBoolean("enableSearch", enableSearch)
                .navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
    }

    private void initView() {
        if (mEnableSearch) {
            mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
                @Override
                public void click(String searchContent) {
                    SearchActivity.start(SearchListActivity.this, searchContent, SimpleSearch.class.getSimpleName());
                }

                @Override
                public void toSearch(String searchContent) {
                    if (TextUtils.isEmpty(searchContent)) {
                        mAdapter.setNewData(mList);
                    } else {
                        List<String> list = new ArrayList<>();
                        for (String s : mList) {
                            if (s.contains(searchContent)) {
                                list.add(s);
                            }
                        }
                        mAdapter.setNewData(list);
                    }
                }
            });
        }
        mTitleBar.setHeaderTitle(mTitle);
        mListView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), ViewUtils.dip2px(this, 0.5f)));
        mAdapter = new SimpleStringAdapter(mList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }

    private static class SimpleStringAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        SimpleStringAdapter(List<String> data) {
            super(R.layout.item_report_search_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            String[] strings = item.split(InquiryDetailActivity.DIVISION_FLAG);
            TextView textView = (TextView) helper.itemView;
            if (strings.length == 1) {
                textView.setText(strings[0]);
            } else {
                textView.setText(null);
                textView.append(strings[0]);
                textView.append("\n");
                SpannableString spannableString = new SpannableString(strings[1]);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), 0, spannableString.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new RelativeSizeSpan(0.8f), 0, spannableString.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.append(spannableString);
            }
        }
    }
}
