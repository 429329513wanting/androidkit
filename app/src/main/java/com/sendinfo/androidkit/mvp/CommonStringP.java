package com.sendinfo.androidkit.mvp;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/18
 *     desc   :
 * </pre>
 */

public class CommonStringP extends IPresenterImpl<ICommonStringView, String> implements IPresenter {
    public CommonStringP(ICommonStringView view) {
        super(view);
    }

    @Override
    public void requestSuccess(String data, HttpDto httpDto) {
        super.requestSuccess(data, httpDto);
        mView.onSucess(data, httpDto);
    }

    @Override
    public void requestFail(String msg, HttpDto httpDto) {
        super.requestFail(msg, httpDto);

    }
}
