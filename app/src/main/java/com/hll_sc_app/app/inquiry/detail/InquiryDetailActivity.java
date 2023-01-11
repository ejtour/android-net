package com.hll_sc_app.app.inquiry.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.relevance.goods.GoodsRelevanceListActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.app.simple.SearchListActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.InquiryRefreshEvent;
import com.hll_sc_app.bean.inquiry.InquiryBean;
import com.hll_sc_app.bean.inquiry.InquiryBindResp;
import com.hll_sc_app.bean.inquiry.InquiryDetailBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.KeyboardWatcher;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/19
 */
@Route(path = RouterConfig.INQUIRY_DETAIL)
public class InquiryDetailActivity extends BaseLoadActivity implements IInquiryDetailContract.IInquiryDetailView, KeyboardWatcher.SoftKeyboardStateListener {

    public static void start(InquiryBean bean) {
        if (bean == null) return;
        RouterUtil.goToActivity(RouterConfig.INQUIRY_DETAIL, bean);
    }

    @BindView(R.id.aid_top_group)
    ConstraintLayout mTopGroup;
    @BindView(R.id.aid_list_view)
    RecyclerView mListView;
    @BindView(R.id.aid_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aid_search_view)
    SearchView mSearchView;
    @BindView(R.id.aid_bottom_group)
    Group mBottomGroup;
    @BindView(R.id.aid_enquiryShopNum)
    TextView mTxtEnquiryShopNum;

    @BindView(R.id.txt_cycle_star_date)
    TextView mTxtStarDate;
    @BindView(R.id.txt_cycle_end_date)
    TextView mTxtEndDate;
    @BindView(R.id.layout_time)
    LinearLayout mLayoutTime;

