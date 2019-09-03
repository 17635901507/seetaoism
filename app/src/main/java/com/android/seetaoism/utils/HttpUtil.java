package com.android.seetaoism.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.seetaoism.app.BaseApp;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BuildConfig;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//李开新
public class HttpUtil {
    private static volatile HttpUtil httpUtil;
    private final Retrofit.Builder builder;

    private HttpUtil(){
        builder = new Retrofit.Builder()
                .client(getOkhttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
    }
    public static HttpUtil getHttpUtil() {
        if(httpUtil == null){
            synchronized (HttpUtil.class){
                if(httpUtil == null){
                    httpUtil = new HttpUtil();
                }
            }
        }
        return httpUtil;
    }

    public <T> T getService(String baseurl,Class<T> mClass){
        return builder.baseUrl(baseurl).build().create(mClass);
    }

    //网络缓存的地址
    private static String PATH_DATA = BaseApp.getBaseApp().getCacheDir().getAbsolutePath() +
            File.separator + "data";

    private static String PATH_CACHE = PATH_DATA + "/NetCache";
    //自定义客户端
    private OkHttpClient getOkhttpClient() {
        //设置本地缓存文件
        File file = new File(PATH_CACHE);
        //设置缓存文件大小
        Cache cache = new Cache(file, 1024 * 1024 * 50);
        //设置日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)//设置日志拦截器
                .addInterceptor(new MyCacheIntercepter())//设置网络拦截器
                .addNetworkInterceptor(new MyCacheIntercepter())
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }
    //网络拦截器
    public class MyCacheIntercepter implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            if (!isNetworkAvailable(BaseApp.getBaseApp())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();

            }

            Response originalResponse = chain.proceed(request);

            if (isNetworkAvailable(BaseApp.getBaseApp())) {
                int maxAge = 0;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 15 * 60;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    }

    /**
     * 检测是否有网
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }
}
