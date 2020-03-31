package com.hll_sc_app.app.wallet.authentication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.wallet.OcrImageResp;
import com.hll_sc_app.bean.wallet.WalletInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hll_sc_app.app.wallet.authentication.CommonMethod.PERMANENT_DATE;
import static com.hll_sc_app.app.wallet.authentication.CommonMethod.setUploadImg;
import static com.hll_sc_app.app.wallet.authentication.CommonMethod.transformDate;

/**
 * 法人信息
 *
 * @author zc
 */
public class PersonInfoSmallFragment extends BaseLazyFragment implements IAuthenticationContract.IFragment,
        IAuthenticationContract.IUploadFragment, IAuthenticationContract.IOcrFragment {

    @BindView(R.id.edt_lpName)
    EditText mLpName;
    @BindView(R.id.edt_lpIDCardNo)
    EditText mLpIDCardNo;
    @BindView(R.id.txt_lp_begin_date)
    TextView mLpBeginDate;
    @BindView(R.id.txt_lp_end_date)
    TextView mLpEndDate;
    @BindView(R.id.img_card_front)
    ImgUploadBlock mImgCardFront;
    @BindView(R.id.img_card_back)
    ImgUploadBlock mImgCardBack;

    private Unbinder unbinder;
    private IAuthenticationContract.IView mView;
    private ImgUploadBlock mCurrentImgShowDelBlock;
    private boolean isStartDate;

    private DateWindow mDateWindow;

    /**
     * 设置上传组件的公共配置
     *
     * @param title
     */
    private void initUpload(ImgUploadBlock imgUploadBlock, String title) {
        setUploadImg(imgUploadBlock, title, v -> {
            imgUploadBlock.showImage("");
            WalletInfo walletInfo = mView.getWalletInfo();
            if (imgUploadBlock == mImgCardFront) {
                walletInfo.setImgLPIDCardFront("");
            } else if (imgUploadBlock == mImgCardBack) {
                walletInfo.setImgLPIDCardBack("");
            }
            isSubmit();
        }, uploadImgBlock -> {
            mCurrentImgShowDelBlock = imgUploadBlock;
            return true;
        });
    }


    @Override
    public void showOcrInfo(OcrImageResp resp) {
        if (mCurrentImgShowDelBlock == mImgCardFront) {
            OcrImageResp.ImgLPIDCardFrontBean frontBean = JsonUtil.fromJson(resp.getImgLPIDCardFront(), OcrImageResp.ImgLPIDCardFrontBean.class);
            mLpName.setText(frontBean.getName());
            mLpIDCardNo.setText(frontBean.getNum());
            mView.getWalletInfo().setLpName(frontBean.getName());
            mView.getWalletInfo().setLpIDCardNo(frontBean.getNum());
        } else if (mCurrentImgShowDelBlock == mImgCardBack) {
            OcrImageResp.ImgLPIDCardBackBean backBean = JsonUtil.fromJson(resp.getImgLPIDCardBack(), OcrImageResp.ImgLPIDCardBackBean.class);
            mLpBeginDate.setText(transformDate(backBean.getStart_date()));
            mLpEndDate.setText(transformDate(backBean.getEnd_date()));
            mView.getWalletInfo().setLpIDCardPeriodBeginDate(backBean.getStart_date());
            mView.getWalletInfo().setLpIDCardPeriod(backBean.getEnd_date());
        }
        isSubmit();
    }

    @Override
    public void registerView(IAuthenticationContract.IView view) {
        mView = view;
    }

    @Override
    public void setUploadUrl(String url) {
        WalletInfo walletInfo = mView.getWalletInfo();
        if (mCurrentImgShowDelBlock != null) {
            mCurrentImgShowDelBlock.showImage(url);
            if (mCurrentImgShowDelBlock == mImgCardFront) {
                walletInfo.setImgLPIDCardFront(url);
                mView.ocrImage(1, url);
            } else if (mCurrentImgShowDelBlock == mImgCardBack) {
                walletInfo.setImgLPIDCardBack(url);
                mView.ocrImage(2, url);
            }
            isSubmit();
        }
    }

    private void isSubmit() {
        mView.setSubmitButtonEnable(isInputComplete());
    }

    /**
     * 判断是否填充完整
     *
     * @return
     */
    private boolean isInputComplete() {
        WalletInfo walletInfo = mView.getWalletInfo();
        return !TextUtils.isEmpty(walletInfo.getLpName())
                && !TextUtils.isEmpty(walletInfo.getLpIDCardNo())
                && !TextUtils.isEmpty(walletInfo.getLpIDCardPeriodBeginDate())
                && !TextUtils.equals("0", walletInfo.getLpIDCardPeriodBeginDate())
                && !TextUtils.isEmpty(walletInfo.getLpIDCardPeriod())
                && !TextUtils.equals("0", walletInfo.getLpIDCardPeriod())
                && !TextUtils.isEmpty(walletInfo.getImgLPIDCardFront())
                && !TextUtils.isEmpty(walletInfo.getImgLPIDCardBack());
    }

    @OnClick({R.id.txt_lp_begin_date, R.id.txt_lp_end_date, R.id.txt_title_img_front_demo, R.id.txt_title_img_back_demo})
    public void onClick(View view) {
        WalletInfo walletInfo = mView.getWalletInfo();
        switch (view.getId()) {
            case R.id.txt_lp_begin_date:
                CommonMethod.showLongValidDateDialog(getActivity(), (dialog, index) -> {
                    dialog.dismiss();
                    walletInfo.setLpIDCardPeriod("");
                    mLpEndDate.setText("");
                    if (index == 0) {
                        walletInfo.setLpIDCardPeriodBeginDate(PERMANENT_DATE);
                        walletInfo.setLpIDCardPeriod(PERMANENT_DATE);
                        mLpBeginDate.setText("长期有效");
                        mLpEndDate.setText("长期有效");
                        isSubmit();
                    } else {
                        isStartDate = true;
                        showDateWindow(true);
                    }
                });
                break;
            case R.id.txt_lp_end_date:
                CommonMethod.showLongValidDateDialog(getActivity(), (dialog, index) -> {
                    dialog.dismiss();
                    if (index == 0) {
                        walletInfo.setLpIDCardPeriod(PERMANENT_DATE);
                        mLpEndDate.setText("长期有效");
                        isSubmit();
                    } else {
                        isStartDate = false;
                        showDateWindow(false);
                    }
                });
                break;
            case R.id.txt_title_img_front_demo:
                new DemoImgWindow(getActivity()).showDemo(DemoImgWindow.TYPE_LICENSE);
                break;
            case R.id.txt_title_img_back_demo:
                new DemoImgWindow(getActivity()).showDemo(DemoImgWindow.TYPE_LICENSE);
                break;
            default:
                break;
        }
    }

    private void showDateWindow(boolean isStart) {
        WalletInfo walletInfo = mView.getWalletInfo();
        CommonMethod.showDate((BaseLoadActivity) mView, mDateWindow, isStart, walletInfo.getLpIDCardPeriodBeginDate(), walletInfo.getLpIDCardPeriod(), (sDate, oDate) -> {
            if (isStartDate) {
                mLpBeginDate.setText(transformDate(sDate));
                walletInfo.setLpIDCardPeriodBeginDate(sDate);
            } else {
                mLpEndDate.setText(transformDate(sDate));
                walletInfo.setLpIDCardPeriod(sDate);
            }
            isSubmit();
        });
    }


    @Override
    protected void onVisible() {
        super.onVisible();
        isSubmit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_wallet_small_step_2, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {
        //设置上传图片组件的配置
        initUpload(mImgCardFront, "点击上传证件照正面");
        initUpload(mImgCardBack, "点击上传证件照反面");
        //初始化显示页面
        WalletInfo walletInfo = mView.getWalletInfo();

        mLpName.setText(walletInfo.getLpName());
        mLpIDCardNo.setText(walletInfo.getLpIDCardNo());
        mLpBeginDate.setText(transformDate(walletInfo.getLpIDCardPeriodBeginDate()));
        mLpEndDate.setText(transformDate(walletInfo.getLpIDCardPeriod()));
        mImgCardFront.showImage(walletInfo.getImgLPIDCardFront());
        mImgCardBack.showImage(walletInfo.getImgLPIDCardBack());
        //绑定文本输入框的监听
        initTextViewWatch();
    }

    private void initTextViewWatch() {
        mLpName.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setLpName(value);
            mView.getWalletInfo().setReceiverName(value);
        }));
        mLpIDCardNo.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setLpIDCardNo(value);
        }));
    }

    private TextWatcher generateWatcher(CommonMethod.AddInputChangeWalletInfo addInputChangeWalletInfo) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addInputChangeWalletInfo.onChanged(s.toString());
                isSubmit();
            }
        };
    }
}
