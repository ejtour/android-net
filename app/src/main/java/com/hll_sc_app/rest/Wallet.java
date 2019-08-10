package com.hll_sc_app.rest;

import com.hll_sc_app.api.WalletService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.wallet.AreaInfo;
import com.hll_sc_app.bean.wallet.AuthInfo;
import com.hll_sc_app.bean.wallet.BankBean;
import com.hll_sc_app.bean.wallet.RechargeResp;
import com.hll_sc_app.bean.wallet.WalletStatusResp;
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
     * 查询钱包状态
     */
    public static void queryWalletStatus(SimpleObserver<WalletStatusResp> observer) {
        WalletService.INSTANCE.queryWalletStatus(BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("groupType", "1").create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取钱包明细列表
     *
     * @param pageNum      页码
     * @param beginTime    开始时间 yyyyMMdd
     * @param endTime      结束时间 yyyyMMdd
     * @param settleUnitID 结算主体id
     */
    public static void getWalletDetailsList(int pageNum, String beginTime, String endTime, String settleUnitID, SimpleObserver<DetailsListResp> observer) {
        WalletService.INSTANCE
                .getWalletDetailsList(BaseMapReq.newBuilder()
                        .put("pageNo", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("beginTime", beginTime)
                        .put("endTime", endTime)
                        .put("settleUnitID", settleUnitID).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 导出钱包明细列表
     *
     * @param req 明细请求
     */
    public static void exportWalletDetailsList(ExportReq req, SimpleObserver<ExportResp> observer) {
        WalletService.INSTANCE
                .exportWalletDetailsList(new BaseReq<>(req))
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
    public static void queryAuthInfo(SimpleObserver<AuthInfo> observer) {
        WalletService.INSTANCE
                .queryAuthInfo(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID()).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询省市列表
     *
     * @param areaType     获取省列表时 areaType传2,areaParentId 传ZP1
     *                     获取省的市列表 areaType 传3,areaParentId 传省编码
     *                     获取市的区列表 areaType 传4,areaParentId 传市编码
     * @param areaParentId 上级区域编码
     */
    public static void queryAreaList(int areaType, String areaParentId, SimpleObserver<List<AreaInfo>> observer) {
        WalletService.INSTANCE
                .queryAreaList(BaseMapReq.newBuilder()
                        .put("areaType", String.valueOf(areaType))
                        .put("areaParentId", areaParentId).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 开通账号
     *
     * @param info 待核验信息
     */
    public static void createAccount(AuthInfo info, SimpleObserver<Object> observer) {
        UserBean user = GreenDaoUtils.getUser();
        info.setGroupID(user.getGroupID());
        info.setGroupName(user.getGroupName());
        info.setGroupType(1);
        WalletService.INSTANCE
                .createAccount(new BaseReq<>(info))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 实名认证
     *
     * @param info 待核验信息
     */
    public static void authAccount(AuthInfo info, SimpleObserver<Object> observer) {
        WalletService.INSTANCE
                .authAccount(new BaseReq<>(info))
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
}
