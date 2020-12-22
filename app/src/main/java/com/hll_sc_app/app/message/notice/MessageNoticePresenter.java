package com.hll_sc_app.app.message.notice;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DownloadUtil;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/21/20.
 */
class MessageNoticePresenter implements IMessageNoticeContract.IMessageNoticePresenter {
    private IMessageNoticeContract.IMessageNoticeView mView;

    @Override
    public void download(String url) {
        mView.showLoading();
        DownloadUtil.getInstance().download("http://res.hualala.com/" + url, "Documents", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String path) {
                if (mView.isActive()) {
                    mView.success(path);
                }
            }

            @Override
            public void onDownloading(int progress) {
                // no-op
            }

            @Override
            public void onDownloadFailed() {
                if (mView.isActive()) {
                    mView.showToast("下载失败");
                    mView.hideLoading();
                }
            }
        });
    }

    @Override
    public void register(IMessageNoticeContract.IMessageNoticeView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
