package com.gaojunhui.day7_5multiimageselector;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.bean.Image;

public class MainActivity extends AppCompatActivity {
    private Button bt_open;
    private ArrayList<String> arrayList;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_open= (Button) findViewById(R.id.bt_open);
        iv= (ImageView) findViewById(R.id.iv);
        arrayList=new ArrayList<>();
        bt_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiImageSelector selector=MultiImageSelector.create(MainActivity.this);
                selector.showCamera(true);
                selector.multi();
                selector.count(4);
                selector.origin(arrayList);
                selector.start(MainActivity.this,0023);
            }
        });
        openSettings();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case 0023:
                    List<String> paths=data.getStringArrayListExtra(MultiImageSelectorActivity
                    .EXTRA_RESULT);
                    for (String path:paths){
                        Log.i("-------", "----- " + path);
                    }
                    iv.setImageBitmap(BitmapFactory.decodeFile(paths.get(0)));
                    break;
            }
        }
    }
    public void openSettings(){
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("必须同意打开权限")
                .setNegativeButton("打开设置界面", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent2=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent2.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent2);
                    }
                }).create().show();
    }
}
