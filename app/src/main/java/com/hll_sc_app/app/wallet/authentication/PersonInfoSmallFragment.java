package com.hll_sc_app.app.wallet.authentication;

import static com.hll_sc_app.app.wallet.authentication.CommonMethod.PERMANENT_DATE;
import static com.hll_sc_app.app.wallet.authentication.CommonMethod.setUploadImg;
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
public class PersonInfoSmallFragment extends BaseLazyFragment implements IAuthenticationContract.IFragment,
        IAuthenticationContract.IUploadFragment, IAuthenticationContract.IOcrFragment {

    @BindView(R.id.txt_legal_person)
    TextView mLegalPerson;
    @BindView(R.id.edt_lpName)
    EditText mLpName;
    @BindView(R.id.edt_lpIDCardNo)
    EditText mLpIDCardNo;
    @BindView(R.id.edt_lpAddress)
    EditText mLpAddress;
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

    private SingleSelectionDialog<NameValue> mLegalPersonDialog;
    @BindViews({R.id.txt_title_legal_person, R.id.txt_legal_person, R.id.border})
    List<View> mBenefitViews;

    @BindViews({R.id.txt_title_lpName, R.id.txt_title_lpAddress})
    List<TextView> mTitleList;

    @BindViews({R.id.edt_lpName, R.id.edt_lpIDCardNo,
            R.id.txt_lp_begin_date, R.id.txt_lp_end_date, R.id.edt_lpAddress,
    })
    List<TextView> mHintList;

    public static PersonInfoSmallFragment newInstance(boolean lpPage) {
        Bundle args = new Bundle();
        args.putBoolean("lpPage", lpPage);
        PersonInfoSmallFragment fragment = new PersonInfoSmallFragment();
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
        WalletInfo walletInfo = mView.getWalletInfo();
        boolean lpPage = isLpPage();
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
            mCurrentImgShowDelBlock = imgUploadBlock;
            return true;
        });
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
            if (mCurrentImgShowDelBlock == mImgCardFront) {
                if (lpPage) {
                    walletInfo.setImgLPIDCardFront(url);
                } else {
                    walletInfo.setImgBeneficiaryPicFront(url);
                }
                mView.ocrImage(1, url);
            } else if (mCurrentImgShowDelBlock == mImgCardBack) {
                if (lpPage) {
                    walletInfo.setImgLPIDCardBack(url);
                } else {
                    walletInfo.setImgBeneficiaryPicBack(url);
                }
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
        if (isLpPage()) {
            return !TextUtils.isEmpty(walletInfo.getLpName())
                    && !TextUtils.isEmpty(walletInfo.getLpIDCardNo())
                    && !TextUtils.isEmpty(walletInfo.getLpIDCardPeriodBeginDate())
                    && !TextUtils.equals("0", walletInfo.getLpIDCardPeriodBeginDate())
                    && !TextUtils.isEmpty(walletInfo.getLpIDCardPeriod())
                    && !TextUtils.equals("0", walletInfo.getLpIDCardPeriod())
                    && !TextUtils.isEmpty(walletInfo.getImgLPIDCardFront())
                    && !TextUtils.isEmpty(walletInfo.getImgLPIDCardBack())
                    && !TextUtils.isEmpty(walletInfo.getLpAddress());
        } else {
            return !TextUtils.isEmpty(walletInfo.getBeneficiaryName())
                    && !TextUtils.isEmpty(walletInfo.getBeneficiaryCertNo())
                    && !TextUtils.isEmpty(walletInfo.getBeneficiaryCertBeginDate())
                    && !TextUtils.equals("0", walletInfo.getBeneficiaryCertBeginDate())
                    && !TextUtils.isEmpty(walletInfo.getBeneficiaryCertEndDate())
                    && !TextUtils.equals("0", walletInfo.getBeneficiaryCertEndDate())
                    && !TextUtils.isEmpty(walletInfo.getImgBeneficiaryPicFront())
                    && !TextUtils.isEmpty(walletInfo.getImgBeneficiaryPicBack())
                    && !TextUtils.isEmpty(walletInfo.getBeneficiaryAddress());
        }
    }

    @OnClick({R.id.txt_lp_begin_date, R.id.txt_lp_end_date, R.id.txt_legal_person,})
    public void onClick(View view) {
        WalletInfo walletInfo = mView.getWalletInfo();
        boolean lpPage = isLpPage();
        switch (view.getId()) {
            case R.id.txt_legal_person:
                if (mLegalPersonDialog == null) {
                    List<NameValue> list = new ArrayList<>();
                    list.add(new NameValue("是", "1"));
                    list.add(new NameValue("否", "0"));
                    mLegalPersonDialog = SingleSelectionDialog.newBuilder((Activity) mView, NameValue::getName)
                            .refreshList(list)
                            .setTitleText(((TextView) view).getHint())
                            .select(list.get(walletInfo.getBeneficiaryIsLp() == 0 ? 1 : 0))
                            .setOnSelectListener(nameValue -> {
                                byte isLp = Byte.parseByte(nameValue.getValue());
                                mLegalPerson.setText(nameValue.getName());
                                walletInfo.setBeneficiaryIsLp(isLp);
                                if (isLp == 1) {
                                    updateViews();
                                }
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
//            case R.id.txt_title_img_front_demo:
//                new DemoImgWindow(getActivity()).showDemo(DemoImgWindow.TYPE_LICENSE);
//                break;
//            case R.id.txt_title_img_back_demo:
//                new DemoImgWindow(getActivity()).showDemo(DemoImgWindow.TYPE_LICENSE);
//                break;
            default:
                break;
        }
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
            walletInfo.setReceiverName(walletInfo.getLpName());
            mLpIDCardNo.setText(walletInfo.getLpIDCardNo());
            mLpBeginDate.setText(transformDate(walletInfo.getLpIDCardPeriodBeginDate()));
            mLpEndDate.setText(transformDate(walletInfo.getLpIDCardPeriod()));
            mLpAddress.setText(walletInfo.getLpAddress());
            mImgCardFront.showImage(walletInfo.getImgLPIDCardFront());
            mImgCardBack.showImage(walletInfo.getImgLPIDCardBack());
        } else {
            ViewCollections.run(mBenefitViews, (view, index) -> view.setVisibility(View.VISIBLE));
            boolean isLp = walletInfo.getBeneficiaryIsLp() == 1;
            mLegalPerson.setText(isLp ? "是" : "否");
            mLpName.setText(walletInfo.getBeneficiaryName());
            mLpIDCardNo.setText(walletInfo.getBeneficiaryCertNo());
            mLpBeginDate.setText(transformDate(walletInfo.getBeneficiaryCertBeginDate()));
            mLpEndDate.setText(transformDate(walletInfo.getBeneficiaryCertEndDate()));
            mLpAddress.setText(walletInfo.getBeneficiaryAddress());
            mImgCardFront.showImage(walletInfo.getImgBeneficiaryPicFront());
            mImgCardBack.showImage(walletInfo.getImgBeneficiaryPicBack());
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
        rootView = inflater.inflate(R.layout.fragment_wallet_small_step_2, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
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
                walletInfo.setReceiverName(value);
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
