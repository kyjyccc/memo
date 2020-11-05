package com.example.memo;

import android.os.Bundle;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddActivity extends AppCompatActivity {

    private TextView text7;
    private EditText edit6;
    private ImageButton button7,button8,button9;

    private DBHelper mDBHelper;
    private SQLiteDatabase db;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        text7 = findViewById(R.id.text7);
        edit6 = findViewById(R.id.edit6);
        button9 = findViewById(R.id.button9);

        mDBHelper = new DBHelper(this);

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        final String time = simpleDateFormat.format(date);
        text7.setText(time);

        //返回
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                query();
            }
        });

        //保存
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edit6.getText().toString().trim();
                if (content.isEmpty()){
                    showMsg("请在这里输入你的内容");
                }else {
                    add(time.trim(),content);
                    finish();
                    query();
                }
            }
        });

        //清空
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }

    //添加数据
    private void add(String time,String content){
        db = mDBHelper.getWritableDatabase();
        values = new ContentValues();
        values.put("time",time);
        values.put("content",content);
        db.insert("memo",null,values);
        showMsg("保存成功");
    }

    //清空数据
    private void delete(){
        db = mDBHelper.getWritableDatabase();
        db.delete("memo",null,null);

        String result = "";
        Intent intent = new Intent(AddActivity.this,MainActivity.class);
        intent.putExtra("info",result);
        startActivity(intent);

        showMsg("清空成功");
    }

    //弹出消息
    private void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


    //查询数据
    private void query(){
        db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("memo",null,null,null,
                null,null,null);

        String result = "";
        while (cursor.moveToNext()){
            result += "\n" + cursor.getString(1);
            result += "\n内容：" + cursor.getString(2);
            result += "\n";
        }

        Intent intent = new Intent(AddActivity.this,MainActivity.class);
        intent.putExtra("info",result);
        startActivity(intent);
    }

}