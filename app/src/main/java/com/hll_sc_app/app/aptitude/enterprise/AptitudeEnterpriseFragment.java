package com.hll_sc_app.app.aptitude.enterprise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hll_sc_app.R;
import com.hll_sc_app.app.aptitude.AptitudeActivity;
import com.hll_sc_app.app.aptitude.AptitudePresenter;
import com.hll_sc_app.app.aptitude.IAptitudeCallback;
import com.hll_sc_app.app.aptitude.IAptitudeContract;
import com.hll_sc_app.app.aptitude.type.AptitudeTypeActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.SimpleSearch;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.bean.aptitude.AptitudeReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.aptitude.AptitudeListView;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

public class AptitudeEnterpriseFragment extends BaseLazyFragment implements IAptitudeContract.IAptitudeView, IAptitudeCallback {
    @BindView(R.id.fae_search_view)
    SearchView mSearchView;
    @BindView(R.id.fae_list_view)
    AptitudeListView mListView;
    private GlideImageView mLicense;
    private String mLicenseUrl;
    private IAptitudeContract.IAptitudePresenter mPresenter;
    private boolean mEditable;
    private View mHeaderView;

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
        mHeaderView = View.inflate(requireContext(), R.layout.view_aptitude_enterprise_header, null);
        mLicense = mHeaderView.findViewById(R.id.aeh_license);
        ViewGroup.LayoutParams params = mLicense.getLayoutParams();
        params.width = (int) ((UIUtils.getScreenWidth(requireContext()) - UIUtils.dip2px(72)) / 5.0f); // 图片间距为8，屏幕边距为20;
        mLicense.setLayoutParams(params);
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
        if (resultCode == Activity.RESULT_OK && data != null
                && requestCode == Constant.IMG_SELECT_REQ_CODE) {
            List<String> list = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(list)) mListView.imageUpload(list.get(0));
        }
        if (data != null
                && requestCode == AptitudeTypeActivity.REQ_CODE) {
            mListView.changeType(data.getParcelableExtra("parcelable"), data.getStringArrayExtra("types"));
        }
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    @Override
    public void setData(List<AptitudeBean> list) {
        List<AptitudeBean> beans = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            for (AptitudeBean bean : list) {
                if (CommonUtils.getInt(bean.getAptitudeType()) == 0) {
                    mLicenseUrl = bean.getAptitudeUrl();
                } else if (!TextUtils.isEmpty(bean.getAptitudeType())) {
                    beans.add(bean);
                }
            }
        }
        updateLicense();
        mListView.setList(beans);
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public void saveSuccess() {
        setEditable(false);
        mPresenter.start();
    }

    private void updateLicense() {
        if (mLicenseUrl == null) {
            mLicenseUrl = ((AptitudeActivity) requireActivity()).getLicenseUrl();
        }
        if (!TextUtils.isEmpty(mLicenseUrl)) {
            mLicense.setImageURL(mLicenseUrl);
            mListView.setHeaderView(mHeaderView);
        } else {
            mListView.clearHeaderView();
        }
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        updateLicense();
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
