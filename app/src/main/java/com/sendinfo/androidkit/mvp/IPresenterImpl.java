package com.sendinfo.androidkit.mvp;

import com.blankj.utilcode.util.LogUtils;
import com.lzy.okgo.OkGo;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/17
 *     desc   :
 * </pre>
 */

public class IPresenterImpl<T extends IView,V> implements IPresenter,
        RequestCallBack<V> {

    protected T mView;
    private Disposable mDisposable;

    private HttpRequestImpl httpRequest = new HttpRequestImpl();
    public IPresenterImpl(T view){

        mView = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

        OkGo.getInstance().cancelTag(this);
        mView = null;

    }

    @Override
    public void getData(HttpDto httpDto) {

        if (isViewExist()){

            if (!httpDto.isSilence()){

                mView.showProgressDialog();

            }

            httpDto.setTag(this);
            httpRequest.getData((RequestCallBack<BaseResponse>) this,httpDto);
        }
    }


    @Override
    public void requestSuccess(String s, HttpDto httpDto) {

        if (isViewExist()){

            mView.dismissDialog();
        }
    }

    @Override
    public void requestFail(String msg, HttpDto httpDto) {


        if (isViewExist()){

            mView.showSweetDialog(SweetAlertDialog.ERROR_TYPE,"提示",msg);

            mView.dismissDialogForRequest();

        }

    }

    @Override
    public boolean isViewExist() {

        return mView != null;
    }

    @Override
    public void requestSuccess(final Observable<String> observable, final HttpDto httpDto) {

        if (isViewExist()){

            mView.dismissDialog();
            mView.dismissDialogForRequest();

        }

        observable.compose(mView.bindLifeCycle()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>()
        {
            @Override public void onSubscribe(Disposable disposable)
            {
                mDisposable = disposable;
            }

            @Override public void onNext(Object o)
            {

                requestSuccess((String) o,httpDto);

            }

            /**
             * 解析错误会回调这里,如果提前进行解析
             * @param e
             */
            @Override public void onError(Throwable e)
            {
                requestFail("数据格式无法正常解析",httpDto);

            }

            @Override public void onComplete()
            {

            }
        });

    }
}
