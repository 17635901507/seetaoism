package com.android.seetaoism.login;

import com.android.seetaoism.basef.BasePresenter;
import com.android.seetaoism.basef.IBaseCallBack;
import com.android.seetaoism.data.entity.User;
import com.android.seetaoism.data.repositories.LoginRepository;
import com.android.seetaoism.exceptions.ResultException;

import java.util.HashMap;

public class LoginGetUserPresenter extends BasePresenter<LoginContract.ILoginGetUserInfoView> implements LoginContract.ILoginGetUserInfoPresenter {
    private LoginContract.ILoginModel mModel;

    public LoginGetUserPresenter() {
        mModel = LoginRepository.getmInstance();
    }

    @Override
    public void getUserInfoByToken(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        mModel.getUserInfoByToken(getLifecycleProvider(), params, new IBaseCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                mView.onUserInfoSuccess(data);
            }

            @Override
            public void onFail(ResultException e) {
                mView.onUserInfoFail(e.getMessage());
            }
        });
    }
}
