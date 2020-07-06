package com.hll_sc_app.widget.aptitude;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.aptitude.AptitudeInfoKV;
import com.hll_sc_app.bean.aptitude.AptitudeInfoReq;
import com.hll_sc_app.bean.aptitude.AptitudeInfoResp;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/24
 */

public class AptitudeNormalInfoView extends LinearLayout implements IAptitudeInfoCallback {
    @BindView(R.id.ani_authorization)
    TextView mAuthorization;
    @BindView(R.id.ani_authorization_img)
    ImgUploadBlock mAuthorizationImg;
    @BindView(R.id.ani_info)
    TextView mInfo;
    @BindView(R.id.ani_info_img)
    ImgUploadBlock mInfoImg;
    @BindView(R.id.ani_supply)
    EditText mSupply;
    @BindView(R.id.ani_quality)
    TextView mQuality;
    @BindView(R.id.ani_flow)
    TextView mFlow;
    @BindViews({R.id.ani_authorization, R.id.ani_authorization_img, R.id.ani_info, R.id.ani_info_img, R.id.ani_supply, R.id.ani_quality, R.id.ani_flow})
    List<View> mInputViews;
    private ImgUploadBlock mCurUpload;

    public AptitudeNormalInfoView(Context context) {
        this(context, null);
    }

    public AptitudeNormalInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AptitudeNormalInfoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
        int space = UIUtils.dip2px(10);
        setPadding(space, 0, space, 0);
        View view = View.inflate(context, R.layout.view_aptitude_normal_info, this);
        ButterKnife.bind(this, view);
        setEditable(false);
    }

    @Override
    public void withData(AptitudeInfoResp resp) {
        if (resp == null) return;
        handleValue(mAuthorization, resp.getCertificateAuthorization());
        if (mAuthorizationImg.getVisibility() == VISIBLE) {
            mAuthorizationImg.showImage(resp.getCertificateAuthorizationImage());
        }
        handleValue(mInfo, resp.getManufacturerInfo());
        if (mInfoImg.getVisibility() == VISIBLE) {
            mInfoImg.showImage(resp.getManufacturerInfoImage());
        }
        mSupply.setText(resp.getSupplyFor());
        handleValue(mQuality, resp.getHasQualitySystem());
        handleValue(mFlow, resp.getHasStandardProcedure());
    }

    @Override
    public void setEditable(boolean editable) {
        for (View view : mInputViews) {
            if (view instanceof EditText) {
                view.setFocusable(editable);
                view.setFocusableInTouchMode(editable);
            } else if (view instanceof TextView) {
                view.setClickable(editable);
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(0, 0, editable ? R.drawable.ic_arrow_gray : 0, 0);
            } else if (view instanceof ImgUploadBlock) {
                ((ImgUploadBlock) view).setEditable(editable);
            }
        }
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public AptitudeInfoReq getReq() {
        AptitudeInfoReq req = new AptitudeInfoReq();
        List<AptitudeInfoKV> list = new ArrayList<>();
        req.setAptitudeList(list);
        if (mAuthorization.getText().toString().equals("有") && TextUtils.isEmpty(mAuthorizationImg.getImgUrl())) {
            ToastUtils.showShort("请上传产品授权证明");
            return null;
        }
        if (mInfo.getText().toString().equals("有") && TextUtils.isEmpty(mInfoImg.getImgUrl())) {
            ToastUtils.showShort("请上传厂家全套资料");
            return null;
        }
        list.add(new AptitudeInfoKV(mAuthorization.getTag().toString(),
                mAuthorization.getTag(R.id.base_tag_1) == null ? "" : mAuthorization.getTag(R.id.base_tag_1).toString()));
        list.add(new AptitudeInfoKV(mAuthorizationImg.getTag().toString(),
                mAuthorizationImg.getImgUrl() == null ? "" : mAuthorizationImg.getImgUrl()));

        list.add(new AptitudeInfoKV(mInfo.getTag().toString(),
                mInfo.getTag(R.id.base_tag_1) == null ? "" : mInfo.getTag(R.id.base_tag_1).toString()));
        list.add(new AptitudeInfoKV(mInfoImg.getTag().toString(),
                mInfoImg.getImgUrl() == null ? "" : mInfoImg.getImgUrl()));

        String supply = mSupply.getText().toString().trim();
        list.add(new AptitudeInfoKV(mSupply.getTag().toString(), supply));

        list.add(new AptitudeInfoKV(mQuality.getTag().toString(),
                mQuality.getTag(R.id.base_tag_1) == null ? null : mQuality.getTag(R.id.base_tag_1).toString()));

        list.add(new AptitudeInfoKV(mFlow.getTag().toString(),
                mFlow.getTag(R.id.base_tag_1) == null ? null : mFlow.getTag(R.id.base_tag_1).toString()));
        return req;
    }

    @OnClick({R.id.ani_authorization, R.id.ani_info, R.id.ani_quality, R.id.ani_flow})
    public void onViewClicked(View view) {
        List<NameValue> list = Arrays.asList(new NameValue("有", "1"), new NameValue("无", "2"));
        NameValue current = null;
        if (view.getTag(R.id.base_tag_1) != null) {
            int value = CommonUtils.getInt(view.getTag(R.id.base_tag_1).toString());
            if (value > 0) {
                current = list.get(value - 1);
            }
        }
        SingleSelectionDialog.newBuilder(((Activity) getContext()), NameValue::getName)
                .setTitleText("请选择")
                .refreshList(list)
                .select(current)
                .setOnSelectListener(nameValue -> {
                    handleValue(((TextView) view), nameValue.getValue());
                })
                .create().show();
    }

    private void handleValue(TextView textView, String value) {
        int result = CommonUtils.getInt(value);
        textView.setText(result == 1 ? "有" : result == 2 ? "无" : "");
        textView.setTag(R.id.base_tag_1, value);
        switch (textView.getId()) {
            case R.id.ani_authorization:
                if (result == 1) {
                    mAuthorizationImg.setVisibility(VISIBLE);
                } else if (result == 2) {
                    mAuthorizationImg.deleteImage();
                    mAuthorizationImg.setVisibility(GONE);
                }
                break;
            case R.id.ani_info:
                if (result == 1) {
                    mInfoImg.setVisibility(VISIBLE);
                } else {
                    mInfoImg.deleteImage();
                    mInfoImg.setVisibility(GONE);
                }
                break;
        }
    }

    public void showImage(String url) {
        if (mCurUpload != null) {
            mCurUpload.showImage(url);
        }
    }

    @OnTouch({R.id.ani_authorization_img, R.id.ani_info_img})
    public boolean onTouch(View view) {
        mCurUpload = (ImgUploadBlock) view;
        return false;
    }
}
