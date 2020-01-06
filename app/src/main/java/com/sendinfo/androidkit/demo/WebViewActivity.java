package com.sendinfo.androidkit.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qmuiteam.qmui.widget.QMUIProgressBar;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.base.BaseMVPActivity;
import com.ycbjie.webviewlib.BridgeHandler;
import com.ycbjie.webviewlib.CallBackFunction;
import com.ycbjie.webviewlib.InterWebListener;
import com.ycbjie.webviewlib.ProgressWebView;
import com.ycbjie.webviewlib.X5WebView;

import butterknife.BindView;

public class WebViewActivity extends BaseMVPActivity {

    @BindView(R.id.web_view)
    X5WebView webView;
    @BindView(R.id.pb)
    QMUIProgressBar pb;


    public static final String url = "http://220.191.224.192:8085/app/bigdata/search_gis.html?token=e767d052-8e07-4b23-bda4-7785446b666f&scenicId=404&componentType=1";
    public static final String url2 = "http://bgappceshi.sendinfo.com.cn//app/Pages/dataAnalysis/year_report.html?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndGNsIiwiYXV0aCI6Imx2Z01hbmFnZXIiLCJ1c2VyX3VuaXF1ZV9rZXkiOiJ1c2VyOmJhY2tncm91bmRfdXNlcl9jYWNoZTpndGNsOjZkMTZkODdhM2UxZjYwNmJmYjdiYjM3YmVlMjMyMzg0IiwiZXhwIjoxNTc4MTIwODIwfQ.PhTUkUcuBhfD1TB0ZpOkcRPNeOOotPUB5waiQapVolKR93yHutnzlpbnDgwFM5yJD212zcFO3ix_ptDymoIn0w&usertype=&scenicId=996&name=gtcl&realName=%E6%A1%82%E6%9E%97%E6%97%85%E8%82%A1";
    public static final String url3 = "http://bgguankongtest.sendinfo.com.cn//app/amaster/event_index.html?corpName=%E6%A0%87%E5%87%86%E6%99%AF%E5%8C%BA&token=574ae3dd-9721-416d-8c30-2ab3f47e7b2c&usertype=&scenicId=&name=vmp&realName=vip%E7%94%A8%E6%88%B7&userId=4";


    @Override
    protected void initArgs(Intent intent) {


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null){

            webView.getSettings().setJavaScriptEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null){

            webView.getSettings().setJavaScriptEnabled(false);
        }
    }

    @Override
    protected void initView() {

        myTopNavBar.setTitle("详情");
        webView.loadUrl(url3);
        webView.getX5WebChromeClient().setWebListener(interWebListener);
        webView.getX5WebViewClient().setWebListener(interWebListener);

        webView.registerHandler("callAndroid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {

                LogUtils.d(data);
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    private InterWebListener interWebListener = new InterWebListener() {
        @Override
        public void hindProgressBar() {

            pb.setVisibility(View.GONE);

        }

        @Override
        public void showErrorView(int type) {

            ToastUtils.showLong("加载失败");
        }

        @Override
        public void startProgress(int newProgress) {

            pb.setProgress(newProgress);

        }

        @Override
        public void showTitle(String title) {

        }
    };

}
