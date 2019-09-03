package com.android.seetaoism;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.seetaoism.adapter.VpAdapter;
import com.android.seetaoism.app.BaseApp;
import com.android.seetaoism.basef.BaseActivity;
import com.android.seetaoism.basef.Constants;
import com.android.seetaoism.basef.MvpBaseActivity;
import com.android.seetaoism.data.entity.User;
import com.android.seetaoism.login.LoginContract;
import com.android.seetaoism.login.LoginGetUserPresenter;
import com.android.seetaoism.utils.DataCacheUtils;
import com.android.seetaoism.utils.LogJsonFormat;
import com.android.seetaoism.utils.Logger;
import com.android.seetaoism.utils.SpUtil;
import com.android.seetaoism.utils.SystemFacade;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends MvpBaseActivity<LoginContract.ILoginGetUserInfoPresenter> implements LoginContract.ILoginGetUserInfoView, View.OnClickListener {


    ViewPager mVp;
    private View inflate;
    private Button btn_start;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isFirstLaunch = (boolean) SpUtil.getParam(Constants.ISFIRSTLAUNCH, true);
        // TODO: 2019/9/2 第一次判断是否为第一次启动
        if(isFirstLaunch){
            setContentView(R.layout.activity_guide);
            mVp = onViewCreatedBind(R.id.vp,null);
            initView();
        }else{
            //todo 倒计时5秒，然后去获取用户信息
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(GuideActivity.this,MainActivity.class));
                }
            },5000);
            File mCacheUserFile = SystemFacade.getExternalCacheDir(BaseApp.getBaseApp(), Constants.CACHE_USER_DATA_FILE_NAME);
            User user = DataCacheUtils.getDataFromFile(User.class, mCacheUserFile);
            if(user != null && user.getToken() != null && !TextUtils.isEmpty(user.getToken().getValue())){
                mPresenter.getUserInfoByToken(user.getToken().getValue());
            }
        }
    }

    private void initView() {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<View> vpList = new ArrayList<>();
        list.add(R.mipmap.start1);
        list.add(R.mipmap.start2);
        list.add(R.mipmap.start3);
        for (int i = 0; i < list.size(); i++) {
            inflate = LayoutInflater.from(this).inflate(R.layout.vp_img, null);
            ImageView imageView = inflate.findViewById(R.id.img);
            btn_start = inflate.findViewById(R.id.btn_start);
            Glide.with(this).load(list.get(i)).into(imageView);
            vpList.add(inflate);
        }
        if(btn_start!=null){
            btn_start.setOnClickListener(this);
        }

        VpAdapter vpAdapter = new VpAdapter(vpList);
        mVp.setAdapter(vpAdapter);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 2) {
                    btn_start.setVisibility(View.VISIBLE);
                } else {
                    btn_start.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onUserInfoSuccess(User user) {
        Logger.d("获取用户信息成功");
        LogJsonFormat.format(user.toString());
        Toast.makeText(this, "获取用户信息成功" + user.toString(), Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        SpUtil.setParam(Constants.USERINFO,json);

    }

    @Override
    public void onUserInfoFail(String str) {
        Logger.d("获取用户信息失败:%S",str);
        Toast.makeText(this, "获取用户信息失败"+str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public LoginContract.ILoginGetUserInfoPresenter createPresenter() {
        return new LoginGetUserPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
                //点击保存是否第一次进入APP为false
                SpUtil.setParam(Constants.ISFIRSTLAUNCH,false);
                break;
        }
    }
}
