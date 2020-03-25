package com.example.uplibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.uplibrary.adapter.TicketAdapter;
import com.example.uplibrary.bean.FaceTicketVo;
import com.wuyr.secondfloorbehavior.OnStateChangeListener;
import com.wuyr.secondfloorbehavior.SecondFloorBehavior;

import java.util.ArrayList;

public class FloorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<FaceTicketVo> datas = new ArrayList<>();
    private TicketAdapter ticketAdapter;
    private SwipeRefreshLayout refresh_view;
    private RelativeLayout secondView;
    private SecondFloorBehavior behavior;
    private View infoTv;
    private TextView backtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor);

        this.recyclerView = findViewById(R.id.recycler_view);
        this.refresh_view = findViewById(R.id.refresh_view);
        this.secondView = findViewById(R.id.second_view);
        this.infoTv = findViewById(R.id.header_tv);
        this.backtv = findViewById(R.id.back_first_tv);
        initRecycler();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) secondView.getLayoutParams();
        behavior = (SecondFloorBehavior) params.getBehavior();
        behavior.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(int state) {

                if (state == SecondFloorBehavior.STATE_NORMAL){


                }else if(state == SecondFloorBehavior.STATE_DRAGGING){


                } else if(state == SecondFloorBehavior.STATE_PREPARED){


                }

            }
        });

        if (refresh_view.isRefreshing()){

            behavior.setOnEnterSecondFloorListener(null);

        }
        backtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                behavior.leaveSecondFloor();
            }
        });


    }
    public void onEnterSecondFloor(){

    }
    public void onExitSecondFloor(){

    }

    private void initRecycler(){

        for (int i=0;i<35;i++){

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
        refresh_view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        refresh_view.setRefreshing(false);
                    }
                },3000);

            }
        });



    }
}
