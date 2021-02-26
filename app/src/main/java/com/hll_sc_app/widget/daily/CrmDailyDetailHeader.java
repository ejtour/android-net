package com.hll_sc_app.widget.daily;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hll_sc_app.R;
import com.hll_sc_app.bean.daily.DailyBean;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.ThumbnailView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

public class CrmDailyDetailHeader extends ConstraintLayout {
    @BindView(R.id.ddh_today_content)
    TextView mToday;
    @BindView(R.id.ddh_tomorrow_content)
    TextView mTomorrow;
    @BindView(R.id.ddh_help_content)
    TextView mHelp;
    @BindView(R.id.ddh_remark_content)
    TextView mRemark;
    @BindView(R.id.ddh_remark_pic)
    ThumbnailView mRemarkPic;
    @BindView(R.id.ddh_read_status)
    TextView mReadStatus;
    @BindView(R.id.ddh_review_status)
    TextView mReviewStatus;
    @BindView(R.id.ddh_time)
    TextView mTime;

    public CrmDailyDetailHeader(Context context) {
        this(context, null);
    }

    public CrmDailyDetailHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CrmDailyDetailHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundColor(Color.WHITE);
        View view = View.inflate(context, R.layout.view_crm_daily_detail_header, this);
        ButterKnife.bind(this, view);
    }

    public void setData(DailyBean data) {
        mToday.setText(data.getTodayWork());
        mTomorrow.setText(data.getTomorrowPlan());
        mHelp.setText(data.getNeedHelp());
        mRemark.setText(data.getRemark());
        if (TextUtils.isEmpty(data.getImgUrls())) {
            mRemarkPic.setVisibility(GONE);
        } else {
            mRemarkPic.setVisibility(VISIBLE);
            mRemarkPic.enablePreview(true);
            String[] split = data.getImgUrls().split(",");
            mRemarkPic.setData(split);
        }
        boolean readSelected = data.getReadStatus() == 1;
        mReadStatus.setText(readSelected ? "已读" : "未读");
        mReadStatus.setSelected(readSelected);
        boolean reviewed = data.getReplyStatus() == 1;
        mReviewStatus.setText(reviewed ? String.format("%s条点评", data.getReplyNum()) : "暂无点评");
        mReviewStatus.setSelected(reviewed);
        mTime.setText(String.format("提交时间：%s", DateUtil.getReadableTime(data.getCreateTime(), Constants.SLASH_YYYY_MM_DD_HH_MM)));
    }

    public void updateNum(int num) {
        mReviewStatus.setText(String.format("%s条点评", num));
        mReviewStatus.setSelected(true);
    }
}
