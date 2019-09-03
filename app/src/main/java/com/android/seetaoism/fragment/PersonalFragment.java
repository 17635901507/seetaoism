package com.android.seetaoism.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.seetaoism.basef.BaseFragment;
import com.android.seetaoism.data.entity.Token;
import com.android.seetaoism.data.entity.User;
import com.android.seetaoism.data.entity.UserInfo;
import com.android.seetaoism.login.LoginActivity;
import com.android.seetaoism.R;
import com.android.seetaoism.basef.Constants;
import com.android.seetaoism.utils.Logger;
import com.android.seetaoism.utils.SpUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment {


    @BindView(R.id.tv_collect)
    TextView mTvCollect;
    @BindView(R.id.view)
    View mView;
    @BindView(R.id.tv_login)
    TextView mTvlogin;
    @BindView(R.id.imageView5)
    ImageView mImageView5;
    @BindView(R.id.imageView6)
    ImageView mImageView6;
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    @BindView(R.id.imageView7)
    ImageView mImageView7;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.v_collect)
    View mVCollect;
    @BindView(R.id.v_message)
    View mVMessage;
    @BindView(R.id.v_setting)
    View mVSetting;
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.imageView2)
    ImageView mImageView2;
    @BindView(R.id.imageView3)
    ImageView mImageView3;
    @BindView(R.id.imageView8)
    ImageView mImageView8;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    private Unbinder unbinder;

    public PersonalFragment() {
        // Required empty public constructor
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initView(View root) {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    @Override
    public void onResume() {
        super.onResume();
        String userJson = (String) SpUtil.getParam(Constants.USERINFO, "");
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);
        UserInfo userInfo = user.getUserInfo();
        String headUrl = userInfo.getHeadUrl();
        Glide.with(mBaseActivity).load(headUrl).into(mIvHead);
        mTvlogin.setText(userInfo.getNickname());
        mBtnLogin.setVisibility(View.GONE);

    }


    @OnClick({R.id.tv_collect, R.id.tv_login, R.id.imageView5, R.id.imageView6, R.id.tv_message, R.id.imageView7, R.id.tv_setting, R.id.view, R.id.v_collect, R.id.v_message, R.id.v_setting, R.id.iv_head, R.id.barrier2, R.id.imageView3,R.id.btn_login})
    public void onClick(View v) {
        boolean isLogin = (boolean) SpUtil.getParam(Constants.ISLOGIN, false);
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_collect:

                if (!isLogin) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_login:
                if (!isLogin) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.imageView5:
                break;
            case R.id.imageView6:
                break;
            case R.id.tv_message:
                if (!isLogin) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.imageView7:
                break;
            case R.id.tv_setting:

                break;
            case R.id.view:
                break;
            case R.id.v_collect:
                if (!isLogin) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.v_message:
                if (!isLogin) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.v_setting:
                Toast.makeText(mBaseActivity, "跳转设置界面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_head:
                if (!isLogin) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.imageView2:
                break;
            case R.id.imageView3:
                break;
            case R.id.btn_login:
                if (!isLogin) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getUserInfo(UserInfo userInfo){
        if(userInfo != null){

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
