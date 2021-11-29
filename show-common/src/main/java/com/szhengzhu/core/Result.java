package com.szhengzhu.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回的结果集
 *
 * @author Administrator
 * @date 2019年2月20日
 * @param <T>
 */
@Data
@NoArgsConstructor
public class Result<T> {

    private String code = "200";

    private String msg = "success";

    private T data;

    public Result(T data) {
        this.data = data;
    }

    public Result(StatusCode status) {
        this.code = status.code;
        this.msg = status.msg;
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(StatusCode status, T data) {
        this.code = status.code;
        this.msg = status.msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return code.equals("200") && data != null;
    }
}
