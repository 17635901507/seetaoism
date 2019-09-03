package com.android.seetaoism.login;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.android.seetaoism.R;
import com.android.seetaoism.basef.Constants;
import com.android.seetaoism.basef.BaseActivity;
import com.android.seetaoism.login.register.RegisterFragment;

public class LoginActivity extends BaseActivity {

    public static final String SMS_CODE_TYPE_REGISTER = "1";
    public static final String SMS_CODE_TYPE_MODIFY_PSW = "2";
    public static final String SMS_CODE_TYPE_MODIFY_PHONE_NUM = "3";
    public static final String SMS_CODE_TYPE_LOGIN = "4";


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //EventBus.getDefault().register(this);
        addFragment(getSupportFragmentManager(), LoginPsFragment.class, R.id.login_fragment_container, null);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
    }
}