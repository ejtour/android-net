package com.hll_sc_app.app.report.customreceivequery.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveDetailBean;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SyncHorizontalScrollView;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/***
 * 客户收货查询
 * */
@Route(path = RouterConfig.ACTIVITY_QUERY_CUSTOM_RECEIVE_DETAIL)
public class CustomReceiveDetailActivity extends BaseLoadActivity implements ICustomReceiveDetailContract.IView {
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
    @BindView(R.id.sync_scroll_title)
    SyncHorizontalScrollView mScrollTitle;
    @BindView(R.id.sync_scroll_content)
    SyncHorizontalScrollView mScrollContent;
    @BindView(R.id.recyclerView)
    RecyclerView mListView;
    @BindView(R.id.txt_footer)
    TextView mTxtFooter;
    @Autowired(name = "ownerId")
    String mOwnerId;
    @Autowired(name = "object")
    CustomReceiveListResp.RecordsBean mRecordBean;

    private Unbinder unbinder;
    private ICustomReceiveDetailContract.IPresent mPresent;
    private DetailListAdapter mAdapter;

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
        setContentView(R.layout.activirty_report_custom_receive_detail);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresent = CustomReceiveDetailPresent.newInstance();
        mPresent.register(this);
        mPresent.queryDetail();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mAdapter = new DetailListAdapter(null);
        mListView.setAdapter(mAdapter);

        mScrollTitle.setLinkageViews(mScrollContent);
        mScrollContent.setLinkageViews(mScrollTitle);

        mTxtNo.setText(mRecordBean.getVoucherNo());
        mTxtDate.setText("发生日期: " + CalendarUtils.getDateFormatString(mRecordBean.getCreateTime(), "yyyyMMddHHmmss", "yyyy/MM/dd"));
        mTxtType.setText(CustomReceiveListResp.getTypeName(mRecordBean.getVoucherType()));
        mTxtWarehouse.setText(mRecordBean.getHouseName());
        mTxtMark.setText(mRecordBean.getVoucherRemark());
        mImgStatus.setImageResource(mRecordBean.getVoucherStatus() == 1 ? R.drawable.ic_report_custom_receive_no_pass : R.drawable.ic_report_custom_receive_pass);
    }

    @Override
    public void querySuccess(List<CustomReceiveDetailBean> customReceiveDetailBeans) {
        if (customReceiveDetailBeans.size() > 0) {
            mTxtPerson.setText("审核人: " + customReceiveDetailBeans.get(0).getAuditBy());
        }
        mAdapter.setEmptyView(EmptyView.newBuilder(this)
                .setImage(R.drawable.ic_char_empty)
                .setTipsTitle("喔唷，居然是「 空 」的").create());
        mAdapter.setNewData(customReceiveDetailBeans);

        BigDecimal number = new BigDecimal(0);
        BigDecimal price = new BigDecimal(0);
        BigDecimal priceNoTax = new BigDecimal(0);
        for (CustomReceiveDetailBean bean : customReceiveDetailBeans) {
            number = CommonUtils.addDouble(number, bean.getGoodsNum());
            price = CommonUtils.addDouble(price, bean.getTaxAmount());
            priceNoTax = CommonUtils.addDouble(priceNoTax, bean.getPretaxAmount());
        }

        mTxtFooter.setText(String.format("合计: 品项：%s，数量：%s，金额：¥%s，未税金额：¥%s，",
                customReceiveDetailBeans.size(),
                number.toString(),
                price.toString(),
                priceNoTax.toString()
        ));
    }

    @Override
    public String getOwnerId() {
        return mOwnerId;
    }

    @Override
    public String getVoucherId() {
        return mRecordBean.getVoucherID();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            mAdapter.setEmptyView(EmptyView.newBuilder(this).setOnClickListener(() -> {
                mPresent.queryDetail();
            }).setNetError(true).create());
        }
    }

    private class DetailListAdapter extends BaseQuickAdapter<CustomReceiveDetailBean, BaseViewHolder> {

        public DetailListAdapter(@Nullable List<CustomReceiveDetailBean> data) {
            super(R.layout.list_item_custom_receive_detail, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomReceiveDetailBean item) {
            helper.setText(R.id.txt_no, String.valueOf(helper.getAdapterPosition() + 1))
                    .setText(R.id.txt_result, "")
                    .setText(R.id.txt_code, item.getGoodsCode())
                    .setText(R.id.txt_name, item.getGoodsName())
                    .setText(R.id.txt_spec, item.getGoodsDesc())
                    .setText(R.id.txt_unit, item.getStandardUnit())
                    .setText(R.id.txt_number, String.valueOf(item.getGoodsNum()))
                    .setText(R.id.txt_unit_price, "¥" + CommonUtils.formatMoney(item.getTaxPrice()))
                    .setText(R.id.txt_price, "¥" + CommonUtils.formatMoney(item.getTaxAmount()))
                    .setText(R.id.txt_tax_rate, String.valueOf(item.getRateValue()))
                    .setText(R.id.txt_unit_price_no_tax, "¥" + CommonUtils.formatMoney(item.getPretaxPrice()))
                    .setText(R.id.txt_price_no_taxt, "¥" + CommonUtils.formatMoney(item.getPretaxAmount()))
                    .setText(R.id.unit_assist, item.getAssistUnit())
                    .setText(R.id.number_assist, CommonUtils.formatNumber(item.getAuxiliaryNum()))
                    .setText(R.id.date_create, CalendarUtils.getDateFormatString(item.getProductionDate(), "yyyyMMddHHmmss", "yyyy/MM/dd"))
                    .setText(R.id.batch_number, item.getBatchNumber())
                    .setText(R.id.txt_remark, item.getDetailRemark())
                    .setBackgroundColor(R.id.ll_container, Color.parseColor(helper.getAdapterPosition() % 2 == 0 ? "#FFFFFF" : "#F9F9F9"));
        }
    }
}
