package com.android.seetaoism.base;

public interface BaseView<T,V> {
    void onSuccess(T t);
    void onFail(V v);
}
