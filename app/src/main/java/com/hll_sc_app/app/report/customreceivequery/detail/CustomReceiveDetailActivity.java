package com.hll_sc_app.app.report.customreceivequery.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveDetailBean;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/***
 * 客户收货查询
 * */
@Route(path = RouterConfig.ACTIVITY_QUERY_CUSTOM_RECEIVE_DETAIL)
public class CustomReceiveDetailActivity extends BaseLoadActivity implements ICustomReceiveDetailContract.IView {
    private static final int[] WIDTH_ARRAY = {40, 100, 110, 190, 120, 50, 80, 100, 100, 100, 100, 90, 100, 100, 100, 100, 300};
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
    @Autowired(name = "ownerId")
    String mOwnerId;
    @Autowired(name = "object")
    CustomReceiveListResp.RecordsBean mRecordBean;
    private Unbinder unbinder;
    private ICustomReceiveDetailContract.IPresent mPresent;
    private AtomicInteger mIndex = new AtomicInteger();

    public static void start(String ownerId, CustomReceiveListResp.RecordsBean recordsBean) {
        ARouter.getInstance()
                .build(RouterConfig.ACTIVITY_QUERY_CUSTOM_RECEIVE_DETAIL)
                .withString("ownerId", ownerId)
                .withParcelable("object", recordsBean)
                .setProvider(new LoginInterceptor()).navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
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
        mExcel.setHeaderView(generateHeader());
        mExcel.setColumnDataList(generateColumnData());
        mTxtNo.setText(mRecordBean.getVoucherNo());
        mTxtDate.setText(String.format("发生日期：%s", DateUtil.getReadableTime(mRecordBean.getCreateTime(), Constants.SLASH_YYYY_MM_DD)));
        mTxtType.setText(CustomReceiveListResp.getTypeName(mRecordBean.getVoucherType()));
        mTxtWarehouse.setText(mRecordBean.getHouseName());
        mTxtMark.setText(mRecordBean.getVoucherRemark());
        mImgStatus.setImageResource(mRecordBean.getVoucherStatus() == 1 ? R.drawable.ic_report_custom_receive_no_pass : R.drawable.ic_report_custom_receive_pass);
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

    @Override
    public void querySuccess(List<CustomReceiveDetailBean> records) {
        mIndex.set(0);
        BigDecimal number = new BigDecimal(0);
        BigDecimal price = new BigDecimal(0);
        BigDecimal priceNoTax = new BigDecimal(0);
        if (!CommonUtils.isEmpty(records)) {
            for (CustomReceiveDetailBean bean : records) {
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
        mExcel.setData(records, false);
    }

    @Override
    public String getOwnerId() {
        return mOwnerId;
    }

    @Override
    public String getVoucherId() {
        return mRecordBean.getVoucherID();
    }
}
