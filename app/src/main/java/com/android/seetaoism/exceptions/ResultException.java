package com.android.seetaoism.exceptions;

public class ResultException extends Throwable{
    public int code;

    public ResultException(int code,String message) {
        super(message);
        this.code = code;
    }

    public ResultException(String message){
        super(message);
        this.code = -1;
    }

}
