package com.hll_sc_app.app.complainmanage.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.hll_sc_app.R;
import com.hll_sc_app.app.complainmanage.add.ComplainMangeAddActivity;
import com.hll_sc_app.app.complainmanage.history.ComplainHistorylActivity;
import com.hll_sc_app.app.complainmanage.innerlog.InnerLoglActivity;
import com.hll_sc_app.app.complainmanage.sendcomplainreply.SendComplainReplyActivity;
import com.hll_sc_app.app.order.details.OrderDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.complain.ComplainDetailResp;
import com.hll_sc_app.bean.complain.ComplainStatusResp;
import com.hll_sc_app.bean.event.ComplainManageEvent;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hll_sc_app.bean.window.OptionType.OPTION_PHONE_LINK;
import static com.hll_sc_app.citymall.util.CalendarUtils.FORMAT_HH_MM_SS;

/**
 * 投诉详情
 */
@Route(path = RouterConfig.ACTIVITY_COMPLAIN_DETAIL)
public class ComplainMangeDetailActivity extends BaseLoadActivity implements IComplainMangeDetailContract.IView {
    private Unbinder unbinder;
    @Autowired(name = "object0")
    String mCompaintId;
    @BindView(R.id.txt_status)
    TextView mTxtStatus;
    @BindView(R.id.txt_status_time)
    TextView mTxtStatusTime;
    @BindView(R.id.txt_my_reply_platform)
    TextView mTxtMyReplyPlatform;
    @BindView(R.id.txt_plat_reply)
    TextView mTxtPlatReply;
    @BindView(R.id.txt_my_reply)
    TextView mTxtMyReply;
    @BindView(R.id.group_my_reply_platform)
    Group mGroupMyReplyPlatform;
    @BindView(R.id.group_plat_reply)
    Group mGroupPlatReply;
    @BindView(R.id.group_my_reply)
    Group mGroupMyReply;
    @BindView(R.id.txt_group)
    TextView mTxtGroup;
    @BindView(R.id.txt_shop)
    TextView mTxtShop;
    @BindView(R.id.txt_type)
    TextView mTxtComplainType;
    @BindView(R.id.txt_reason)
    TextView mTxtReason;
    @BindView(R.id.txt_explain)
    TextView mTxtExplain;
    @BindView(R.id.ll_croll_photo)
    LinearLayout mScrollPhoto;
    @BindView(R.id.recyclerView)
    RecyclerView mProductRecyclerView;
    @BindView(R.id.txt_order_code)
    TextView mTxtOrderCode;
    @BindView(R.id.txt_title_history)
    TextView mTxtHistory;
    @BindView(R.id.txt_title_inject)
    TextView mTxtInject;
    @BindView(R.id.txt_complain_person)
    TextView mTxtPerson;
    @BindView(R.id.txt_title_complain_phone)
    TextView mTxtPhone;
    @BindView(R.id.txt_driver)
    TextView mTxtDriver;
    @BindView(R.id.txt_path)
    TextView mTxtPath;
    @BindView(R.id.txt_link)
    TextView mBtnLink;
    @BindView(R.id.txt_btn_log)
    TextView mBtnLog;
    @BindView(R.id.txt_btn_reply)
    TextView mBtnReply;
    @BindView(R.id.group_order_code)
    Group mGroupOrderCode;
    @BindView(R.id.group_complain_info)
    Group mGroupComplainInfo;
    @BindView(R.id.group_product)
    Group mGroupProduct;
    @BindView(R.id.title_bar)
    TitleBar mTitle;


    private ComplainStatusResp mComplainStatusResp;
    private ComplainDetailResp mComplainDetailResp;

    private IComplainMangeDetailContract.IPresent mPresent;

    private Map<Integer, String[]> statusTip = new ArrayMap<>();
    private Map<Integer, String[]> platformStatusTip = new ArrayMap<>();

    private ContextOptionsWindow mBottomMenuWindow;
    public static void start(String compaintId) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_COMPLAIN_DETAIL, compaintId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getStaticData();
        setContentView(R.layout.activity_complain_manage_detail);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        EventBus.getDefault().register(this);
        mPresent = ComplainMangeDetailPresent.newInstance();
        mPresent.register(this);
        mPresent.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void getStaticData() {
        statusTip.put(1, TextUtils.split("采购商已发起投诉，请尽快处理", "\\|"));
        statusTip.put(2, TextUtils.split("||您已回复该投诉，请等待采购商结束投诉|平台客服已回复,请等待采购商结束投诉", "\\|"));
        statusTip.put(3, TextUtils.split("采购商已结束本次投诉", "\\|"));
        statusTip.put(5, TextUtils.split("|采购商已申请平台介入，请等待平台客服回复|您已申请平台介入，请等待回复", "\\|"));
        statusTip.put(6, TextUtils.split("采购商已继续投诉，请尽快处理", "\\|"));

        platformStatusTip.put(1, TextUtils.split("平台已收到您的投诉，请等待客服回复～", "\\|"));
        platformStatusTip.put(2, TextUtils.split("||您已回复平台客服|平台客服已回复您的投诉，请注意查看", "\\|"));
        platformStatusTip.put(3, TextUtils.split("您已结束本次投诉", "\\|"));
        platformStatusTip.put(4, TextUtils.split("您已撤销本次投诉", "\\|"));
    }

