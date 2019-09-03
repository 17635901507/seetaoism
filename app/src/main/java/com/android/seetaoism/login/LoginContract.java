package com.android.seetaoism.login;

import com.android.seetaoism.basef.IBaseCallBack;
import com.android.seetaoism.basef.IBasePresenter;
import com.android.seetaoism.basef.IBaseView;
import com.android.seetaoism.data.entity.User;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

public interface LoginContract {
    // -------------- 密码登录 -------------------

    public interface ILoginPsView extends IBaseView<ILoginPsPresenter> {

        void onLoginSuccess(User user);

        void onLoginFail(String msg);
    }


    public interface ILoginPsPresenter extends IBasePresenter<ILoginPsView> {

        void login(String phoneNum, String ps);
    }

    // -------------- 密码登录 -------------------


    // -------------- 验证码登录 -------------------
    public interface ILoginCodeView extends IBaseView<ILoginCodePresenter> {

    }


    public interface ILoginCodePresenter extends IBasePresenter<ILoginCodeView> {

    }

    // -------------- 验证码登录 -------------------


    // -------------- 注册 -------------------
    public interface IRegisterView extends IBaseView<IRegisterPresenter> {
        void onSmsCodeResult(String msg,boolean success);
        void onVerifySmsCodeResult(String msg,boolean success);
    }


    public interface IRegisterPresenter extends IBasePresenter<IRegisterView> {
        void getSmsCode(String phoneNumber);
        void verifySmsCode(String phoneNumber,String code);
    }

    // -------------- 注册 -------------------


    // -------------- 注册确认密码 -------------------
    interface IRegisterSetPsdView extends IBaseView<IRegisterSetPsdPresenter>{
        void onRegisterResultSuccess(User user);
        void onRegisterResultFail(String msg);
    }

    interface IRegisterSetPsdPresenter extends IBasePresenter<IRegisterSetPsdView>{
        void register(String phoneNumber,String psd,String confirmPsd);
    }
    // -------------- 注册确认密码 -------------------


    // -------------- 获取用户信息 -------------------
    interface ILoginGetUserInfoView extends IBaseView<ILoginGetUserInfoPresenter>{
        void onUserInfoSuccess(User user);
        void onUserInfoFail(String str);
    }
    interface ILoginGetUserInfoPresenter extends IBasePresenter<ILoginGetUserInfoView>{
        void getUserInfoByToken(String token);
    }
    // -------------- 获取用户信息 -------------------


    interface ILoginModel {

        void loginByPs(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack);

        void loginByCode(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack);

        void getSmsCode(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack);

        void verifySmsCode(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack);

        void register(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack);

        void getUserInfoByToken(LifecycleProvider provider,Map<String,String> params,IBaseCallBack<User> callBack);
    }
}
