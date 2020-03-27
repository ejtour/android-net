package com.hll_sc_app.app.wallet.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.wallet.bank.BankListActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.event.RefreshWalletStatus;
import com.hll_sc_app.bean.wallet.OcrImageResp;
import com.hll_sc_app.bean.wallet.WalletInfo;
import com.hll_sc_app.citymall.util.CommonUtils;

import com.hll_sc_app.widget.NoScrollViewPager;
import com.hll_sc_app.widget.TitleBar;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 实名认证 主页面
 *
 * @author zc
 */
@Route(path = RouterConfig.ACTIVITY_WALLET_AUTHEN_ACCOUNT, extras = Constant.LOGIN_EXTRA)
public class AuthenticationActivity extends BaseLoadActivity implements IAuthenticationContract.IView {
    @BindView(R.id.header_bar)
    TitleBar mHeaderBar;
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.txt_next)
    TextView mNext;
    @BindView(R.id.txt_pre)
    TextView mPre;
    @BindView(R.id.img_step)
    ImageView mImgStep;
    @BindView(R.id.ll_button_bottom)
    LinearLayout mLlButton;
    private Unbinder unbinder;
    private WalletInfo mWalletInfo;

    private PagerAdapter mPagerAdapter;
   /* @Autowired(name = "parcelable",required = true)
    WalletInfo mWalletStatusResp;*/
    private IAuthenticationContract.IPresent mPresent;

    private ArrayList<IAuthenticationContract.IFragment> mFragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_authentication);
        StatusBarCompat.setStatusBarColor(this, 0xFFFFFFFF);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        mPresent = AuthenticationPresent.newInstance();
        mPresent.register(this);
        mPresent.getWalletInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mViewPager.getLayoutParams();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    mHeaderBar.setHeaderTitle("商户类型");
                    mImgStep.setVisibility(View.GONE);
                    mLlButton.setVisibility(View.GONE);
                    mNext.setText("下一步");
                    layoutParams.topMargin = 0;
                } else if (position == 1) {
                    mHeaderBar.setHeaderTitle("实名认证");
                    mImgStep.setVisibility(View.VISIBLE);
                    mImgStep.setImageResource(R.drawable.ic_wallet_title_step_1);
                    mLlButton.setVisibility(View.VISIBLE);
                    mNext.setText("下一步");
                    layoutParams.topMargin = UIUtils.dip2px(70);
                } else if (position == 2) {
                    mHeaderBar.setHeaderTitle("实名认证");
                    mImgStep.setVisibility(View.VISIBLE);
                    mLlButton.setVisibility(View.VISIBLE);
                    mImgStep.setImageResource(R.drawable.ic_wallet_title_step_2);
                    mNext.setText("下一步");
                    layoutParams.topMargin = UIUtils.dip2px(70);
                } else if (position == 3) {
                    mHeaderBar.setHeaderTitle("实名认证");
                    mImgStep.setVisibility(View.VISIBLE);
                    mLlButton.setVisibility(View.VISIBLE);
                    mImgStep.setImageResource(R.drawable.ic_wallet_title_step_3);
                    mNext.setText("下一步");
                    layoutParams.topMargin = UIUtils.dip2px(70);
                } else if (position == 4) {
                    mHeaderBar.setHeaderTitle("实名认证");
                    mImgStep.setVisibility(View.VISIBLE);
                    mLlButton.setVisibility(View.VISIBLE);
                    mNext.setText("下一步");
                    mImgStep.setImageResource(R.drawable.ic_wallet_title_step_4);
                    layoutParams.topMargin = UIUtils.dip2px(70);
                } else if (position == 5) {
                    mHeaderBar.setHeaderTitle("实名认证");
                    mImgStep.setVisibility(View.VISIBLE);
                    mLlButton.setVisibility(View.VISIBLE);
                    mImgStep.setImageResource(R.drawable.ic_wallet_title_step_5);
                    mNext.setText("提交");
                    layoutParams.topMargin = UIUtils.dip2px(70);
                } else if (position == 6) {
                    mHeaderBar.setHeaderTitle("提交成功");
                    mImgStep.setVisibility(View.GONE);
                    mLlButton.setVisibility(View.GONE);
                    layoutParams.topMargin = 0;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mHeaderBar.setLeftBtnClick(v -> {
            if (mViewPager.getCurrentItem() == 6) {
                return;
            }
            goBack();
        });

        mNext.setOnClickListener(v -> {
            int index = mViewPager.getCurrentItem();
            if (index == mPagerAdapter.getCount() - 2) {
                mPresent.setWalletInfo();
            } else if (index < mPagerAdapter.getCount() - 2) {
                mViewPager.setCurrentItem(index + 1);
            } else {
                ARouter.getInstance()
                        .build(RouterConfig.WALLET)
                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        .setProvider(new LoginInterceptor())
                        .navigation();
            }
        });

        mPre.setOnClickListener(v -> {
            int index = mViewPager.getCurrentItem();
            if (index > 0) {
                mViewPager.setCurrentItem(index - 1);
            }
        });

    }

    @Override
    public void getWalletInfoSuccess(WalletInfo walletInfo) {
        mWalletInfo = walletInfo;
        initView();
    }

    private void goBack() {
        int index = mViewPager.getCurrentItem();
        if (index == 6) {
            return;
        }
        if (index != 0) {
            mViewPager.setCurrentItem(index - 1);
        } else {
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle("确定要离开吗")
                .setMessage("离开后将不能保存您当前所做的更改")
                .setCancelable(false)
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    if (item == 0) {
                        finish();
                    }
                }, "去意已决", "暂不离开")
                .create()
                .show();
    }

    @Override
    public WalletInfo getWalletInfo() {
        if (mWalletInfo == null) {
            mWalletInfo = new WalletInfo();
        }
        return mWalletInfo;
    }

    @Override
    public void setWalletInfoSuccess() {
        EventBus.getDefault().post(new RefreshWalletStatus(false));
        //显示成功页面
        int index = mViewPager.getCurrentItem();
        mViewPager.setCurrentItem(index + 1);
    }

    @Override
    public void setSubmitButtonEnable(boolean enable) {
        mNext.setEnabled(enable);
//        mNext.setEnabled(true);
    }

    @Override
    public void uploadImgSuccess(String url) {
        int index = mViewPager.getCurrentItem();
        ((IAuthenticationContract.IUploadFragment) mFragmentList.get(index)).setUploadUrl(url);
    }

    @Override
    public void goToNextStep() {
        mNext.performClick();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case ImgUploadBlock.REQUEST_CODE_CHOOSE:
                    List<String> list = Matisse.obtainPathResult(data);
                    if (!CommonUtils.isEmpty(list)) {
                        mPresent.imageUpload(new File(list.get(0)));
                    }
                    break;
                case BankListActivity.REQ_CODE:
                    ((IAuthenticationContract.ISettleInfoFragment) mFragmentList.get(5)).updateBank(data.getParcelableExtra(BankListActivity.BANK_KEY));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void ocrImage(int lpCardType, String imageUrl) {
        mPresent.ocrImage(lpCardType, imageUrl);
    }

    @Override
    public void orcImageSuccess(OcrImageResp resp) {
        ((IAuthenticationContract.IOcrFragment) mFragmentList.get(mViewPager.getCurrentItem())).showOcrInfo(resp);
    }

    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentList = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            IAuthenticationContract.IFragment fragment;
            if (position == 0) {
                fragment = new BusinessTypeFragment();
            } else if (position == 1) {
                fragment = new BaseInfoFragment();
            } else if (position == 2) {
                fragment = new PersonInfoFragment();
            } else if (position == 3) {
                fragment = new LinkInfoFragment();
            } else if (position == 4) {
                fragment = new OperateInfoFragment();
            } else if (position == 5) {
                fragment = new SettleInfoFragment();
            } else {
                fragment = new SuccessFragment();
            }
            fragment.registerView(AuthenticationActivity.this);
            mFragmentList.add(fragment);
            return (Fragment) fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //no-op
        }

        @Override
        public int getCount() {
            return 7;
        }
    }
}
