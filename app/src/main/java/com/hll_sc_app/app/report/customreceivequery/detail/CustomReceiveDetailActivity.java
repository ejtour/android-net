package com.hll_sc_app.app.report.customreceivequery.detail;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveDetailBean;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/***
 * 客户收货查询
 * */
@Route(path = RouterConfig.ACTIVITY_QUERY_CUSTOM_RECEIVE_DETAIL)
public class CustomReceiveDetailActivity extends BaseLoadActivity implements ICustomReceiveDetailContract.IView {
    private static final int[] WIDTH_ARRAY = {40, 100, 110, 190, 120, 50, 80, 100, 100, 100, 100, 90, 100, 100, 100, 100, 300};
    private static final int[] WIDTH_ARRAY_2 = {40, 110, 190, 120, 50, 80, 100, 100, 100, 100, 90, 100, 100, 100, 100, 300};
    @BindView(R.id.crd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.txt_no)
    TextView mTxtNo;
    @BindView(R.id.txt_date)
    TextView mTxtDate;
    @BindView(R.id.txt_person)
    TextView mTxtPerson;
    @BindView(R.id.img_status)
    ImageView mImgStatus;
    @BindView(R.id.txt_type)
    TextView mTxtType;
    @BindView(R.id.txt_warehouse)
    TextView mTxtWarehouse;
    @BindView(R.id.txt_mark)
    TextView mTxtMark;
    @BindView(R.id.crd_excel)
    ExcelLayout mExcel;
    @BindView(R.id.txt_footer)
    TextView mTxtFooter;
    @BindView(R.id.crd_confirm)
    TextView mConfirm;
    @Autowired(name = "ownerName")
    String mOwnerName;
    @Autowired(name = "object")
    CustomReceiveListResp.RecordsBean mRecordBean;
    @Autowired(name = "opType")
    int mOpType;
    private Unbinder unbinder;
    private ICustomReceiveDetailContract.IPresent mPresent;
    private ContextOptionsWindow mOptionsWindow;
    private AtomicInteger mIndex = new AtomicInteger();

