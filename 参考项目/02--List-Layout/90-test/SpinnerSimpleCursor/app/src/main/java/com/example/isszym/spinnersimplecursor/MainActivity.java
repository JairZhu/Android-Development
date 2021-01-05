package com.example.isszym.spinnersimplecursor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBOpenHandler dbOpenHandler = new DBOpenHandler(this,
                "dbStu.db3", null, 1);
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase(); // 用于insert/upatde/delete
        //db.execSQL("DROP TABLE stu");
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS stu(_id INTEGER PRIMARY KEY autoincrement, num INTEGER, name varchar(24))";
        db.execSQL(CREATE_TABLE);
        spinner1 = (Spinner) findViewById(R.id.spinner);

        Cursor cursor = db.rawQuery("select _id,num,name from stu", null);

        if (cursor != null) {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.spinner_item, cursor,
            new String[]{"num", "name"}, new int[]{R.id.num, R.id.name}, 0);
            spinner1.setAdapter(adapter);
        }
        db.close();

        Button btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                AddRows();
            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                Cursor cursor = (Cursor) spinner1.getSelectedItem();
                String name = cursor.getString(1);
                String num = cursor.getString(2);
                System.out.println(num+" "+name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }
    void AddRows() {
        DBOpenHandler dbOpenHandler = new DBOpenHandler(this,
                "dbStu.db3", null, 1);
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase(); // 用于insert/upatde/delete
        //db.execSQL("DROP TABLE stu");

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS stu(_id INTEGER PRIMARY KEY autoincrement, num INTEGER, name varchar(24))";
        db.execSQL(CREATE_TABLE);
        db.execSQL("DELETE FROM stu;");
        String INSERT_DATA = "";
        INSERT_DATA = "INSERT INTO stu(num, name) values(150010121, '陈伟霆')";
        db.execSQL(INSERT_DATA);
        INSERT_DATA = "INSERT INTO stu(num, name) values(150010122, '迪丽热巴')";
        db.execSQL(INSERT_DATA);
        INSERT_DATA = "INSERT INTO stu(num, name) values(150010123, '张艺兴')";
        db.execSQL(INSERT_DATA);
        INSERT_DATA = "INSERT INTO stu(num, name) values(150010124, '鹿晗')";
        db.execSQL(INSERT_DATA);

        db.close();
    }

}

class DBOpenHandler extends SQLiteOpenHelper {
    private final String TAG = "MySQLiteOpenHelper";

    public DBOpenHandler(Context context, String dbName,
                         SQLiteDatabase.CursorFactory factory, int dbVersion) {
        //上下文,数据库名称,游标工厂，数据库版本号（>=1）   优点：可以处理版本变化
        super(context, dbName, factory, dbVersion);
        Log.d(TAG, "MySQLiteOpenHelper");
    }

    //可以在这里创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
    }

    //如果版本号发生变化，可以在这里做一些事，例如，为某个表增加一列
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade");
    }

}

