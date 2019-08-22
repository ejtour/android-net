package com.hll_sc_app.app.report.deliveryTime.detail;

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
import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.hll_sc_app.bean.enums.TimeFlagEnum;
import com.hll_sc_app.bean.event.SalesManSearchEvent;
import com.hll_sc_app.bean.report.deliveryTime.DeliveryTimeItem;
import com.hll_sc_app.bean.report.deliveryTime.DeliveryTimeReq;
import com.hll_sc_app.bean.report.deliveryTime.DeliveryTimeResp;
import com.hll_sc_app.bean.report.inspectLack.detail.InspectLackDetailItem;
import com.hll_sc_app.bean.report.inspectLack.detail.InspectLackDetailReq;
import com.hll_sc_app.bean.report.inspectLack.detail.InspectLackDetailResp;
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

@Route(path = RouterConfig.REPORT_DELIVERY_TIME_DETAIL)
public class DeliveryTimeDetailActivity extends BaseLoadActivity implements IDeliveryTimeDetailContract.IDeliveryTimeDetailView, BaseQuickAdapter.OnItemClickListener {

    private static final int COLUMN_NUM = 12;
    private static final int[] WIDTH_ARRAY = {120,80,80, 60, 60, 60, 60, 60,60,60,60,60};
    @BindView(R.id.txt_date_name)
    TextView mTimeText;
    @BindView(R.id.rl_select_date)
    RelativeLayout mRlSelectDate;
    @BindView(R.id.ogd_excel)
    ExcelLayout mExcel;
    @BindView(R.id.txt_options)
    ImageView textOption;
    private IDeliveryTimeDetailContract.IDeliveryTimeDetailPresenter mPresenter;
    @Autowired(name = "parcelable")
    DeliveryTimeReq mParam = new DeliveryTimeReq();
    private ContextOptionsWindow mExportOptionsWindow;
    private ContextOptionsWindow mOptionsWindow;
    @BindView(R.id.report_delivery_time_arrow)
    ImageView reportDateArrow;

    private DateRangeWindow mDateRangeWindow;

    private String startDate = "";
    private String endDate = "";

    private int timeFlag = 0;

    private boolean isClickCustomerDefined = false;

