package com.sendinfo.androidkit.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.othershe.nicedialog.NiceDialog;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.mvp.IPresenter;
import com.sendinfo.androidkit.mvp.IView;

import com.sendinfo.androidkit.widget.MyTopNavBar;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public abstract class BaseMVPActivity<T extends IPresenter>
        extends RxAppCompatActivity
        implements IView

{

    protected MyTopNavBar myTopNavBar;
    protected T mPresenter;
    protected RxPermissions rxPermissions;
    protected MyApplication myApplication;
    private LinearLayout parentLinearLayout;
    private SweetAlertDialog mSweetAlertDialog;
    private InputMethodManager inputMethodManager;
    private NiceDialog loadingDialog;



    protected abstract void initArgs(Intent intent);

    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getLayoutId();

    //生命周期
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = getAppApplication();
        initContentView(R.layout.activity_base_mvp);
        setContentView(getLayoutId());
        configTopBar();
        ButterKnife.bind(this);
        rxPermissions = new RxPermissions(this);

        ImmersionBar.with(this).init();

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            initArgs(getIntent());
            initView();
            initData();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mPresenter != null) {

            mPresenter.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        if (mPresenter != null) {

            mPresenter.onDestroy();
        }
        if (mSweetAlertDialog != null){

            mSweetAlertDialog.dismiss();
        }
        if (loadingDialog != null){

            loadingDialog.dismiss();
        }

        EventBus.getDefault().unregister(this);

        ImmersionBar.with(this).destroy();

        super.onDestroy();

    }
    private void configTopBar(){

        myTopNavBar = findViewById(R.id.my_top_bar);
        myTopNavBar.setBackClickListener((v)->finish());
    }

    //IView 方法

    @Override
    public void showProgressDialog() {


        loadingDialog =(NiceDialog)NiceDialog.init()
                .setLayoutId(R.layout.loading_layout)
                .setWidth(135)
                .setHeight(135)
                .setOutCancel(false)
                .setDimAmount(0)
                .show(getSupportFragmentManager());


    }
    @Override public void showDialog(int type, String title, String content)
    {
        showDialog(type, title, content, "确定", null, new SweetAlertDialog.OnSweetClickListener()
        {
            @Override public void onClick(SweetAlertDialog sweetAlertDialog)
            {
                sweetAlertDialog.dismiss();
            }
        }, null);
    }

    @Override
    public void showDialog(int type, String title, String content, String confirmText, String cancelText, SweetAlertDialog.OnSweetClickListener confirmListener, SweetAlertDialog.OnSweetClickListener cancelListener)
    {
        if(mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
        {
            mSweetAlertDialog.changeAlertType(type);
        }
        else
        {
            mSweetAlertDialog = new SweetAlertDialog(this, type);
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
    public void showToast(String msg) {

        ToastUtils.showShort(msg);
    }

    @Override
    public void dismissDialog() {

        if (mSweetAlertDialog != null) {

            mSweetAlertDialog.dismiss();
        }

    }

    @Override
    public void dismissDialogForRequest() {

        if (loadingDialog != null){

            loadingDialog.dismiss();
        }
    }

    @NonNull @Override public LifecycleTransformer<Object> bindLifeCycle() {

        return this.bindUntilEvent(ActivityEvent.DESTROY);
    }

    private static long mLastClickTime;
    public static final int MIN_CLICK_DELAY_TIME = 500;

    @Override
    public boolean isFastClick() {

        // 当前时间
        long currentTime = System.currentTimeMillis();
        // 两次点击的时间差
        long time = currentTime - mLastClickTime;
        if (0 < time && time < MIN_CLICK_DELAY_TIME) {
            return true;
        }
        mLastClickTime = currentTime;
        return false;
    }

    @Override
    public void showKeyBoard(EditText editText) {

        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

    }

    @Override
    public void hideKeyBoard() {

        try {
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ignored) {
        }
    }

    //如果不是新建Activity回传值

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    protected MyApplication getAppApplication() {
        if (null == myApplication) {
            myApplication = (MyApplication) getApplication();
        }
        return myApplication;
    }

    /**
     * 重新初始化根布局
     *
     * @param layoutResID
     */
    private void initContentView(@LayoutRes int layoutResID) {

        ViewGroup viewGroup = findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(parentLinearLayout);
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);

    }
    @Override
    public void setContentView(@LayoutRes int layoutResID){

        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);

    }

    /**
     * 点击软键盘之外的空白处，隐藏软件盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    protected boolean isShouldHideInput(View v, MotionEvent event) {

        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public DisplayMetrics getDisplayMetrics() {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics;
    }

    @Subscribe
    public void onEvent(NotingEvent event) {
    }

    private class NotingEvent {
    }


    protected void checkPermission(CheckPermissionListener checkPermissionListener, String... permissions) {
        rxPermissions.requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (checkPermissionListener != null)
                            checkPermissionListener.onPermissionBack(permission);
                        if (permission.granted) {
                            // 用户已经同意该权限
                            LogUtils.d(permission.name + " is granted.");

                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，
                            // 还会提示请求权限的对话框
                            LogUtils.d(permission.name + " is denied. More info should be provided.");

                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            LogUtils.d(permission.name + " is denied.");
                        }
                    }
                });
    }

    public interface CheckPermissionListener {
        void onPermissionBack(Permission permission);
    }
}
