package com.android.seetaoism.basef;

import android.app.Activity;
import android.support.annotation.StringRes;

import com.trello.rxlifecycle2.LifecycleProvider;

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V>{
    protected V mView;
    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    protected LifecycleProvider getLifecycleProvider(){

        if(mView != null){
            return (LifecycleProvider) mView;
        }
        return null;
    }
    protected String getString(@StringRes int id){

        if(mView != null){
            Activity activity = mView.getActivityObj();
            if(activity != null){
                return activity.getString(id);
            }
        }

        return "";
    }
}
