package com.hll_sc_app.app.cooperation.detail.details;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.application.CooperationApplicationActivity;
import com.hll_sc_app.app.cooperation.detail.CooperationDetailActivity;
import com.hll_sc_app.app.cooperation.invite.CooperationInviteActivity;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.citymall.App;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.ViewCollections;

/**
 * 合作采购商详细资料底部按钮
 *
 * @author zhuyingsong
 * @date 2019-07-20
 */
public class CooperationButtonView extends LinearLayout {
    public static final String TYPE_FORMAL_APPLICATION = "formalCooperation";
    public static final String TYPE_MY_APPLICATION = "myApplication";
    public static final String TYPE_COOPERATION_APPLICATION = "cooperationApplication";
    @BindView(R.id.txt_del)
    TextView mTxtDel;
    @BindView(R.id.txt_add)
    TextView mTxtAdd;
    @BindView(R.id.txt_agree_other)
    TextView mTxtAgreeOther;
    @BindView(R.id.txt_reject_other)
    TextView mTxtRejectOther;
    @BindView(R.id.txt_delete_my0)
    TextView mTxtDeleteMy0;
    @BindView(R.id.ll_status0_my)
    LinearLayout mLlStatus0My;
    @BindView(R.id.txt_repeat_my)
    TextView mTxtRepeatMy;
    @BindView(R.id.txt_delete_my1)
    TextView mTxtDeleteMy1;
    @BindView(R.id.txt_wait_my0)
    TextView mTxtWaitMy0;
    @BindView(R.id.ll_status1_my)
    LinearLayout mLlStatus1My;
    @BindView(R.id.ll_status0_other)
    LinearLayout mLlStatus0Other;
    @BindViews({R.id.txt_del, R.id.txt_add, R.id.txt_agree_other, R.id.txt_reject_other, R.id.ll_status0_other,
        R.id.txt_delete_my0, R.id.ll_status0_my, R.id.txt_repeat_my, R.id.txt_delete_my1, R.id.ll_status1_my,
        R.id.txt_wait_my0})
    List<View> mButtonList;

    private Listener mListener;
    private CooperationPurchaserDetail mDetail;

    public CooperationButtonView(Context context) {
        super(context);
    }

    public CooperationButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(VERTICAL);
        View view = View.inflate(context, R.layout.view_cooperation_details_button, this);
        ButterKnife.bind(this, view);

    }

    public void showButton(String actionType, String status, int cooperationActive) {
        ViewCollections.run(mButtonList, (view, index) -> view.setVisibility(GONE));
        //已停止合作 不显示任何按钮
        if (isComeFromCheck() && cooperationActive == 1) {
            return;
        }
        //正式合作
        if (TextUtils.equals(actionType, TYPE_FORMAL_APPLICATION)) {
            if (!UserConfig.crm()) mTxtDel.setVisibility(VISIBLE);
        } else if (TextUtils.equals(actionType, TYPE_MY_APPLICATION)) {//我的申请
            if (TextUtils.equals(status, "0")) {
                // 我申请 等待别人审批
                mLlStatus0My.setVisibility(VISIBLE);
                mTxtWaitMy0.setVisibility(VISIBLE);
                mTxtDeleteMy0.setVisibility(VISIBLE);
            } else if (TextUtils.equals(status, "1")) {
                mLlStatus1My.setVisibility(VISIBLE);
                mTxtRepeatMy.setVisibility(VISIBLE);
                mTxtDeleteMy1.setVisibility(VISIBLE);
            }
        } else if (TextUtils.equals(actionType, TYPE_COOPERATION_APPLICATION)) {//合作申请
            if (TextUtils.equals(status, "0")) {// 别人申请 我审批
                mLlStatus0Other.setVisibility(VISIBLE);
                mTxtAgreeOther.setVisibility(VISIBLE);
                mTxtRejectOther.setVisibility(VISIBLE);
            }
        } else {
            // 从未添加过
            mTxtAdd.setVisibility(VISIBLE);
        }
    }

    /**
     * 是否从查看页面进来后，需要判断是否停止合作，停止合作则隐藏
     *
     * @return
     */
    private boolean isComeFromCheck() {
        if (App.equalToLastActivity(CooperationDetailActivity.class.getSimpleName(), 1)
                || App.equalToLastActivity(CooperationApplicationActivity.class.getSimpleName(), 1)
                || App.equalToLastActivity(CooperationInviteActivity.class.getSimpleName(), 1)) {
            return true;
        }
        return false;
    }

    @OnClick({R.id.txt_del, R.id.txt_add, R.id.txt_agree_other, R.id.txt_reject_other, R.id.txt_delete_my0,
        R.id.txt_repeat_my, R.id.txt_delete_my1})
    public void onViewClicked(View view) {
        if (mListener == null || mDetail == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.txt_del:
            case R.id.txt_delete_my1:
            case R.id.txt_delete_my0:
                // 我邀请-删除合作
                // 我邀请
                // 解除合作
                mListener.del(mDetail);
                break;
            case R.id.txt_add:
                mListener.add(mDetail);
                break;
            case R.id.txt_agree_other:
                mListener.agree(mDetail);
                break;
            case R.id.txt_reject_other:
                mListener.reject(mDetail);
                break;
            case R.id.txt_repeat_my:
                mListener.repeat(mDetail);
                break;
            default:
                break;
        }
    }

    public void setListener(Listener listener, CooperationPurchaserDetail detail) {
        this.mListener = listener;
        this.mDetail = detail;
    }

    public interface Listener {
        /**
         * 解除合作
         *
         * @param detail detail
         */
        void del(CooperationPurchaserDetail detail);

        /**
         * 拒绝合作
         *
         * @param detail detail
         */
        void reject(CooperationPurchaserDetail detail);

        /**
         * 同意合作
         *
         * @param detail detail
         */
        void agree(CooperationPurchaserDetail detail);

        /**
         * 添加合作
         *
         * @param detail detail
         */
        void add(CooperationPurchaserDetail detail);

        /**
         * 重新验证合作
         *
         * @param detail detail
         */
        void repeat(CooperationPurchaserDetail detail);
    }
}
