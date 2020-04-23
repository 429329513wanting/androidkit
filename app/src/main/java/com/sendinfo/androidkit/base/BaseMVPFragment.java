package com.sendinfo.androidkit.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.othershe.nicedialog.NiceDialog;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.mvp.IPresenter;
import com.sendinfo.androidkit.mvp.IView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/18
 *     desc   :
 * </pre>
 */

public abstract class BaseMVPFragment<T extends IPresenter>
        extends RxFragment implements IView {

    // 将代理类通用行为抽出来
    protected T mPresenter;
    private LayoutInflater inflater;
    private InputMethodManager mInputMethodManager;
    private ViewGroup container;
    public View mView;
    private SweetAlertDialog mSweetAlertDialog;
    private NiceDialog loadingDialog;


    protected abstract void initArgs(Bundle bundle);

    protected abstract void initView(Bundle bundle);

    protected abstract void initData();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        this.inflater = inflater;
        this.container = container;
        mInputMethodManager =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        try
        {
            initArgs(getArguments());
            initView(savedInstanceState);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return mView;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        initData();
        super.onViewCreated(view, savedInstanceState);
    }

    //子类重写
    protected void setContentView(int layout)
    {
        mView = inflater.inflate(layout, container, false);
        ButterKnife.bind(this,mView);
    }
    @Override public void onResume()
    {
        super.onResume();
        if (mPresenter != null)
        {
            mPresenter.onResume();
        }
    }

    @Override public void onPause()
    {
        super.onPause();
    }

    @Override public void onDestroyView()
    {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
        {
            mSweetAlertDialog.dismiss();
            mSweetAlertDialog = null;
        }

        EventBus.getDefault().unregister(this);
        super.onDestroyView();
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (null != parent)
        {
            parent.removeView(mView);
        }
    }

    @Override public void onDestroy()
    {
        super.onDestroy();
        if (mPresenter != null)
        {
            mPresenter.onDestroy();
        }
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    //IView

    @Override
    public void showToast(String msg) {

        if (!StringUtils.isEmpty(msg))
        {
            ToastUtils.showLong(msg);
        }
    }

    @Override
    public void showProgressDialog() {

        loadingDialog =(NiceDialog)NiceDialog.init()
                .setLayoutId(R.layout.loading_layout)
                .setWidth(135)
                .setHeight(135)
                .setOutCancel(true)
                .setDimAmount(0)
                .show(((BaseMVPActivity) getActivity()).getSupportFragmentManager());
    }


    @Override public void showSweetDialog(int type, String title, String content)
    {
        showSweetDialog(type, title, content, "确定", null, new SweetAlertDialog.OnSweetClickListener()
        {
            @Override public void onClick(SweetAlertDialog sweetAlertDialog)
            {
                sweetAlertDialog.dismiss();
            }
        }, null);
    }

    @Override
    public void showSweetDialog(int type, String title, String content, String confirmText, String cancelText, SweetAlertDialog.OnSweetClickListener confirmListener, SweetAlertDialog.OnSweetClickListener cancelListener)
    {
        if(mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
        {
            mSweetAlertDialog.changeAlertType(type);
        }
        else
        {
            mSweetAlertDialog = new SweetAlertDialog(getActivity(), type);
            mSweetAlertDialog.setCancelable(false);
        }
        // Title
        if(!StringUtils.isEmpty(title))
        {
            mSweetAlertDialog.setTitleText(title);
        }
        else
        {
            mSweetAlertDialog.setTitleText("");
        }
        // content
        if(!StringUtils.isEmpty(content))
        {
            mSweetAlertDialog.setContentText(content);
        }
        else
        {
            mSweetAlertDialog.showContentText(false);
        }
        // confirmText
        if(!StringUtils.isEmpty(confirmText))
        {
            mSweetAlertDialog.setConfirmText(confirmText);
        }
        // cancelText
        if(!StringUtils.isEmpty(cancelText))
        {
            mSweetAlertDialog.setCancelText(cancelText);
        }
        else
        {
            mSweetAlertDialog.showCancelButton(false);
        }
        // confirmListener
        if(confirmListener != null)
        {
            mSweetAlertDialog.setConfirmClickListener(confirmListener);
        }
        else
        {
            mSweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
            {
                @Override public void onClick(SweetAlertDialog sweetAlertDialog)
                {
                    sweetAlertDialog.dismiss();
                }
            });
        }
        // confirmListener
        if(confirmListener != null)
        {
            mSweetAlertDialog.setCancelClickListener(cancelListener);
        }
        else
        {
            mSweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener()
            {
                @Override public void onClick(SweetAlertDialog sweetAlertDialog)
                {
                    sweetAlertDialog.dismiss();
                }
            });
        }
        mSweetAlertDialog.show();
    }


    @Override
    public void dismissDialog() {


        if (mSweetAlertDialog != null){

            mSweetAlertDialog.dismiss();
        }
    }
    @Override
    public void dismissDialogForRequest() {

        if (loadingDialog != null){

            loadingDialog.dismiss();
        }
    }

    @Override
    public LifecycleTransformer<Object> bindLifeCycle() {
        return this.bindUntilEvent(FragmentEvent.DESTROY);
    }

    private static long mLastClickTime;
    public static final int MIN_CLICK_DELAY_TIME = 500;

    @Override
    public boolean isFastClick()
    {
        // 当前时间
        long currentTime = System.currentTimeMillis();
        // 两次点击的时间差
        long time = currentTime-mLastClickTime;
        if(0 < time && time < MIN_CLICK_DELAY_TIME)
        {
            return true;
        }
        mLastClickTime = currentTime;
        return false;
    }

    @Override
    public void showKeyBoard(EditText editText) {

        mInputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

    }
    /**
     * 隐藏键盘
     */

    @Override
    public void hideKeyBoard() {

        try
        {
            mInputMethodManager
                    .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ignored)
        {
        }
    }
}
