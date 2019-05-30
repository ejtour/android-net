package com.hll_sc_app.base.greendao;

import android.app.Application;

/**
 * DaoSession管理器
 *
 * @author zhuyingsong
 * @date 2018/12/19
 */
public class DaoSessionManager {
    private static final String DB_NAME = "supplier.db";
    private static Application mApplication;

//    public static DaoSession getDaoSession() {
//        return Instance.INSTANCE;
//    }

//    private static class Instance {
//        static final DaoSession INSTANCE = initDaoSession(mApplication);
//    }

    /**
     * 在Application中进行初始化
     *
     * @param application application-
     */
    public static void init(Application application) {
        DaoSessionManager.mApplication = application;
    }

//    private static DaoSession initDaoSession(Application application) {
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(application, DB_NAME);
//        Database db = helper.getWritableDb();
//        return new DaoMaster(db).newSession();
//    }
}
