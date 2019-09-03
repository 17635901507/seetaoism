package com.android.seetaoism.basef;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.seetaoism.R;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends RxFragment {


    protected BaseActivity mBaseActivity;
    private Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(getLayoutId(),container,false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    protected abstract void initView(View root);

    protected abstract int getLayoutId();
    protected <T extends View> T onViewCreatedBind(int id,View.OnClickListener listener){
        T t = getView().findViewById(id);
        t.setOnClickListener(listener);
        return t;
    }
    protected void back(){
        getFragmentManager().popBackStack();
    }

    public int enter() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_right_in;
    }

    public int exit() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_left_out;
    }

    public int popEnter() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_left_in;
    }

    public int popExit() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_right_out;
    }

    public boolean isNeedAddToBackStack(){
        return true;
    }
    public boolean isNeedAnimation() {
        return true;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) activity;
        }
    }


    protected void addFragment(FragmentManager manager, Class<? extends BaseFragment> aClass, int containerId, Bundle args) {
        if (mBaseActivity != null) {
            mBaseActivity.addFragment(manager, aClass, containerId, args);
        }
    }
    protected void showToast(@StringRes int id){
        Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@NonNull String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
