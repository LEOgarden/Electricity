package android.leo.electricity;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.leo.electricity.greendao.gen.DaoMaster;
import android.leo.electricity.greendao.gen.DaoSession;
import android.leo.electricity.utils.SharedPerferencesUtil;
import android.media.session.MediaSession;
import android.util.Log;

/**
 * Created by Leo on 2017/7/6.
 */

public class MyApplication extends Application {
    private static Context mContext ;

    public static String token=null;
    public static String phone = null;

    private DaoMaster.OpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        token = (String) SharedPerferencesUtil.getParam(mContext, "token", "", "share_data");
        //phone = (String) SharedPerferencesUtil.getParam(mContext, "phoneno", "", "share_data");
        setDatabase();
    }

    public static Context getInstance() {
        return mContext;
    }

    private void setDatabase(){
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

}
