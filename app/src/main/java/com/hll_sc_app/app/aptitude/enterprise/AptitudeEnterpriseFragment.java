package com.hll_sc_app.app.aptitude.enterprise;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aptitude.AptitudeActivity;
import com.hll_sc_app.app.aptitude.IAptitudeCallback;
import com.hll_sc_app.app.aptitude.enterprise.add.AptitudeEnterpriseAddActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.SimpleSearch;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.aptitude.AptitudeEnterpriseBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

public class AptitudeEnterpriseFragment extends BaseLazyFragment implements IAptitudeEnterpriseContract.IAptitudeEnterpriseView, IAptitudeCallback {
    @BindView(R.id.fae_search_view)
    SearchView mSearchView;
    @BindView(R.id.fae_list_view)
    RecyclerView mListView;
    private GlideImageView mLicense;
    private Adapter mAdapter;
    private String mLicenseUrl;
    private List<AptitudeEnterpriseBean> mList;
    private IAptitudeEnterpriseContract.IAptitudeEnterprisePresenter mPresenter;

    public static AptitudeEnterpriseFragment newInstance() {
        return new AptitudeEnterpriseFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = AptitudeEnterprisePresenter.newInstance();
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
        mAdapter = new Adapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            AptitudeEnterpriseBean item = mAdapter.getItem(position);
            if (item == null) return;
            if (view.getId() == R.id.iae_del) {
                mPresenter.delete(item);
            } else {
                AptitudeEnterpriseAddActivity.start(requireActivity(), item);
            }
        });
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
        mListView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(requireContext()));
        View header = View.inflate(requireContext(), R.layout.view_aptitude_enterprise_header, null);
        mLicense = header.findViewById(R.id.aeh_license);
        mAdapter.setHeaderView(header);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(requireActivity(), searchContent, SimpleSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mAdapter.setNewData(filter(searchContent));
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
        if (requestCode == AptitudeEnterpriseAddActivity.REQ_CODE && resultCode == Activity.RESULT_OK) {
            initData();
        }
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    @Override
    public void setData(List<AptitudeEnterpriseBean> list) {
        mList = new ArrayList<>();
        AptitudeEnterpriseBean zero = null;
        if (!CommonUtils.isEmpty(list)) {
            for (AptitudeEnterpriseBean bean : list) {
                if (CommonUtils.getInt(bean.getAptitudeType()) == 0) {
                    mLicenseUrl = bean.getAptitudeUrl();
                } else {
                    mList.add(bean);
                }
            }
        }
        updateLicense();
        mAdapter.setNewData(filter(mSearchView.getSearchContent()));
    }

    private void updateLicense() {
        if (mLicenseUrl == null) {
            mLicenseUrl = ((AptitudeActivity) requireActivity()).getLicenseUrl();
        }
        if (mLicenseUrl != null) {
            mLicense.setImageURL(mLicenseUrl);
        }
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        updateLicense();
    }

    private List<AptitudeEnterpriseBean> filter(String searchWords) {
        if (TextUtils.isEmpty(searchWords) || mList == null) {
            return mList;
        }
        List<AptitudeEnterpriseBean> list = new ArrayList<>();
        for (AptitudeEnterpriseBean bean : mList) {
            if (bean.getAptitudeName().contains(searchWords)) {
                list.add(bean);
            }
        }
        return list;
    }

    public String getTypes() {
        if (mAdapter == null) {
            return "";
        } else {
            List<AptitudeEnterpriseBean> list = mAdapter.getData();
            if (CommonUtils.isEmpty(list)) return "";
            List<String> types = new ArrayList<>();
            for (AptitudeEnterpriseBean bean : list) {
                types.add(bean.getAptitudeType());
            }
            return TextUtils.join(",", types);
        }
    }

    @Override
    public void rightClick() {
        AptitudeEnterpriseAddActivity.start(requireActivity(), getTypes());
    }

    private static class Adapter extends BaseQuickAdapter<AptitudeEnterpriseBean, BaseViewHolder> {

        Adapter() {
            super(R.layout.item_aptitude_enterprise);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
            helper.addOnClickListener(R.id.iae_del)
                    .addOnClickListener(R.id.iae_root);
            return helper;
        }

        @Override
        protected void convert(BaseViewHolder helper, AptitudeEnterpriseBean item) {
            ((GlideImageView) helper.setText(R.id.iae_type, item.getAptitudeName())
                    .setText(R.id.iae_date, DateUtil.getReadableTime(item.getEndTime(), Constants.SLASH_YYYY_MM_DD))
                    .getView(R.id.iae_image)).setImageURL(item.getAptitudeUrl());
        }
    }
}
