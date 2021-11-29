package com.szhengzhu.exception;

import com.szhengzhu.core.StatusCode;
import lombok.Data;

/**
 * @author jehon
 */
@Data
public class ShowException extends RuntimeException {

    private static final long serialVersionUID = -19820923284381998L;

    private String code;

    private String msg;

    public ShowException(StatusCode statusCode) {
        super(statusCode.msg);
        this.code = statusCode.code;
        this.msg = statusCode.msg;
    }

    public ShowException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
