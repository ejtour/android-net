package com.hll_sc_app.app.feedbackcomplain.feedback.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.complain.FeedbackDetailResp;
import com.hll_sc_app.widget.TriangleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 意见反馈详情
 */
@Route(path = RouterConfig.ACTIVITY_FEED_BACK_DETAIL)
public class FeedbackDetailActivity extends BaseLoadActivity implements IFeedbackDetailContract.IView {
    @BindView(R.id.list_view)
    RecyclerView mListView;
    @Autowired(name = "object0")
    String mId;
    private Unbinder unbinder;
    private IFeedbackDetailContract.IPresent mPresent;
    private FeedbackDetailAdapter mAdapter;

    public static void start(String id) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_FEED_BACK_DETAIL, id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_feedback_detail);
        unbinder = ButterKnife.bind(this);
        mPresent = FeedbackDetailPresent.newInstance();
        mPresent.register(this);
        mPresent.queryFeedback(mId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showDetail(FeedbackDetailResp resp) {
        FeedbackDetailResp.DetailBean tailBean = new FeedbackDetailResp.DetailBean();
        tailBean.setTail(true);
        tailBean.setType(1);
        resp.getDetails().add(tailBean);
        mAdapter = new FeedbackDetailAdapter(resp.getDetails());
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    private class FeedbackDetailAdapter extends BaseQuickAdapter<FeedbackDetailResp.DetailBean, BaseViewHolder> {
        private LinearLayout.LayoutParams mImgParams;

        public FeedbackDetailAdapter(@Nullable List<FeedbackDetailResp.DetailBean> data) {
            super(R.layout.list_item_feedback_detail, data);
            int imgSize = UIUtils.dip2px(60);
            mImgParams = new LinearLayout.LayoutParams(imgSize, imgSize);
            mImgParams.rightMargin = UIUtils.dip2px(10);
        }

        @Override
        protected void convert(BaseViewHolder helper, FeedbackDetailResp.DetailBean item) {
            TextView mTxtTitle = helper.getView(R.id.txt_title);
            TriangleView triangleView = helper.getView(R.id.arrow);
            LinearLayout mLlContainer = helper.getView(R.id.ll_scroll_photo);
            mLlContainer.removeAllViews();
            ConstraintLayout.LayoutParams titleParams = (ConstraintLayout.LayoutParams) mTxtTitle.getLayoutParams();
            ConstraintLayout.LayoutParams arrowParams = (ConstraintLayout.LayoutParams) triangleView.getLayoutParams();
            if (item.getType() == 0) {//用户反馈
                mTxtTitle.setText("您的反馈内容");
                titleParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                titleParams.startToStart = -1;
                arrowParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                arrowParams.startToStart = -1;
            } else {//客服反馈
                mTxtTitle.setText("客服回复内容");
                titleParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                titleParams.endToEnd = -1;
                arrowParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                arrowParams.endToEnd = -1;
            }


            helper.setGone(R.id.txt_content, !item.isTail());
            helper.setGone(R.id.scroll_photo, !item.isTail());
            helper.setGone(R.id.txt_tail, item.isTail());

            if (item.isTail()) {
                return;
            }

            helper.setText(R.id.txt_content, item.getContent());
            if (TextUtils.isEmpty(item.getContentImg())) {
                return;
            }
            String[] imgs = item.getContentImg().split(",");
            for (String img : imgs) {
                GlideImageView imageView = new GlideImageView(FeedbackDetailActivity.this);
                imageView.setLayoutParams(mImgParams);
                imageView.isPreview(true);
                imageView.setUrls(imgs);
                imageView.setImageURL(img);
                mLlContainer.addView(imageView);
            }
        }
    }
}
