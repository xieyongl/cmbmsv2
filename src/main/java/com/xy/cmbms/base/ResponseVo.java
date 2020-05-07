package com.xy.cmbms.base;


import com.xy.cmbms.enums.ErrorEnum;

import java.io.Serializable;

/**
 * @author Xieyong
 * @date 2019/11/28 - 17:49
 */
public class ResponseVo<T> implements Serializable {

        // 错误码
        private int code;
        // 错误信息
        private String error;
        // 返回数据
        private T data;

        public ResponseVo() {
            super();
        }

        public ResponseVo(ErrorEnum errorEnum) {
            this(errorEnum, null);
        }

        public ResponseVo(ErrorEnum errorEnum, T data) {
            this.code = errorEnum.getErrorCode();
            this.error = errorEnum.getMessage();
            this.data = data;
        }

        public ResponseVo(int errorCode, String message) {
            this.code = errorCode;
            this.error = message;
        }

        public ResponseVo(int errorCode, String message, T data) {
            this.code = errorCode;
            this.error = message;
            this.data = data;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
}

