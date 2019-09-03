package com.android.seetaoism.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.android.seetaoism.utils.Logger;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        ButterKnife.bind(this);
        Date date = new Date();
        Logger.d(String.format("时间：%1$s姓名是%2$s", date, "likaixin"));
        initMVP();
        initView();
        initData();
        initListener();
    }

    @SuppressLint("ResourceType")
    public void addFragment(Class<? extends RxFragment> aClass, FragmentManager manager, @StringRes int id,Bundle args) {
        String tag = aClass.getName();
        Fragment fragment = manager.findFragmentByTag(tag);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            try {
                fragment = aClass.newInstance();
                BaseFragment baseFragment = (BaseFragment) fragment;
                transaction.setCustomAnimations(baseFragment.enter(),baseFragment.exit(),baseFragment.popEnter(),baseFragment.popExit());
                transaction.add(id,fragment);
                if(baseFragment.isNeedToAddBackStack()){
                    transaction.addToBackStack(tag);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }else{
            if(fragment.isAdded()){
                if(fragment.isHidden()){
                    transaction.show(fragment);
                }else{

                }
            }else{
                transaction.add(id,fragment);
            }
        }
        if(fragment != null){
            fragment.setArguments(args);
            hideBeforeFragment(transaction,manager,fragment);
            transaction.commit();
        }

    }

    private void hideBeforeFragment( FragmentTransaction transaction,FragmentManager manager, Fragment curFragment){
        List<Fragment> fragments = manager.getFragments();
        for (Fragment fragment: fragments) {
            if(curFragment != fragment && !fragment.isHidden()){
                transaction.hide(fragment);
            }
        }
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

    protected void addFragment(FragmentManager manager, Class<? extends RxFragment> aClass, int containerId, Bundle args) {
        String tag = aClass.getName();

        Fragment fragment = manager.findFragmentByTag(tag);

        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            try {
                fragment = aClass.newInstance();
                BaseFragment baseFragment = (BaseFragment) fragment;
                transaction.setCustomAnimations(baseFragment.enter(), baseFragment.exit(), baseFragment.popEnter(), baseFragment.popExit());
                transaction.add(containerId, fragment);
                if (baseFragment.isNeedToAddBackStack()) {
                    transaction.addToBackStack(tag);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            if (fragment.isAdded()) {
                if (fragment.isHidden()) {
                    transaction.show(fragment);
                }
            } else {
                transaction.add(containerId, fragment);
            }
        }
        if (fragment != null) {
            fragment.setArguments(args);
            hideBeforeFragment(manager, transaction, fragment);
            transaction.commit();
        }
    }


    //除当前fragment以外的所有fragment进行隐藏
    private void hideBeforeFragment(FragmentManager manager, FragmentTransaction transaction, Fragment currentFragment) {
        List<Fragment> fragments = manager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != currentFragment && !fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int rId) {
        Toast.makeText(this, rId, Toast.LENGTH_SHORT).show();
    }
}
