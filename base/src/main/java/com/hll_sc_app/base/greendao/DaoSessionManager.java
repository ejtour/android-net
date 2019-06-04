package com.hll_sc_app.base.greendao;

import android.app.Application;

import com.hll_sc_app.base.bean.DaoMaster;
import com.hll_sc_app.base.bean.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * DaoSession管理器
 *
 * @author zhuyingsong
 * @date 20190604
 */
public class DaoSessionManager {
    private static final String DB_NAME = "supplier.db";
    private static Application mApplication;

    public static DaoSession getDaoSession() {
        return Instance.INSTANCE;
    }

    /**
     * 在Application中进行初始化
     *
     * @param application application-
     */
    public static void init(Application application) {
        DaoSessionManager.mApplication = application;
    }

    private static DaoSession initDaoSession(Application application) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(application, DB_NAME);
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }

    private static class Instance {
        static final DaoSession INSTANCE = initDaoSession(mApplication);
    }
}
