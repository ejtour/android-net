package com.hll_sc_app.app.price.domestic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.bean.price.CategoryBean;
import com.hll_sc_app.bean.price.DomesticPriceBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hll_sc_app.citymall.util.CalendarUtils.FORMAT_DATE_TIME;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/28
 */

public class PriceDomesticFragment extends BaseLazyFragment implements IPriceDomesticContract.IPriceDomesticView {
    @BindView(R.id.fpd_gain_rank_label)
    TextView mGainRankLabel;
    @BindView(R.id.fpd_gain_rank_icon)
    ImageView mGainRankIcon;
    @BindView(R.id.fpd_price_rank)
    TextView mPriceRank;
    @BindView(R.id.fpd_date)
    TextView mDate;
    @BindView(R.id.fpd_category)
    TextView mCategory;
    @BindView(R.id.fpd_date_range)
    TextView mDateRange;
    @BindView(R.id.fpd_last_avg)
    TextView mLastAvg;
    @BindView(R.id.fpd_cur_avg)
    TextView mCurAvg;
    @BindView(R.id.fpd_first_group)
    Group mFirstGroup;
    @BindView(R.id.fpd_price_1)
    TextView mPrice;
    @BindView(R.id.fpd_second_group)
    Group mSecondGroup;
    @BindView(R.id.fpd_list_view)
    RecyclerView mListView;
    @BindView(R.id.fpd_empty_view)
    EmptyView mEmptyView;
    Unbinder unbinder;
    private int mSortType = 1;
    private String mDateContent;
    private String mCategoryContent = "0";
    private DateWindow mDateWindow;
    private SingleSelectionDialog mDialog;
    private IPriceDomesticContract.IPriceDomesticPresenter mPresenter;
    private List<CategoryBean> mCategoryList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = PriceDomesticPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_price_domestic, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        processPrice(mCurAvg);
        processPrice(mLastAvg);
        processPrice(mPrice);
        Date date = new Date();
        setDateText(date);
        mListView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(requireContext(), R.color.color_eeeeee),
                ViewUtils.dip2px(requireContext(), 0.5f)));
        mEmptyView.setOnActionClickListener(mPresenter::start);
    }

    private void setDateText(Date date) {
        mDateContent = CalendarUtils.format(date, Constants.SIGNED_YYYY_MM_DD);
        mDate.setText(CalendarUtils.format(date, FORMAT_DATE_TIME));
        mDateRange.setText(String.format("%s-%s",
                CalendarUtils.format(CalendarUtils.getDateBefore(date, 7), FORMAT_DATE_TIME),
                CalendarUtils.format(CalendarUtils.getDateBefore(date, 1), FORMAT_DATE_TIME)));
    }

    private void processPrice(TextView view) {
        String source = view.getText().toString();
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(1.5f), 0, source.indexOf("("), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_222222)),
                0, source.indexOf("("), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(ss);
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fpd_gain_rank_group, R.id.fpd_price_rank, R.id.fpd_date_group, R.id.fpd_category_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fpd_gain_rank_group:
                gainRank();
                break;
            case R.id.fpd_price_rank:
                priceRank();
                break;
            case R.id.fpd_date_group:
                showDateWindow();
                break;
            case R.id.fpd_category_group:
                showCategoryDialog();
                break;
        }
    }

    private void gainRank() {
        int oldSort = mSortType;
        if (mSortType == 0 || mSortType == 2) {
            mSortType = 1;
            mGainRankIcon.setRotation(0);
        } else {
            mSortType = 2;
            mGainRankIcon.setRotation(180);
        }
        if (oldSort == 0) {
            resetSort();
            mFirstGroup.setVisibility(View.VISIBLE);
            mSecondGroup.setVisibility(View.GONE);
            mGainRankIcon.setVisibility(View.VISIBLE);
            mGainRankLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary));
        }
        mPresenter.loadList();
    }

    private void priceRank() {
        if (mSortType != 0) {
            mSortType = 0;
            mFirstGroup.setVisibility(View.GONE);
            mSecondGroup.setVisibility(View.VISIBLE);
            resetSort();
            mPriceRank.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary));
            mPresenter.loadList();
        }
    }

    private void resetSort() {
        mGainRankIcon.setVisibility(View.GONE);
        mGainRankLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_999999));
        mPriceRank.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_999999));
    }

    private void showCategoryDialog() {
        if (CommonUtils.isEmpty(mCategoryList)) {
            mPresenter.start();
            return;
        }
        if (mDialog == null) {
            mDialog = SingleSelectionDialog.newBuilder(requireActivity(), CategoryBean::getFatherName)
                    .setTitleText("选择类别")
                    .refreshList(mCategoryList)
                    .setOnSelectListener(categoryBean -> {
                        mCategory.setText(categoryBean.getFatherName());
                        mCategoryContent = categoryBean.getFatherCode();
                        mPresenter.loadList();
                    })
                    .create();
        }
        mDialog.show();
    }

    private void showDateWindow() {
        if (mDateWindow == null) {
            mDateWindow = new DateWindow(requireActivity());
            mDateWindow.setCalendar(new Date());
            mDateWindow.setSelectListener(date -> {
                setDateText(date);
                mPresenter.start();
            });
        }
        mDateWindow.showAtLocation(getView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public String getDate() {
        return mDateContent;
    }

    @Override
    public String getCategory() {
        return mCategoryContent;
    }

    @Override
    public int getSortType() {
        return mSortType;
    }

    @Override
    public void cacheCategory(List<CategoryBean> list) {
        mCategoryList = list;
    }

    @Override
    public void setData(List<DomesticPriceBean> list) {
        mEmptyView.setVisibility(View.GONE);
        setAdapter(list);
        if (CommonUtils.isEmpty(list)) {
            mEmptyView.reset();
            mEmptyView.setTips("没有数据哦");
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    private void setAdapter(List<DomesticPriceBean> list) {
        mListView.setAdapter(mSortType == 0 ? new PriceAvgAdapter(list) : new PriceGainAdapter(list));
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            mEmptyView.setNetError();
            if (mListView.getAdapter() == null || mListView.getAdapter().getItemCount() == 0) {
                mEmptyView.setVisibility(View.VISIBLE);
            }
        }
    }
}
