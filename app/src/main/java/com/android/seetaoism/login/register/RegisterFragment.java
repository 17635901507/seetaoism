package com.android.seetaoism.login.register;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.seetaoism.R;
import com.android.seetaoism.basef.Constants;
import com.android.seetaoism.basef.MvpBaseFragment;
import com.android.seetaoism.login.LoginContract;
import com.android.seetaoism.utils.Logger;
import com.android.seetaoism.utils.SystemFacade;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends MvpBaseFragment<LoginContract.IRegisterPresenter> implements LoginContract.IRegisterView, View.OnClickListener {


    private ImageView register_btn_close;
    private ImageView imageView3;
    private EditText register_edt_phone_number;
    private ImageView login_iv_clean;
    private EditText register_edt_code;
    private TextView register_tv_get_code;
    private Button register_btn_next;
    private ImageView imageView6;
    private ImageView imageView7;
    private TextView register_tv_goto_login;
    private int mCountDown;
    private CountDownTimer countDownTimer;
    private String number;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView(View inflate) {
        register_btn_close = (ImageView) inflate.findViewById(R.id.register_btn_close);
        imageView3 = (ImageView) inflate.findViewById(R.id.imageView3);
        register_edt_phone_number = (EditText) inflate.findViewById(R.id.register_edt_phone_number);
        login_iv_clean = (ImageView) inflate.findViewById(R.id.login_iv_clean);
        register_edt_code = (EditText) inflate.findViewById(R.id.register_edt_code);
        register_tv_get_code = (TextView) inflate.findViewById(R.id.register_tv_get_code);
        register_btn_next = (Button) inflate.findViewById(R.id.register_btn_next);
        imageView6 = (ImageView) inflate.findViewById(R.id.imageView6);
        imageView7 = (ImageView) inflate.findViewById(R.id.imageView7);
        register_tv_goto_login = (TextView) inflate.findViewById(R.id.register_tv_goto_login);

        register_btn_next.setOnClickListener(this);
        register_tv_get_code.setOnClickListener(this);
        register_btn_close.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn_next:
                handVerifySmsCode();

                break;
            case R.id.register_tv_get_code:
                handGetSmsCode();
                break;
            case R.id.register_btn_close:
                back();
                break;

        }
    }

    private void handVerifySmsCode() {
        if(mPresenter == null){
            return;
        }
        String smsCode = register_edt_code.getText().toString().trim();
        if(!SystemFacade.isValidSmsCodeNumber(smsCode) ){
            showToast(R.string.text_error_input_sms_code);
            return;
        }


        String phoneNumber = register_edt_phone_number.getText().toString().trim();

        if(!SystemFacade.isValidPhoneNumber(phoneNumber) ){
            showToast(R.string.text_error_input_phone_number);
            return;
        }
        mPresenter.verifySmsCode(number,smsCode);

    }

    private void handGetSmsCode() {
        mCountDown = 60;
        countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                register_tv_get_code.setText(String.format(Locale.CHINA, "%ds", mCountDown--));
            }

            @Override
            public void onFinish() {
                resetGetCodeTextView();
            }
        };
        countDownTimer.start();
        register_tv_get_code.setTextColor(getResources().getColor(R.color.gray));
        register_tv_get_code.setEnabled(false);

        //发送获取验证码请求
        number = register_edt_phone_number.getText().toString().trim();
        if(!SystemFacade.isValidSmsCodeNumber(number)){
            showToast(R.string.text_error_input_sms_code);
        }
        mPresenter.getSmsCode(number);
    }

    private void submit() {
        // validate
        number = register_edt_phone_number.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(getContext(), "number不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }

    @SuppressLint("WrongConstant")
    @Override
    public void onSmsCodeResult(String msg, boolean success) {
        if(!success){
            showToast(""+success+msg);
            Logger.d("获取验证码失败%s",msg);
            countDownTimer.cancel();
            resetGetCodeTextView();
        }else{
            showToast(success+"");
        }
    }

    private void resetGetCodeTextView() {
        register_tv_get_code.setTextColor(getResources().getColor(R.color.red));
        register_tv_get_code.setText(R.string.text_register_get_code);
        register_tv_get_code.setEnabled(true);
    }

    @Override
    public void onVerifySmsCodeResult(String msg, boolean success) {
        if(success){
            showToast("下一步"+success);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.RequestParamsKey.MOBILE,register_edt_phone_number.getText().toString().trim());
            addFragment(getFragmentManager(), RegisterSetPswFragment.class, R.id.login_fragment_container, bundle);
        }else{
            showToast(msg);
        }
    }

    @Override
    public LoginContract.IRegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }
}
