package cn.com.hxx.fakewaterfall.uti.httputil.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by apple on 2018/5/28.
 */

public class ArticleData implements MultiItemEntity, Serializable {
    private int id;
    private String title;
    private String description;
    private String action_type;
    private String action_param;
    private String banner_image;
    private String share_image;

    private String label;
    private String color_value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getAction_param() {
        return action_param;
    }

    public void setAction_param(String action_param) {
        this.action_param = action_param;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public String getShare_image() {
        return share_image;
    }

    public void setShare_image(String share_image) {
        this.share_image = share_image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor_value() {
        return color_value;
    }

    public void setColor_value(String color_value) {
        this.color_value = color_value;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
