package com.android.seetaoism.login.register;

import com.android.seetaoism.basef.BasePresenter;
import com.android.seetaoism.basef.IBaseCallBack;
import com.android.seetaoism.basef.IBasePresenter;
import com.android.seetaoism.data.entity.User;
import com.android.seetaoism.data.repositories.LoginRepository;
import com.android.seetaoism.exceptions.ResultException;
import com.android.seetaoism.login.LoginContract;

import java.util.HashMap;

public class RegisterSetPswPresenter extends BasePresenter<LoginContract.IRegisterSetPsdView> implements LoginContract.IRegisterSetPsdPresenter {

    private LoginContract.ILoginModel mRepository;


    public RegisterSetPswPresenter() {
        mRepository = LoginRepository.getmInstance();
    }
    @Override
    public void register(String phoneNumber, String psd, String confirmPsd) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile",phoneNumber);
        map.put("password",psd);
        map.put("affirm_password",confirmPsd);
        mRepository.register(getLifecycleProvider(), map, new IBaseCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                mView.onRegisterResultSuccess(data);
            }

            @Override
            public void onFail(ResultException e) {
                mView.onRegisterResultFail(e.getMessage());
            }
        });
    }
}
