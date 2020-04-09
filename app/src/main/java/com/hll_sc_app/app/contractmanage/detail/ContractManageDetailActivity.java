package com.hll_sc_app.app.contractmanage.detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.contractmanage.ContractManageActivity;
import com.hll_sc_app.app.contractmanage.add.ContractManageAddActivity;
import com.hll_sc_app.app.contractmanage.linkcontractlist.LinkContractListActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.ImageViewActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.DownLoadBean;
import com.hll_sc_app.bean.contract.ContractListResp;
import com.hll_sc_app.bean.contract.ContractProductListResp;
import com.hll_sc_app.bean.event.ContractManageEvent;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.utils.DownloadUtil;
import com.hll_sc_app.widget.adapter.DownloadAdapter;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 合同管理-详情
 */
@Route(path = RouterConfig.ACTIVITY_CONTRACT_MANAGE_DETAIL)
public class ContractManageDetailActivity extends BaseLoadActivity implements IContractManageDetailContract.IView {

    @BindView(R.id.txt_type)
    TextView mTxtType;
    @BindView(R.id.txt_name)
    TextView mTxtName;
    @BindView(R.id.txt_status)
    TextView mTxtStatus;
    @BindView(R.id.txt_no)
    TextView mTxtNo;
    @BindView(R.id.txt_group_name)
    TextView mTxtGroupName;
    @BindView(R.id.txt_time_span)
    TextView mTxtTimeSpan;
    @BindView(R.id.txt_person)
    TextView mTxtPerson;
    @BindView(R.id.txt_time)
    TextView mTxtTime;
    @BindView(R.id.txt_left_days)
    TextView mTxtLeftDays;
    @BindView(R.id.txt_btn_del)
    TextView mTxtBtnDel;
    @BindView(R.id.txt_btn_mdf)
    TextView mTxtBtnMdf;
    @BindView(R.id.txt_btn_check)
    TextView mTxtBtnCheck;
    @BindView(R.id.txt_shop_name)
    TextView mTxtShopName;
    @BindView(R.id.txt_link_contract)
    TextView mTxtLinkContract;
    @BindView(R.id.edt_contract_amount)
    EditText mEdtContractAmount;
    @BindView(R.id.edt_bk)
    EditText mEdtBk;
    @BindView(R.id.txt_btn_stop)
    TextView mTxtBtnStop;
    @BindView(R.id.list_download)
    RecyclerView mListDownload;
    @BindView(R.id.txt_btn_detail)
    TextView mTxtBtnDetail;
    @BindView(R.id.list_product)
    ExcelLayout mExcel;
    @Autowired(name = "parcelable")
    ContractListResp.ContractBean mBean;


    private Unbinder unbinder;
    private IContractManageDetailContract.IPresent mPresent;

    private static final int[] WIDTH_ARRAY = {80, 80, 80, 200};

