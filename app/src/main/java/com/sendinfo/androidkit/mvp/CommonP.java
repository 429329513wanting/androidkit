package com.sendinfo.androidkit.mvp;

import com.blankj.utilcode.util.ToastUtils;
import com.sendinfo.androidkit.util.JsonUtil.JsonUtil;


/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/18
 *     desc   :
 * </pre>
 */

public class CommonP extends IPresenterImpl<ICommonView ,BaseResponse> implements IPresenter{
    public CommonP(ICommonView view) {
        super(view);
    }

    @Override
    public void requestSuccess(String data, HttpDto httpDto) {
        super.requestSuccess(data,httpDto);

        BaseResponse baseResponse = null;
        try {

            baseResponse = JsonUtil.getObject(data,BaseResponse.class);


        }catch (Exception e){

            ToastUtils.showLong("解析异常");
            e.printStackTrace();
        }

        if (baseResponse != null){

            mView.Success(baseResponse);
        }

    }
}
