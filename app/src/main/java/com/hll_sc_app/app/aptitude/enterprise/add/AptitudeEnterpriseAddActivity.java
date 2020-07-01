package com.hll_sc_app.app.aptitude.enterprise.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.aptitude.AptitudeEnterpriseBean;
import com.hll_sc_app.bean.aptitude.AptitudeTypeBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;
import com.zhihu.matisse.Matisse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

@Route(path = RouterConfig.APTITUDE_ENTERPRISE_ADD)
public class AptitudeEnterpriseAddActivity extends BaseLoadActivity implements IAptitudeEnterpriseAddContract.IAptitudeEnterpriseAddView {
    public static final int REQ_CODE = 0x838;
    @BindView(R.id.aea_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aea_type)
    TextView mType;
    @BindView(R.id.aea_time)
    TextView mTime;
    @BindView(R.id.aea_image)
    ImgUploadBlock mImage;
    @Autowired(name = "parcelable")
    AptitudeEnterpriseBean mReq;
    @Autowired(name = "object0")
    String mTypes;
    private SingleSelectionDialog mDialog;
    private DateWindow mDateWindow;
    private IAptitudeEnterpriseAddContract.IAptitudeEnterpriseAddPresenter mPresenter;
    private List<AptitudeTypeBean> mList;

    public static void start(Activity activity, String types) {
        Object[] args = {types};
        RouterUtil.goToActivity(RouterConfig.APTITUDE_ENTERPRISE_ADD, activity, REQ_CODE, args);
    }

    public static void start(Activity activity, AptitudeEnterpriseBean bean) {
        if (!RightConfig.checkRight(activity.getString(R.string.right_aptitude_update))) {
            ToastUtils.showShort(activity.getString(R.string.right_tips));
            return;
        }
        RouterUtil.goToActivity(RouterConfig.APTITUDE_ENTERPRISE_ADD, activity, REQ_CODE, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_aptitude_enterprise_add);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        if (mReq == null) {
            mReq = new AptitudeEnterpriseBean();
            mReq.setGroupID(UserConfig.getGroupID());
        }
        mPresenter = AptitudeEnterpriseAddPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::save);
        if (mReq != null) {
            mTitleBar.setHeaderTitle("编辑资质");
            mType.setClickable(false);
            mType.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            mType.setText(mReq.getAptitudeName());
            mTime.setText(DateUtil.getReadableTime(mReq.getEndTime(), Constants.SLASH_YYYY_MM_DD));
            mImage.showImage(mReq.getAptitudeUrl());
            mImage.setOnDeleteListener(v -> mReq.setAptitudeUrl(""));
        }
    }

    private void save(View view) {
        if (TextUtils.isEmpty(mReq.getAptitudeName())) {
            showToast("请选择证件类型");
            return;
        }
        if (TextUtils.isEmpty(mReq.getAptitudeUrl())) {
            showToast("请上传证件照");
            return;
        }
        if (TextUtils.isEmpty(mReq.getEndTime())) {
            showToast("请选择到期时间");
            return;
        }
        mPresenter.save(mReq);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null
                && requestCode == ImgUploadBlock.REQUEST_CODE_CHOOSE) {
            List<String> list = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(list)) mPresenter.upload(list.get(0));
        }
    }

    @OnClick(R.id.aea_type)
    public void selectType() {
        if (mList == null) {
            mPresenter.start();
            return;
        }
        if (mDialog == null) {
            if (!TextUtils.isEmpty(mTypes)) {
                for (String s : mTypes.split(",")) {
                    for (AptitudeTypeBean bean : mList) {
                        if (bean.getAptitudeType().equals(s)) {
                            bean.setEnable(false);
                            break;
                        }
                    }
                }
            }
            mDialog = SingleSelectionDialog.newBuilder(this, AptitudeTypeBean::getAptitudeName, AptitudeTypeBean::isEnable)
                    .setTitleText("请选择证件类型")
                    .refreshList(mList)
                    .setOnSelectListener(aptitudeTypeBean -> {
                        mType.setText(aptitudeTypeBean.getAptitudeName());
                        mReq.setAptitudeType(aptitudeTypeBean.getAptitudeType());
                        mReq.setAptitudeName(aptitudeTypeBean.getAptitudeName());
                    })
                    .create();
        }
        mDialog.show();
    }

    @OnClick(R.id.aea_time)
    public void selectTime() {
        if (mDateWindow == null) {
            mDateWindow = new DateWindow(this);
            mDateWindow.setSelectListener(date -> {
                mTime.setText(CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD));
                mReq.setEndTime(CalendarUtils.toLocalDate(date));
            });
        }
        mDateWindow.showAtLocation(getWindow().getDecorView(), Gravity.END, 0, 0);
    }

    @Override
    public void setData(List<AptitudeTypeBean> list) {
        mList = list.subList(1, list.size());
    }

    @Override
    public void success() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setImageUrl(String url) {
        mImage.showImage(url);
        mReq.setAptitudeUrl(url);
    }
}
