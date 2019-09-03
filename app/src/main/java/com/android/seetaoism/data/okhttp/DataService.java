package com.android.seetaoism.data.okhttp;

import com.android.seetaoism.BuildConfig;
import com.android.seetaoism.basef.Constants;
import com.android.seetaoism.data.okhttp.interceptor.CommonParamsInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataService {

    private static final long DEFAULT_TIMEOUT = 20000; // 默认超时20s

    private static volatile ApiService mApiService;

    private DataService(){}
    public static ApiService getApiService(){

        if(mApiService == null){

            synchronized (DataService.class){

                if(mApiService == null){

                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();


                    /**
                     * 注意，如果有大文件下载，或者 response 里面的body 很大，要么不加HttpLoggingInterceptor 拦截器
                     * 如果非要加，日志级别不能是 BODY,否则容易内存溢出。
                     */
                    if (BuildConfig.DEBUG) {
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    } else {
                        logging.setLevel(HttpLoggingInterceptor.Level.NONE);
                    }

                    OkHttpClient httpClient = new OkHttpClient.Builder()
                            .addInterceptor(logging)
                            .addInterceptor(new CommonParamsInterceptor())
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .client(httpClient)
                            .addConverterFactory(GsonConverterFactory.create()) // 帮我们把json 窜转为 entity 对象
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 结合 rxjava 使用
                            .baseUrl(Constants.BASE_URL)
                            .build();
                    mApiService = retrofit.create(ApiService.class);
                }

            }
        }
        return mApiService;
    }
}
