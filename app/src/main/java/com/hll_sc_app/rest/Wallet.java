package com.hll_sc_app.rest;

import com.hll_sc_app.api.WalletService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.wallet.BankBean;
import com.hll_sc_app.bean.wallet.RechargeResp;
import com.hll_sc_app.bean.wallet.WalletInfo;
import com.hll_sc_app.bean.wallet.details.DetailsListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/27
 */

public class Wallet {

    /**
     * 获取钱包明细列表
     *
     * @param pageNum      页码
     * @param beginTime    开始时间 yyyyMMdd
     * @param endTime      结束时间 yyyyMMdd
     * @param settleUnitID 结算主体id
     * @param transType    交易类型
     */
    public static void getWalletDetailsList(int pageNum,
                                            String beginTime,
                                            String endTime,
                                            String settleUnitID,
                                            String transType,
                                            SimpleObserver<DetailsListResp> observer) {
        WalletService.INSTANCE
                .getWalletDetailsList(BaseMapReq.newBuilder()
                        .put("pageNo", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("beginTime", beginTime)
                        .put("endTime", endTime)
                        .put("settleUnitID", settleUnitID)
                        .put("transType", transType)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 充值
     *
     * @param money        金额
     * @param settleUnitID 结算主体 id
     */
    public static void recharge(double money, String settleUnitID, SimpleObserver<RechargeResp> observer) {
        UserBean user = GreenDaoUtils.getUser();
        WalletService.INSTANCE
                .recharge(BaseMapReq.newBuilder()
                        .put("groupID", user.getGroupID())
                        .put("groupName", user.getGroupName())
                        .put("terminalIp", UIUtils.getIpAddressString())
                        .put("redirectUrl", "22cityRecharge")
                        .put("settleUnitID", settleUnitID)
                        .put("transAmount", CommonUtils.formatNumber(money))
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 提现
     *
     * @param money        金额
     * @param settleUnitID 结算主体 id
     */
    public static void withdraw(double money, String settleUnitID, SimpleObserver<Object> observer) {
        UserBean user = GreenDaoUtils.getUser();
        WalletService.INSTANCE
                .withdraw(BaseMapReq.newBuilder()
                        .put("groupID", user.getGroupID())
                        .put("settleUnitID", settleUnitID)
                        .put("transAmount", CommonUtils.formatNumber(money))
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取认证信息/进件详情
     */
    public static void queryWalletInfo(SimpleObserver<WalletInfo> observer) {
        WalletService.INSTANCE
                .queryAuthInfo(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID()).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取银行列表
     *
     * @param pageNum 页数
     */
    public static void getBankList(int pageNum, SimpleObserver<List<BankBean>> observer) {
        WalletService.INSTANCE
                .getBankList(BaseMapReq.newBuilder()
                        .put("pageNo", String.valueOf(pageNum))
                        .put("pageSize", "20").create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 充值报备
     *
     * @param docID        报备id
     * @param settleUnitID 结算主体id
     */
    public static void rechargeReport(String docID, String settleUnitID, SimpleObserver<MsgWrapper<Object>> observer) {
        WalletService.INSTANCE
                .rechargeReport(BaseMapReq.newBuilder()
                        .put("docID", docID)
                        .put("settleUnitID", settleUnitID)
                        .create())
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
