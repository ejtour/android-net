package com.hll_sc_app.app.crm.customer.search;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.customer.CustomerBean;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/27
 */

@Route(path = RouterConfig.CRM_CUSTOMER_SEARCH)
public class CustomerSearchActivity extends BaseLoadActivity implements ICustomerSearchContract.ICustomerSearchView {
    private static final int REQ_CODE = 0x323;
    @BindView(R.id.ccs_search_edit)
    EditText mSearchEdit;
    @BindView(R.id.ccs_search_clear)
    ImageView mSearchClear;
    @BindView(R.id.ccs_list_view)
    RecyclerView mListView;
    @BindView(R.id.ccs_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "object0")
    String mID;
    @Autowired(name = "object1")
    int mType;
    private CustomerSearchAdapter mAdapter;
    private ICustomerSearchContract.ICustomerSearchPresenter mPresenter;
    private EmptyView mEmptyView;

    /**
     * @param id   已选id
     * @param type 0-意向客户 1-合作门店 2-合作集团
     */
    public static void start(Activity context, String id, int type) {
        Object[] args = {id, type};
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_SEARCH, context, REQ_CODE, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_crm_customer_search);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = CustomerSearchPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mAdapter = new CustomerSearchAdapter();
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Parcelable item = mAdapter.getItem(position);
            if (item != null) {
                Intent intent = new Intent();
                intent.putExtra("parcelable", item);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @OnEditorAction(R.id.ccs_search_edit)
    public boolean editAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            search();
        }
        return true;
    }

    @OnTextChanged(R.id.ccs_search_edit)
    public void onTextChanged(CharSequence s) {
        mSearchClear.setVisibility(s.toString().length() > 0 ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.ccs_close)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.ccs_search_clear)
    public void clear() {
        mSearchEdit.setText("");
        mPresenter.start();
    }

    @OnClick(R.id.ccs_search_button)
    public void search() {
        mPresenter.start();
    }

    @Override
    public void setData(List<? extends Parcelable> list, boolean append) {
        Parcelable select = null;
        if (!TextUtils.isEmpty(mID)) {
            for (Parcelable parcelable : list) {
                if (parcelable instanceof CustomerBean && ((CustomerBean) parcelable).getId().equals(mID)
                        || parcelable instanceof PurchaserShopBean && ((PurchaserShopBean) parcelable).getShopID().equals(mID)
                        || parcelable instanceof PurchaserBean && ((PurchaserBean) parcelable).getPurchaserID().equals(mID)) {
                    select = parcelable;
                    break;
                }
            }
        }
        if (select != null)
            mAdapter.select(select);

        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("搜索不到相关客户");
            }
            mAdapter.setNewData(new ArrayList<>(list));
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public String getSearchWords() {
        return mSearchEdit.getText().toString().trim();
    }

    @Override
    public int getType() {
        return mType;
    }
}
