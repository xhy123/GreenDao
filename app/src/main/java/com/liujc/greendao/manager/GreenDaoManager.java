package com.liujc.greendao.manager;

import com.liujc.greendao.dao.DaoMaster;
import com.liujc.greendao.dao.DaoSession;

/**
 * 类名称：GreenDaoManager
 * 创建者：Create by liujc
 * 创建时间：Create on 2016/12/1 14:57
 * 描述：greendao 管理类   此管理类扩展性不太好  AbstractDatabaseManager扩展性比较好，解耦合
 * 最近修改时间：2016/12/1 14:57
 * 修改人：Modify by liujc
 */
public class GreenDaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static GreenDaoManager mInstance; //单例

    private GreenDaoManager(){
        if (mInstance == null) {
//            DaoMaster.DevOpenHelper devOpenHelper = new
//                    DaoMaster.DevOpenHelper(MyApplication.getContext(), "database_name", null);//此处openhelper为自动生成开发所使用，发布版本需自定义
            MySQLiteOpenHelper devOpenHelper = new
                    MySQLiteOpenHelper(new GreenDaoContext(), "database_name.db", null);//GreenDaoContext为创建数据库路径使用
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {//保证异步处理安全操作

                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }
    public DaoSession getSession() {
        return mDaoSession;
    }
    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}
