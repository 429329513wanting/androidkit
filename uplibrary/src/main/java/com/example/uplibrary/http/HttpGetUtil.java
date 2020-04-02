package com.example.uplibrary.http;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;



public class HttpGetUtil extends Thread {

    private String url_path,url;
    private Map<String,String>formMap;
    private ResultCallBack callBack;
    private Activity activity;
    private SweetAlertDialog processDialog = null;
    String response = "";


    private final static int responseSuccess = 0x00;
    private final static int responseFail = 0x01;
    private final static int showProcess = 0x02;



    private Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == responseSuccess){

                if (processDialog != null){

                    processDialog.dismiss();
                }
                String response = String.valueOf(msg.obj);

                if (response != null && response.isEmpty() == false){

                    callBack.onSuccess(response);

                }else {

                    ToastUtils.showLong("服务器返回空");
                }


            }else if(msg.what == responseFail){

                if (processDialog != null){

                    processDialog.dismiss();
                }
                ToastUtils.showLong(String.valueOf(msg.obj));
                callBack.onFail(String.valueOf(msg.obj));


            }else if(msg.what == showProcess){

                if (processDialog == null){

                    processDialog = new SweetAlertDialog(activity,SweetAlertDialog.PROGRESS_TYPE);
                    processDialog.setContentText("加载中...");
                    processDialog.setContentTextSize(14);
                    processDialog.show();

                }

            }

        }
    };


    public HttpGetUtil(String url, Map<String,String>params, final Activity activity,  ResultCallBack callBack){


        Utils.init(activity);
        this.url = url;
        this.activity = activity;
        this.callBack = callBack;
        this.formMap = params;

    }

    @Override
    public void run() {
        super.run();

        handler.sendEmptyMessage(showProcess);

        //接口地址
        url_path = url+"?"+formDataConnect(this.formMap);
        try{
            LogUtils.d("请求url:"+url_path+"\n");

            //使用该地址创建一个 URL 对象
            URL nurl = new URL(url_path);
            //使用创建的URL对象的openConnection()方法创建一个HttpURLConnection对象
            HttpURLConnection httpURLConnection = (HttpURLConnection)nurl.openConnection();
            /**
             * 设置HttpURLConnection对象的参数
             */
            // 设置请求方法为 GET 请求
            httpURLConnection.setRequestMethod("GET");
            //使用输入流
            httpURLConnection.setDoInput(true);
            //GET 方式，不需要使用输出流
            httpURLConnection.setDoOutput(false);
            //设置超时
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(1000);
            //连接
            httpURLConnection.connect();
            //还有很多参数设置 请自行查阅
            //连接后，创建一个输入流来读取response
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            //每次读取一行，若非空则添加至 stringBuilder
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            //读取所有的数据后，赋值给 response
            response = stringBuilder.toString().trim();
            LogUtils.d("response"+"\n"+JsonTool.jsonPrintFormat(response));


            Message message = new Message();
            message.what = responseSuccess;
            message.obj = response;
            handler.sendMessage(message);


            bufferedReader.close();
            httpURLConnection.disconnect();


        }catch (final Exception e){
            e.printStackTrace();

            Message message = new Message();
            message.what = responseFail;
            message.obj = e.getLocalizedMessage();
            handler.sendMessage(message);
        }

    }

    public void execRequest(){

        this.start();
    }

    public String formDataConnect(Map<String,String> form_data){
        StringBuilder url_form = new StringBuilder();
        //遍历map，按照url参数形式拼接
        for(String key:form_data.keySet()){
            if(url_form.length() != 0){
                //从第二个参数开始，每个参数key、value前添加 & 符号
                url_form.append("&");
            }
            url_form.append(key).append("=").append(form_data.get(key));
        }
        return url_form.toString();
    }

    /**
     * 参数转换函数
     * map -> http[post] 参数
     * @params
     * @return
     */
    public String jsonDataConnect(Map<String,String> params){


        Gson gson = new Gson();
        String jsons = gson.toJson(params);
        return jsons;
    }
}