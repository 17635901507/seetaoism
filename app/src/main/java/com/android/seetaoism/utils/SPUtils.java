package com.android.seetaoism.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.seetaoism.app.BaseApp;


/*
 * created by taofu on 2019-06-11
 **/
public class SPUtils {


    private static final String GLOBAL_SP_FILE_NAME = "sp_config";

    public static void commitValue(String name,String key,String value){
        SharedPreferences sharedPreferences = BaseApp.getBaseApp().getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void applayValue(String name,String key,String value){
        SharedPreferences sharedPreferences = BaseApp.getBaseApp().getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveValueToDefaultSpByApply(String key,String value) {
            applayValue(GLOBAL_SP_FILE_NAME, key, value);
    }
    public static void saveValueToDefaultSpByCommit(String key,String value) {
        commitValue(GLOBAL_SP_FILE_NAME, key, value);
    }
    public static String getValue(String name,String key){
        SharedPreferences sharedPreferences = BaseApp.getBaseApp().getSharedPreferences(name, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(key, "");
    }
    public static String getValue(String key){

        return getValue(GLOBAL_SP_FILE_NAME, key);

    }
}
