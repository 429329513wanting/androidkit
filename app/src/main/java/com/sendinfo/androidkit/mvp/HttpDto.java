package com.sendinfo.androidkit.mvp;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.base.BodyRequest;
import com.lzy.okgo.request.base.Request;
import com.sendinfo.androidkit.util.Constraint;
import com.sendinfo.androidkit.util.JsonUtil.JsonUtil;
import com.sendinfo.androidkit.widget.KLog.KLog;


import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/17
 *     desc   : 构建请求类
 * </pre>
 */

public class HttpDto {

    public final static int GET = 0x100000;
    public final static int POST = 0x100001;
    public final static int DELETE = 0x100002;
    public final static int PUT = 0x100003;

    private String url;

    private int requestMethod = POST;
    private Object tag;



    //是否显示加载网络提示框
    private boolean silence = false;

    private Map<String, String> params;

    //一般的字符串提交
    private String bodyStr;

    //json格式字符串提交
    private String bodyJson;


    private Map<String, String> heads;

    public HttpDto(String url){

        this(url,false);
    }
    public HttpDto(String url, boolean silence){

        String faceDoman = SPUtils.getInstance().getString(Constraint.FACE_IP);
        if (!StringUtils.isEmpty(SPUtils.getInstance().getString(Constraint.FACE_PORT))){

            faceDoman = faceDoman+":"+ SPUtils.getInstance().getString(Constraint.FACE_PORT);
        }

        this.url = url.replaceAll("###",faceDoman)
                .replaceAll("@@@",
                        SPUtils.getInstance().getString(Constraint.BUSINESS_IP)
                                +":"+SPUtils.getInstance().getString(Constraint.BUSINESS_PORT));

        heads = new HashMap<>();
        this.silence = silence;
    }

    public Request getRequest(){

        Request request = null;
        if (requestMethod == POST){

            request = OkGo.post(url);

        }else if(requestMethod == GET){

            request = OkGo.get(url);

        }else if(requestMethod == DELETE){

            request = OkGo.delete(url);

        }else if(requestMethod == PUT){

            request = OkGo.put(url);

        }

        if (params != null){

            request.params(params);

        }

        if(!StringUtils.isEmpty(bodyStr))
        {
            ((BodyRequest) request).upString(bodyStr);
        }
        if(!StringUtils.isEmpty(bodyJson))
        {
            ((BodyRequest) request).upJson(bodyJson);
        }
        if(heads != null)
        {
            for(Map.Entry<String, String> entry : heads.entrySet())
            {
                request.headers(entry.getKey(), entry.getValue());
            }
        }
        request.tag(this);
        return request;
    }
    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getHeads() {
        return heads;
    }

    public void setHeads(Map<String, String> heads) {
        this.heads = heads;
    }

    public int getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(int requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public String getBodyStr()
    {
        return bodyStr;
    }

    public HttpDto setBodyStr(String bodyStr)
    {
        this.bodyStr = bodyStr;
        return this;
    }

    public String getBodyJson()
    {
        return bodyJson;
    }

    public HttpDto setBodyJson(String bodyJson)
    {
        this.bodyJson = bodyJson;
        return this;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public boolean isSilence() {
        return silence;
    }

    public void setSilence(boolean silence) {
        this.silence = silence;
    }

    public void print()
    {
        KLog.d("╔══════════════════════════════════════════════════");
        KLog.d("║    "+getHttpRequestMethod()+":"+url);
        if(params != null)
        {
            KLog.d("║    请求参数为:");
            for(Map.Entry<String, String> entry : params.entrySet())
            {
                if("data".equals(entry.getKey()))
                {
                    KLog.d("║    "+entry.getKey()+" : ");
                    KLog.jsondata(entry.getValue());
                }
                else
                {
                    KLog.d("║    "+entry.getKey()+" = "+entry.getValue());
                }
            }
        }

        if(!StringUtils.isEmpty(bodyStr))
        {
            KLog.d("║    "+ JsonUtil.jsonPrintFormat(bodyStr));
        }

        if(!StringUtils.isEmpty(bodyJson))
        {
            KLog.d("║    "+JsonUtil.jsonPrintFormat(bodyJson));
        }

        KLog.d("║    ");

        if(heads != null)
        {
            KLog.d("║    请求头为:");
            for(Map.Entry<String, String> entry : heads.entrySet())
            {
                KLog.d("║    "+entry.getKey()+" = "+entry.getValue());
            }
        }

        KLog.d("╚══════════════════════════════════════════════════");

    }
    private String getHttpRequestMethod(){

        if (requestMethod == POST){

            return "POST";
        }else {
            return "GET";
        }
    }
}
