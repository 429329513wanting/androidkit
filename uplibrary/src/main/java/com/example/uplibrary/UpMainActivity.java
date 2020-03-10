package com.example.uplibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.example.uplibrary.http.HttpAPI;
import com.example.uplibrary.http.HttpGetUtil;
import com.example.uplibrary.http.ResultCallBack;
import com.example.uplibrary.http.UICallBack;

import java.util.HashMap;
import java.util.Map;

public class UpMainActivity extends AppCompatActivity {

    private Button oneBtn;
    private Button twoBtn;
    private Button currBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_main);

        Utils.init(this);
        LogUtils.d("UpMainActivity");

        oneBtn = findViewById(R.id.up_one_btn);
        twoBtn = findViewById(R.id.up_two_btn);
        currBtn = findViewById(R.id.up_curr_btn);

        oneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toListVC(v);

            }
        });

        twoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toListVC(v);
            }
        });

        currBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toListVC(v);
            }
        });

    }
    private void toListVC(View view){


        Intent intent = new Intent(this,TicketListActivity.class);
        if (view.getId() == R.id.up_one_btn){

            intent.putExtra(Constraint.UP_TYPE,"1");

        }else if (view.getId() == R.id.up_two_btn){

            intent.putExtra(Constraint.UP_TYPE,"2");

        }else {


            Map<String,String> params = new HashMap<>();
            params.put("username","sx_bgs");
            params.put("password","e10adc3949ba59abbe56e057f20f883e");

            HttpAPI.login(params, this, new UICallBack() {
                @Override
                public void result(Object result) {

                    ToastUtils.showLong(result.toString());
                }

                @Override
                public void fail(String msg) {

                }
            });

            intent.putExtra(Constraint.UP_TYPE,"0");
        }

        startActivity(intent);
    }
}
