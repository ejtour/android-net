package com.hll_sc_app.app.aftersales.apply.select;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.common.AfterSalesType;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.AfterSalesGoodsSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyParam;
import com.hll_sc_app.bean.aftersales.AfterSalesDetailsBean;
import com.hll_sc_app.bean.event.SearchEvent;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/12
 */

@Route(path = RouterConfig.AFTER_SALES_SELECT)
public class AfterSalesSelectActivity extends BaseLoadActivity {
    public static final int REQ_CODE = 0x913;

    public static void start(Activity activity, List<AfterSalesDetailsBean> list, int type) {
        AfterSalesApplyParam param = new AfterSalesApplyParam();
        param.setAfterSalesType(type);
        param.setAfterSalesDetailList(list);
        RouterUtil.goToActivity(RouterConfig.AFTER_SALES_SELECT, activity, REQ_CODE, param);
    }

    @BindView(R.id.ass_list_view)
    RecyclerView mListView;
    @BindView(R.id.ass_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.ass_search_view)
    SearchView mSearchView;
    @BindView(R.id.ass_select_num)
    TextView mSelectNum;
    @BindView(R.id.ass_sum)
    TextView mSum;
    @Autowired(name = "parcelable")
    AfterSalesApplyParam mParam;
    DetailsType mType;
    private AfterSalesSelectAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_after_sales_select);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        ARouter.getInstance().inject(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView() {
        mType = mParam.getAfterSalesType() == AfterSalesType.RETURN_DEPOSIT ? DetailsType.DEPOSIT : DetailsType.COMMODITY;
        mTitleBar.setHeaderTitle(mType.getTitle());
        // 水平分割线
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        mAdapter = new AfterSalesSelectAdapter(mParam.getAfterSalesDetailList(), mType, this::updateSelectNum);
        mListView.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(searchContent, AfterSalesGoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mAdapter.setNewData(filter(searchContent));
            }
        });
    }

    private void updateSelectNum() {
        int num = 0;
        double price = 0;
        for (AfterSalesDetailsBean bean : mParam.getAfterSalesDetailList()) {
            if (bean.isSelected()) {
                num++;
                price = CommonUtils.addDouble(price, bean.getPendingRefundAmount(), 0).doubleValue();
            }
        }
        mSelectNum.setText(String.valueOf(num));
        mSum.setText(price == 0 ? "- -" : "¥" + CommonUtils.formatMoney(price));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSearchWords(SearchEvent event) {
        mSearchView.showSearchContent(true, event.getName());
    }

    private List<AfterSalesDetailsBean> filter(String searchWords) {
        if (TextUtils.isEmpty(searchWords)) {
            return mParam.getAfterSalesDetailList();
        }
        List<AfterSalesDetailsBean> list = new ArrayList<>();
        for (AfterSalesDetailsBean bean : mParam.getAfterSalesDetailList()) {
            if (bean.getProductName().contains(searchWords)) {
                list.add(bean);
            }
        }
        return list;
    }

    @OnClick(R.id.ass_apply)
    public void apply() {
        // 传值
        Intent intent = new Intent();
        for (AfterSalesDetailsBean bean : mParam.getAfterSalesDetailList()) {
            if (bean.isSelected()) {
                // 将所有 refundNum <= 0 的选项设置为未选择
                if (bean.getRefundNum() <= 0) {
                    showToast("有退货商品未输入数量，请检查!");
                    return;
                }
                // 确保退货数量不大于可退货数量
                if (bean.getRefundNum() > bean.getRefundableNum())
                    bean.setRefundNum(bean.getRefundableNum());
            }
        }
        intent.putExtra("parcelable", mParam);
        setResult(RESULT_OK, intent);
        finish();
    }
}
