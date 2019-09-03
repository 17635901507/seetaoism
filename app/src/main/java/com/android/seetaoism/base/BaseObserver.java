package com.android.seetaoism.base;

import com.android.seetaoism.R;
import com.android.seetaoism.app.BaseApp;
import com.android.seetaoism.utils.Logger;
import com.android.seetaoism.utils.SystemUtil;
import com.android.seetaoism.utils.ToastUtil;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class BaseObserver<T> implements Observer<T> {
    private final String TAG = getClass().getName();
    /**
     * 解析数据失败
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络问题
     */
    public static final int BAD_NETWORK = 1002;
    /**
     * 连接错误
     */
    public static final int CONNECT_ERROR = 1003;
    /**
     * 连接超时
     */
    public static final int CONNECT_TIMEOUT = 1004;

    private BaseModel mModel;
    private Disposable mDisposable;

    public BaseObserver(BaseModel model) {
        mModel = model;
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            //   HTTP错误
            onException(BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            //   连接错误
            onException(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //  连接超时
            onException(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //  解析错误
            onException(PARSE_ERROR);
        } else {
            if (e != null) {
                onError(e.toString());
            } else {
                onError(BaseApp.getBaseApp().getString(R.string.unknow_error));
            }
        }
    }

    private void onException(int unknownError) {
        String err = "";
        switch (unknownError) {
            case CONNECT_ERROR:
                err = BaseApp.getBaseApp().getString(R.string.conn_error);
                break;
            case CONNECT_TIMEOUT:
                err = BaseApp.getBaseApp().getString(R.string.conn_timeout);
                break;
            case BAD_NETWORK:
                err = BaseApp.getBaseApp().getString(R.string.net_error);
                break;
            case PARSE_ERROR:
                err = BaseApp.getBaseApp().getString(R.string.parse_error);
                break;
            default:
                err = BaseApp.getBaseApp().getString(R.string.unknow_error);
                break;
        }
        onError(err);
        ToastUtil.showShort(err);
    }

    public abstract void onError(String msg);

    @Override
    public void onComplete() {
        Logger.d(TAG, "onComplete: ");
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (!SystemUtil.isNetworkConnected()) {
            ToastUtil.showShort(BaseApp.getBaseApp().getString(R.string.net_unused));
            return;
        }
        //subscribe(d);
        mDisposable = d;
        mModel.addDisposable(d);
        Logger.d("onSubscribe mCompositeDisposable 长度:" + mModel.compositeDisposable.size());
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
        mDisposable.dispose();
        mModel.compositeDisposable.remove(mDisposable);
        Logger.d("onNext mCompositeDisposable 长度:" + mModel.compositeDisposable.size());
    }

    protected abstract void onSuccess(T t);

}
