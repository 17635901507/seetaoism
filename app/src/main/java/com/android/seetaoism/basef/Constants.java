package com.android.seetaoism.basef;

import android.os.Environment;

import java.io.File;

public interface Constants {
    String BASE_URL = "http://www.seetaoism.com/";
    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codeest" + File.separator + "GeekNews";
    public static final String FILE_PROVIDER_AUTHORITY="com.codeest.seedaos.fileprovider";

    String DATA  = "DATA";
    String MODE = "MODE";
    String DAY_NIGHT_FRAGMENT_POS = "DAY_NIGHT_FRAGMENT_POS";
    int TYPE_ZHIHU  = 0;
    int TYPE_SETTING = 6;
    String ISLOGIN = "islogin";
    String OPEN = "open";
    String VERIFYFRAGMENT = "verifyfragment";
    String REGISTERFRAGMENT = "registerfragment";
    String USERINFO = "userinfo";
    String CACHE_USER_DATA_FILE_NAME = "user_info.json";
    String ISFIRSTLAUNCH = "isfirstlaunch";

    public interface RequestParamsKey{

        String LANG = "lang"; // 语言类型
        String FROM = "from"; // 设备类型 android ,ios ,pc
        String SIGNATURE = "signature"; // 把时间长戳和 随机数通过sha1 机密生成的一个 签名串
        String TIMES_STAMP = "timestamp"; // 时间戳
        String NONCE = "nonce"; // 随机数

        String MOBILE = "mobile"; // 手机号
        String SMS_CODE_TYPE = "type"; // 1, 注册，2， 修改密码，3， 修改手机号，4。登录


        String SMS_CODE = "sms_code";
    }
}
