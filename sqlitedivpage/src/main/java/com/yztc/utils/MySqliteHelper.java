package com.yztc.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SQLiteOpenHelper sqlite数据库操作的帮助类
 * 1.提供了onCreate()onUpgrade()回调函数
 * 2.提供了创建数据库获取数据库对象的方法
 *
 */
public class MySqliteHelper extends SQLiteOpenHelper{
    //必须创建的构造函数 MySqliteHelper(上下文,数据库名称,游标工厂,数据库的版本号)
    public MySqliteHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySqliteHelper(Context context){
        super(context,Constant.DATABASE_NAME,null,Constant.DATABASE_VERSION);
    }

    /**
     * 表示当数据库创建的时候回调的函数
     * @param db 数据库对象
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("tag","------onCreate----");
        String sql="create table "+Constant.TABLE_NAME+"("+Constant._ID+
                " Integer primary key,"+Constant.NAME+" varchar(5)," +
                Constant.AGE+" Integer)";
        db.execSQL(sql);//执行除了查询之外的语句
    }

    /**
     * 表示数据库升级版本时回调的函数
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 表示数据库打开时回调的函数
     * @param db
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.i("tag","------onOpen----");
    }
}
