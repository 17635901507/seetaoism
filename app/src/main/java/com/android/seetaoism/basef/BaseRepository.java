package com.android.seetaoism.basef;

import com.android.seetaoism.data.entity.User;
import com.android.seetaoism.exceptions.ResultException;
import com.android.seetaoism.utils.Logger;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/*
 * created by taofu on 2019-05-20
 **/
public class BaseRepository {
    protected<T,R> void observer(LifecycleProvider provider, Observable<T> observable, Function<T, ObservableSource<R>> flatMap, final IBaseCallBack<R> callBack){
        //参数解释：
        //LifecycleProvider provider  因为我们不知道P层到底是用Activity还是用Fragment所以，我们直接传他的父类，在本类中判断到底是用Activity还是Fragment
        //Observable<T> observable  因为在Service 中我们并只知道要用哪一个实体类用Rxjava来请求数据，所以此处用泛型代替
        //Function RXjava的操作符，功能是替换原来的被观察者成为新的数据源向继续向观察者发送信息。
        // 也就是说它可以把原来的类型转换成我们需要的类型，例如把原来的HttpResult<User>转换成ObservableSource<User>
        //CallBack<R> callBack 接口回调数据应该很熟悉了。

        observable.subscribeOn(Schedulers.io())
                //解除绑定：有时用户在请求网络的时候会把界面关闭即是销毁，但是网络请求不会停止，
                // 回来的数据可能已然会展示，但此时Activity已然被销毁，可能会造成异常和短暂的内存泄漏。
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(flatMap)
                //取消绑定 判断provider 到底是Activity还是Fragment
                .compose(provider instanceof RxFragment ? ((RxFragment)provider).<R>bindUntilEvent(FragmentEvent.DESTROY) : ((RxAppCompatActivity)provider).<R>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<R>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(R r) {
                        Logger.d("BaseRepository observer onNext %s ",r.toString());
                        callBack.onSuccess(r);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("BaseRepository observer onError %s ",e.toString());
                        if(e instanceof ResultException){
                            callBack.onFail((ResultException) e);
                        }else{
                            callBack.onFail(new ResultException(e.getMessage()));
                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
