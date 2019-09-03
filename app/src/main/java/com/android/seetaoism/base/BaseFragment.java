package com.android.seetaoism.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.seetaoism.R;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends RxFragment {

    private Unbinder unbinder;
    protected FragmentActivity activity;

    public BaseFragment(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(getLayoutId(), null);
        unbinder = ButterKnife.bind(this,inflate);
        initMVP();
        initView();
        initData();
        initListener();
        return inflate;
    }

    protected void initMVP() {

    }

    protected void initListener() {

    }

    protected void initData() {

    }

    protected void initView() {

    }


    protected abstract int getLayoutId();

    private BaseActivity mBaseActivity;
    private String mTag;

    public int enter(){
        if(!isNeedAnimation()){
            return 0;
        }
        return R.anim.common_page_right_in;
    }

    public int exit(){
        if(!isNeedAnimation()){
            return 0;
        }
        return R.anim.common_page_left_out;
    }

    public int popEnter(){
        if(!isNeedAnimation()){
            return 0;
        }
        return R.anim.common_page_left_in;
    }

    public int popExit(){
        if(!isNeedAnimation()){
            return 0;
        }
        return R.anim.common_page_right_out;
    }

    public boolean isNeedAnimation(){
        return true;
    }

    protected boolean isNeedToAddBackStack(){
        return false;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof BaseActivity){
            mBaseActivity = (BaseActivity) activity;
        }
    }

    protected void addFragment(FragmentManager manager, Class<? extends BaseFragment> aClass, int containerId, Bundle args){
        if (mBaseActivity != null) {
            mBaseActivity.addFragment(manager, aClass, containerId, args);
        }

    }

    protected String getTAG(){
        if(TextUtils.isEmpty(mTag)){
            mTag = getClass().getSimpleName();
        }
        return mTag;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder != null){
            unbinder.unbind();
        }
    }

    public Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        activity = getActivity();
    }

    protected void toast(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
