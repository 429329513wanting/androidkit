package com.example.uplibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.uplibrary.adapter.TicketAdapter;
import com.example.uplibrary.bean.FaceTicketVo;
import com.example.uplibrary.http.HttpAPI;
import com.example.uplibrary.http.HttpGetUtil;
import com.example.uplibrary.http.JsonTool;
import com.example.uplibrary.http.ResultCallBack;
import com.example.uplibrary.http.UICallBack;
import com.example.uplibrary.widget.PicassoImageEngine;
import com.example.uplibrary.widget.city.CityPickerActivity;
import com.maning.imagebrowserlibrary.ImageEngine;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.maning.imagebrowserlibrary.model.ImageBrowserConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpMainActivity extends AppCompatActivity {

    private Button oneBtn;
    private Button twoBtn;
    private Button currBtn,flowerBtn;
    private RecyclerView recyclerView;
    private ArrayList<FaceTicketVo> datas = new ArrayList<>();
    private TicketAdapter ticketAdapter;
    private SwipeRefreshLayout refresh_view;
    private HorizontalScrollView hscr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_main);

        Utils.init(this);
        LogUtils.d("UpMainActivity");

        oneBtn = findViewById(R.id.up_one_btn);
        twoBtn = findViewById(R.id.up_two_btn);
        currBtn = findViewById(R.id.up_curr_btn);
        flowerBtn = findViewById(R.id.two_flower_btn);
        this.recyclerView = findViewById(R.id.recycler_view);
        this.refresh_view = findViewById(R.id.refresh_view);
        hscr = findViewById(R.id.h_scr);

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

        flowerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils.startActivity(FloorActivity.class);

            }
        });
        Button indiButton = findViewById(R.id.indicator_btn);
        indiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils.startActivity(IndicatorActivity.class);
            }
        });

        Button vpButton = findViewById(R.id.vp_fragment_btn);
        vpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils.startActivity(VPFragmentActivity.class);

            }
        });
        Button videoButton = findViewById(R.id.video_btn);
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils.startActivity(VideoActivity.class);

            }
        });
        Button cityButton = findViewById(R.id.city_btn);
        cityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UpMainActivity.this, CityPickerActivity.class);
                startActivityForResult(intent,1000);


            }
        });
        Button browerButton = findViewById(R.id.brower_btn);
        browerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList sourceImageList = new ArrayList<>();
                sourceImageList.add("https://hqctv.oss-cn-beijing.aliyuncs.com/hqctv/1912/e970d89b-e1bd-4d6a-ad47-8ac7de902545.jpg");
                sourceImageList.add("https://hqctv.oss-cn-beijing.aliyuncs.com/hqctv/2001/b3bcf018-4210-4231-bbd1-2abcb578df7b.jpg");
                sourceImageList.add("https://hqctv.oss-cn-beijing.aliyuncs.com/hqctv/1912/0b5bea84-f50e-4d56-8bf1-01c8d3784fb0.jpg");
                sourceImageList.add("https://hqctv.oss-cn-beijing.aliyuncs.com/hqctv/1907/f5d3d1dd-3345-4d15-876b-90fd91b7a1e3.jpg");
                sourceImageList.add("https://hqctv.oss-cn-beijing.aliyuncs.com/hqctv/1912/c2b01b43-2cc5-4df6-9329-2425fb59792b.jpg");
                sourceImageList.add("https://hqctv.oss-cn-beijing.aliyuncs.com/hqctv/1907/c472c40e-7c8c-46f2-adce-00ce3aadc079.jpg");
                sourceImageList.add("https://hqctv.oss-cn-beijing.aliyuncs.com/hqctv/1912/185af0ef-abbe-4d3e-9492-1fdcd895a95a.jpg");
                sourceImageList.add("https://hqctv.oss-cn-beijing.aliyuncs.com/hqctv/1912/5293140e-8a35-4c63-a23a-3b08e37d3335.jpg");

                 ImageBrowserConfig.TransformType transformType = ImageBrowserConfig.TransformType.Transform_Default;
                 ImageBrowserConfig.IndicatorType indicatorType = ImageBrowserConfig.IndicatorType.Indicator_Number;
                 ImageBrowserConfig.ScreenOrientationType screenOrientationType = ImageBrowserConfig.ScreenOrientationType.Screenorientation_Default;
                 ImageEngine imageEngine = new PicassoImageEngine();
                 int openAnim = R.anim.mn_browser_enter_anim;
                 int exitAnim = R.anim.mn_browser_exit_anim;

                MNImageBrowser.with(UpMainActivity.this)
                        .setCurrentPosition(0)
                        .setImageEngine(imageEngine)
                        .setImageList(sourceImageList)
                        .setFullScreenMode(true)
                        .setCustomProgressViewLayoutID(R.layout.layout_custom_progress_view)
                        .setActivityOpenAnime(openAnim)
                        .setActivityExitAnime(exitAnim)
                        .show(new ImageView(UpMainActivity.this));



            }
        });

        initRecycler();
        hscr.setSmoothScrollingEnabled(true);
        hscr.pageScroll(View.FOCUS_LEFT);

        hscr.setOnTouchListener(new View.OnTouchListener() {

            private int lastScrollX = 0;
            private int TouchEventId = -9987832;

            Handler handler = new Handler(){

                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);

                    int avwidth = ScreenUtils.getScreenWidth()-SizeUtils.dp2px(10)*2;

                    if (msg.what == TouchEventId){

                        if (lastScrollX == hscr.getScrollX()){

                            int indexScrollTo = Math.round(lastScrollX/avwidth);
                            LogUtils.d("", "stop scroll - " + lastScrollX
                                    + "|" + avwidth
                                    + "|" + lastScrollX/(avwidth)
                                    + "|" + indexScrollTo);

                            if (indexScrollTo > 0){

                                hscr.smoothScrollTo(indexScrollTo*avwidth,0);
                            }else {

                                hscr.smoothScrollTo(0,0);
                            }

                        }else {

                            handler.sendMessageDelayed(handler.obtainMessage(TouchEventId),100);
                            lastScrollX = hscr.getScrollX();
                        }
                    }


                }
            };


            @Override
            public boolean onTouch(View v, MotionEvent event) {

                LogUtils.d("", "touch event - action: " + event.getAction()
                        + "|" + event.getX()
                        + "|" + event.getY()
                        + "|" + hscr.getScrollX()
                        + "|" + hscr.getScrollY());
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    handler.sendMessageDelayed(handler.obtainMessage(TouchEventId), 100);
                }

                return false;
            }
        });


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

            //SIGN
            Map<String,String> params1 = new HashMap<>();
            Map<String,String>jsonMap = new HashMap<>();
            jsonMap.put("phone","sx_bgs");
            jsonMap.put("name","办公室");
            jsonMap.put("intro","中国浙江省杭州市江干区凯旋路201-2号");

            params1.put("token","567116db-efca-4b9f-89ea-ab99cda05881");
            params1.put("json", JsonTool.toJson(jsonMap));
            HttpAPI.sign(params1, this, new UICallBack() {
                @Override
                public void result(Object result) {

                }

                @Override
                public void fail(String msg) {

                }
            });

            intent.putExtra(Constraint.UP_TYPE,"0");
        }

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 1000){

                ToastUtils.showLong((String) data.getExtras().get("city"));
            }
        }
    }
}
