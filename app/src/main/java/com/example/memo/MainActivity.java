package com.example.memo;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private ImageButton button5, button6;
    private EditText edit5;
    private TextView text6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        button5 = findViewById(R.id.button5);
        edit5 = findViewById(R.id.edit5);
        text6 = findViewById(R.id.text6);

        //新建备忘录
        button6 = (ImageButton) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        //获取传递过来的值并显示
        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        text6.setText(info);

        //查询
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = edit5.getText().toString().trim();
                String result = text6.getText().toString().trim();

                //判断输入是否为空
                if (!query.isEmpty()) {
                    int rs = result.indexOf(query); //内容第一次出现的位置

                    //判断查询的内容是否存在
                    if (rs != -1) {
                        String one = result.substring(0, rs + 1);
                        String two = result.substring(rs + 1, result.length());

                        int last = one.lastIndexOf("2020");
                        int first = two.indexOf("2020");

                        String m, n;

                        if (last == -1) {
                            m = one;
                        } else {
                            String m2 = one.substring(last, one.length());
                            m = m2;
                        }

                        if (first == -1) {
                            n = two;
                        } else {
                            String n2 = two.substring(0, first);
                            n = n2;
                        }

                        Intent intent = new Intent(MainActivity.this, QueryActivity.class);
                        intent.putExtra("query", query);
                        intent.putExtra("info", m + n);
                        startActivity(intent);
                    } else {
                        showMsg("没有对应内容");
                    }
                } else {
                    showMsg("请输入你想要查询的内容");
                }
            }

        });
    }

    //弹出消息
    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}