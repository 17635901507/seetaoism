package com.android.seetaoism.basef;

public interface IBasePresenter<T> {
    void attachView(T view);

    void detachView();
}
