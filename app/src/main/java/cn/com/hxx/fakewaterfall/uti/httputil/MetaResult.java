package cn.com.hxx.fakewaterfall.uti.httputil;

/**
 * Created by apple on 2018/5/28.
 */

public class MetaResult {
    private int status;
    private String message;
    private int code;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
