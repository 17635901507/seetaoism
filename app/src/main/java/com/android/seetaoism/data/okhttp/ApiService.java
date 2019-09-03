package com.android.seetaoism.data.okhttp;

import com.android.seetaoism.data.entity.HttpResult;
import com.android.seetaoism.data.entity.User;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiService {
    //http://www.seetaoism.com/
    @POST("api/user/login")
    @FormUrlEncoded
    Observable<HttpResult<User>> getLogin(@FieldMap Map<String,String> map);

    /**
     * 验证码登录
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/smslogin")
    Observable<HttpResult<User>> loginByCode(@FieldMap Map<String, String> map);


    /**
     * 注册
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/register")
    Observable<HttpResult<User>> register(@FieldMap Map<String, String> map);

    /**
     * 发送验证码
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/sms/sendsms")
    Observable<HttpResult<String>> getSmsCode(@FieldMap Map<String, String> map);

    /**
     * 验证码是否正确
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/sms/checksmscode")
    Observable<HttpResult<String>> verifySmsCode(@FieldMap Map<String, String> map);

    /**
     * 获取用户信息
     * @method get
     * @param api /api/user/getuserinfo
     * @param map
     * @return
     */
    @GET("api/user/getuserinfo")
    Observable<HttpResult<User>> getUserInfoByToken(@QueryMap Map<String,String> map);

}
