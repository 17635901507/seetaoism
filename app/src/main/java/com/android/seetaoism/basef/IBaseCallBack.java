package com.android.seetaoism.basef;

import com.android.seetaoism.exceptions.ResultException;

public interface IBaseCallBack<T> {
    void onSuccess(T data);

    void onFail(ResultException e);
}
