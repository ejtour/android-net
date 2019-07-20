package com.hll_sc_app.app.cooperation.detail.details;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合作采购商详细资料底部按钮
 *
 * @author zhuyingsong
 * @date 2019-07-20
 */
public class CooperationButtonView extends LinearLayout {
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

    public void showButton(String actionType, String status) {
        ButterKnife.apply(mButtonList, (view, index) -> view.setVisibility(GONE));
        switch (status) {
            case "0":
                if (TextUtils.equals(actionType, TYPE_MY_APPLICATION)) {
                    // 我申请 等待别人审批
                    mLlStatus0My.setVisibility(VISIBLE);
                    mTxtWaitMy0.setVisibility(VISIBLE);
                    mTxtDeleteMy0.setVisibility(VISIBLE);
                } else if (TextUtils.equals(actionType, TYPE_COOPERATION_APPLICATION)) {
                    // 别人申请 我审批
                    mLlStatus0Other.setVisibility(VISIBLE);
                    mTxtAgreeOther.setVisibility(VISIBLE);
                    mTxtRejectOther.setVisibility(VISIBLE);
                }
                break;
            case "1":
                // 未同意
                if (TextUtils.equals(actionType, TYPE_MY_APPLICATION)) {
                    mLlStatus1My.setVisibility(VISIBLE);
                    mTxtRepeatMy.setVisibility(VISIBLE);
                    mTxtDeleteMy1.setVisibility(VISIBLE);
                }
                break;
            case "2":
                // 已同意
                mTxtDel.setVisibility(VISIBLE);
                break;
            case "3":
                // 从未添加过
                mTxtAdd.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
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
                break;
            case R.id.txt_agree_other:
                mListener.agree(mDetail);
                break;
            case R.id.txt_reject_other:
                mListener.reject(mDetail);
                break;
            case R.id.txt_repeat_my:
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
    }
}