    public static void start(ContractListResp.ContractBean bean) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_CONTRACT_MANAGE_DETAIL, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_manage_detail);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        mPresent = ContractManageDetailPresent.newInstance();
        mPresent.register(this);
        mPresent.getAlllProduct(mBean.getContractID());
        initView();
    }

    private void initView() {
        mTxtName.setText(mBean.getContractName());
        mTxtType.setText(mBean.getTranContractType());
        mTxtStatus.setText(mBean.getTransformStatus());
        mTxtStatus.setTextColor(ContractManageActivity.ContractListAdapter.getStatusColor(mBean.getStatus()));
        mTxtNo.setText(mBean.getContractCode());
        mTxtGroupName.setText(mBean.getPurchaserName());
        mTxtShopName.setText(mBean.getShopName());
        mTxtTimeSpan.setText(CalendarUtils.getDateFormatString(mBean.getStartDate(), "yyyyMMdd", "yyyy/MM/dd") + " - " +
                CalendarUtils.getDateFormatString(mBean.getEndDate(), "yyyyMMdd", "yyyy/MM/dd"));
        mTxtPerson.setText(mBean.getSignEmployeeName());
        mTxtTime.setText(CalendarUtils.getDateFormatString(mBean.getSignDate(), "yyyyMMdd", "yyyy/MM/dd"));

        mTxtLeftDays.setText(String.valueOf(mBean.getDistanceExpirationDate()));

        mTxtLinkContract.setText(String.format("已经关联%s份合同", (mBean.getIsSon() == 2 && !TextUtils.isEmpty(mBean.getExtContractID())) ? 1 :
                mBean.getIsSon() == 1 ?
                        mBean.getSonContractNum() : 0));

        mTxtLinkContract.setOnClickListener(v -> {
            if (mBean.getIsSon() == 2) {//子合同
                LinkContractListActivity.start(mBean.getExtContractID(), "");
            } else {
                LinkContractListActivity.start("", mBean.getContractID());
            }
        });

        mEdtContractAmount.setText(mBean.getContractTotalAmount());
        mEdtBk.setText(mBean.getRemarks());

        //todo 附件没有 则不显示
        List<DownLoadBean> downLoadBeans = JsonUtil.parseJsonList(mBean.getAttachment(), DownLoadBean.class);
        DownloadAdapter downloadAdapter = new DownloadAdapter(downLoadBeans);
        downloadAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.img_operation) {
                DownLoadBean downLoadBean = downloadAdapter.getItem(position);
                if (TextUtils.equals(view.getTag().toString(), "jpg")) {//查看图片 todo 图片类型需要扩展
                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(this, view,
                                    "image");
                    Intent intent = new Intent(this, ImageViewActivity.class);
                    intent.putExtra("url", downLoadBean.getUrl());
                    ActivityCompat.startActivity(this, intent, options.toBundle());
                } else {//下载
                    //todo 下载文件方法
                    DownloadUtil.getInstance().download("http://res.hualala.com/group3/M01/A3/32/wKgVe14F-kKkfvEuAABMi7RQLQw747.jpg","", new DownloadUtil.OnDownloadListener() {
                        @Override
                        public void onDownloadSuccess(String path) {
                            ToastUtils.showShort("保存成功"+path);
                        }

                        @Override
                        public void onDownloading(int progress) {
//                            ToastUtils.showShort("已下载"+progress+"%");
                        }

                        @Override
                        public void onDownloadFailed() {
                            ToastUtils.showShort("保存失败");
                        }
                    });
                }
            }
        });

        mListDownload.setAdapter(downloadAdapter);

        //底部按钮显示逻辑
        if (mBean.getStatus() == 0) {//待审核
            mTxtBtnDel.setVisibility(View.VISIBLE);
            mTxtBtnMdf.setVisibility(View.VISIBLE);
            mTxtBtnCheck.setVisibility(View.VISIBLE);
            mTxtBtnStop.setVisibility(View.GONE);
            mTxtBtnDetail.setVisibility(View.GONE);
//            if (GreenDaoUtils.containsAuth("")) {
//            }
        } else if (mBean.getStatus() == 2) {//执行中
            mTxtBtnDel.setVisibility(View.GONE);
            mTxtBtnMdf.setVisibility(View.GONE);
            mTxtBtnCheck.setVisibility(View.GONE);
            mTxtBtnStop.setVisibility(View.VISIBLE);
            mTxtBtnDetail.setVisibility(View.VISIBLE);
        } else if (mBean.getStatus() == 1) {//已审核
            mTxtBtnDel.setVisibility(View.GONE);
            mTxtBtnMdf.setVisibility(View.GONE);
            mTxtBtnCheck.setVisibility(View.GONE);
            mTxtBtnDetail.setVisibility(View.GONE);
            mTxtBtnStop.setVisibility(View.VISIBLE);
        } else {
            mTxtBtnDel.setVisibility(View.GONE);
            mTxtBtnMdf.setVisibility(View.GONE);
            mTxtBtnCheck.setVisibility(View.GONE);
            mTxtBtnStop.setVisibility(View.GONE);
            mTxtBtnDetail.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.txt_btn_del, R.id.txt_btn_mdf, R.id.txt_btn_check, R.id.txt_btn_stop, R.id.txt_btn_detail})
    public void onEvent(View view) {
        switch (view.getId()) {
            case R.id.txt_btn_del:
                SuccessDialog.newBuilder(this)
                        .setImageTitle(R.drawable.ic_dialog_failure)
                        .setImageState(R.drawable.ic_dialog_state_failure)
                        .setMessageTitle("是否删除该合同")
                        .setMessage("xxxxxxxxxxxx")
                        .setButton((dialog, item) -> {
                            dialog.dismiss();
                            if (item == 1) {
                                mPresent.delete(mBean.getContractID());
                            }
                        }, "我再看看", "确认删除").create().show();
                break;
            case R.id.txt_btn_mdf:
                ContractManageAddActivity.start(mBean);
                finish();
                break;
            case R.id.txt_btn_check:
                SuccessDialog.newBuilder(this)
                        .setImageTitle(R.drawable.ic_dialog_failure)
                        .setImageState(R.drawable.ic_dialog_state_failure)
                        .setMessageTitle("是否审核通过该合同")
                        .setMessage("xxxxxxxxxxxx")
                        .setButton((dialog, item) -> {
                            dialog.dismiss();
                            if (item == 1) {
                                mPresent.undateStatus(mBean.getContractID(), 1);
                            }
                        }, "我再看看", "确认审核通过").create().show();
                break;
            case R.id.txt_btn_stop:
                SuccessDialog.newBuilder(this)
                        .setImageTitle(R.drawable.ic_dialog_failure)
                        .setImageState(R.drawable.ic_dialog_state_failure)
                        .setMessageTitle("是否终止该合同")
                        .setMessage("xxxxxxxxxxxx")
                        .setButton((dialog, item) -> {
                            dialog.dismiss();
                            if (item == 1) {
                                mPresent.undateStatus(mBean.getContractID(), 3);
                            }
                        }, "我再看看", "确认终止").create().show();
                break;
            case R.id.txt_btn_detail:

                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void deleteSuccess() {
        showToast("删除成功");
        EventBus.getDefault().post(new ContractManageEvent(true));
        finish();
    }

    @Override
    public void undateStatusSuccess(int status) {
        if (status == 1) {
            showToast("审核通过");
            EventBus.getDefault().post(new ContractManageEvent(true));
            finish();
        }
    }


    @Override
    public void getProductSuccess(ContractProductListResp resp) {
        mExcel.setEnableLoadMore(false);
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setHeaderView(View.inflate(this, R.layout.view_contract_product_list_header, null));
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                // no-op
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.getAlllProduct(mBean.getContractID());
            }
        });
        mExcel.setEmptyView("目前没有商品");
        mExcel.setData(resp.getList(), false);

    }


    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        return array;
    }

}
