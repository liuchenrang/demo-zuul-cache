package com.xiaoshijie.gateway.http;

import lombok.Data;
import org.apache.logging.log4j.util.Strings;

@Data
public class RestResultBuilder {
    boolean success;
    int code;
    String msg = Strings.EMPTY;
    Object data;

    public RestResultBuilder() {
    }
    public static RestResultBuilder createRestErrorResult(){
        return new RestResultBuilder(false, 500);
    }
    public static RestResultBuilder createRestTrueResult(){
        return new RestResultBuilder(true,200);
    }

    public RestResultBuilder(boolean success, int code) {
        this.success = success;
        this.code = code;
    }

    public RestResultBuilder(boolean success, int code, Object data) {
        this.success = success;
        this.code = code;
        this.data = data;
    }

    public RestResultBuilder(boolean success, int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public RestResultBuilder setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public RestResultBuilder setCode(int code) {
        this.code = code;
        return this;
    }

    public RestResultBuilder setData(Object data) {
        this.data = data;
        return this;
    }
}
