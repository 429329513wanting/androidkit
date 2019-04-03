package com.sendinfo.androidkit.module.mecenter.contract;

import com.sendinfo.androidkit.bean.LoginVo;
import com.sendinfo.androidkit.mvp.IPresenter;
import com.sendinfo.androidkit.mvp.IView;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2019/04/03
 *     desc   :
 * </pre>
 */

public interface LoginContract {

    interface View extends IView {

        void loginSuccess(LoginVo loginVo);
    }

    interface Presenter extends IPresenter{


    }
}
