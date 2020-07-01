package com.hll_sc_app.app.inquiry.detail;

import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.inquiry.InquiryBean;
import com.hll_sc_app.bean.inquiry.InquiryBindResp;
import com.hll_sc_app.bean.user.GroupParamBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Inquiry;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Constants;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/19
 */

class InquiryDetailPresenter implements IInquiryDetailContract.IInquiryDetailPresenter {
    private IInquiryDetailContract.IInquiryDetailView mView;

    private InquiryDetailPresenter() {
    }

    public static InquiryDetailPresenter newInstance() {
        return new InquiryDetailPresenter();
    }

    @Override
    public void start() {
        Inquiry.queryDetail(mView.getID(), new SimpleObserver<InquiryBean>(mView) {
            @Override
            public void onSuccess(InquiryBean bean) {
                mView.setData(bean);
            }
        });
    }

    @Override
    public void register(IInquiryDetailContract.IInquiryDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void submit(InquiryBean bean) {
        Inquiry.submit(bean, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                if (mView.isSubmit()) {
                    mView.success();
                    mView.showToast("提交成功");
                } else {
                    mView.showToast("保存成功");
                }
            }
        });
    }

    @Override
    public void generate() {
        User.queryGroupParam("7", new SimpleObserver<List<GroupParamBean>>(mView) {
            @Override
            public void onSuccess(List<GroupParamBean> groupParamBeans) {
                boolean onlyReceive = groupParamBeans.get(0).getParameValue() == 2;
                GlobalPreference.putParam(Constants.ONLY_RECEIVE, onlyReceive);
                if (onlyReceive) {
                    mView.toGenerate(null);
                } else {
                    queryBind();
                }
            }
        });
    }

    private void queryBind() {
        Inquiry.bindResult(mView.getID(), new SimpleObserver<InquiryBindResp>(mView) {
            @Override
            public void onSuccess(InquiryBindResp inquiryBindResp) {
                if (CommonUtils.isEmpty(inquiryBindResp.getUnbindList())) {
                    mView.toGenerate(inquiryBindResp);
                } else {
                    mView.toBind(inquiryBindResp);
                }
            }
        });
    }
}
