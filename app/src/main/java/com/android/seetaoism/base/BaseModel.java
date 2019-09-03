package com.android.seetaoism.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseModel {
    public CompositeDisposable compositeDisposable;

    public void onDestory() {
        //切换所有的Disposable对象
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    public void addDisposable(Disposable d) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(d);
    }

}
