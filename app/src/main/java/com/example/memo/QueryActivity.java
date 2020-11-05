package com.example.memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class QueryActivity extends AppCompatActivity {

    private EditText edit7;
    private TextView text8;
    private ImageButton button10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        edit7 = findViewById(R.id.edit7);
        text8 = findViewById(R.id.text8);

        //获取传递过来的值并显示
        Intent intent = getIntent();
        String query = intent.getStringExtra("query");
        String info = intent.getStringExtra("info");
        edit7.setText(query);
        text8.setText(info);

    }

}