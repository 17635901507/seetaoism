package com.android.seetaoism.data.okhttp.interceptor;

import com.android.seetaoism.basef.Constants;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommonParamsInterceptor implements Interceptor {
    private static String SHA1_KEY = "K;9)Bq|ScMF1h=Vp5uA-G87d(_fi[aP,.w^{vQ:W";


    private HashMap<String, String> mParams;

    public CommonParamsInterceptor() {

        mParams = new HashMap<>();

        mParams.put(Constants.RequestParamsKey.LANG, "zh");
        mParams.put(Constants.RequestParamsKey.FROM, "android");




    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        String method = request.method();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonce = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));

        mParams.put(Constants.RequestParamsKey.TIMES_STAMP, timestamp);
        mParams.put(Constants.RequestParamsKey.NONCE,nonce);
        mParams.put(Constants.RequestParamsKey.SIGNATURE, getSHA1(SHA1_KEY,timestamp,nonce));


        //拦截的请求的路径
        String oldUrl = request.url().toString();

        if ("GET".equals(method.toUpperCase())) {

            /*HashMap<String, String> rootMap = new HashMap<>();

            HttpUrl mHttpUrl = request.url();


            Set<String> paramNames = mHttpUrl.queryParameterNames();

            for (String key : paramNames) {
                rootMap.put(key, mHttpUrl.queryParameter(key));
            }

            rootMap.putAll(mParams);
*/

            // https://www.wanandroid.com/article/list/0/json?lang=zn&from=android

            StringBuilder stringBuilder = new StringBuilder();//创建一个stringBuilder...字符串缓冲区

            stringBuilder.append(oldUrl);

            if (oldUrl.contains("?")) {

                if (oldUrl.indexOf("?") == oldUrl.length() - 1) {

                } else {
                    stringBuilder.append("&");
                }
            } else {
                stringBuilder.append("?");
            }

            //添加公共参数....source=android&appVersion=100&
            for (Map.Entry<String, String> entry : mParams.entrySet()) {
                stringBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");

            }
            //删掉最后一个&符号
            if (stringBuilder.indexOf("&") != -1) {
                stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
            }
            //得到含有公共参数的新路径.....使用新路径去请求
            String newUrl = stringBuilder.toString();
            //新的路径构建一个新的请求
            request = request.newBuilder().url(newUrl).build();

        } else if ("POST".equals(method.toUpperCase())) {

            //参数在请求的实体内容上....拿到以前的参数,把以前的参数和公共参数全都添加到请求实体上
            RequestBody requestBody = request.body();

            if (requestBody instanceof FormBody) { // 如果是form 表单提交数据

                FormBody oldBody = (FormBody) requestBody;

                FormBody.Builder builder = new FormBody.Builder();

                //先添加之前的参数
                for (int i = 0; i < oldBody.size(); i++) {
                    //键值对的形式添加
                    builder.add(oldBody.name(i), oldBody.value(i));
                }
                //添加公共参数
                for (Map.Entry<String, String> entry : mParams.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }

                //构建一个新的请求
                request = request.newBuilder().url(oldUrl).post(builder.build()).build();
            }
        }

        return chain.proceed(request);


    }


    public String getSHA1(String key, String timestamp, String nonce) {
        try {
            String[] array = new String[]{key, timestamp, nonce};
            StringBuffer sb = new StringBuffer();
            // 字符串排序
            Arrays.sort(array);
            for (int i = 0; i < 3; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
