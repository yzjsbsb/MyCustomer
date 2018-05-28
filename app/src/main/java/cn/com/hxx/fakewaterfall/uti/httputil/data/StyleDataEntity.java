package cn.com.hxx.fakewaterfall.uti.httputil.data;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by apple on 2018/5/28.
 */

public class StyleDataEntity extends SectionEntity<StyleProductData> {

    private String size_desc_url;


    public StyleDataEntity(boolean isHeader, String header, String size_desc_url) {
        super(isHeader, header);
        this.size_desc_url = size_desc_url;
    }

    public StyleDataEntity(StyleProductData t) {
        super(t);
    }


    public String getSize_desc_url() {
        return size_desc_url;
    }

    public void setSize_desc_url(String size_desc_url) {
        this.size_desc_url = size_desc_url;
    }
}