    @Override
    public String getComplaintID() {
        return mCompaintId;
    }

    @Override
    public void showComplainStatus(ComplainStatusResp complainStatusResp) {
        mComplainStatusResp = complainStatusResp;
        initView();
    }

    @Override
    public void showComplainDetail(ComplainDetailResp complainDetailResp) {
        mComplainDetailResp = complainDetailResp;
        initView();

    }

    private void initView() {
        if (mComplainDetailResp == null || mComplainStatusResp == null) {
            return;
        }
        if (mComplainStatusResp.getSource() == 2 && mComplainStatusResp.getStatus() == 1) {
            mTitle.setRightText("编辑");
            mTitle.setRightBtnClick(v -> {
                ComplainMangeAddActivity.start(mComplainDetailResp);
            });
        }
        showTitleStatus();
        showReplyComplainToPlat();
        showPlatReply();
        showReplyComplainToSupplyer();
        showCompainDetail();
        showProducts();
        showOrder();
        showContactAndPhone();
        showDriverInfo();
        showButton();
    }

    private void showTitleStatus() {
        mTxtStatus.setText(getStatusText());
        mTxtStatusTime.setText(CalendarUtils.getDateFormatString(mComplainStatusResp.getCreateTime(), FORMAT_HH_MM_SS, "yyyy/MM/dd HH:mm"));
    }

    /**
     * 向平台投诉时我的回复在平台回复之前
     */
    private void showReplyComplainToPlat() {
        if (mComplainDetailResp.getTarget() != 3) {
            mGroupMyReplyPlatform.setVisibility(View.GONE);
            return;
        }
        for (int i = 0; i < mComplainStatusResp.getReplyList().size(); i++) {
            if (mComplainStatusResp.getReplyList().get(i).getSource() == 2) {
                mGroupMyReplyPlatform.setVisibility(View.VISIBLE);
                mTxtMyReplyPlatform.setText(mComplainStatusResp.getReplyList().get(i).getReply());
                return;
            }
        }

        mGroupMyReplyPlatform.setVisibility(View.GONE);

    }

    /*平台回复*/
    private void showPlatReply() {
        for (int i = 0; i < mComplainStatusResp.getReplyList().size(); i++) {
            if (mComplainStatusResp.getReplyList().get(i).getSource() == 3) {
                mGroupPlatReply.setVisibility(View.VISIBLE);
                mTxtPlatReply.setText(mComplainStatusResp.getReplyList().get(i).getReply());
                return;
            }
        }
        mGroupPlatReply.setVisibility(View.GONE);
    }

    /**
     * 向供应商投诉时我的回复在平台回复之后
     */
    private void showReplyComplainToSupplyer() {
        if (mComplainDetailResp.getTarget() != 2) {
            mGroupMyReply.setVisibility(View.GONE);
            return;
        }
        for (int i = 0; i < mComplainStatusResp.getReplyList().size(); i++) {
            if (mComplainStatusResp.getReplyList().get(i).getSource() == 2) {
                mGroupMyReply.setVisibility(View.VISIBLE);
                mTxtMyReply.setText(mComplainStatusResp.getReplyList().get(i).getReply());
                return;
            }
        }

        mGroupMyReply.setVisibility(View.GONE);

    }

    /**
     * 展示投诉详情
     */
    private void showCompainDetail() {
        mTxtGroup.setText(mComplainDetailResp.getPurchaserName());
        mTxtShop.setText(mComplainDetailResp.getPurchaserShopName());
        mTxtComplainType.setText(mComplainDetailResp.getTypeName());
        mTxtReason.setText(mComplainDetailResp.getReasonName());
        mTxtExplain.setText(mComplainDetailResp.getComplaintExplain());

        if (!TextUtils.isEmpty(mComplainDetailResp.getImgUrls())) {
            int size = ViewUtils.dip2px(this, 60);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
            layoutParams.rightMargin = ViewUtils.dip2px(this, 10);

            String[] urls = mComplainDetailResp.getImgUrls().split(",");
            for (String url : urls) {
                GlideImageView glideImageView = new GlideImageView(this);
                glideImageView.setImageURL(url);
                glideImageView.isPreview(true);
                glideImageView.setUrls(urls);
                glideImageView.setRadius(5);
                glideImageView.setLayoutParams(layoutParams);
                mScrollPhoto.addView(glideImageView);
            }
        }
    }

    /**
     * 投诉商品
     */
    private void showProducts() {
        if (TextUtils.isEmpty(mComplainDetailResp.getProducts())) {
            mGroupProduct.setVisibility(View.GONE);
            return;
        }
        List<SkuGoodsBean> produceBeans = JSON.parseArray(mComplainDetailResp.getProducts(), SkuGoodsBean.class);
        mProductRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mProductRecyclerView.setAdapter(new ProductAdapter(produceBeans));

    }

