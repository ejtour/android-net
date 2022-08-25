package com.hll_sc_app.app.wallet.authentication;

import static com.hll_sc_app.app.wallet.authentication.CommonMethod.PERMANENT_DATE;
import static com.hll_sc_app.app.wallet.authentication.CommonMethod.setUploadImg;
import static com.hll_sc_app.app.wallet.authentication.CommonMethod.transformCardType;
import static com.hll_sc_app.app.wallet.authentication.CommonMethod.transformDate;
import static com.hll_sc_app.app.wallet.authentication.CommonMethod.updateTipByLp;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.wallet.OcrImageResp;
import com.hll_sc_app.bean.wallet.WalletInfo;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import butterknife.ViewCollections;

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
    @BindView(R.id.txt_legal_person)
    TextView mLegalPerson;
    @BindView(R.id.edt_lpIDCardNo)
    EditText mLpIDCardNo;
    @BindView(R.id.txt_lp_begin_date)
    TextView mLpBeginDate;
    @BindView(R.id.txt_lp_end_date)
    TextView mLpEndDate;
    @BindView(R.id.edt_lpPhone)
    EditText mLpPhone;
    @BindView(R.id.edt_lpAddress)
    EditText mLpAddress;
    @BindView(R.id.img_card_front)
    ImgUploadBlock mImgCardFront;
    @BindView(R.id.img_card_back)
    ImgUploadBlock mImgCardBack;
    @BindViews({R.id.txt_title_legal_person, R.id.txt_legal_person, R.id.border})
    List<View> mBenefitViews;

    @BindViews({R.id.txt_title_lpCardType, R.id.txt_title_lpAddress,
            R.id.txt_title_img_front, R.id.txt_title_img_back,
            R.id.txt_title_lpName, R.id.txt_title_lpPhone,
    })
    List<TextView> mTitleList;

    @BindViews({
            R.id.txt_lpCardType, R.id.edt_lpName,
            R.id.edt_lpIDCardNo, R.id.txt_lp_begin_date, R.id.txt_lp_end_date,
            R.id.edt_lpPhone, R.id.edt_lpAddress,
    })
    List<TextView> mHintList;

    private Unbinder unbinder;
    private IAuthenticationContract.IView mView;
    private ImgUploadBlock mCurrentImgShowDelBlock;
    private boolean isStartDate;

    private SingleSelectionDialog<NameValue> mLegalPersonDialog;

    public static PersonInfoFragment newInstance(boolean lpPage) {
        Bundle args = new Bundle();
        args.putBoolean("lpPage", lpPage);
        PersonInfoFragment fragment = new PersonInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean isLpPage() {
        boolean lpPage = true;
        Bundle bundle = getArguments();
        if (bundle != null) {
            lpPage = bundle.getBoolean("lpPage");
        }
        return lpPage;
    }

    /**
     * 设置上传组件的公共配置
     *
     * @param title
     */
    private void initUpload(ImgUploadBlock imgUploadBlock, String title) {
        boolean lpPage = isLpPage();
        WalletInfo walletInfo = mView.getWalletInfo();
        setUploadImg(imgUploadBlock, title, v -> {
            imgUploadBlock.showImage("");
            if (imgUploadBlock == mImgCardFront) {
                if (lpPage) {
                    walletInfo.setImgLPIDCardFront("");
                } else {
                    walletInfo.setImgBeneficiaryPicFront("");
                }
            } else if (imgUploadBlock == mImgCardBack) {
                if (lpPage) {
                    walletInfo.setImgLPIDCardBack("");
                } else {
                    walletInfo.setImgBeneficiaryPicBack("");
                }
            }
            isSubmit();
        }, uploadImgBlock -> {
            if (getCardType() == -1) {
                showToast(mLpCardType.getHint().toString());
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
        WalletInfo walletInfo = mView.getWalletInfo();
        boolean lpPage = isLpPage();
        if (mCurrentImgShowDelBlock == mImgCardFront) {
            OcrImageResp.ImgLPIDCardFrontBean frontBean = JsonUtil.fromJson(resp.getImgLPIDCardFront(), OcrImageResp.ImgLPIDCardFrontBean.class);
            mLpName.setText(frontBean.getName());
            mLpIDCardNo.setText(frontBean.getNum());
            mLpAddress.setText(frontBean.getAddress());
            if (lpPage) {
                walletInfo.setLpName(frontBean.getName());
                walletInfo.setLpIDCardNo(frontBean.getNum());
                walletInfo.setLpAddress(frontBean.getAddress());
            } else {
                walletInfo.setBeneficiaryName(frontBean.getName());
                walletInfo.setBeneficiaryCertNo(frontBean.getNum());
                walletInfo.setBeneficiaryAddress(frontBean.getAddress());
            }
        } else if (mCurrentImgShowDelBlock == mImgCardBack) {
            OcrImageResp.ImgLPIDCardBackBean backBean = JsonUtil.fromJson(resp.getImgLPIDCardBack(), OcrImageResp.ImgLPIDCardBackBean.class);
            mLpBeginDate.setText(transformDate(backBean.getStart_date()));
            mLpEndDate.setText(transformDate(backBean.getEnd_date()));
            if (lpPage) {
                walletInfo.setLpIDCardPeriodBeginDate(backBean.getStart_date());
                walletInfo.setLpIDCardPeriod(backBean.getEnd_date());
            } else {
                walletInfo.setBeneficiaryCertBeginDate(backBean.getStart_date());
                walletInfo.setBeneficiaryCertEndDate(backBean.getEnd_date());
            }
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
        boolean lpPage = isLpPage();
        if (mCurrentImgShowDelBlock != null) {
            mCurrentImgShowDelBlock.showImage(url);
            int cardType = getCardType();
            if (mCurrentImgShowDelBlock == mImgCardFront) {
                if (lpPage) {
                    walletInfo.setImgLPIDCardFront(url);
                } else {
                    walletInfo.setImgBeneficiaryPicFront(url);
                }

                if (cardType == 0) {//身份证
                    mView.ocrImage(1, url);
                }
            } else if (mCurrentImgShowDelBlock == mImgCardBack) {
                if (lpPage) {
                    walletInfo.setImgLPIDCardBack(url);
                } else {
                    walletInfo.setImgBeneficiaryPicBack(url);
                }
                if (cardType == 0) {//身份证
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
        if (isLpPage()) {
            return !TextUtils.isEmpty(walletInfo.getLpName())
                    && walletInfo.getLpCardType() != -1
                    && !TextUtils.isEmpty(walletInfo.getLpIDCardNo())
                    && !TextUtils.isEmpty(walletInfo.getLpIDCardPeriodBeginDate())
                    && !TextUtils.equals("0", walletInfo.getLpIDCardPeriodBeginDate())
                    && !TextUtils.isEmpty(walletInfo.getLpIDCardPeriod())
                    && !TextUtils.equals("0", walletInfo.getLpIDCardPeriod())
                    && !TextUtils.isEmpty(walletInfo.getLpPhone())
                    && !TextUtils.isEmpty(walletInfo.getImgLPIDCardFront())
                    && !TextUtils.isEmpty(walletInfo.getImgLPIDCardBack())
                    && !TextUtils.isEmpty(walletInfo.getLpAddress());
        } else {
            return !TextUtils.isEmpty(walletInfo.getBeneficiaryName())
                    && walletInfo.getBeneficiaryCertType() != -1
                    && !TextUtils.isEmpty(walletInfo.getBeneficiaryCertNo())
                    && !TextUtils.isEmpty(walletInfo.getBeneficiaryCertBeginDate())
                    && !TextUtils.equals("0", walletInfo.getBeneficiaryCertBeginDate())
                    && !TextUtils.isEmpty(walletInfo.getBeneficiaryCertEndDate())
                    && !TextUtils.equals("0", walletInfo.getBeneficiaryCertEndDate())
                    && !TextUtils.isEmpty(walletInfo.getBeneficiaryPhone())
                    && !TextUtils.isEmpty(walletInfo.getImgBeneficiaryPicFront())
                    && !TextUtils.isEmpty(walletInfo.getImgBeneficiaryPicBack())
                    && !TextUtils.isEmpty(walletInfo.getBeneficiaryAddress());
        }
    }

    private int getCardType() {
        WalletInfo walletInfo = mView.getWalletInfo();
        if (isLpPage()) {
            return walletInfo.getLpCardType();
        } else {
            return walletInfo.getBeneficiaryCertType();
        }
    }

    @OnClick({R.id.txt_lpCardType, R.id.txt_legal_person, R.id.txt_lp_begin_date, R.id.txt_lp_end_date, R.id.txt_title_img_front_demo, R.id.txt_title_img_back_demo})
    public void onClick(View view) {
        WalletInfo walletInfo = mView.getWalletInfo();
        boolean lpPage = isLpPage();
        switch (view.getId()) {
            case R.id.txt_lpCardType:
                List<NameValue> values = generateCardTypeBean();
                int selectIndex = getSelectIndex(getCardType());
                SingleSelectionDialog.newBuilder((Activity) mView, NameValue::getName)
                        .refreshList(values)
                        .setTitleText(((TextView) view).getHint())
                        .select(selectIndex > -1 ? values.get(selectIndex) : null)
                        .setOnSelectListener(NameValue -> {
                            mLpCardType.setText(NameValue.getName());
                            if (lpPage) {
                                walletInfo.setLpCardType(Integer.parseInt(NameValue.getValue()));
                            } else {
                                walletInfo.setBeneficiaryCertType(Integer.parseInt(NameValue.getValue()));
                            }
                            isSubmit();
                        })
                        .create().show();
                break;
            case R.id.txt_legal_person:
                if (mLegalPersonDialog == null) {
                    List<NameValue> list = new ArrayList<>();
                    list.add(new NameValue("是", "1"));
                    list.add(new NameValue("否", "0"));
                    mLegalPersonDialog = SingleSelectionDialog.newBuilder((Activity) mView, NameValue::getName)
                            .refreshList(list)
                            .select(list.get(walletInfo.getBeneficiaryIsLp() == 0 ? 1 : 0))
                            .setTitleText(((TextView) view).getHint())
                            .setOnSelectListener(nameValue -> {
                                byte type = Byte.parseByte(nameValue.getValue());
                                mLegalPerson.setText(nameValue.getName());
                                walletInfo.setBeneficiaryIsLp(type);
                                updateViews();
                            })
                            .create();
                }
                mLegalPersonDialog.show();
                break;
            case R.id.txt_lp_begin_date:
                CommonMethod.showLongValidDateDialog(getActivity(), (dialog, index) -> {
                    dialog.dismiss();
                    if (lpPage) {
                        walletInfo.setLpIDCardPeriod("");
                    } else {
                        walletInfo.setBeneficiaryCertEndDate("");
                    }
                    mLpEndDate.setText("");
                    if (index == 0) {
                        if (lpPage) {
                            walletInfo.setLpIDCardPeriodBeginDate(PERMANENT_DATE);
                            walletInfo.setLpIDCardPeriod(PERMANENT_DATE);
                        } else {
                            walletInfo.setBeneficiaryCertBeginDate(PERMANENT_DATE);
                            walletInfo.setBeneficiaryCertEndDate(PERMANENT_DATE);
                        }
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
                        if (lpPage) {
                            walletInfo.setLpIDCardPeriod(PERMANENT_DATE);
                        } else {
                            walletInfo.setBeneficiaryCertEndDate(PERMANENT_DATE);
                        }
                        mLpEndDate.setText("长期有效");
                        isSubmit();
                    } else {
                        isStartDate = false;
                        showDateWindow(false);
                    }
                });
                break;
            case R.id.txt_title_img_front_demo:
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
        boolean lpPage = isLpPage();
        String begin;
        String end;
        if (lpPage) {
            begin = walletInfo.getLpIDCardPeriodBeginDate();
        } else {
            begin = walletInfo.getBeneficiaryCertBeginDate();
        }
        if (lpPage) {
            end = walletInfo.getLpIDCardPeriod();
        } else {
            end = walletInfo.getBeneficiaryCertEndDate();
        }
        CommonMethod.showDate((BaseLoadActivity) mView, isStart, begin, end, (sDate, oDate) -> {
            if (isStartDate) {
                mLpBeginDate.setText(oDate);
                if (lpPage) {
                    walletInfo.setLpIDCardPeriodBeginDate(sDate);
                } else {
                    walletInfo.setBeneficiaryCertBeginDate(sDate);
                }
            } else {
                mLpEndDate.setText(oDate);
                if (lpPage) {
                    walletInfo.setLpIDCardPeriod(sDate);
                } else {
                    walletInfo.setBeneficiaryCertEndDate(sDate);
                }
            }
            isSubmit();
        });
    }


    @Override
    protected void onVisible() {
        super.onVisible();
        updateViews();
    }

    private void updateViews() {
        boolean lpPage = isLpPage();
        //初始化显示页面
        WalletInfo walletInfo = mView.getWalletInfo();
        updateTipByLp(lpPage, mTitleList, mHintList);
        if (lpPage) {
            mLpName.setText(walletInfo.getLpName());
            mLpCardType.setText(transformCardType(walletInfo.getLpCardType()));
            mLpIDCardNo.setText(walletInfo.getLpIDCardNo());
            mLpBeginDate.setText(transformDate(walletInfo.getLpIDCardPeriodBeginDate()));
            mLpEndDate.setText(transformDate(walletInfo.getLpIDCardPeriod()));
            mLpPhone.setText(walletInfo.getLpPhone());
            mImgCardFront.showImage(walletInfo.getImgLPIDCardFront());
            mImgCardBack.showImage(walletInfo.getImgLPIDCardBack());
            mLpAddress.setText(walletInfo.getLpAddress());
        } else {
            ViewCollections.run(mBenefitViews, (view, index) -> view.setVisibility(View.VISIBLE));
            boolean isLp = walletInfo.getBeneficiaryIsLp() == 1;
            mLegalPerson.setText(isLp ? "是" : "否");
            mLpName.setText(walletInfo.getBeneficiaryName());
            mLpCardType.setText(transformCardType(walletInfo.getBeneficiaryCertType()));
            mLpIDCardNo.setText(walletInfo.getBeneficiaryCertNo());
            mLpBeginDate.setText(transformDate(walletInfo.getBeneficiaryCertBeginDate()));
            mLpEndDate.setText(transformDate(walletInfo.getBeneficiaryCertEndDate()));
            mLpPhone.setText(walletInfo.getBeneficiaryPhone());
            mImgCardFront.showImage(walletInfo.getImgBeneficiaryPicFront());
            mImgCardBack.showImage(walletInfo.getImgBeneficiaryPicBack());
            mLpAddress.setText(walletInfo.getBeneficiaryAddress());
            mLpName.setEnabled(!isLp);
            mLpCardType.setEnabled(!isLp);
            mLpCardType.setCompoundDrawablesWithIntrinsicBounds(0, 0, isLp ? 0 : R.drawable.ic_arrow_gray, 0);
            mLpIDCardNo.setEnabled(!isLp);
            mLpBeginDate.setEnabled(!isLp);
            mLpBeginDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, isLp ? 0 : R.drawable.ic_arrow_gray, 0);
            mLpEndDate.setEnabled(!isLp);
            mLpEndDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, isLp ? 0 : R.drawable.ic_arrow_gray, 0);
            mLpPhone.setEnabled(!isLp);
            mImgCardFront.setEditable(!isLp);
            mImgCardBack.setEditable(!isLp);
            mLpAddress.setEnabled(!isLp);
        }
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
        //绑定文本输入框的监听
        initTextViewWatch();
    }

    private void initTextViewWatch() {
        WalletInfo walletInfo = mView.getWalletInfo();
        boolean lpPage = isLpPage();
        mLpName.addTextChangedListener(generateWatcher(value -> {
            if (lpPage) {
                walletInfo.setLpName(value);
            } else {
                walletInfo.setBeneficiaryName(value);
            }
        }));
        mLpIDCardNo.addTextChangedListener(generateWatcher(value -> {
            if (lpPage) {
                walletInfo.setLpIDCardNo(value);
            } else {
                walletInfo.setBeneficiaryCertNo(value);
            }
        }));
        mLpPhone.addTextChangedListener(generateWatcher(value -> {
            if (lpPage) {
                walletInfo.setLpPhone(value);
            } else {
                walletInfo.setBeneficiaryPhone(value);
            }
        }));
        mLpAddress.addTextChangedListener(generateWatcher(value -> {
            if (lpPage) {
                walletInfo.setLpAddress(value);
            } else {
                walletInfo.setBeneficiaryAddress(value);
            }
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
