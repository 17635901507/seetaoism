package com.android.seetaoism.basef;

import android.app.Activity;

public interface IBaseView<T extends IBasePresenter> {
    T createPresenter();

    Activity getActivityObj();
}
