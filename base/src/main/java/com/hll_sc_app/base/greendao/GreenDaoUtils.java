package com.hll_sc_app.base.greendao;

import com.hll_sc_app.base.bean.AccountBean;
import com.hll_sc_app.base.bean.AccountBeanDao;
import com.hll_sc_app.base.bean.AuthBean;
import com.hll_sc_app.base.bean.AuthBeanDao;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.bean.UserBeanDao;
import com.hll_sc_app.base.bean.UserShop;
import com.hll_sc_app.base.utils.UserConfig;

import java.util.List;

/**
 * 数据库操作
 *
 * @author zhuyingsong
 * @date 20180604
 */
public class GreenDaoUtils {
    private static UserBean mCurUser;

    public synchronized static UserBean getUser() {
        if (mCurUser == null) {
            mCurUser = DaoSessionManager
                    .getDaoSession()
                    .getUserBeanDao()
                    .queryBuilder()
                    .where(UserBeanDao.Properties.AccessToken.eq(UserConfig.accessToken()))
                    .unique();
        }
        return mCurUser;
    }

    public synchronized static void updateUser(UserBean bean) {
        mCurUser = bean;
        DaoSessionManager.getDaoSession().getUserBeanDao().insertOrReplace(bean);
    }

    public static void updateShopList(List<UserShop> list) {
        DaoSessionManager.getDaoSession().getUserShopDao().insertOrReplaceInTx(list);
    }

    public static List<UserShop> getShopList() {
        return DaoSessionManager.getDaoSession().getUserShopDao().queryBuilder().list();
    }

    public synchronized static void clear() {
        DaoSessionManager.getDaoSession().getUserBeanDao().deleteAll();
        DaoSessionManager.getDaoSession().getUserShopDao().deleteAll();
        mCurUser = null;
    }


    /**
     * 更新缓存的账号
     * 最多存10个
     */
    public static void updateAccount(AccountBean accountBean) {
        if (queryAllAccount().size() == 10) {
            AccountBean old = DaoSessionManager.getDaoSession().getAccountBeanDao().queryBuilder().orderAsc(AccountBeanDao.Properties.Time).list().get(0);
            deleteAccount(old.getAccount());
        }
        DaoSessionManager.getDaoSession().getAccountBeanDao().insertOrReplaceInTx(accountBean);
    }

    public static List<AccountBean> queryAllAccount() {
        return DaoSessionManager.getDaoSession().getAccountBeanDao().queryBuilder().orderDesc(AccountBeanDao.Properties.Time).list();
    }

    public static void deleteAccount(String key) {
        DaoSessionManager.getDaoSession().getAccountBeanDao().deleteByKey(key);
    }

    private static void deleteAllAccount() {
        DaoSessionManager.getDaoSession().getAccountBeanDao().deleteAll();
    }

    public static void updateAuthList(List<AuthBean> list) {
        DaoSessionManager.getDaoSession().getAuthBeanDao().insertOrReplaceInTx(list);
    }

    public static boolean containsAuth(String rightCode) {
        AuthBean resp = DaoSessionManager
                .getDaoSession()
                .getAuthBeanDao()
                .queryBuilder()
                .where(AuthBeanDao.Properties.AuthCode.eq(rightCode))
                .unique();
        return resp != null;
    }

    public static void deleteAuthList() {
        DaoSessionManager.getDaoSession().getAuthBeanDao().deleteAll();
    }
}
