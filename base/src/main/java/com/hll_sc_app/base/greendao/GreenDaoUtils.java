package com.hll_sc_app.base.greendao;

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

    public static UserBean getUser() {
        return DaoSessionManager
            .getDaoSession()
            .getUserBeanDao()
            .queryBuilder()
            .where(UserBeanDao.Properties.AccessToken.eq(UserConfig.accessToken()))
            .unique();
    }

    public static void updateUser(UserBean bean) {
        DaoSessionManager.getDaoSession().getUserBeanDao().insertOrReplace(bean);
    }

    public static void updateShopList(List<UserShop> list) {
        DaoSessionManager.getDaoSession().getUserShopDao().insertOrReplaceInTx(list);
    }

    public static void clear() {
        DaoSessionManager.getDaoSession().getUserBeanDao().deleteAll();
        DaoSessionManager.getDaoSession().getUserShopDao().deleteAll();
    }
}
