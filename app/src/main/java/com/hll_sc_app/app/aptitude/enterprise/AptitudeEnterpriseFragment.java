package com.hll_sc_app.app.aptitude.enterprise;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.aptitude.AptitudeActivity;
import com.hll_sc_app.app.aptitude.AptitudePresenter;
import com.hll_sc_app.app.aptitude.IAptitudeCallback;
import com.hll_sc_app.app.aptitude.IAptitudeContract;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.SimpleSearch;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.bean.aptitude.AptitudeReq;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.aptitude.AptitudeListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

public class AptitudeEnterpriseFragment extends BaseLazyFragment implements IAptitudeContract.IAptitudeView, IAptitudeCallback {
    @BindView(R.id.fae_search_view)
    SearchView mSearchView;
    @BindView(R.id.fae_list_view)
    AptitudeListView mListView;
    @BindView(R.id.fae_time_filter)
    TextView mTimeFilter;
    private IAptitudeContract.IAptitudePresenter mPresenter;
    private boolean mEditable;

    public static AptitudeEnterpriseFragment newInstance() {
        return new AptitudeEnterpriseFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = AptitudePresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_aptitude_enterprise, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mSearchView.setSearchBackgroundColor(R.drawable.bg_white_radius_15_solid);
        mSearchView.setHint("请输入证件类型搜索");
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(requireActivity(), searchContent, SimpleSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mListView.setSearchWords(searchContent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
        mListView.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    @Override
    public void setData(List<AptitudeBean> list) {
        mListView.setList(list);
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public void saveSuccess() {
        setEditable(false);
        mPresenter.loadList();
    }

    @Override
    public void expireTip(String msg) {
        TipsDialog.newBuilder(requireActivity())
                .setMessage(msg)
                .setButton((dialog, item) -> dialog.dismiss(), "知道了")
                .setTitle("资质到期")
                .create().show();
    }

    @Override
    public boolean in30Day() {
        return mTimeFilter.isSelected();
    }

    @Override
    public void rightClick() {
        if (mEditable) {
            AptitudeReq req = new AptitudeReq();
            req.setGroupID(UserConfig.getGroupID());
            req.setAptitudeList(mListView.getList());
            mPresenter.save(req);
        } else {
            setEditable(true);
        }
    }

    @OnClick(R.id.fae_time_filter)
    void clickFilter(View view) {
        view.setSelected(!view.isSelected());
        mPresenter.loadList();
    }

    @Override
    public boolean isEditable() {
        return mEditable;
    }

    @Override
    public void setEditable(boolean editable) {
        mEditable = editable;
        mListView.setEditable(mEditable);
        mSearchView.setVisibility(editable ? View.GONE : View.VISIBLE);
        ((AptitudeActivity) requireActivity()).onPageSelected(1);
    }
}
