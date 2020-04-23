package com.example.uplibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.uplibrary.adapter.Page2Adapter;
import com.example.uplibrary.adapter.TicketAdapter;
import com.example.uplibrary.bean.FaceTicketVo;
import com.example.uplibrary.bean.FaceVo;
import com.example.uplibrary.http.HttpAPI;
import com.example.uplibrary.http.HttpGetUtil;
import com.example.uplibrary.http.JsonTool;
import com.example.uplibrary.http.ResultCallBack;
import com.example.uplibrary.http.UICallBack;
import com.example.uplibrary.service.MyService;
import com.example.uplibrary.testcall.MyLocationListener;
import com.example.uplibrary.widget.PicassoImageEngine;
import com.example.uplibrary.widget.city.CityPickerActivity;
import com.maning.imagebrowserlibrary.ImageEngine;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.maning.imagebrowserlibrary.model.ImageBrowserConfig;
import com.sunfusheng.marqueeview.MarqueeView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpMainActivity extends AppCompatActivity {

    private Button oneBtn;
    private Button twoBtn;
    private Button currBtn,flowerBtn;
    private RecyclerView recyclerView;
    private ArrayList<FaceTicketVo> datas = new ArrayList<>();
    private TicketAdapter ticketAdapter;
    private SwipeRefreshLayout refresh_view;
    private ViewPager2 viewPager2;
    private MarqueeView marqueeView;

    private MyService myService;
    private boolean isBound = false;
    private ServiceConnection conn;

    private LocationClient locationClient;

    private MapView mapView;
    private BaiduMap mBaiduMap;

    class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {


            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            LogUtils.d("latitude:"+ new BigDecimal(latitude).toString(),
                    "longitude:"+new BigDecimal(longitude).toString(),"addr:"+location.getAddrStr());


            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

            if (location == null || mapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
        }
    }

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
        viewPager2 = findViewById(R.id.view_pager2);
        marqueeView = findViewById(R.id.marque_view);

        //百度地图部分
        mapView = findViewById(R.id.map_view);
        mapView.setLogoPosition(LogoPosition.logoPostionleftBottom);
        mBaiduMap = mapView.getMap();

        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        builder.target(new LatLng(30.2849159999,120.1523159));
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        mBaiduMap.setMyLocationEnabled(true);

        //定义Maker坐标点
        LatLng point = new LatLng(30.2949159999, 120.1723159);
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.map);

        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        Marker marker = (Marker) mBaiduMap.addOverlay(option);
        Bundle bundle = new Bundle();
        FaceVo faceVo = new FaceVo();
        faceVo.type = "face";
        bundle.putSerializable("obj",faceVo);
        marker.setExtraInfo(bundle);



        //marker点击事件
        BaiduMap.OnMarkerClickListener markerClickListener = new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                FaceVo faceVo1 = (FaceVo) marker.getExtraInfo().getSerializable("obj");
                ToastUtils.showLong(faceVo1.type);
                return false;
            }
        };
        mBaiduMap.setOnMarkerClickListener(markerClickListener);
        //地图事件回调
        BaiduMap.OnMapStatusChangeListener statusChangeListener = new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

                String type = "";
                if (i==1){
                    type = "手势操作";

                }else if(i == 2){

                    type = "地图API自己调用";

                }else {
                    type = "开发者调用";
                }
                ToastUtils.showLong("handletype:"+type);
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {

                ToastUtils.showLong(mapStatus.target.latitude+""+"\n"+mapStatus.target.longitude+"");
                LogUtils.d(mapStatus.target.latitude+"",mapStatus.target.longitude+"");
            }
        };
        mBaiduMap.setOnMapStatusChangeListener(statusChangeListener);



        //

        List<String>msgs = new ArrayList<>();
        msgs.add("附近快到了发动机李开复家里开附近快到了发动机李开复家里开附近快到了发动机李开复家里开................");
        msgs.add("附近快到了发动..........");

        marqueeView.startWithList(msgs);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {


            }
        });



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
                overridePendingTransition(R.anim.activity_anmie_alpha_leftscal_in,R.anim.activity_anmie_alpha_scal_out);

            }
        });
        Button indiButton = findViewById(R.id.indicator_btn);
        indiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils.startActivity(IndicatorActivity.class);
                overridePendingTransition(R.anim.activity_anmie_zoomin,R.anim.activity_anmie_zoomout);
            }
        });

        Button vpButton = findViewById(R.id.vp_fragment_btn);
        vpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils.startActivity(VPFragmentActivity.class);
                overridePendingTransition(R.anim.activity_anmie_alpha_scal_in,R.anim.activity_anmie_alpha_scal_out);

            }
        });
        Button videoButton = findViewById(R.id.video_btn);
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils.startActivity(VideoActivity.class);

            }
        });
        Button scryButton = findViewById(R.id.scry_btn);
        scryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils.startActivity(ScrollYActivity.class);


            }
        });
        Button fastButton = findViewById(R.id.fast_btn);
        fastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils.startActivity(FastActivity.class);


            }
        });
        Button cityButton = findViewById(R.id.city_btn);
        cityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UpMainActivity.this, CityPickerActivity.class);
                startActivityForResult(intent,1000);
                overridePendingTransition(R.anim.activity_anmie_in,R.anim.activity_anmie_out);


            }
        });
        Button merageButton = findViewById(R.id.merage_btn);
        merageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils.startActivity(MerageActivity.class);
            }
        });
        Button locaButton = findViewById(R.id.location_btn);
        locaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationClient = new LocationClient(getApplicationContext());
                locationClient.registerLocationListener(new MyLocationListener());
                LocationClientOption option = new LocationClientOption();
                option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
                option.setScanSpan(0);
                option.setCoorType("bd09ll");
                option.setOpenGps(true);
                locationClient.setLocOption(option);
                locationClient.restart();
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

        final ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                isBound = true;
                MyService.MyBinder myBinder = (MyService.MyBinder) service;
                myService = myBinder.getService();
                myService.setFlag("flag haved changed from activity");


            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

                isBound = false;
            }
        };
        this.conn = serviceConnection;

        Button serverButton = findViewById(R.id.server_btn);
        serverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UpMainActivity.this,MyService.class);
                bindService(intent,serviceConnection,BIND_AUTO_CREATE);
                startService(intent);
            }
        });


        initRecycler();
        initViewPage2();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
        mapView.onDestroy();
    }

    private void initViewPage2() {

        List<String>datas = new ArrayList<>();
        for (int i=0;i<12;i++){

            datas.add(i+"");
        }
        Page2Adapter page2Adapter = new Page2Adapter(datas);
        viewPager2.setAdapter(page2Adapter);
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.setCurrentItem(0);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ToastUtils.showLong(position+"");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
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
