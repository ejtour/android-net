package com.hll_sc_app.app.info.license;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.info.ModifyType;
import com.hll_sc_app.app.upload.IImageUploadContract;
import com.hll_sc_app.app.upload.ImageUploadPresenter;
import com.hll_sc_app.app.wallet.common.WalletHelper;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.user.CertifyReq;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.TitleBar;
import com.zhihu.matisse.Matisse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/6
 */
@Route(path = RouterConfig.INFO_LICENSE)
public class InfoLicenseActivity extends BaseLoadActivity implements IImageUploadContract.IImageUploadView {
    private static final int REQ_CODE = 0x659;
    @BindView(R.id.ail_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.ail_write_name)
    EditText mWriteName;
    @BindView(R.id.ail_write_no)
    EditText mWriteNo;
    @BindView(R.id.ail_write_start)
    TextView mWriteStart;
    @BindView(R.id.ail_write_end)
    TextView mWriteEnd;
    @BindView(R.id.ail_write_pic)
    ImgUploadBlock mWritePic;
    @BindView(R.id.ail_write_group)
    ConstraintLayout mWriteGroup;
    @BindView(R.id.ail_read_pic)
    GlideImageView mReadPic;
    @BindView(R.id.ail_read_name)
    TextView mReadName;
    @BindView(R.id.ail_read_no)
    TextView mReadNo;
    @BindView(R.id.ail_read_expires)
    TextView mReadExpires;
    @BindView(R.id.ail_read_group)
    RelativeLayout mReadGroup;
    @Autowired(name = "parcelable")
    InfoLicenseParam mParam;
    private boolean mIsStartDate;
    private DateWindow mDateWindow;
    private IImageUploadContract.IImageUploadPresenter mPresenter;

    public static void start(Activity context, CertifyReq req) {
        RouterUtil.goToActivity(RouterConfig.INFO_LICENSE, context, REQ_CODE, InfoLicenseParam.createFromCertifyReq(req));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_info_license);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null
                && requestCode == Constant.IMG_SELECT_REQ_CODE) {
            List<String> list = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(list)) mPresenter.upload(list.get(0));
        }
    }

    private void initData() {
        mPresenter = ImageUploadPresenter.newInstance();
        mPresenter.register(this);
    }

    private void initView() {
        if (mParam.isEditable()) {
            mTitleBar.setRightText("保存");
            mTitleBar.setRightBtnClick(this::save);
            mWriteGroup.setVisibility(View.VISIBLE);
            mWriteStart.setText(getReadableData(mParam.getStartTime()));
            mWriteEnd.setText(getReadableData(mParam.getEndTime()));
            mWriteName.setText(mParam.getLicenseName());
            mWriteNo.setText(mParam.getBusinessNo());
            mWritePic.showImage(mParam.getLicencePhotoUrl());
        } else {
            mReadGroup.setVisibility(View.VISIBLE);
            mReadPic.isPreview(true);
            mReadPic.setImageURL(mParam.getLicencePhotoUrl());
            mReadName.setText(mParam.getLicenseName());
            mReadNo.setText(mParam.getBusinessNo());
            mReadExpires.setText(String.format("%s - %s", getReadableData(mParam.getStartTime()), getReadableData(mParam.getEndTime())));
        }
    }

    private void save(View view) {
        mParam.setLicenseName(mWriteName.getText().toString().trim());
        mParam.setBusinessNo(mWriteNo.getText().toString().trim());
        Intent intent = new Intent();
        intent.putExtra("type", ModifyType.LICENSE);
        intent.putExtra("obj", mParam);
        setResult(RESULT_OK, intent);
        finish();
    }

    private String getReadableData(String date) {
        return TextUtils.isEmpty(date) ? "" :
                WalletHelper.PERMANENT_DATE.equals(date) ? "长期有效" :
                        DateUtil.getReadableTime(date, Constants.SLASH_YYYY_MM_DD);
    }

    @OnClick(R.id.ail_write_start)
    public void selectStartDate() {
        showDialog((dialog, item) -> {
            dialog.dismiss();
            if (item == 0) {
                mParam.setStartTime(WalletHelper.PERMANENT_DATE);
                mParam.setEndTime(WalletHelper.PERMANENT_DATE);
                mWriteStart.setText("长期有效");
                mWriteEnd.setText("长期有效");
            } else showDateWindow(true);
        });
    }

    @OnClick(R.id.ail_write_end)
    public void selectEndDate() {
        showDialog((dialog, item) -> {
            dialog.dismiss();
            if (item == 0) {
                mParam.setEndTime(WalletHelper.PERMANENT_DATE);
                mWriteEnd.setText("长期有效");
            } else showDateWindow(false);
        });
    }

    private void showDialog(TipsDialog.OnClickListener listener) {
        TipsDialog.newBuilder(this)
                .setTitle("选择有效期")
                .setMessage("如果营业执照长期有效，请选择\"长期有效\",否则选择具体时间")
                .setButton(listener, "长期有效", "具体时间")
                .create()
                .show();
    }

    private void showDateWindow(boolean start) {
        mIsStartDate = start;
        if (mDateWindow == null) {
            mDateWindow = new DateWindow(this);
            mDateWindow.setSelectListener(date -> {
                String sDate = CalendarUtils.format(date, CalendarUtils.FORMAT_LOCAL_DATE);
                String tDate = CalendarUtils.format(date, Constants.SIGNED_YYYY_MM_DD);
                if (mIsStartDate) {
                    mParam.setStartTime(sDate);
                    mWriteStart.setText(tDate);
                } else {
                    mParam.setEndTime(sDate);
                    mWriteEnd.setText(tDate);
                }
            });
        }
        WalletHelper.showDate(this, mDateWindow, start, mParam.getStartTime(), mParam.getEndTime());
    }

    @Override
    public void setImageUrl(String url) {
        mWritePic.showImage(url);
        mParam.setLicencePhotoUrl(url);
    }
}
