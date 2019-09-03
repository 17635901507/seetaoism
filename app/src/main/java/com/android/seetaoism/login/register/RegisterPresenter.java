package com.android.seetaoism.login.register;

import com.android.seetaoism.R;
import com.android.seetaoism.basef.BasePresenter;
import com.android.seetaoism.basef.Constants;
import com.android.seetaoism.basef.IBaseCallBack;
import com.android.seetaoism.data.repositories.LoginRepository;
import com.android.seetaoism.exceptions.ResultException;
import com.android.seetaoism.login.LoginActivity;
import com.android.seetaoism.login.LoginContract;

import java.util.HashMap;
import java.util.Map;

public class RegisterPresenter extends BasePresenter<LoginContract.IRegisterView> implements LoginContract.IRegisterPresenter {
    private LoginContract.ILoginModel mRepository;


    public RegisterPresenter() {
        mRepository = LoginRepository.getmInstance();
    }
    @Override
    public void getSmsCode(String phoneNumber) {
        Map<String,String> params = new HashMap<>();
        params.put(Constants.RequestParamsKey.MOBILE, phoneNumber);
        params.put(Constants.RequestParamsKey.SMS_CODE_TYPE, LoginActivity.SMS_CODE_TYPE_REGISTER);

        mRepository.getSmsCode(getLifecycleProvider(), params, new IBaseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                mView.onSmsCodeResult(data, true);
            }

            @Override
            public void onFail(ResultException e) {
                mView.onSmsCodeResult(getString(R.string.text_error_get_sms_code_fail), false);
            }

        });
    }

    @Override
    public void verifySmsCode(String phoneNumber, String code) {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.RequestParamsKey.MOBILE,phoneNumber);
        params.put(Constants.RequestParamsKey.SMS_CODE_TYPE,LoginActivity.SMS_CODE_TYPE_REGISTER);
        params.put(Constants.RequestParamsKey.SMS_CODE,code);
        mRepository.verifySmsCode(getLifecycleProvider(), params, new IBaseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                mView.onVerifySmsCodeResult(data,true);
            }

            @Override
            public void onFail(ResultException e) {
                mView.onVerifySmsCodeResult(getString(R.string.text_error_get_sms_code_fail),false);
            }

        });
    }
}
