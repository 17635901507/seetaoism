package com.android.seetaoism.login;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.seetaoism.R;
import com.android.seetaoism.basef.Constants;
import com.android.seetaoism.basef.MvpBaseFragment;
import com.android.seetaoism.data.entity.User;
import com.android.seetaoism.data.entity.UserInfo;
import com.android.seetaoism.data.repositories.LoginRepository;
import com.android.seetaoism.login.register.RegisterFragment;
import com.android.seetaoism.utils.Logger;

import org.greenrobot.eventbus.EventBus;

public class LoginPsFragment extends MvpBaseFragment<LoginContract.ILoginPsPresenter> implements LoginContract.ILoginPsView, View.OnClickListener {
    private ImageView login_ps_close;
    private ImageView imageView3;
    private EditText login_edt_phone_number;
    private ImageView login_iv_clean;
    private EditText login_edt_password;
    private ImageView login_iv_show_password;
    private Button login_btn_login;
    private ImageView imageView6;
    private ImageView imageView7;
    private TextView login_tv_verity;
    private TextView login_tv_regist;
    private TextView login_tv_three;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_ps;
    }
    @Override
    public void onLoginSuccess(User user) {
        Logger.d("登录成功%S",user.toString());
        Toast.makeText(getActivityObj(), "登录成功", Toast.LENGTH_LONG).show();
        EventBus.getDefault().postSticky(user);
        getActivityObj().finish();
    }

    @Override
    public void onLoginFail(String msg) {
        Toast.makeText(getActivityObj(), msg, Toast.LENGTH_LONG).show();
        Logger.d("登录失败%S",msg);
    }

    @Override
    public LoginContract.ILoginPsPresenter createPresenter() {
        return new LoginPsPresenter();
    }

    @Override
    public boolean isNeedAddToBackStack() {
        return false;
    }

    @Override
    protected void initView(View inflate) {
        login_ps_close = (ImageView) inflate.findViewById(R.id.login_ps_close);
        login_ps_close.setOnClickListener(this);
        imageView3 = (ImageView) inflate.findViewById(R.id.imageView3);
        login_edt_phone_number = (EditText) inflate.findViewById(R.id.login_edt_phone_number);
        login_iv_clean = (ImageView) inflate.findViewById(R.id.login_iv_clean);
        login_edt_password = (EditText) inflate.findViewById(R.id.login_edt_password);
        login_iv_show_password = (ImageView) inflate.findViewById(R.id.login_iv_show_password);
        login_btn_login = (Button) inflate.findViewById(R.id.login_btn_login);
        imageView6 = (ImageView) inflate.findViewById(R.id.imageView6);
        imageView7 = (ImageView) inflate.findViewById(R.id.imageView7);

        login_btn_login.setOnClickListener(this);
        login_tv_verity = (TextView) inflate.findViewById(R.id.login_tv_verity);
        login_tv_verity.setOnClickListener(this);
        login_tv_regist = (TextView) inflate.findViewById(R.id.login_tv_regist);
        login_tv_regist.setOnClickListener(this);
        login_tv_three = (TextView) inflate.findViewById(R.id.login_tv_three);
        login_tv_three.setOnClickListener(this);
    }


    boolean a = true;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login:
                submit();
                break;
            case R.id.login_tv_verity:
                addFragment(getFragmentManager(),LoginVerifyFragment.class,R.id.login_fragment_container,null);
                break;
            case R.id.login_tv_regist:
                addFragment(getFragmentManager(), RegisterFragment.class,R.id.login_fragment_container,null);
                break;
            case R.id.login_ps_close:
                Toast.makeText(getActivityObj(), "关闭", Toast.LENGTH_SHORT).show();
                getActivityObj().finish();
                break;
            case R.id.login_iv_clean:
                login_edt_phone_number.setText("");
                break;
            case R.id.login_iv_show_password:
                a = !a;
                if(a){
                    login_edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else{
                    login_edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

                break;
        }
    }

    private void submit() {
        // validate
        String number = login_edt_phone_number.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(getContext(), "number不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = login_edt_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "password不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        mPresenter.login(number, password);

    }
}