    private boolean isFirstClickCustomerDefined = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_delivery_time_detail);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = DeliveryTimeDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTimeText.setText(String.format("%s", OptionType.OPTION_REPORT_PRE_SEVEN_DATE));
        mParam.setTimeFlag(TimeFlagEnum.NEARLY_SEVEN_DAYS.getCode());
        mParam.setNeedNearlyData(-1);
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadDeliveryTimeDetailList();
            }
        });
    }

    /**
     * 自定义时间组件
     */
    private void showDateRangeWindow() {
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                if (start != null && end != null) {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTimeInMillis(start.getTimeInMillis());
                    String startStr = CalendarUtils.format(calendarStart.getTime(), Constants.SLASH_YYYY_MM_DD);
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTimeInMillis(end.getTimeInMillis());
                    String endStr = CalendarUtils.format(calendarEnd.getTime(),  Constants.SLASH_YYYY_MM_DD);
                    mTimeText.setText(String.format("%s-%s", startStr, endStr));
                    startDate =  CalendarUtils.format(calendarStart.getTime(), CalendarUtils.FORMAT_SERVER_DATE);
                    endDate  =   CalendarUtils.format(calendarEnd.getTime(), CalendarUtils.FORMAT_SERVER_DATE);
                    mParam.setNeedNearlyData(-1);
                    mParam.setTimeFlag(timeFlag);
                    mParam.setStartDate(startDate);
                    mParam.setEndDate(endDate);
                    mPresenter.loadDeliveryTimeDetailList();
                }
            });
        }
        mDateRangeWindow.showAsDropDownFix(mRlSelectDate);
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this).setListener(this);
        }
        List<OptionsBean> list = new ArrayList<>();
        list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_PRE_SEVEN_DATE));
        list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_PRE_THIRTY_DATE));
        list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_PRE_NINETY_DATE));
        list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_REPORT_CUSTOMER_DEFINE));
        mOptionsWindow.setOnDismissListener(()->{
            reportDateArrow.setRotation(0);
        });
        reportDateArrow.setRotation(180);
        mOptionsWindow.refreshList(list);
        mOptionsWindow.showAsDropDownFix(view, Gravity.LEFT);
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @OnClick({R.id.txt_date_name, R.id.img_back, R.id.txt_options})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_date_name:
                if(!isClickCustomerDefined || !isFirstClickCustomerDefined) {
                    showOptionsWindow(mTimeText);
                }
                if(isClickCustomerDefined){
                    showDateRangeWindow();
                }
                break;
            case R.id.txt_options:
                showExportOptionsWindow(textOption);
                break;
            default:
                break;
        }
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
        Utils.bindEmail(this, (email) -> mPresenter.exportDeliveryTimeDetail(email, reqParams));
    }

    @Override
    public void export(String email) {
        Gson gson = new Gson();
        String reqParams = gson.toJson(getRequestParams());
        mPresenter.exportDeliveryTimeDetail(email, reqParams);
    }

    public DeliveryTimeReq getRequestParams(){
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
        View view = View.inflate(this,R.layout.item_delivery_time_detail_header,null);
        if(isDisPlay) {
            mExcel.setHeaderView(view);
        }
        return view;
    }

    private View generatorFooter(DeliveryTimeResp deliveryTimeResp, boolean isDisplay){
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
            array[8] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[8]));
            array[9] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[9]));
            array[10] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[10]));
            array[11] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[11]));
            row.updateItemData(array);
            row.updateRowDate("合计",deliveryTimeResp.getTotalExecuteOrderNum()+"",
                    deliveryTimeResp.getTotalDeliveryOrderNum()+"",deliveryTimeResp.getTotalInspectionOrderNum()+"",
                    deliveryTimeResp.getTotalOnTimeInspectionNum()+"",
                    deliveryTimeResp.getTotalOnTimeInspectionRate().equals("-2")?"": new BigDecimal(deliveryTimeResp.getTotalOnTimeInspectionRate()).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"%",
                    deliveryTimeResp.getTotalWithin15MinInspectionNum()+"",
                    deliveryTimeResp.getTotalWithin15MinInspectionRate().equals("-2")?"": new BigDecimal(deliveryTimeResp.getTotalWithin15MinInspectionRate()).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"%",
                    deliveryTimeResp.getTotalWithin30MinInspectionNum()+"",
                    deliveryTimeResp.getTotalWithin30MinInspectionRate().equals("-2")?"": new BigDecimal(deliveryTimeResp.getTotalWithin30MinInspectionRate()).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"%",
                    deliveryTimeResp.getTotalBeyond30MinInspectionNum()+"",
                    deliveryTimeResp.getTotalBeyond30MinInspectionRate().equals("-2")?"": new BigDecimal(deliveryTimeResp.getTotalBeyond30MinInspectionRate()).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"%");
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
        array[8] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[4]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[9] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[5]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[10] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[6]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[11] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[7]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        return array;
    }

    @Override
    public void setDeliveryTimeDetailList(DeliveryTimeResp deliveryTimeResp, boolean append) {
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(deliveryTimeResp.getRecords()) && deliveryTimeResp.getRecords().size() == 20);
        List<List<CharSequence>> list = new ArrayList<>();
        if (!CommonUtils.isEmpty(deliveryTimeResp.getRecords())) {
            for (DeliveryTimeItem bean : deliveryTimeResp.getRecords()) {
                list.add(convertToRowData(bean));
            }
            mExcel.setData(list, append);
            mExcel.setFooterView(generatorFooter(deliveryTimeResp,true));
            mExcel.setHeaderView(generateHeader(true));
        }else{
            mExcel.setData(new ArrayList<>(), append);
            mExcel.setFooterView(generatorFooter(deliveryTimeResp, append));
            mExcel.setHeaderView(generateHeader(append));
        }

    }

    private List<CharSequence> convertToRowData(DeliveryTimeItem item){
        List<CharSequence> list = new ArrayList<>();
        list.add(CalendarUtils.getDateFormatString(item.getDate()+"",CalendarUtils.FORMAT_LOCAL_DATE,Constants.SLASH_YYYY_MM_DD)); // 时间
        list.add(CommonUtils.formatNumber(item.getExecuteOrderNum())); // 要求到货单量
        list.add(CommonUtils.formatNumber(item.getDeliveryOrderNum())); // 发货单量
        list.add(CommonUtils.formatNumber(item.getInspectionOrderNum())); // 签收单量
        list.add(CommonUtils.formatNumber(item.getOnTimeInspectionNum())); //按要求时间配送单量
        list.add(CommonUtils.formatNumber(item.getOnTimeInspectionRate().equals("-2")?"": new BigDecimal(item.getOnTimeInspectionRate()).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"%")); // 按要求时间配送单量占比
        list.add(CommonUtils.formatNumber(item.getWithin15MinInspectionNum())); //差异15分钟内配送单量
        list.add(CommonUtils.formatNumber(item.getWithin15MinInspectionRate().equals("-2")?"": new BigDecimal(item.getWithin15MinInspectionRate()).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"%")); // 差异15分钟内配送单量占比
        list.add(CommonUtils.formatNumber(item.getWithin30MinInspectionNum())); //差异30分钟内配送单量
        list.add(CommonUtils.formatNumber(item.getWithin30MinInspectionRate().equals("-2")?"": new BigDecimal(item.getWithin30MinInspectionRate()).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"%")); // 差异30分钟内配送单量占比
        list.add(CommonUtils.formatNumber(item.getBeyond30MinInspectionNum())); //差异30分钟以上配送单量
        list.add(CommonUtils.formatNumber(item.getBeyond30MinInspectionRate().equals("-2")?"": new BigDecimal(item.getBeyond30MinInspectionRate()).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"%")); // 差异30分钟以上配送单量占比
        return list;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 选项监听
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null) {
            return;
        }
        boolean isExportBtn = false;
        //重置下
        isClickCustomerDefined = false;
        if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_PRE_SEVEN_DATE)) {
            timeFlag = TimeFlagEnum.NEARLY_SEVEN_DAYS.getCode();
            mTimeText.setText(OptionType.OPTION_REPORT_PRE_SEVEN_DATE);
        }else if(TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_PRE_THIRTY_DATE)){
            timeFlag = TimeFlagEnum.NEARLY_THIRTY_DAYS.getCode();
            mTimeText.setText(OptionType.OPTION_REPORT_PRE_THIRTY_DATE);
        }else if(TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_PRE_NINETY_DATE)){
            timeFlag = TimeFlagEnum.NEARLY_NINETY_DAYS.getCode();
            mTimeText.setText(OptionType.OPTION_REPORT_PRE_NINETY_DATE);
        }else if(TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_REPORT_CUSTOMER_DEFINE)){
            timeFlag = TimeFlagEnum.CUSTOMDEFINE.getCode();
            isClickCustomerDefined = true;
            isFirstClickCustomerDefined = true;
            //显示时间组件
            showDateRangeWindow();
            dismissWindows();
            return;
        }else if(TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_EXPORT_DETAIL_INFO)){
            isExportBtn = true;
        }
        if(!isExportBtn){
            mParam.setTimeFlag(timeFlag);
           mPresenter.loadDeliveryTimeDetailList();
        }else{
            export(null);
        }
        dismissWindows();
    }

    private void dismissWindows(){
        if (mOptionsWindow != null) {
            mOptionsWindow.dismiss();
        }
        if (mExportOptionsWindow != null) {
            mExportOptionsWindow.dismiss();
        }
    }
}
