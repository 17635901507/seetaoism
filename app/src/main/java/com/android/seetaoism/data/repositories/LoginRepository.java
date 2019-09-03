package com.android.seetaoism.data.repositories;

import android.annotation.SuppressLint;
import android.service.autofill.Dataset;

import com.android.seetaoism.app.BaseApp;
import com.android.seetaoism.basef.BaseRepository;
import com.android.seetaoism.basef.Constants;
import com.android.seetaoism.basef.IBaseCallBack;
import com.android.seetaoism.data.entity.HttpResult;
import com.android.seetaoism.data.entity.User;
import com.android.seetaoism.data.entity.UserInfo;
import com.android.seetaoism.data.okhttp.DataService;
import com.android.seetaoism.exceptions.ResultException;
import com.android.seetaoism.login.LoginContract;
import com.android.seetaoism.utils.DataCacheUtils;
import com.android.seetaoism.utils.SPUtils;
import com.android.seetaoism.utils.SpUtil;
import com.android.seetaoism.utils.SystemFacade;
import com.google.gson.Gson;
import com.trello.rxlifecycle2.LifecycleProvider;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class LoginRepository extends BaseRepository implements LoginContract.ILoginModel {
    private volatile static LoginRepository mInstance;

    private File mCacheUserFile;
    private LoginRepository(){
        mCacheUserFile = SystemFacade.getExternalCacheDir(BaseApp.getBaseApp(),Constants.CACHE_USER_DATA_FILE_NAME);
    }

    public static LoginRepository getmInstance() {
        if(mInstance == null){
            synchronized (LoginRepository.class){
                if(mInstance == null){
                    mInstance = new LoginRepository();
                }
            }
        }
        return mInstance;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loginByPs(LifecycleProvider provider, Map<String, String> params, final IBaseCallBack<User> callBack) {
        observer(provider, DataService.getApiService().getLogin(params), new Function<HttpResult<User>, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(HttpResult<User> userHttpResult) throws Exception {
                if(userHttpResult.data != null){

                    //登录成功后把用户信息保存到文件，方便获取用户信息
                    //保存 User 和 Token
                    if(mCacheUserFile != null){
                        DataCacheUtils.saveDataToFile(userHttpResult.data,mCacheUserFile);
                    }
                    return Observable.just(userHttpResult.data);
                }
                return Observable.error(new ResultException(userHttpResult.message));
            }
        },callBack);

    }

    @Override
    public void loginByCode(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack) {

    }

    @Override
    public void getSmsCode(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack) {
        observer(provider, DataService.getApiService().getSmsCode(params), new Function<HttpResult<String>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(HttpResult<String> stringHttpResult) throws Exception {
                if(stringHttpResult.code == 1){
                    return Observable.just(stringHttpResult.data);
                }
                return Observable.error(new ResultException(stringHttpResult.message));
            }
        },callBack);
    }

    @Override
    public void verifySmsCode(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack) {
        observer(provider, DataService.getApiService().verifySmsCode(params), new Function<HttpResult<String>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(HttpResult<String> stringHttpResult) throws Exception {
                if(stringHttpResult.code == 1){
                    return Observable.just(stringHttpResult.data);
                }
                return Observable.error(new ResultException(stringHttpResult.code,stringHttpResult.message));
            }
        },callBack);
    }

    @Override
    public void register(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack) {
        observer(provider, DataService.getApiService().register(params), new Function<HttpResult<User>, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(HttpResult<User> userHttpResult) throws Exception {
                if(userHttpResult.code == 1 && userHttpResult.data != null){
                    //保存 User 和 Token
                    if(mCacheUserFile != null){
                        DataCacheUtils.saveDataToFile(userHttpResult.data,mCacheUserFile);
                    }
                    return Observable.just(userHttpResult.data);
                }
                return Observable.error(new ResultException(userHttpResult.code,userHttpResult.message));
            }
        },callBack);
    }

    @Override
    public void getUserInfoByToken(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack) {
        observer(provider, DataService.getApiService().getUserInfoByToken(params), new Function<HttpResult<User>, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(HttpResult<User> userHttpResult) throws Exception {
                if(userHttpResult.data != null && userHttpResult.code == 1){
                    //通过 Token 获取的User里面不带有Token。所以我们需要把原来保存的用户信息里面的Token 重新赋给新的User里面
                    User user = userHttpResult.data;
                    if(user.getToken() == null){
                        User oldUser = DataCacheUtils.getDataFromFile(User.class, mCacheUserFile);
                        if(oldUser != null && oldUser.getToken() != null){
                            user.setToken(oldUser.getToken());
                        }
                    }

                    //再次保存用户信息
                    if(mCacheUserFile != null){
                        DataCacheUtils.saveDataToFile(userHttpResult.data,mCacheUserFile);
                    }
                    return Observable.just(userHttpResult.data);
                }
                return Observable.error(new ResultException(userHttpResult.code,userHttpResult.message));
            }
        },callBack);
    }
}
