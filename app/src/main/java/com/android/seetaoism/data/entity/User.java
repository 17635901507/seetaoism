package com.android.seetaoism.data.entity;

public class User {
    private Token token;
    private UserInfo user_info;


    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return user_info;
    }

    public void setUserInfo(UserInfo user_info) {

        this.user_info = user_info;
    }

    @Override
    public String toString() {
        return "User{" +
                "token=" + token +
                ", user_info=" + user_info +
                '}';
    }
}
