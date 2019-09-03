package com.android.seetaoism.text;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

/*
1、获取一个manager
2、开启一个事物
3、add/replace
    1、Attach/show
4、commit


add     ---     remove
Attach  ---     detach
show    ---     hide

Replace     add + remove

addFragment(Class fClass,int containerId)
1、把需要显示的fragment添加到指定容器，同时隐藏上一个
2、不同fragment可以自定义自己的进出场动画
3、可以自己决定是否把这一次事物加入回退栈
* */
/*
 * 功能：
 * 用于显示一个fragment到指定view group id,如果这个fragment没有被添加，则调用add方法进行添加，如果已经添加了，但是被隐藏了那么直接显示
 * @param manager：FragmentManager
 * @param aClass：需要显示的fragment
 * @param containerId：fragment被添加到的ViewGroup id
 * @param args：Bundle 用于参数传递
 * @return：返回添加的fragment
 *
 *               *先查询fragmentManager所在的Activity中是否已经添加了这个fragment
 *               *第一步：先从mAdded的一个ArrayList便利查找，如果找不到，再从mActive的SparseArray的Map里面查找
 *               *
 *               *注意：
 *               *1、一个fragment被remove后，只会送mAdded里面删除，不会从mActive里面删除，
 *               *transaction从回退栈里面移除后才会从mActive里面移除
 *               *2、当我们add一个fragment时，会把我们的fragment添加到mAdded里面，不会添加到mActive里边。
 *               *3、只有当我们把transaction添加到回退栈里边时，才会把fragment添加到mActive里边。
 *               *
 *               *所以我们通过findFragmentByTag方法找出来的fragment不一定被添加到我们的Activity里边
 *
 *
 * **/
public class BaseActivity extends RxAppCompatActivity {
    protected BaseFragment addFrgment(FragmentManager manager,Class<? extends BaseFragment> fClass,int containerId,Bundle args){
        String tag = fClass.getName();

        Fragment fragment =  manager.findFragmentByTag(tag);

        BaseFragment baseFragment = null;
        FragmentTransaction transaction = manager.beginTransaction();
        if(fragment == null){
            try {
                baseFragment = fClass.newInstance();
                if(baseFragment ==null){
                    throw new UnsupportedOperationException(tag+"必须有无参构造");
                }
                // 设置 fragment 进入，退出， 弹进，弹出的动画
                transaction.setCustomAnimations(baseFragment.enter(), baseFragment.exit(), baseFragment.popEnter(), baseFragment.popExit());

                transaction.add(containerId, baseFragment, tag); // add 一个 fragment

                if(baseFragment.isNeedAddToBackStack()){
                    transaction.addToBackStack(tag);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }else{
            baseFragment = (BaseFragment) fragment;

            if(baseFragment.isAdded()){
                if(baseFragment.isHidden()){
                    transaction.show(baseFragment);
                }
            }else{
                transaction.add(containerId, baseFragment, tag); // add 一个 fragment
            }
        }
        hide(manager,transaction,baseFragment);
        baseFragment.setArguments(args);
        transaction.commit();
        return baseFragment;
    }

    private void hide(FragmentManager manager, FragmentTransaction transaction, Fragment baseFragment) {
        List<Fragment> fragments = manager.getFragments();
        for (Fragment f :fragments) {
            if(baseFragment!=f &&!baseFragment.isHidden()){
                transaction.hide(baseFragment);
            }

        }
    }
}
