package com.yztc.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yztc.com.yztc.bean.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sqq on 16/5/30.
 * 操作数据库工具类
 */
public class DbManger {
    private static MySqliteHelper helper;
    public static MySqliteHelper getIntance(Context context){
        if(helper==null){
            helper=new MySqliteHelper(context);
        }
        return helper;
    }

    /**
     * 根据指定表明获取数据总条目
     * @param db  数据库对象
     * @param tableName 数据表名称
     * @param selectArgs 查询条件的占位符取值
     * @return  数据总条目
     */
    public static int querySqlteCount(SQLiteDatabase db,String tableName,
                                      String[] selectArgs){
        int size=0;
        if(db!=null){
            Cursor cursor=db.rawQuery("select * from "+tableName,selectArgs);
            size=cursor.getCount();//getCount()获取cursor中数据总条目
        }
        return size;
    }

    /**
     * 根据当前页码获取对应的list集合的数据
     * @param db  数据库对象
     * @param tableName  表名
     * @param currentPage  当前页码
     * @param pageSize 每页数据
     * @return  当前页对应的数据
     *
     * select * from person where _id>1 limit 0,20;
     *  limit ?,?
     *  第一个？ 表示当前页码的第一条数据的下标
     *  第二个？ 表示每页展示的数据条目
     *
     *  currentpage 1    0，20
     *              2    20，20
     */
    public static List<Person> getListByCursor(SQLiteDatabase db,String tableName,
                                               int currentPage,int pageSize){
         Cursor cursor=null;
        int index=(currentPage-1)*pageSize;//根据当前页获取该页第一条数据的下标
        if(db!=null){
            String sql="select * from "+tableName+" limit ?,?";
            cursor=db.rawQuery(sql,new String[]{index+"",pageSize+""});
        }
        return cusorToList(cursor);
    }

    /**
     * 根据cursor解析存储到list集合中
     * @param cursor
     * @return
     */
    public static List<Person> cusorToList(Cursor cursor){
        List<Person> list=new ArrayList<>();
        while(cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex(Constant._ID));
            String name=cursor.getString(cursor.getColumnIndex(Constant.NAME));
            int age=cursor.getInt(cursor.getColumnIndex(Constant.AGE));
            Person p=new Person(age,id,name);
            list.add(p);
        }
        return list;
    }

}
