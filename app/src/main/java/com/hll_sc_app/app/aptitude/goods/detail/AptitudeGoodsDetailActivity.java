package com.hll_sc_app.app.aptitude.goods.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aptitude.goods.add.AptitudeGoodsAddActivity;
import com.hll_sc_app.app.aptitude.type.AptitudeTypeActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.bean.aptitude.AptitudeProductBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.DateTimePickerDialog;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.ImageUploadGroup;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.ViewCollections;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/11/25
 */
@Route(path = RouterConfig.APTITUDE_GOODS_DETAIL)
public class AptitudeGoodsDetailActivity extends BaseLoadActivity implements IAptitudeGoodsDetailContract.IAptitudeGoodsDetailView {
    public static final int REQ_CODE = 0x6;
    @BindView(R.id.agd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.agd_type_name)
    TextView mTypeName;
    @BindView(R.id.agd_image_upload)
    ImageUploadGroup mImageUpload;
    @BindView(R.id.agd_end_time)
    TextView mEndTime;
    @BindView(R.id.agd_check_time)
    TextView mCheckTime;
    @BindView(R.id.agd_empty_tip)
    TextView mEmptyTip;
    @BindView(R.id.agd_header_bar)
    LinearLayout mHeaderBar;
    @BindView(R.id.agd_search_view)
    SearchView mSearchView;
    @BindView(R.id.agd_list_view)
    RecyclerView mListView;
    @BindView(R.id.agd_bottom_bar)
    FrameLayout mBottomBar;
    @BindViews({R.id.agd_type_name, R.id.agd_end_time, R.id.agd_check_time})
    List<TextView> mButtonViews;
    @Autowired(name = "parcelable")
    AptitudeBean mCurBean;
    private boolean mHasChanged;
    private AptitudeGoodsDetailAdapter mAdapter;
    private IAptitudeGoodsDetailContract.IAptitudeGoodsDetailPresenter mPresenter;
    private EmptyView mEmptyView;
    private boolean mEditable;
    private DateTimePickerDialog mPickerDialog;
    private DateWindow mDateWindow;

