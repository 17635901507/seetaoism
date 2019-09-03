package com.android.seetaoism.data.entity;

public class UserInfo {
    private String head_url;
    private String nickname;
    private String mobile;
    private String email;


    public String getHeadUrl() {
        return head_url;
    }

    public void setHeadUrl(String head_url) {
        this.head_url = head_url;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "head_url='" + head_url + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
