package com.example.uplibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.uplibrary.adapter.FaceAdapter;
import com.example.uplibrary.adapter.TicketAdapter;
import com.example.uplibrary.bean.FaceTicketVo;
import com.example.uplibrary.bean.FaceVo;

import java.util.ArrayList;
import java.util.List;

public class RecoardFaceActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private Button nextBtn;
    private TextView back_tv;
    private FaceAdapter faceAdapter;
    private List<FaceVo>datas = new ArrayList<>();
    private int faceNum = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoard_face);
        this.faceNum =  getIntent().getIntExtra("facenum",0);

        initViews();

    }

    private void initViews(){

        this.back_tv = findViewById(R.id.back_tv);
        this.back_tv.setOnClickListener(this);
        this.nextBtn = findViewById(R.id.next_btn);
        this.nextBtn.setOnClickListener(this);
        this.recyclerView = findViewById(R.id.recycler_view);

        initRecycler();


    }

    private void initRecycler(){

        for (int i=0;i<faceNum;i++){

            FaceVo vo = new FaceVo();
            datas.add(vo);
        }

        GridLayoutManager manager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(manager);
        faceAdapter = new FaceAdapter(R.layout.face_item_layout,datas);
        faceAdapter.setNewData(datas);
        recyclerView.setAdapter(faceAdapter);
        faceAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {


                ToastUtils.showLong(position+"");

            }
        });


    }



    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.back_tv){

            finish();

        }else if(v.getId() == R.id.next_btn){


        }

    }
}