    @Autowired(name = "parcelable")
    InquiryBean mBean;
    @Autowired(name = "object0")
    String mInquiryID;
    private IInquiryDetailContract.IInquiryDetailPresenter mPresenter;
    private InquiryDetailAdapter mAdapter;
    private EmptyView mEmptyView;
    private boolean mIsSubmit;
    private KeyboardWatcher mKeyboardWatcher;
    public static final String DIVISION_FLAG = "DIVISION_FLAG";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        if (mBean != null && TextUtils.equals("2", "" + mBean.getEnquiryType())) {
            // 1询价中 2 已完成
            mLayoutTime.setVisibility(View.VISIBLE);
            String startDate = CalendarUtils.format(CalendarUtils.parseBusiness(mBean.getCycleStartDate()), "yyyy/MM/dd");
            String endDate = CalendarUtils.format(CalendarUtils.parseBusiness(mBean.getCycleEndDate()), "yyyy/MM/dd");
            mTxtStarDate.setText(getSpannableString("周期开始 ", startDate));
            mTxtEndDate.setText(getSpannableString("周期结束 ", endDate));
        }
        mPresenter = InquiryDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private SpannableString getSpannableString(String str, String des) {
        SpannableString spannableString = new SpannableString(str + des);
        if (!TextUtils.isEmpty(des)) {
            spannableString.setSpan(new ForegroundColorSpan(0xFF7B7B7B),
                    str.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new RelativeSizeSpan(0.9F),
                    str.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    @Override
    protected void onDestroy() {
        mKeyboardWatcher.removeSoftKeyboardStateListener(this);
        super.onDestroy();
    }

    private void initView() {
        mKeyboardWatcher = new KeyboardWatcher(this);
        mKeyboardWatcher.addSoftKeyboardStateListener(this);
        initAdapter();
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(InquiryDetailActivity.this, searchContent, GoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                if (mBean == null) return;
                setList(filter(searchContent));
            }
        });
        mTitleBar.setRightBtnClick(this::save);
    }

    private void initAdapter() {
        if (mBean == null || mAdapter != null) return;
        boolean editable = mBean.getEnquiryStatus() == 1;
        SimpleDecoration decor = new SimpleDecoration(editable ? Color.TRANSPARENT : ContextCompat.getColor(this, R.color.color_eeeeee), ViewUtils.dip2px(this, editable ? 10 : 0.5f));
        if (mBean.getEnquiryStatus() > 1) {
            decor.setLineMargin(UIUtils.dip2px(53), 0, 0, 0, Color.WHITE);
        }
        mListView.addItemDecoration(decor);
        mAdapter = new InquiryDetailAdapter(editable, mBean.getEnquiryType() != 2);
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

    @OnClick(R.id.aid_submit)
    public void submit(View view) {
        if (mBean == null) return;
        for (InquiryDetailBean bean : mBean.getDetailList()) {
            if (CommonUtils.getDouble(bean.getEnquiryPrice()) == 0) {
                showToast("输入金额必须大于0！");
                return;
            }
        }
        mIsSubmit = true;
        mBean.setEnquiryStatus(2);
        mPresenter.submit(mBean);
    }

    @OnClick(R.id.aid_enquiryShopNum_content)
    public void toShowShopList(View view) {
        if (mBean == null) return;
        List<InquiryBean> inquiryBeans = mBean.getEnquiryShops();
        ArrayList<String> shopNames = new ArrayList<>();
        if (!CommonUtils.isEmpty(inquiryBeans)) {
            for (InquiryBean bean : inquiryBeans) {
                if (!TextUtils.isEmpty(bean.getLinkman())) {
                    shopNames.add(bean.getShopName() + DIVISION_FLAG + bean.getLinkman() + "/" + bean.getLinkTel());
                } else {
                    shopNames.add(bean.getShopName());
                }
            }
        }
        if (!CommonUtils.isEmpty(shopNames)) {
            SearchListActivity.start(mBean.getPurchaserName(), shopNames, true);
        }
    }

    private void save(View view) {
        if (mBean == null) return;
        if (mBean.getEnquiryStatus() == 1) {
            mIsSubmit = false;
            mPresenter.submit(mBean);
        } else {
            mPresenter.generate();
        }
    }

    @OnTouch({R.id.aid_root_view, R.id.aid_list_view})
    public boolean onTouch() {
        UIUtils.hideActivitySoftKeyboard(this);
        return false;
    }

    @Override
    public void setData(InquiryBean bean) {
        mBean = bean;
        initAdapter();
        mTitleBar.setHeaderTitle(mBean.getPurchaserName());
        if (mBean.getEnquiryStatus() == 1) {
            mTopGroup.setVisibility(View.VISIBLE);
            mBottomGroup.setVisibility(View.VISIBLE);
            mTitleBar.setRightText("保存");
        } else if (mBean.getEnquiryStatus() == 2) {
            mTitleBar.setRightText("生成报价单");
        }
        mTxtEnquiryShopNum.setText(String.format(Locale.getDefault(), "共%d家", bean.getEnquiryShops() != null ?
                bean.getEnquiryShops().size() : 0));
        setList(filter(mSearchView.getSearchContent()));
    }

    private void setList(List<InquiryDetailBean> list) {
        if (mAdapter == null) return;
        if (CommonUtils.isEmpty(list)) {
            initEmptyView();
            mEmptyView.reset();
            mEmptyView.setTips("暂无数据");
        }
        mAdapter.setNewData(list);
    }

    private List<InquiryDetailBean> filter(String content) {
        if (TextUtils.isEmpty(content) || CommonUtils.isEmpty(mBean.getDetailList())) {
            return mBean.getDetailList();
        }
        List<InquiryDetailBean> list = new ArrayList<>();
        for (InquiryDetailBean bean : mBean.getDetailList()) {
            if (bean.getGoodsName().contains(content)) {
                list.add(bean);
            }
        }
        return list;
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    @Override
    public String getID() {
        return mBean == null ? mInquiryID : mBean.getId();
    }

    @Override
    public boolean isSubmit() {
        return mIsSubmit;
    }

    @Override
    public void success() {
        EventBus.getDefault().post(new InquiryRefreshEvent());
        finish();
    }

    @Override
    public void toGenerate(InquiryBindResp resp) {
        if (resp == null && mBean == null) return;
        RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD,
                resp != null ? resp.convertToQuotationReq() : mBean.convertToQuotationReq());
    }

    @Override
    public void toBind(InquiryBindResp resp) {
        GoodsRelevanceListActivity.start(resp);
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
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        // no-op
    }

    @Override
    public void onSoftKeyboardClosed() {
        mTitleBar.requestFocus();
    }
}