    /**
     * @param bean 资质
     */
    public static void start(Activity context, AptitudeBean bean) {
        ARouter.getInstance().build(RouterConfig.APTITUDE_GOODS_DETAIL)
                .withParcelable("parcelable", bean)
                .setProvider(new LoginInterceptor())
                .navigation(context, REQ_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aptitude_goods_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        mPresenter = AptitudeGoodsDetailPresenter.newInstance();
        mPresenter.register(this);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null
                && requestCode == AptitudeTypeActivity.REQ_CODE) {
            AptitudeBean aptitudeBean = (AptitudeBean) data.getParcelableExtra("parcelable");
            String[] types = data.getStringArrayExtra("types");
            if (types != null && types.length > 0 && !TextUtils.isEmpty(mCurBean.getAptitudeType())) {
                if (!Arrays.asList(types).contains(mCurBean.getAptitudeType())) {
                    mCurBean.setAptitudeType(null);
                    mCurBean.setAptitudeName(null);
                    mTypeName.setText(null);
                }
            }
            if (aptitudeBean != null) {
                mTypeName.setText(aptitudeBean.getAptitudeName());
                mCurBean.setAptitudeName(aptitudeBean.getAptitudeName());
                mCurBean.setAptitudeType(aptitudeBean.getAptitudeType());
            }
        }
        mImageUpload.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null && requestCode == AptitudeGoodsAddActivity.REQ_CODE) {
            mCurBean.setAptitudeList(data.getParcelableArrayListExtra("parcelable"));
            updateList();
        }


        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }

    private List<AptitudeProductBean> filter() {
        String searchContent = mSearchView.getSearchContent();
        if (TextUtils.isEmpty(searchContent)) {
            return mCurBean.getAptitudeList();
        }
        List<AptitudeProductBean> list = new ArrayList<>();
        if (!CommonUtils.isEmpty(mCurBean.getAptitudeList())) {
            for (AptitudeProductBean bean : mCurBean.getAptitudeList()) {
                if (bean.getAptitudeProduct() != null && !TextUtils.isEmpty(bean.getAptitudeProduct().getProductName())
                        && bean.getAptitudeProduct().getProductName().contains(searchContent)) {
                    list.add(bean);
                }
            }
        }
        return list;
    }

    private void initData() {
        updateData();
        setEditable(mCurBean == null);
        if (mCurBean != null) {
            mPresenter.start();
        } else {
            mCurBean = new AptitudeBean();
            mCurBean.setGroupID(UserConfig.getGroupID());
        }
    }

    private void initView() {
        mTitleBar.setRightBtnClick(v -> {
            if (mEditable) {
                save();
            } else {
                setEditable(true);
            }
        });
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee),
                ViewUtils.dip2px(this, 0.5f));
        decor.setLineMargin(UIUtils.dip2px(56), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mAdapter = new AptitudeGoodsDetailAdapter();
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.agd_remove) adapter.remove(position);
            handleDataSetChanged();
        });
        mEmptyView = EmptyView.newBuilder(this)
                .setImage(R.drawable.ic_quotation_add_empty)
                .setTips("点击下面按钮添加适用商品")
                .setTipsButton("添加商品")
                .setOnClickListener(new EmptyView.OnActionClickListener() {
                    @Override
                    public void retry() {
                        // no-op
                    }

                    @Override
                    public void action() {
                        addProduct();
                    }
                })
                .create();
        mAdapter.setEmptyView(mEmptyView);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(AptitudeGoodsDetailActivity.this, searchContent, GoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                updateList();
            }
        });
    }

    private void save() {
        List<String> urls = mImageUpload.getUploadImgUrls();
        if (!CommonUtils.isEmpty(urls)) {
            mCurBean.setAptitudeUrl(TextUtils.join(",", urls));
        } else {
            showToast("请上传资质图片");
            return;
        }
        if (TextUtils.isEmpty(mCurBean.getEndTime())) {
            showToast("请选择到期日期");
            return;
        }
        mPresenter.save(mCurBean);
    }

    @OnClick({R.id.agd_type_name, R.id.agd_end_time, R.id.agd_check_time})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.agd_type_name:
                selectType();
                break;
            case R.id.agd_end_time:
                selectEndTime();
                break;
            case R.id.agd_check_time:
                selectCheckTime();
                break;
        }
    }

    private void selectType() {
        AptitudeTypeActivity.start(this, 2, new ArrayList<>(), mCurBean.getAptitudeType());
    }

    private void selectCheckTime() {
        if (mPickerDialog == null) {
            Calendar endTime = Calendar.getInstance();
            int year = endTime.get(Calendar.YEAR);
            endTime.set(Calendar.YEAR, year + 3);
            mPickerDialog = DateTimePickerDialog.newBuilder(this)
                    .setBeginTime(CalendarUtils.parse("201701010000", Constants.UNSIGNED_YYYY_MM_DD_HH_MM).getTime())
                    .setEndTime(endTime.getTimeInMillis())
                    .setSelectTime((mCurBean != null && !TextUtils.isEmpty(mCurBean.getCheckTime())) ?
                            DateUtil.parse(mCurBean.getCheckTime()).getTime() : System.currentTimeMillis())
                    .setTitle("选择到期日期")
                    .setCallback(new DateTimePickerDialog.SelectCallback() {
                        @Override
                        public void select(Date time) {
                            mCheckTime.setText(CalendarUtils.format(time, CalendarUtils.FORMAT_YYYY_MM_DD_HH_MM));
                            mCurBean.setCheckTime(CalendarUtils.format(time, Constants.UNSIGNED_YYYY_MM_DD_HH_MM));
                        }
                    })
                    .create();
        }
        mPickerDialog.show();
    }

    private void selectEndTime() {
        if (mDateWindow == null) {
            mDateWindow = new DateWindow(this);
            mDateWindow.setCalendar(mCurBean != null && !TextUtils.isEmpty(mCurBean.getEndTime()) ?
                    DateUtil.parse(mCurBean.getEndTime()) : new Date());
            mDateWindow.setSelectListener(date -> {
                mEndTime.setText(CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD));
                mCurBean.setEndTime(CalendarUtils.format(date, Constants.UNSIGNED_YYYY_MM_DD));
            });
        }
        mDateWindow.showAtLocation(getWindow().getDecorView(), Gravity.END, 0, 0);
    }

    private void setEditable(boolean editable) {
        mEditable = editable;
        mAdapter.setEditable(mEditable);
        if (mCurBean != null && TextUtils.isEmpty(mCurBean.getCheckTime())) {
            mCheckTime.setText(!mEditable ? "— —" : "");
        }
        if (mEditable) {
            if (!TextUtils.isEmpty(mSearchView.getSearchContent())) {
                mSearchView.showSearchContent(false, "");
            }
            mTitleBar.setRightText("保存");
            mTitleBar.setHeaderTitle(TextUtils.isEmpty(getID()) ? "新增商品资质" : "编辑商品资质");
        } else {
            mTitleBar.setRightText("编辑");
            mTitleBar.setHeaderTitle("查看商品资质");
        }
        ViewCollections.run(mButtonViews, (view, index) -> {
            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, mEditable ? R.drawable.ic_arrow_gray : 0, 0);
            view.setClickable(mEditable);
        });
        mImageUpload.setEditable(mEditable);
        handleDataSetChanged();
    }

    private void handleDataSetChanged() {
        if (mEditable) {
            mSearchView.setVisibility(View.GONE);
            if (CommonUtils.isEmpty(mAdapter.getData())) {
                mEmptyView.reset();
                mEmptyView.setTips("点击下面按钮添加适用商品");
                mEmptyView.setImage(R.drawable.ic_quotation_add_empty);
                mEmptyView.setTipsButton("添加商品");
                mEmptyTip.setVisibility(View.VISIBLE);
                mHeaderBar.setVisibility(View.GONE);
                mBottomBar.setVisibility(View.GONE);
            } else {
                mEmptyTip.setVisibility(View.GONE);
                mHeaderBar.setVisibility(View.VISIBLE);
                mBottomBar.setVisibility(View.VISIBLE);
            }
        } else {
            mSearchView.setVisibility(View.VISIBLE);
            mEmptyTip.setVisibility(View.GONE);
            mHeaderBar.setVisibility(View.GONE);
            mBottomBar.setVisibility(View.GONE);
            if (CommonUtils.isEmpty(mAdapter.getData())) {
                mEmptyView.reset();
                mEmptyView.setTips("暂无数据");
            }
        }
    }

    @OnClick(R.id.agd_add_goods)
    void addProduct() {
        AptitudeGoodsAddActivity.start(this, mCurBean.getAptitudeList() == null ?
                new ArrayList<>() : new ArrayList<>(mCurBean.getAptitudeList()));
    }

    @Override
    public String getID() {
        return mCurBean == null ? "" : mCurBean.getId();
    }

    @Override
    public void setData(AptitudeBean bean) {
        mCurBean = bean;
        updateData();
    }

    private void updateData() {
        if (mCurBean != null) {
            mTypeName.setText(mCurBean.getAptitudeName());
            mImageUpload.showImages(mCurBean.getAptitudeUrl().split(","));
            mEndTime.setText(TextUtils.isEmpty(mCurBean.getEndTime()) ? "— —" :
                    DateUtil.getReadableTime(mCurBean.getEndTime(), Constants.SLASH_YYYY_MM_DD));
            mCheckTime.setText(TextUtils.isEmpty(mCurBean.getCheckTime()) ? "— —" :
                    DateUtil.getReadableTime(mCurBean.getCheckTime(), CalendarUtils.FORMAT_YYYY_MM_DD_HH_MM));
            updateList();
        }
    }

    private void updateList() {
        mAdapter.setNewData(filter());
        handleDataSetChanged();
    }

    @Override
    public void saveSuccess() {
        if (TextUtils.isEmpty(getID())) {
            setResult(RESULT_OK);
            finish();
        } else {
            mHasChanged = true;
            setEditable(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (mHasChanged) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }
}
