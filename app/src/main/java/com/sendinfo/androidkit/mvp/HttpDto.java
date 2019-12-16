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
    private int method = POST;
    private Object tag;


    //是否显示加载网络提示框
    private boolean silence = false;

    private Map<String, String> params;

    //一般的字符串提交
    private String bodyStr;

    //json格式字符串提交
    private String bodyJson;

    private BaseModel baseModel;


    private Map<String, String> headers;

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
                                +":"+SPUtils.getInstance().getString(Constraint.BUSINESS_PORT))
                .replace("!!!",Constraint.SERVER_TWO_IP);

        headers = new HashMap<>();
        this.silence = silence;
    }

    public Request getRequest(){

        Request request = null;
        if (method == POST){

            request = OkGo.post(url);

        }else if(method == GET){

            request = OkGo.get(url);

        }else if(method == DELETE){

            request = OkGo.delete(url);

        }else if(method == PUT){

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
        if(headers != null)
        {
            for(Map.Entry<String, String> entry : headers.entrySet())
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

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpDto setHeaders(Map<String, String> heads) {
        this.headers = heads;
        return this;
    }

    public int getMethod() {

        return method;
    }

    public HttpDto setMethod(int method) {

        this.method = method;
        return this;
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

    public HttpDto setBaseModel(BaseModel baseModel) {
        this.baseModel = baseModel;
        this.params = JsonUtil.getMapForObj(this.baseModel);
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

    public HttpDto setSilence(boolean silence) {
        this.silence = silence;
        return this;
    }

    public void print()
    {
        KLog.d("╔══════════════════════════════════════════════════");
        KLog.d("║    "+getHttpmethod()+":"+url);
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

        if(headers != null)
        {
            KLog.d("║    请求头为:");
            for(Map.Entry<String, String> entry : headers.entrySet())
            {
                KLog.d("║    "+entry.getKey()+" = "+entry.getValue());
            }
        }

        KLog.d("╚══════════════════════════════════════════════════");

    }
    private String getHttpmethod(){

        if (method == POST){

            return "POST";

        }else if (method == GET){

            return "GET";

        }else if (method == DELETE){

            return "DELETE";

        }else if (method == PUT){

            return "PUT";
        }
        return "";
    }
}
