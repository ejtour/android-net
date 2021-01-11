package com.hll_sc_app.app.message.notice;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.app.web.WebViewProxy;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.permission.RequestPermissionUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.message.MessageDetailBean;
import com.hll_sc_app.bean.message.MultiUrlItem;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.adapter.AttachmentAdapter;
import com.hll_sc_app.widget.TitleBar;

import java.io.File;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

@Route(path = RouterConfig.MESSAGE_NOTICE)
public class MessageNoticeActivity extends BaseLoadActivity implements IMessageNoticeContract.IMessageNoticeView {
    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;
    @Autowired(name = "parcelable")
    MessageDetailBean mBean;
    private AttachmentAdapter mAdapter;
    private MultiUrlItem mCurItem;
    private WebViewProxy mWebViewProxy;
    private IMessageNoticeContract.IMessageNoticePresenter mPresenter;

    /**
     * @param bean 消息明细
     */
    public static void start(MessageDetailBean bean) {
        RouterUtil.goToActivity(RouterConfig.MESSAGE_NOTICE, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        mPresenter = new MessageNoticePresenter();
        mPresenter.register(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        if (mWebViewProxy != null) {
            mWebViewProxy.destroy();
        }
        super.onDestroy();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("消息详情");
        mListView.setBackgroundColor(Color.WHITE);
        mAdapter = new AttachmentAdapter();
        mListView.setAdapter(mAdapter);
        int space = UIUtils.dip2px(10);
        mListView.setPadding(space, space * 2, space, space * 2);
        View header = View.inflate(this, R.layout.view_message_notice_header, null);
        mAdapter.setHeaderView(header);
        header.<TextView>findViewById(R.id.mnh_title).setText(mBean.getMessageTitle());
        header.<TextView>findViewById(R.id.mnh_time).setText(mBean.getActionTime());
        if (mBean.getMessageSrc() == 1) {
            header.<TextView>findViewById(R.id.mnh_author).setText(mBean.getExtGroupName());
            Bundle args = new Bundle();
            args.putString(Constants.WEB_DATA, "<html>" +
                    "<body>" +
                    mBean.getMessageContent() +
                    "</body>" +
                    "</html>");
            mWebViewProxy = new WebViewProxy(args, header.findViewById(R.id.mnh_web_container));
            mWebViewProxy.initWebView();
        } else {
            header.<TextView>findViewById(R.id.mnh_author).setText(String.format("%s官方", BuildConfig.ODM_NAME));
            header.<TextView>findViewById(R.id.mnh_message).setText(Html.fromHtml(mBean.getMessageContent()));
        }
        if (!CommonUtils.isEmpty(mBean.getFileInfoList())) {
            mAdapter.setFiles(mBean.getFileInfoList());
        } else if (!TextUtils.isEmpty(mBean.getImgUrl())) {
            mAdapter.setData(Arrays.asList(mBean.getImgUrl().split(",")));
        }
        mAdapter.setOnItemChildClickListener((adapter1, view, position) -> {
            mCurItem = mAdapter.getItem(position);
            if (mCurItem == null) return;
            if (view.getId() == R.id.ia_action) {
                new RequestPermissionUtils(this, RequestPermissionUtils.STORAGE, () -> mPresenter.download(mCurItem.getUrl())).requestPermission();
            }
        });
    }

    @Override
    public void success(String path) {
        File destFile = new File(mAdapter.getPath(), mCurItem.getName());
        if (!destFile.exists()) {
            new File(path).renameTo(destFile);
        }
        mAdapter.notifyItemChanged(mAdapter.getData().indexOf(mCurItem) + mAdapter.getHeaderLayoutCount());
        String filePath = destFile.getPath();
        filePath = filePath.substring(filePath.toLowerCase().indexOf("/documents"));
        Snackbar.make(mListView, "下载成功：" + filePath, Snackbar.LENGTH_SHORT).show();
        hideLoading();
    }
}
