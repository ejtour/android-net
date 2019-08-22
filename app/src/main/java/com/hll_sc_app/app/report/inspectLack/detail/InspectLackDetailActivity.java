package com.hll_sc_app.app.report.inspectLack.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.SalesManSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.enums.TimeTypeEnum;
import com.hll_sc_app.bean.event.SalesManSearchEvent;
import com.hll_sc_app.bean.report.inspectLack.InspectLackItem;
import com.hll_sc_app.bean.report.inspectLack.InspectLackResp;
import com.hll_sc_app.bean.report.inspectLack.detail.InspectLackDetailItem;
import com.hll_sc_app.bean.report.inspectLack.detail.InspectLackDetailReq;
import com.hll_sc_app.bean.report.inspectLack.detail.InspectLackDetailResp;
import com.hll_sc_app.bean.report.req.BaseReportReqParam;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.REPORT_INSPECT_LACK_DETAIL)
public class InspectLackDetailActivity extends BaseLoadActivity implements IInspectLackDetailContract.IInspectLackDetailView {

    private static final int COLUMN_NUM = 8;
    private static final int[] WIDTH_ARRAY = {140,100,80, 80, 80, 80, 80, 80};
    @BindView(R.id.txt_date_name)
    TextView mTimeText;
    @BindView(R.id.ogd_excel)
    ExcelLayout mExcel;
    @BindView(R.id.txt_options)
    ImageView textOption;
    private IInspectLackDetailContract.IInspectLackDetailPresenter mPresenter;
    @Autowired(name = "parcelable")
    InspectLackDetailReq mParam = new InspectLackDetailReq();
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    DateWindow dateWindow;
    private ContextOptionsWindow mExportOptionsWindow;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_inspect_lack);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = InspectLackDetailPresenter.newInstance();
        mPresenter.register(this);
        EventBus.getDefault().register(this);
        mPresenter.start();
    }

    private void initView() {
        Date date = new Date();
        String startDateStr = CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD);
        mTimeText.setText(String.format("%s", startDateStr));
        mParam.setDate(CalendarUtils.getDateFormatString(startDateStr,Constants.SLASH_YYYY_MM_DD,CalendarUtils.FORMAT_LOCAL_DATE));
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadInspectLackDetailList();
            }
        });
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @OnClick({R.id.txt_date_name, R.id.img_back, R.id.txt_options,R.id.edt_search,R.id.img_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_date_name:
                showDateRangeWindow();
                break;
            case R.id.txt_options:
                showExportOptionsWindow(textOption);
                break;
            case R.id.edt_search:
                SearchActivity.start("", SalesManSearch.class.getSimpleName());
                break;
            case R.id.img_clear:
                mParam.setProductName("");
                edtSearch.setText("");
                mPresenter.loadInspectLackDetailList();
                imgClear.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onEvent(SalesManSearchEvent event) {
        String name = event.getSearchWord();
        if (!TextUtils.isEmpty(name)) {
            edtSearch.setText(name);
            mParam.setProductName(name);
            imgClear.setVisibility(View.VISIBLE);
        } else {
            mParam.setProductName("");
        }
        mPresenter.loadInspectLackDetailList();
    }


    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String tip) {
        Utils.exportFailure(this, tip);
    }

    @Override
    public void bindEmail() {
        Gson gson = new Gson();
        String reqParams = gson.toJson(getRequestParams());
        Utils.bindEmail(this, (email) -> mPresenter.exportInspectLackDetail(email, reqParams));
    }

    @Override
    public void export(String email) {
        Gson gson = new Gson();
        String reqParams = gson.toJson(getRequestParams());
        mPresenter.exportInspectLackDetail(email, reqParams);
    }

    public InspectLackDetailReq getRequestParams(){
        return mParam;
    }

    private void showExportOptionsWindow(View v) {
        if (mExportOptionsWindow == null) {
            mExportOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option,
                            OptionType.OPTION_EXPORT_DETAIL_INFO)))
                    .setListener((adapter, view, position) -> {
                        mExportOptionsWindow.dismiss();
                        export(null);
                    });
        }
        mExportOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    private View generateHeader(boolean isDisPlay) {
        ExcelRow row = new ExcelRow(this);
        if(isDisPlay) {
            row.updateChildView(COLUMN_NUM);
            ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[COLUMN_NUM];
            array[0] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[0]));
            array[1] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[1]));
            array[2] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[2]));
            array[3] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[3]));
            array[4] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[4]));
            array[5] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[5]));
            array[6] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[6]));
            array[7] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[7]));
            row.updateItemData(array);
            row.updateRowDate("商品名称", "规格/单位", "发货量", "发货金额(元)", "收货量", "差异量", "差异金额(元)", "差异率");
            row.setBackgroundResource(R.drawable.bg_excel_header);
        }
        return row;
    }

    private View generatorFooter(InspectLackDetailResp inspectLackDetailResp, boolean isDisplay){
        ExcelRow row = new ExcelRow(this);
        if(isDisplay) {
            row.updateChildView(COLUMN_NUM);
            ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[COLUMN_NUM];
            array[0] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[0]));
            array[1] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[1]));
            array[2] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[2]));
            array[3] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[3]));
            array[4] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[4]));
            array[5] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[5]));
            array[6] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[6]));
            array[7] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[7]));
            row.updateItemData(array);
            row.updateRowDate("合计", "--", "--", "--", "--", "--",
                    CommonUtils.formatMoney(Double.parseDouble(inspectLackDetailResp.getTotalInspectionLackAmount())), CommonUtils.formatNumber(new BigDecimal(inspectLackDetailResp.getTotalInspectionLackRate()).multiply(BigDecimal.valueOf(100)).toPlainString()) + "%");
            row.setBackgroundResource(R.drawable.bg_excel_header);
        }
        return row;
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[COLUMN_NUM];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]), Gravity.CENTER_VERTICAL);
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[4] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[4]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[5] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[5]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[6] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[6]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[7] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[7]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        return array;
    }

    private void showDateRangeWindow() {
        dateWindow =dateWindow==null? new DateWindow(this):dateWindow;
        dateWindow.setSelectListener(date -> {
            String serverDate = CalendarUtils.format(date, CalendarUtils.FORMAT_LOCAL_DATE);
            String localDate = CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD);
            setDateSelect(mTimeText,serverDate,localDate);
        });
        dateWindow.showAtLocation(getCurrentFocus(),Gravity.BOTTOM,0,0);
    }
    /**
     * 设置自定义的时间参数
     * @param dateText
     */
    private void setDateSelect(TextView dateText,String serverDate,String localDate){
        dateText.setText(localDate);
        mParam.setDate(serverDate);
        mPresenter.loadInspectLackDetailList();
    }


    @Override
    public void setInspectDetailList(InspectLackDetailResp inspectLackDetailResp, boolean append) {
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(inspectLackDetailResp.getRecords()) && inspectLackDetailResp.getRecords().size() == 20);
        List<List<CharSequence>> list = new ArrayList<>();
        if (!CommonUtils.isEmpty(inspectLackDetailResp.getRecords())) {
            for (InspectLackDetailItem bean : inspectLackDetailResp.getRecords()) {
                list.add(convertToRowData(bean));
            }
            mExcel.setData(list, append);
            mExcel.setFooterView(generatorFooter(inspectLackDetailResp,true));
            mExcel.setHeaderView(generateHeader(true));
        }else{
            mExcel.setData(new ArrayList<>(), append);
            mExcel.setFooterView(generatorFooter(inspectLackDetailResp, append));
            mExcel.setHeaderView(generateHeader(append));
        }

    }

    private List<CharSequence> convertToRowData(InspectLackDetailItem item){
        List<CharSequence> list = new ArrayList<>();
        list.add(item.getProductName()); // 商品名称
        list.add(item.getSpecUnit()); // 规格
        list.add(CommonUtils.formatNumber(item.getOriDeliveryNum())); // 发货量
        list.add(CommonUtils.formatMoney(Double.parseDouble(item.getOriDeliveryAmount()))); // 原发货金额
        list.add(CommonUtils.formatNumber(item.getInspectionNum())); //收货差异商品数
        list.add(CommonUtils.formatNumber(item.getInspectionLackNum())); // 收货差异量
        list.add(CommonUtils.formatMoney(Double.parseDouble(item.getInspectionLackAmount()))); // 收货差异金额
        list.add(CommonUtils.formatNumber(new BigDecimal(item.getInspectionLackRate()).multiply(BigDecimal.valueOf(100)).toPlainString())+"%");//收货差异率
        return list;
    }
}
