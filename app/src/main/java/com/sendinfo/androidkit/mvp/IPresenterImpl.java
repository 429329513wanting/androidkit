package com.sendinfo.androidkit.mvp;

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
    public void requestSuccess(V response, HttpDto httpDto) {

        if (isViewExist()){

            mView.dismissDialog();
        }
    }

    @Override
    public void requestFail(String msg, HttpDto httpDto) {


        if (isViewExist()){

            mView.dismissDialogForRequest();

            String title = "";
            String info = "";
            if(msg.contains("Timeout"))
            {
                title = "连接服务器超时";
                info = "数据加载失败，请重试！";
            }
            else if(msg.contains("504"))
            {
                title = "无网络服务";
                info = "请检查网络后重试！";
            }
            else if(msg.contains("Failed to connect"))
            {
                title = "服务器异常";
                info = "请稍后重试！";
            }
            else if(msg.contains("网络请求失败"))
            {
                title = "网络请求失败";
                info = "请稍后重试！";
            }
            else
            {
                title = "对不起，访问出错了";
                info = "请稍后重试！";
            }


            if (httpDto.isTryAgain()){

                if (httpDto.isFinish()){

                    mView.showSweetDialog(SweetAlertDialog.ERROR_TYPE, title, info, "重试", "取消", new SweetAlertDialog.OnSweetClickListener()
                    {
                        @Override public void onClick(SweetAlertDialog sweetAlertDialog)
                        {
                            getData(httpDto);
                        }
                    }, mView.getFinishListener());

                }else {

                    mView.showSweetDialog(SweetAlertDialog.ERROR_TYPE, title, info, "重试", "取消", new SweetAlertDialog.OnSweetClickListener()
                    {
                        @Override public void onClick(SweetAlertDialog sweetAlertDialog)
                        {
                            getData(httpDto);
                        }
                    }, null);
                }

            }else {


                mView.showSweetDialog(SweetAlertDialog.ERROR_TYPE,title,msg);

            }

        }

    }

    @Override
    public boolean isViewExist() {

        return mView != null;
    }

    @Override
    public void requestSuccess(Observable<V> observable, final HttpDto httpDto) {

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

                //调用了网络回调RequestCallBack的requestSuccess
                requestSuccess((V)o,httpDto);

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
