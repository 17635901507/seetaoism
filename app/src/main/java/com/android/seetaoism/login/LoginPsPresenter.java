package com.android.seetaoism.login;

import com.android.seetaoism.basef.BasePresenter;
import com.android.seetaoism.basef.IBaseCallBack;
import com.android.seetaoism.data.entity.User;
import com.android.seetaoism.data.repositories.LoginRepository;
import com.android.seetaoism.exceptions.ResultException;

import java.util.HashMap;

public class LoginPsPresenter extends BasePresenter<LoginContract.ILoginPsView> implements LoginContract.ILoginPsPresenter {
    private LoginContract.ILoginModel mModel;


    public LoginPsPresenter() {
        mModel = LoginRepository.getmInstance();
    }

    @Override
    public void login(String phoneNum, String ps) {

        HashMap<String,String> map = new HashMap<>();

        map.put("username", phoneNum);
        map.put("password", ps);


        if(mModel != null){
            mModel.loginByPs(getLifecycleProvider(), map, new IBaseCallBack<User>() {
                @Override
                public void onSuccess(User data) {
                    if(mView != null){
                        mView.onLoginSuccess(data);
                    }
                }

                @Override
                public void onFail(ResultException e) {
                    if(mView != null){
                        mView.onLoginFail(e.getMessage());
                    }
                }

            });
        }

    }
}
