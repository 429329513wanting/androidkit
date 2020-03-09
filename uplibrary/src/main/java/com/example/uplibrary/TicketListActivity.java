package com.example.uplibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.uplibrary.adapter.TicketAdapter;
import com.example.uplibrary.bean.FaceTicketVo;

import org.angmarch.views.NiceSpinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class TicketListActivity extends AppCompatActivity {

    private String upType = "0";//升级票类型 1表示一日票升级，2表示二日票升级，0表示当日追加
    private NiceSpinner niceSpinner;
    private TextView back_tv;
    private RecyclerView recyclerView;
    private TicketAdapter ticketAdapter;
    List<String> days;

    private ArrayList<FaceTicketVo> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);
        this.upType = getIntent().getStringExtra(Constraint.UP_TYPE);
        this.back_tv = findViewById(R.id.back_tv);
        this.recyclerView = findViewById(R.id.recycler_view);

        back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });



        initRecycler();




    }
    private void initRecycler(){

        initSpinner();
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

        }else if(upType.equals("2")){

            days = new LinkedList<String>(Arrays.asList("3日票"));

        }else{

            days = new LinkedList<String>(Arrays.asList("1日票","2日票","3日票"));


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
}
