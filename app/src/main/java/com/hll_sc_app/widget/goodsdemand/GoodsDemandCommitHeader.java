package com.hll_sc_app.widget.goodsdemand;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.AreaProductSelectWindow;
import com.hll_sc_app.base.widget.ImgShowDelBlock;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandReq;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/22
 */

public class GoodsDemandCommitHeader extends ConstraintLayout {
    @BindView(R.id.dch_price)
    TextView mPrice;
    @BindView(R.id.dch_brand)
    EditText mBrand;
    @BindView(R.id.dch_spec)
    EditText mSpec;
    @BindView(R.id.dch_origin)
    TextView mOrigin;
    @BindView(R.id.dch_pack)
    TextView mPack;
    @BindView(R.id.dch_manufacturer)
    EditText mManufacturer;
    @BindView(R.id.dch_pic_upload)
    TextView mPicUpload;
    @BindView(R.id.dch_pic_group)
    LinearLayout mPicGroup;
    private GoodsDemandReq mReq;
    private int mPicSize;
    private List<String> mUrls = new ArrayList<>();
    private AreaProductSelectWindow mSelectWindow;
    private SingleSelectionDialog mDialog;
    private boolean inflated;

    public GoodsDemandCommitHeader(Context context) {
        this(context, null);
    }

    public GoodsDemandCommitHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodsDemandCommitHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_goods_demand_commit_header, this);
        ButterKnife.bind(this, view);
        resizePic();
        mPicGroup.setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                if (mReq != null)
                    mReq.setImgUrl(TextUtils.join(",", mUrls));
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
                onChildViewAdded(parent, child);
            }
        });
    }

    private void resizePic() {
        float sw = UIUtils.getScreenWidth(getContext());
        mPicSize = (int) ((sw - UIUtils.dip2px(60)) / 5);
        ViewGroup.LayoutParams layoutParams = mPicUpload.getLayoutParams();
        layoutParams.width = mPicSize;
        layoutParams.height = mPicSize;
    }

    @OnClick({R.id.dch_origin, R.id.dch_pack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dch_origin:
                UIUtils.hideActivitySoftKeyboard((Activity) getContext());
                if (mSelectWindow == null) {
                    mSelectWindow = new AreaProductSelectWindow((Activity) getContext());
                    mSelectWindow.setResultSelectListener(t -> {
                        mReq.setPlaceProvince(t.getShopProvince());
                        mReq.setPlaceProvinceCode(t.getShopProvinceCode());
                        mReq.setPlaceCity(t.getShopCity());
                        mReq.setPlaceCityCode(t.getShopCityCode());
                        mOrigin.setText(String.format("%s - %s", t.getShopProvince(), t.getShopCity()));
                    });
                }
                mSelectWindow.showAtLocation(this, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.dch_pack:
                if (mDialog == null) {
                    List<NameValue> nameValues = new ArrayList<>();
                    nameValues.add(new NameValue("散装", "0"));
                    nameValues.add(new NameValue("包装", "1"));
                    mDialog = SingleSelectionDialog.newBuilder((Activity) getContext(), NameValue::getName)
                            .setTitleText("包装方式")
                            .refreshList(nameValues)
                            .setOnSelectListener(value -> {
                                mReq.setPackMethod(value.getName());
                                mPack.setText(value.getName());
                            })
                            .create();
                }
                mDialog.show();
                break;
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mPicUpload.setOnClickListener(l);
    }

    public void withReq(GoodsDemandReq req) {
        mReq = req;
        if (!TextUtils.isEmpty(req.getId())) {
            if (!TextUtils.isEmpty(req.getImgUrl())) {
                for (String url : req.getImgUrl().split(",")) {
                    addUrl(url);
                }
            }
            if (CommonUtils.getDouble(req.getMarketPrice()) != 0) {
                mPrice.setText(req.getMarketPrice());
            }
            mPack.setText(req.getPackMethod());
            if (!TextUtils.isEmpty(req.getPlaceProvince()) && !TextUtils.isEmpty(req.getPlaceCity())) {
                mOrigin.setText(String.format("%s - %s", req.getPlaceProvince(), req.getPlaceCity()));
            }
            mManufacturer.setText(req.getProducer());
            mBrand.setText(req.getProductBrand());
            mSpec.setText(req.getSpecContent());
        }
        inflated = true;
    }

    @OnTextChanged({R.id.dch_brand, R.id.dch_spec, R.id.dch_manufacturer})
    public void onTextChanged() {
        if (!inflated) return;
        mReq.setProductBrand(mBrand.getText().toString().trim());
        mReq.setSpecContent(mSpec.getText().toString().trim());
        mReq.setProducer(mManufacturer.getText().toString().trim());
    }

    @OnTextChanged(R.id.dch_price)
    public void afterTextChanged(Editable s) {
        Utils.processMoney(s, false);
        mReq.setMarketPrice(String.valueOf(CommonUtils.getDouble(s.toString())));
    }

    public void addUrl(String url) {
        mUrls.add(url);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mPicSize, mPicSize);
        layoutParams.setMargins(0, 0, UIUtils.dip2px(10), 0);
        ImgShowDelBlock del = new ImgShowDelBlock(getContext());
        mPicGroup.addView(del, mUrls.size() - 1, layoutParams);
        del.setImgUrl(url);
        del.setLayoutParams(layoutParams);
        del.setTag(url);
        del.setUrls(mUrls);
        del.setDeleteListener(v -> {
            if (v.getTag() == null) return;
            int index = mUrls.indexOf(v.getTag().toString());
            mUrls.remove(index);
            mPicGroup.removeViewAt(index);
            mPicUpload.setVisibility(VISIBLE);
        });
        if (mUrls.size() == 4) mPicUpload.setVisibility(GONE);
    }
}
