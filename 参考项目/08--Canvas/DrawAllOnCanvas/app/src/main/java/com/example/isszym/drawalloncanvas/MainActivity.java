package com.example.isszym.drawalloncanvas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getPermission(){
        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }
    private void SaveImage(Bitmap finalBitmap) {
       File file=getFile();
       try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
       } catch (Exception e) {
            Toast.makeText(this, "save failed！"+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
       }
    }
    private File getFile(){
        getPermission();
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Images");
        if(!myDir.mkdirs())
            Toast.makeText(this, "mkdirs failed！", Toast.LENGTH_SHORT).show();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        //if (file.exists()) file.delete();
        return file;
    }
    protected void saveBitmap(View vw) {
        MyView2 view = (MyView2) findViewById(R.id.my_view2);
        SaveImage(view.getBitmap());
        return;
    }
}
