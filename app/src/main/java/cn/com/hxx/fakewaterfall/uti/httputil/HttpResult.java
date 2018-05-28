package cn.com.hxx.fakewaterfall.uti.httputil;

/**
 * Created by apple on 2018/5/28.
 */

public class HttpResult<T> {
    private T body;
    private MetaResult meta;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public MetaResult getMeta() {
        return meta;
    }

    public void setMeta(MetaResult meta) {
        this.meta = meta;
    }
}