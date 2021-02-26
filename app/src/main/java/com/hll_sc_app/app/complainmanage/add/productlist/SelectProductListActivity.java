package com.hll_sc_app.app.complainmanage.add.productlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.complainmanage.add.ProductAdapter;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsNameSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 投诉详情-选择投诉商品
 */
@Route(path = RouterConfig.ACTIVITY_COMPLAIN_SELECT_PRODUCT_LIST, extras = Constant.LOGIN_EXTRA)
public class SelectProductListActivity extends BaseLoadActivity implements ISelectProductListContract.IView {

    @Autowired(name = "subBillNo")
    String mSubBillNo;
    @Autowired(name = "selectedBean")
    ArrayList<OrderDetailBean> mSelectedBean;
    @BindView(R.id.recyclerView)
    RecyclerView mProductListView;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.title_bar)
    TitleBar mTitle;

    private Unbinder unbinder;
    private ISelectProductListContract.IPresent mPresent;
    private ProductAdapter mAdpter;


    public static void start(Activity activity, int requestCode, String subBillNo, ArrayList<OrderDetailBean> selectedBean) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_COMPLAIN_SELECT_PRODUCT_LIST)
                .withString("subBillNo", subBillNo)
                .withParcelableArrayList("selectedBean", selectedBean)
                .setProvider(new LoginInterceptor())
                .navigation(activity, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_manage_select_product);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresent = SelectProductListPresent.newInstance();
        mPresent.register(this);
        mPresent.queryList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        if (mSelectedBean == null) {
            mSelectedBean = new ArrayList<>();
        }
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(SelectProductListActivity.this,
                        searchContent, GoodsNameSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresent.queryList();
            }
        });
        mAdpter = new ProductAdapter(null, ProductAdapter.TYPE.SELECT, mSelectedBean);
        mAdpter.setOnItemClickListener((adapter, view, position) -> {
            OrderDetailBean orderDetailBean = mAdpter.getItem(position);
            if (mSelectedBean.contains(orderDetailBean)) {
                mSelectedBean.remove(orderDetailBean);
            } else {
                mSelectedBean.add(orderDetailBean);
            }
            mAdpter.notifyDataSetChanged();
        });
        mProductListView.setAdapter(mAdpter);


        mTitle.setRightBtnClick(v -> {
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra("product", mSelectedBean);
            setResult(RESULT_OK, intent);
            finish();
        });

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

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getSubBillNo() {
        return mSubBillNo;
    }


    @Override
    public void querySuccess(OrderResp orderResp) {
        List<OrderDetailBean> orderDetailBeans = orderResp.getBillDetailList();
        if (orderDetailBeans.size() == 0) {
            mAdpter.setNewData(null);
            mAdpter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("没有能够选择投诉的商品噢~").create());
        } else {
            mAdpter.setNewData(orderDetailBeans);
        }
    }
}