    /**
     * 关联订单
     */
    private void showOrder() {
        mGroupOrderCode.setVisibility(mComplainDetailResp.getTarget() == 2 ? View.VISIBLE : View.GONE);
        if (mComplainDetailResp.getTarget() != 2) {
            return;
        }
        mTxtOrderCode.setText(mComplainDetailResp.getBillID());
    }

    /**
     * 投诉详情 - 投诉人及联系方式
     */
    private void showContactAndPhone() {
        mGroupComplainInfo.setVisibility(mComplainDetailResp.getTarget() == 3 ? View.VISIBLE : View.GONE);
        if (mComplainDetailResp.getTarget() != 3) {
            return;
        }
        mTxtPerson.setText(mComplainDetailResp.getCreateBy());
        mTxtPhone.setText(mComplainDetailResp.getInterventionContact());
    }

    /**
     * 司机路线信息
     */
    private void showDriverInfo() {
        mTxtDriver.setText(mComplainDetailResp.getDriverName());
        mTxtPath.setText(mComplainDetailResp.getLineName());
    }

    @Subscribe(sticky = true)
    public void onEvent(ComplainManageEvent complainManageEvent) {
        if (complainManageEvent.getTarget() == ComplainManageEvent.TARGET.DETAIL) {
            if (complainManageEvent.getEvent() == ComplainManageEvent.EVENT.REFRESH) {
                mPresent.start();
            }
        }
    }
    /**
     * 展示底部按钮
     */
    private void showButton() {
        int status = mComplainStatusResp.getStatus();
        int source = mComplainStatusResp.getSource();
        //投诉管理
        if (mComplainDetailResp.getTarget() == 2) {
            if (status == 1 || status == 5 || status == 6 || (status == 2 && source == 3)) {
                mBtnLog.setVisibility(View.VISIBLE);
                mBtnReply.setVisibility(View.VISIBLE);
            } else {
                mBtnLog.setVisibility(View.VISIBLE);
                mBtnReply.setVisibility(View.GONE);
            }
        }
        //平台投诉
        else if (mComplainDetailResp.getTarget() == 3) {


        }
    }

    /**
     * 头部状态栏
     *
     * @return
     */
    private String getStatusText() {
        int status = mComplainStatusResp.getStatus();
        int source = mComplainStatusResp.getSource();
        //向平台投诉
        if (mComplainDetailResp.getTarget() == 3) {
            return platformStatusTip.get(status)[status == 2 ? source : 0];
        } else {
            return statusTip.get(status)[(status == 2 || status == 5) ? source : 0];
        }
    }

    @OnClick({R.id.txt_title_history, R.id.txt_btn_log, R.id.txt_btn_reply, R.id.txt_title_inject, R.id.view_back_5, R.id.txt_link})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_title_history:
                ComplainHistorylActivity.start(mCompaintId);
                break;
            case R.id.txt_btn_log:
                InnerLoglActivity.start(mCompaintId);
                break;
            case R.id.txt_btn_reply:
                SendComplainReplyActivity.start(mCompaintId);
                break;
            case R.id.txt_title_inject:
                if (mComplainDetailResp.getOperationIntervention() == 1) {
                    showToast("已申请平台介入，请等待平台客服回复");
                    return;
                }
                SuccessDialog.newBuilder(this)
                        .setMessageTitle("您确定要申请平台介入么")
                        .setImageState(R.drawable.ic_dialog_state_failure)
                        .setImageTitle(R.drawable.ic_dialog_failure)
                        .setMessage("平台客服收到申请后会尽快处理\n将在1-3个工作日内做出回复")
                        .setButton((dialog, item) -> {
                            dialog.dismiss();
                            mPresent.applyPlatformInject();
                        }, "我再看看", "确认申请")
                        .create()
                        .show();
                break;
            case R.id.view_back_5:
                if (mComplainDetailResp != null) {
                    OrderDetailActivity.start(mComplainDetailResp.getSubBillID());
                }
                break;
            case R.id.txt_link:
                if (mBottomMenuWindow == null) {
                    mBottomMenuWindow = new ContextOptionsWindow(this);
                    OptionsBean optionsBean = new OptionsBean(R.drawable.ic_phone_circle_white, OPTION_PHONE_LINK);
                    mBottomMenuWindow.refreshList(Arrays.asList(optionsBean));
                    mBottomMenuWindow.setListener((adapter, view1, position) -> {
                        mBottomMenuWindow.dismiss();
                        if (position == 0 && mComplainDetailResp != null) {
                            String phone = mComplainDetailResp.getPurchaserContact();
                            if (mComplainDetailResp.getTarget() == 3) {
                                phone = "01056247979";
                            }
                            UIUtils.callPhone(this, phone);
                        }
                    });
                }
                mBottomMenuWindow.showAsDropDownFix(mBtnLink, 0, -240, Gravity.LEFT);
                break;
            default:
                break;
        }
    }


    @Override
    public void applyPlatformInjectSuccess() {
        showToast("已申请平台介入");
        mPresent.start();
        EventBus.getDefault().post(new ComplainManageEvent(ComplainManageEvent.TARGET.LIST, ComplainManageEvent.EVENT.REFRESH));
    }
}
