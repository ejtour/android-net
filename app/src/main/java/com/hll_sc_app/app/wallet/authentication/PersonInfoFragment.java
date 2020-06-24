package com.hll_sc_app.app.wallet.authentication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
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
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.widget.MultiSelectionWindow;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.SingleSelectionWindow;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hll_sc_app.app.wallet.authentication.CommonMethod.PERMANENT_DATE;
import static com.hll_sc_app.app.wallet.authentication.CommonMethod.setUploadImg;
import static com.hll_sc_app.app.wallet.authentication.CommonMethod.transformCardType;
import static com.hll_sc_app.app.wallet.authentication.CommonMethod.transformDate;

/**
 * 法人信息
 *
 * @author zc
 */
public class PersonInfoFragment extends BaseLazyFragment implements IAuthenticationContract.IFragment,
        IAuthenticationContract.IUploadFragment, IAuthenticationContract.IOcrFragment {

    @BindView(R.id.edt_lpName)
    EditText mLpName;
    @BindView(R.id.txt_lpCardType)
    TextView mLpCardType;
    @BindView(R.id.edt_lpIDCardNo)
    EditText mLpIDCardNo;
    @BindView(R.id.txt_lp_begin_date)
    TextView mLpBeginDate;
    @BindView(R.id.txt_lp_end_date)
    TextView mLpEndDate;
    @BindView(R.id.edt_lpPhone)
    EditText mLpPhone;
    @BindView(R.id.img_card_front)
    ImgUploadBlock mImgCardFront;
    @BindView(R.id.img_card_back)
    ImgUploadBlock mImgCardBack;

    private Unbinder unbinder;
    private IAuthenticationContract.IView mView;
    private ImgUploadBlock mCurrentImgShowDelBlock;
    private boolean isStartDate;

    private SingleSelectionDialog mSelectCardTypeDialog;

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
            if (mView.getWalletInfo().getLpCardType() == -1) {
                showToast("请选择法人证件类型");
                return false;
            }
            mCurrentImgShowDelBlock = imgUploadBlock;
            return true;
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

    }

    @Override
    public void showOcrInfo(OcrImageResp resp) {
        if (mCurrentImgShowDelBlock == mImgCardFront) {
            OcrImageResp.ImgLPIDCardFrontBean frontBean  = JsonUtil.fromJson(resp.getImgLPIDCardFront(), OcrImageResp.ImgLPIDCardFrontBean.class);
            mLpName.setText(frontBean.getName());
            mLpIDCardNo.setText(frontBean.getNum());
            mView.getWalletInfo().setLpName(frontBean.getName());
            mView.getWalletInfo().setLpIDCardNo(frontBean.getNum());
        } else if (mCurrentImgShowDelBlock == mImgCardBack) {
            OcrImageResp.ImgLPIDCardBackBean backBean  = JsonUtil.fromJson(resp.getImgLPIDCardBack(), OcrImageResp.ImgLPIDCardBackBean.class);
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
                if (mView.getWalletInfo().getLpCardType() == 0) {//身份证
                    mView.ocrImage(1, url);
                }
            } else if (mCurrentImgShowDelBlock == mImgCardBack) {
                walletInfo.setImgLPIDCardBack(url);
                if (mView.getWalletInfo().getLpCardType() == 0) {//身份证
                    mView.ocrImage(2, url);
                }
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
                && walletInfo.getLpCardType() != -1
                && !TextUtils.isEmpty(walletInfo.getLpIDCardNo())
                && !TextUtils.isEmpty(walletInfo.getLpIDCardPeriodBeginDate())
                && !TextUtils.equals("0", walletInfo.getLpIDCardPeriodBeginDate())
                && !TextUtils.isEmpty(walletInfo.getLpIDCardPeriod())
                && !TextUtils.equals("0", walletInfo.getLpIDCardPeriod())
                && !TextUtils.isEmpty(walletInfo.getLpPhone())
                && !TextUtils.isEmpty(walletInfo.getImgLPIDCardFront())
                && !TextUtils.isEmpty(walletInfo.getImgLPIDCardBack());
    }

    @OnClick({R.id.txt_lpCardType, R.id.txt_lp_begin_date, R.id.txt_lp_end_date,R.id.txt_title_img_front_demo,R.id.txt_title_img_back_demo})
    public void onClick(View view) {
        WalletInfo walletInfo = mView.getWalletInfo();
        switch (view.getId()) {
            case R.id.txt_lpCardType:
                if (mSelectCardTypeDialog == null) {
                    List<NameValue> list = generateCardTypeBean();
                    int index = getSelectIndex(mView.getWalletInfo().getLpCardType());
                    mSelectCardTypeDialog = SingleSelectionDialog.newBuilder((Activity) mView,NameValue::getName)
                            .refreshList(list)
                            .setTitleText("选择法人证件类型")
                            .select(index>-1 ? list.get(index):null)
                            .setOnSelectListener(NameValue -> {
                                mLpCardType.setText(NameValue.getName());
                                mView.getWalletInfo().setLpCardType(Integer.parseInt(NameValue.getValue()));
                                isSubmit();
                            })
                            .create();
                }
                mSelectCardTypeDialog.show();
                break;
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

    private List<NameValue> generateCardTypeBean() {
        NameValue NameValue1 = new NameValue("身份证（限中国大陆居民）", String.valueOf(0));
        NameValue NameValue2 = new NameValue("护照（限境外人士）", String.valueOf(9));
        NameValue NameValue3 = new NameValue("中国香港居民-来往内地通行证", String.valueOf(2));
        NameValue NameValue4 = new NameValue("中国澳门居民-来往内地通行证", String.valueOf(3));
        NameValue NameValue5 = new NameValue("中国台湾居民-来往内地通行证", String.valueOf(4));
        return Arrays.asList(NameValue1, NameValue2, NameValue3, NameValue4,NameValue5);
    }

    /**
     * 根据证件类型 返回单选窗口里所在的index
     *
     * @param cardType
     * @return
     */
    private int getSelectIndex(int cardType) {
        if (cardType == 0) {
            return 0;
        } else if (cardType == 2) {
            return 2;
        } else if (cardType == 3) {
            return 3;
        } else if (cardType == 4) {
            return 4;
        } else if (cardType == 9) {
            return 1;
        }
        return -1;
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
        mCurrentImgShowDelBlock = null;
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet_person_info, null);
    }

    @Override
    protected void initData() {
        //设置上传图片组件的配置
        initUpload(mImgCardFront, "点击上传证件照正面");
        initUpload(mImgCardBack, "点击上传证件照反面");
        //初始化显示页面
        WalletInfo walletInfo = mView.getWalletInfo();

        mLpName.setText(walletInfo.getLpName());
        mLpCardType.setText(transformCardType(walletInfo.getLpCardType()));
        mLpIDCardNo.setText(walletInfo.getLpIDCardNo());
        mLpBeginDate.setText(transformDate(walletInfo.getLpIDCardPeriodBeginDate()));
        mLpEndDate.setText(transformDate(walletInfo.getLpIDCardPeriod()));
        mLpPhone.setText(walletInfo.getLpPhone());
        mImgCardFront.showImage(walletInfo.getImgLPIDCardFront());
        mImgCardBack.showImage(walletInfo.getImgLPIDCardBack());
        //绑定文本输入框的监听
        initTextViewWatch();
    }

    private void initTextViewWatch() {
        mLpName.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setLpName(value);
        }));
        mLpIDCardNo.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setLpIDCardNo(value);
        }));
        mLpPhone.addTextChangedListener(generateWatcher(value -> {
            mView.getWalletInfo().setLpPhone(value);
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
