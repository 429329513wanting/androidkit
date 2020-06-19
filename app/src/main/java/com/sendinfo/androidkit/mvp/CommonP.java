package com.sendinfo.androidkit.mvp;


/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/18
 *     desc   :
 * </pre>
 */

public class CommonP extends IPresenterImpl<ICommonView,BaseResponse> implements IPresenter{
    public CommonP(ICommonView view) {
        super(view);
    }

    @Override
    public void requestSuccess(BaseResponse data, HttpDto httpDto) {

        mView.Success(data,httpDto);

    }
}
