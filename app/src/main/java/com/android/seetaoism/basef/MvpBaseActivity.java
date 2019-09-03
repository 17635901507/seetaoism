package com.android.seetaoism.basef;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class MvpBaseActivity<P extends IBasePresenter> extends BaseActivity implements IBaseView<P>{
    protected P mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();

        if(mPresenter != null){
            mPresenter.attachView(this);
        }
    }





    @Override
    public Activity getActivityObj() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.detachView();
        }
    }
}
