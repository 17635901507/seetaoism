package com.android.seetaoism.basef;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.android.seetaoism.basef.BaseFragment;
import com.android.seetaoism.utils.Logger;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

public class BaseActivity extends RxAppCompatActivity {

    private String mLogTag;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLogTag = getClass().getSimpleName();
    }
    protected <T extends View> T onViewCreatedBind(int id, View.OnClickListener listener){
        T t = this.findViewById(id);
        t.setOnClickListener(listener);
        return t;
    }

    /**
     * 用于显示一个 fragment 到指定 view group id 。 如果这个fragment 没有被添加，则调用 add 方法进行添加，如果已经添加，但是被隐藏了那么直接显示
     *
     * @param manager     : FragmentManager
     * @param aClass      : 需要显示的fragment
     * @param containerId : fragment 被添加到的view group Id.
     * @param args        : Bundle ，用于参数传递
     * @return ： 返回 添加的fragment
     * <p>
     * <p>
     * <p>
     * * 先查询fragmentManager 所在的activitiy 中是否已经添加了这个fragment
     * *  第一步 先从一个mAdded 的一个ArrayList遍历查找，如果找不到再从 一个 叫 mActive 的 SparseArray的一个map
     * *  里面查找。
     * *
     * *
     * * 注意：
     * * 1.一个 fragment 被 remove 掉后，只会从 mAdded 里面删除，不会从 mActive 里面删除，只有当这个fragment 所在的 transaction
     * * 从回退栈里面移除后才会 从mActive 删除
     * * 2. 当我们add 一个fragment时 会把我们的fragment 添加到 mAdded 里面，不会添加到 mActive。
     * <p>
     * * 3. 只有当我们把 transaction 添加到回退栈的时候，才会把我们的 fragment 添加到 mActive 里面。
     * *
     * *
     * * 所以我们通过 findFragmentByTag 方法查找出来的 fragment 可能已经被 remove 掉了，但是由于加入了回退栈，因此任然存在 mActive 中。
     */
    @SuppressLint("ResourceType")
    protected BaseFragment addFragment(FragmentManager manager, Class<? extends BaseFragment> aClass, @StringRes int containerId, Bundle args) {

        Logger.d("%s add  %s  ", mLogTag, aClass.getSimpleName());
        // step 1 , 从 FragmentManager 中 通过 tag 查找  fragment,如果能找到就不用 new 新的 fragment,找不到则 new 新的。

        String tag = aClass.getName();

        Fragment fragment = manager.findFragmentByTag(tag);

        BaseFragment baseFragment = null;

        FragmentTransaction transaction = manager.beginTransaction(); // 开启一个事务

        if (fragment == null) {

            try {
                baseFragment = aClass.newInstance(); // 通过反射 new 出一个 fragment 的实例
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (baseFragment == null) {
                throw new UnsupportedOperationException(tag + "必须提供无参构造方法");
            }

            // 设置 fragment 进入，退出， 弹进，弹出的动画
            if (baseFragment.isNeedAnimation()) {
                transaction.setCustomAnimations(baseFragment.enter(), baseFragment.exit(), baseFragment.popEnter(), baseFragment.popExit());
            }
            transaction.add(containerId, baseFragment, tag); // add 一个 fragment

            if (baseFragment.isNeedAddToBackStack()) {
                transaction.addToBackStack(tag);
            }

        } else {
            baseFragment = (BaseFragment) fragment;

            if (baseFragment.isAdded()) {
                if (baseFragment.isHidden()) {
                    transaction.show(baseFragment);
                }
            } else {
                transaction.add(containerId, baseFragment, tag); // add 一个 fragment
            }

        }
        // step 1.1 隐藏之前的fragment
        hideBeforeFragment(manager, transaction, baseFragment);
        baseFragment.setArguments(args);

        transaction.commit();
        return baseFragment;
    }


    /**
     * 除当前 fragment 以外的所有 fragment 进行隐藏
     *
     * @param manager
     * @param transaction
     * @param currentFragment
     */
    private void hideBeforeFragment(FragmentManager manager, FragmentTransaction transaction, Fragment currentFragment) {

        List<Fragment> fragments = manager.getFragments();

        for (Fragment fragment : fragments) {
            if (fragment != currentFragment && !fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }
    }

    protected void showToast(@StringRes int id){
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
    }
    protected void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