    /**
     * @param ownerName   货主名
     * @param recordsBean 单据
     * @param opType      单据操作类型 0-收货查询 1-结算查询 2-单据结算
     */
    public static void start(String ownerName, CustomReceiveListResp.RecordsBean recordsBean, int opType) {
        ARouter.getInstance()
                .build(RouterConfig.ACTIVITY_QUERY_CUSTOM_RECEIVE_DETAIL)
                .withString("ownerName", ownerName)
                .withParcelable("object", recordsBean)
                .withInt("opType", opType)
                .setProvider(new LoginInterceptor()).navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_custom_receive_detail);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresent = CustomReceiveDetailPresent.newInstance();
        mPresent.register(this);
        mPresent.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    private void initView() {
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                // no-op
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh();
            }
        });
        mExcel.setEnableLoadMore(false);
        mExcel.setHeaderView(mOpType == 0 ? generateHeader() : generateHeader2());
        mExcel.setColumnDataList(mOpType == 0 ? generateColumnData() : generateColumnData2());
        mTxtNo.setText(mRecordBean.getVoucherNo());
        mTxtDate.setText(String.format("发生日期：%s", DateUtil.getReadableTime(mRecordBean.getVoucherDate(), Constants.SLASH_YYYY_MM_DD)));
        mTxtType.setText(mRecordBean.getVoucherTypeName());
        mTxtWarehouse.setText(mRecordBean.getWarehouseName());
        mTxtMark.setText(mRecordBean.getVoucherRemark());
        if (mOpType > 0) {
            mTitleBar.setHeaderTitle("单据详情");
            mImgStatus.setVisibility(View.INVISIBLE);
            mTitleBar.setRightBtnVisible(mOpType == 1);
            mTitleBar.setRightBtnClick(this::showOptionsWindow);
        } else {
            mImgStatus.setImageResource(mRecordBean.getVoucherStatus() == 1 ? R.drawable.ic_report_custom_receive_no_pass : R.drawable.ic_report_custom_receive_pass);
        }
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = Collections.singletonList(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_VOUCHER_DETAIL));
            mOptionsWindow = new ContextOptionsWindow(this);
            mOptionsWindow.refreshList(list);
            mOptionsWindow.setListener((adapter, view1, position) -> {
                mOptionsWindow.dismiss();
                mPresent.export(null);
            });
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.END);
    }

    private View generateHeader() {
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < WIDTH_ARRAY.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[i]));
        }
        row.updateItemData(array);
        row.updateRowDate("序号", "质检结果", "品项编码", "品项名称", "规格", "单位", "数量", "单价", "金额", "税率",
                "不含税单价", "不含税金额", "辅助单位", "辅助数量", "生产日期", "批次号", "备注");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    private View generateHeader2() {
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(WIDTH_ARRAY_2.length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY_2.length];
        for (int i = 0; i < WIDTH_ARRAY_2.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY_2[i]));
        }
        row.updateItemData(array);
        row.updateRowDate("序号", "品项编码", "品项名称", "规格", "单位", "数量", "单价", "金额", "税率",
                "不含税单价", "不含税金额", "辅助单位", "辅助数量", "生产日期", "批次号", "备注");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        for (int i = 1; i <= 4; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL);
        }
        array[5] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[5]));
        for (int i = 6; i <= 13; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        array[14] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[14]));
        array[15] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[15]), Gravity.CENTER_VERTICAL);
        array[16] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[16]), Gravity.CENTER_VERTICAL);
        return array;
    }

    private ExcelRow.ColumnData[] generateColumnData2() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY_2.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY_2[0]));
        for (int i = 1; i <= 3; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY_2[i]), Gravity.CENTER_VERTICAL);
        }
        array[4] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY_2[4]));
        for (int i = 5; i <= 12; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY_2[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        array[13] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY_2[13]));
        array[14] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY_2[14]), Gravity.CENTER_VERTICAL);
        array[15] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY_2[15]), Gravity.CENTER_VERTICAL);
        return array;
    }

    @Override
    public void querySuccess(List<CustomReceiveDetailBean> records) {
        mIndex.set(0);
        BigDecimal number = new BigDecimal(0);
        BigDecimal price = new BigDecimal(0);
        BigDecimal priceNoTax = new BigDecimal(0);
        if (!CommonUtils.isEmpty(records)) {
            for (CustomReceiveDetailBean bean : records) {
                bean.setSettle(mOpType > 0);
                bean.setIndex(mIndex.incrementAndGet());
                number = CommonUtils.addDouble(number, bean.getGoodsNum());
                price = CommonUtils.addDouble(price, bean.getTaxAmount());
                priceNoTax = CommonUtils.addDouble(priceNoTax, bean.getPretaxAmount());
            }
        }
        mTxtFooter.setText(String.format("合计：品项：%s，数量：%s，金额：¥%s，未税金额：¥%s。",
                records.size(),
                CommonUtils.formatNum(number.doubleValue()),
                CommonUtils.formatMoney(price.doubleValue()),
                CommonUtils.formatMoney(priceNoTax.doubleValue())));
        mConfirm.setVisibility(mOpType == 2 ? View.VISIBLE : View.GONE);
        mExcel.setData(records, false);
    }

    @Override
    public String getOwnerId() {
        return mRecordBean.getGroupID();
    }

    @Override
    public String getOwnerName() {
        return mOwnerName;
    }

    @Override
    public String getVoucherId() {
        return mRecordBean.getVoucherID();
    }

    @Override
    public void success() {
        EventBus.getDefault().post(mRecordBean);
        finish();
    }

    @OnClick(R.id.crd_confirm)
    void confirm() {
        mPresent.confirm();
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, mPresent::export);
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(this, msg);
    }
}
