package com.yztc.sqlitedivpage;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;

import com.yztc.com.yztc.adapter.MyBaseAdapter;
import com.yztc.com.yztc.bean.Person;
import com.yztc.utils.Constant;
import com.yztc.utils.DbManger;

import java.io.File;
import java.util.List;

/**
 * sqlite数据库分页
 * 总数据 ?   每页展示数据条目(20)   总页数(Math.ceil(总数据/每页数据条目))
 * 当前页码 默认1
 */
public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private int totalNum;//总数据条目
    private int pageSize=20;//每页展示的数据条目
    private int pageNum;//总页数
    private int currentPage=1;//当前页码
    private SQLiteDatabase db;
    private List<Person> list;//数据源
    private MyBaseAdapter adapter;
    private boolean isDivPage;//是否分页
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv= (ListView) findViewById(R.id.lv);
        String path= Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator+"info";
        db=SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
        //获取表中数据总条目
        totalNum= DbManger.querySqlteCount(db, Constant.TABLE_NAME,null);
        //根据总数据以及每页显示数据计算总页数
        pageNum= (int) Math.ceil(totalNum/(double)pageSize);
        if(currentPage==1){
            //获取第一页对应的数据
            list=DbManger.getListByCursor(db,Constant.TABLE_NAME,currentPage,pageSize);
        }
        adapter=new MyBaseAdapter(this,list);
        lv.setAdapter(adapter);

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(isDivPage && scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    if(currentPage<pageNum){//当前页<总页码 说明数据没有加载完毕
                        currentPage++;
                        //添加最新页的数据源
                        list.addAll(DbManger.getListByCursor(db,
                                Constant.TABLE_NAME,currentPage,pageSize));
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isDivPage=((firstVisibleItem+visibleItemCount)==totalItemCount);
            }
        });
    }
}
