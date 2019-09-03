package com.android.seetaoism.login.register;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.seetaoism.R;
import com.android.seetaoism.Text;
import com.android.seetaoism.basef.BaseFragment;
import com.android.seetaoism.basef.Constants;
import com.android.seetaoism.basef.MvpBaseFragment;
import com.android.seetaoism.data.entity.User;
import com.android.seetaoism.login.LoginContract;
import com.android.seetaoism.utils.Logger;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterSetPswFragment extends MvpBaseFragment<LoginContract.IRegisterSetPsdPresenter> implements View.OnClickListener, LoginContract.IRegisterSetPsdView {


    private ImageView mIvClose;
    private ImageView imageView3;
    private EditText mEdtPsd;
    private ImageView mIvShowPsd;
    private EditText mEdtConfirmPsd;
    private ImageView mIvShowConfirmPsd;
    private Button mBtnRegister;
    private ImageView imageView6;
    private ImageView imageView7;
    private TextView mTvGotoLogin;

    public RegisterSetPswFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView(View root) {
        mIvClose = onViewCreatedBind(R.id.register_setting_psw_btn_close, this);
        mEdtPsd = onViewCreatedBind(R.id.register_setting_psd_edt_psd, this);
        mEdtConfirmPsd = onViewCreatedBind(R.id.register_setting_psd_edt_confirm_psd, this);
        mIvShowPsd = onViewCreatedBind(R.id.register_setting_psd_iv_show_psd, this);
        mIvShowConfirmPsd = onViewCreatedBind(R.id.register_setting_psd_show_confirm_psd, this);
        mBtnRegister = onViewCreatedBind(R.id.register_setting_psd_register, this);
        mTvGotoLogin = onViewCreatedBind(R.id.register_setting_psd_tv_goto_register, this);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_set_psw;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_setting_psd_register:
                handClickRegister();
                break;
        }
    }

    private void handClickRegister() {
        if(mPresenter == null){
            return;
        }

        Bundle arguments = getArguments();
        String phoneNumber = null;
        if(arguments != null){
            phoneNumber = (String) arguments.get(Constants.RequestParamsKey.MOBILE);
        }
        if(TextUtils.isEmpty(phoneNumber)){
            return;
        }
        String psd = mEdtPsd.getText().toString().trim();
        String confirmPsd = mEdtConfirmPsd.getText().toString().trim();
        if(TextUtils.isEmpty(psd) && TextUtils.isEmpty(confirmPsd)){
            showToast(R.string.text_error_null_password);
            return;
        }
        if(!psd.equals(confirmPsd)){
            showToast(R.string.text_error_tow_password_not_same);
        }
        mPresenter.register(phoneNumber,psd,confirmPsd);
    }

    @Override
    public LoginContract.IRegisterSetPsdPresenter createPresenter() {
        return new RegisterSetPswPresenter();
    }

    @Override
    public void onRegisterResultSuccess(User user) {
        Logger.d("注册成功%S",user.toString());
        Toast.makeText(mBaseActivity, "注册成功", Toast.LENGTH_SHORT).show();
        getActivityObj().finish();
    }
//17335087065
    @Override
    public void onRegisterResultFail(String msg) {
        Logger.d("注册失败%S",msg);
        Toast.makeText(mBaseActivity, "注册失败", Toast.LENGTH_SHORT).show();
    }
}
