package com.example.uplibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.uplibrary.adapter.TicketAdapter;
import com.example.uplibrary.bean.AppInfoVo;
import com.example.uplibrary.bean.FaceTicketVo;
import com.example.uplibrary.http.HttpAPI;
import com.example.uplibrary.http.JsonTool;
import com.example.uplibrary.http.UICallBack;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class TicketListActivity extends AppCompatActivity implements View.OnClickListener {

    private String upType = "0";//升级票类型 1表示一日票升级，2表示二日票升级，0表示当日追加
    private NiceSpinner niceSpinner;
    private TextView back_tv,num_tv;
    private RecyclerView recyclerView;
    private Button nextBtn;
    private TicketAdapter ticketAdapter;
    private LinearLayout num_ll;
    private ImageView addImg,minusImg;
    private int num = 1;
    List<String> days;

    private ArrayList<FaceTicketVo> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.init(this);
        setContentView(R.layout.activity_ticket_list);
        this.upType = getIntent().getStringExtra(Constraint.UP_TYPE);
        initViews();

    }

    private void initViews(){

        this.back_tv = findViewById(R.id.back_tv);
        this.recyclerView = findViewById(R.id.recycler_view);
        this.num_ll = findViewById(R.id.num_ll);
        this.addImg = findViewById(R.id.add_img);
        this.addImg.setOnClickListener(this);
        this.minusImg = findViewById(R.id.minus_img);
        this.minusImg.setOnClickListener(this);
        this.nextBtn = findViewById(R.id.next_btn);
        this.nextBtn.setOnClickListener(this);
        this.num_tv = findViewById(R.id.num_tv);


        back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        initSpinner();
        initRecycler();
    }
    private void initRecycler(){

        for (int i=0;i<9;i++){

            FaceTicketVo vo = new FaceTicketVo();
            vo.checked = "0";
            datas.add(vo);
        }

        GridLayoutManager manager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(manager);
        ticketAdapter = new TicketAdapter(R.layout.ticket_item_layout,datas);
        ticketAdapter.setNewData(datas);
        recyclerView.setAdapter(ticketAdapter);
        ticketAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                FaceTicketVo vo = (FaceTicketVo) adapter.getData().get(position);
                if (vo.checked.equals("0")){

                    vo.checked = "1";

                }else {

                    vo.checked = "0";
                }
                adapter.setNewData(datas);

            }
        });


    }
    private void initSpinner(){

        niceSpinner = findViewById(R.id.nice_spiner);

        if (upType.equals("1")){

            days = new LinkedList<String>(Arrays.asList("2日票","3日票"));
            num_ll.setVisibility(View.GONE);

        }else if(upType.equals("2")){

            days = new LinkedList<String>(Arrays.asList("3日票"));
            num_ll.setVisibility(View.GONE);

        }else{

            days = new LinkedList<String>(Arrays.asList("1日票","2日票","3日票"));
            num_ll.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);


        }
        niceSpinner.attachDataSource(days);

        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.next_btn){

            Map<String,String> bizParams = new HashMap<>();
            bizParams.put("terminalCode","665742c3-47bc-4dee-a05e-263baafed815");
            bizParams.put("faceInfo","13867551710");
            bizParams.put("remark","");
            bizParams.put("common","");

            Map<String,String>params = new HashMap<>();
            params.put("bizContent", JsonTool.toJson(bizParams));
            params.put("name","zzj.queryOrder");





            HttpAPI.queryOrders(params, this, new UICallBack() {
                @Override
                public void result(Object result) {



                }

                @Override
                public void fail(String msg) {

                }
            });

            //获取app信息
            Map<String,String> infoParams = new HashMap<>();
            HttpAPI.getAPPInfo(infoParams, this, new UICallBack() {
                @Override
                public void result(Object result) {

                    AppInfoVo infoVo = (AppInfoVo)result;
                    SPUtils.getInstance().put(Constraint.APP_ID,infoVo.getAppId());
                    SPUtils.getInstance().put(Constraint.SECRET,infoVo.getAppSecret());
                }

                @Override
                public void fail(String msg) {

                }
            });

            if (upType.equals("0")){

                Intent intent = new Intent(this,RecoardFaceActivity.class);
                intent.putExtra("facenum",num);
                startActivity(intent);

            }

        }else if(v.getId() == R.id.add_img){

            num++;
            num_tv.setText(num+"");


        }else if(v.getId() == R.id.minus_img){


            num--;
            if (num <= 0){

                num = 0;
            }
            num_tv.setText(num+"");



        }
    }
}
