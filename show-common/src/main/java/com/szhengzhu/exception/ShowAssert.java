package com.szhengzhu.exception;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;

/**
 * @author jehon
 */
public class ShowAssert {

    public static void checkTrue(boolean expression, StatusCode statusCode) {
        if (expression) {
            throw new ShowException(statusCode);
        }
    }

    public static void checkStrEmpty(String str, StatusCode statusCode) {
        if (StrUtil.isEmpty(str)) {
            throw new ShowException(statusCode);
        }
    }

    public static void checkResult(Result result) {
        if (!result.isSuccess()) {
            throw new ShowException(result.getCode(), result.getMsg());
        }
    }

    public static void checkSuccess(Result result) {
        if (!result.getCode().equals(Contacts.SUCCESS_CODE)) {
            throw new ShowException(result.getCode(), result.getMsg());
        }
    }

    public static void checkNull(Object reference, StatusCode statusCode) {
        if (ObjectUtil.isNull(reference)) {
            throw new ShowException(statusCode);
        }
    }
}
