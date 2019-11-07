package com.hll_sc_app.app.cardmanage;

import com.hll_sc_app.api.CardManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.cardmanage.CardManageListResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class CardManagePresent implements ICardManageContract.IPresent {

    static int PAGE_SIZE = 20;
    private ICardManageContract.IFragment mFragment;
    private int pageNum = 1;
    private int pageTempNum = 1;

    public static CardManagePresent newInstance() {
        return new CardManagePresent();
    }

    @Override
    public void register(ICardManageContract.IFragment view) {
        mFragment = view;
    }

    @Override
    public void queryList(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("cardNo", mFragment.getCardManageActivity().getSearchText())
                .put("cardStatus", String.valueOf(mFragment.getCardStatus()))
                .put("flag", "2")
                .put("groupID", userBean.getGroupID())
                .put("pageNo", String.valueOf(pageTempNum))
                .put("pageSize", String.valueOf(PAGE_SIZE))
                .create();

        CardManageService.INSTANCE
                .queryCardManageList(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mFragment.showLoading();
                    }
                })
                .doFinally(() -> {
                    mFragment.hideLoading();
                })
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mFragment.getOwner())))
                .subscribe(new BaseCallback<CardManageListResp>() {
                    @Override
                    public void onSuccess(CardManageListResp cardManageListResp) {
                        pageNum = pageTempNum;
                        mFragment.querySuccess(cardManageListResp.getRecords(), pageTempNum > 1);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mFragment.showError(e);
                        pageTempNum = pageNum;
                    }
                });
    }

    @Override
    public void refresh() {
        pageTempNum = 1;
        queryList(false);
    }

    @Override
    public void queryMore() {
        pageTempNum++;
        queryList(false);
    }

    @Override
    public int getPageSize() {
        return PAGE_SIZE;
    }


}
