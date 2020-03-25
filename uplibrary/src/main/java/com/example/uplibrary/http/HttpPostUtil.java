package com.example.uplibrary.http;

import android.app.Activity;
import android.app.Application;
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
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HttpPostUtil extends Thread {

    private String url;
    private Map<String,String>formMap;
    private ResultCallBack callBack;
    private Activity activity;

    private HttpURLConnection connection;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private String line;
    private String response = null;
    private String parameter = null;
    private boolean needSign = true;//是否需要签名

    private SweetAlertDialog processDialog = null;

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


    public HttpPostUtil(String url, Map<String,String>params, Activity activity, ResultCallBack callBack){

        this.url = url;
        this.formMap = params;
        this.callBack = callBack;
        this.activity = activity;
        Utils.init(activity);
    }


    public HttpPostUtil(String url, Map<String,String>params, Activity activity, boolean needSign, ResultCallBack callBack){

        this.url = url;
        this.formMap = params;
        this.callBack = callBack;
        this.activity = activity;
        this.needSign = needSign;
        Utils.init(activity);


    }

    @Override
    public void run() {
        super.run();

        //需要签名机制
        if (needSign){

            this.formMap.put("requestId", UUID.randomUUID().toString());
            this.formMap.put("appId","111111");
            this.formMap.put("version","1.0");
            this.formMap.put("timestampt",System.currentTimeMillis()+"");
            this.formMap.put("sign",buildServerSign(this.formMap,"SECRET"));
        }
        handler.sendEmptyMessage(showProcess);

        LogUtils.d("请求url:"+this.url+"\n"+"请求参数"+"\n"+jsonDataConnect(formMap));



        try {

            if (this.url.equals("") || url == null){

                LogUtils.d("url为空");

                return;

            }
            this.connection = (HttpURLConnection)new URL(url.trim()).openConnection();
            this.connection.setDoInput(true);
            this.connection.setDoOutput(true);
            this.connection.setUseCaches(false);
            this.connection.setRequestMethod("POST");
            this.connection.setReadTimeout(15000);
            this.connection.setConnectTimeout(15000);
            this.connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            parameter = formDataConnect(this.formMap);
            this.connection.setRequestProperty("Content-Length",parameter.getBytes().length+"");

            //connection.setRequestProperty("Content-Type", "application/json");
            //parameter = jsonDataConnect(this.formMap);


            this.connection.connect();

            if (formMap != null && formMap.isEmpty() == false){

                printWriter = new PrintWriter(connection.getOutputStream());
                printWriter.print(parameter);
                printWriter.flush();

            }
            if (connection.getResponseCode() != 200){

                LogUtils.d("responseCode"+"\n"+connection.getResponseCode()+connection.getResponseMessage());
                ToastUtils.showLong(connection.getResponseCode()+"===="+connection.getResponseMessage());

                Message message = new Message();
                message.what = responseFail;
                message.obj = connection.getResponseMessage();
                handler.sendMessage(message);

                return;
            }
            StringBuilder response_cache = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            while ((line = bufferedReader.readLine()) != null){

                response_cache.append(line);

            }
            response = response_cache.toString().trim();
            LogUtils.d("response"+"\n"+JsonTool.jsonPrintFormat(response));


            Message message = new Message();
            message.what = responseSuccess;
            message.obj = message;
            handler.sendMessage(message);



        }catch (final Exception e){


            e.printStackTrace();
            Message message = new Message();
            message.what = responseFail;
            message.obj = e.getLocalizedMessage();
            handler.sendMessage(message);

        }finally {

            try {

                if (printWriter != null){

                    printWriter.close();
                }
                if (bufferedReader != null){
                    bufferedReader.close();
                }
                if (connection != null){
                    connection.disconnect();
                }

            }catch (Exception e){

                e.printStackTrace();
                LogUtils.d("关闭流异常");
                Message message = new Message();
                message.what = responseFail;
                message.obj = "关闭流异常";
                handler.sendMessage(message);


            }
        }

    }

    /**
     * 参数转换函数
     * map -> http[post] 参数
     * @param form_data
     * @return
     */
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

    //开始发请求
    public void execRequest(){

        this.start();
    }

    //算签名
    public  String buildServerSign(Map<String, String> params, String secret) {
        Map<String, String> map = new TreeMap<>(params);
        map.remove("sign");
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : map.entrySet()) {
            if (e.getValue().isEmpty() == false) {
                sb.append(e.getKey()).append(e.getValue());
            }
        }
        String source = secret + sb.toString() + secret;
        return getMd5Value(source).toUpperCase();
    }


    public  String getMd5Value(String sSecret) {
        try {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            bmd5.update(sSecret.getBytes());
            int i;
            StringBuffer buf = new StringBuffer();
            byte[] b = bmd5.digest();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Application getAppContext(){

        Application application = null;
        Class<?> activityThreadClass;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            final Method method2 = activityThreadClass.getMethod(
                    "currentActivityThread", new Class[0]);
            // 得到当前的ActivityThread对象
            Object localObject = method2.invoke(null, (Object[]) null);

            final Method method = activityThreadClass
                    .getMethod("getApplication");
            application = (Application) method.invoke(localObject, (Object[]) null);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return application;
    }
}
