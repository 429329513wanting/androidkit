package com.sendinfo.androidkit.mvp;

import android.widget.EditText;

import com.trello.rxlifecycle2.LifecycleTransformer;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/17
 *     desc   :
 * </pre>
 */

public interface IView {

    void showToast(String msg);
    void showProgressDialog();
    void showSweetDialog(int type, String title, String content);
    void dismissDialog();
    void dismissDialogForRequest();
    void showSweetDialog(int type,
                    String title,
                    String content, String confirmText, String cancelText, SweetAlertDialog.OnSweetClickListener confirmListener,
                    SweetAlertDialog.OnSweetClickListener cancelListener);

    LifecycleTransformer<Object> bindLifeCycle();
    boolean isFastClick();
    void hideKeyBoard();
    void showKeyBoard(EditText editText);

    SweetAlertDialog.OnSweetClickListener getFinishListener();

}
