package com.android.seetaoism.text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Text {
    public static void main(String[] args) {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1);
        String nonce = String.valueOf((int) ((Math.random() * 9 + 1) * 1));
        System.out.println(timestamp+"\n"+nonce);
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        System.out.println(nowTime);
    }
}
