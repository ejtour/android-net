package com.hll_sc_app.app.feedbackcomplain.feedback.detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.feedbackcomplain.feedback.add.FeedbackAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.complain.FeedbackDetailResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ThumbnailView;
import com.hll_sc_app.widget.TriangleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.ll_button_bottom)
    LinearLayout mLlButton;
    private Unbinder unbinder;
    private IFeedbackDetailContract.IPresent mPresent;
    private FeedbackDetailAdapter mAdapter;

    private FeedbackDetailResp mDetail;
    public static void start(String id) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_FEED_BACK_DETAIL, id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @OnClick({R.id.txt_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_confirm:
                FeedbackAddActivity.start(mDetail);
                break;
            default:
                break;
        }
    }
    @Override
    public void showDetail(FeedbackDetailResp resp) {
        mDetail = resp;
        if (CommonUtils.isEmpty(resp.getDetails())) {
            return;
        }
        mLlButton.setVisibility(resp.getIsAnswer() == 1 ? View.VISIBLE : View.GONE);
        //如果最后一个是用户反馈的话，则显示默认客服回复
        int size = resp.getDetails().size();
        if (resp.getDetails().get(size - 1).getType() == 0) {
            FeedbackDetailResp.DetailBean tailBean = new FeedbackDetailResp.DetailBean();
            tailBean.setTail(true);
            tailBean.setType(1);
            resp.getDetails().add(tailBean);
        }
        mAdapter = new FeedbackDetailAdapter(resp.getDetails());
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    private static class FeedbackDetailAdapter extends BaseQuickAdapter<FeedbackDetailResp.DetailBean, BaseViewHolder> {

        public FeedbackDetailAdapter(@Nullable List<FeedbackDetailResp.DetailBean> data) {
            super(R.layout.list_item_feedback_detail, data);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
            ((ThumbnailView) helper.getView(R.id.thumbnail_view)).enablePreview(true);
            return helper;
        }

        @Override
        protected void convert(BaseViewHolder helper, FeedbackDetailResp.DetailBean item) {
            TextView mTxtTitle = helper.getView(R.id.txt_title);
            TriangleView triangleView = helper.getView(R.id.arrow);
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
            helper.setGone(R.id.thumbnail_view, !item.isTail() && !TextUtils.isEmpty(item.getContentImg()));
            helper.setGone(R.id.txt_tail, item.isTail());

            if (item.isTail()) {
                return;
            }

            helper.setText(R.id.txt_content, item.getContent());
            if (TextUtils.isEmpty(item.getContentImg())) {
                return;
            }
            String[] imgs = item.getContentImg().split(",");
            ((ThumbnailView) helper.getView(R.id.thumbnail_view)).setData(imgs);
        }
    }
}
