package cn.com.hxx.fakewaterfall.uti.httputil.data;

import java.util.Date;

/**
 * Created by apple on 2018/5/28.
 */

public class BannerData {

    private String id;
    private String title;
    private String description;
    private String action_type;
    private String action_param;
    private String address;
    private String share_address;
    private Date released_at;
    private String banner;
    private String share_banner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShare_address() {
        return share_address;
    }

    public void setShare_address(String share_address) {
        this.share_address = share_address;
    }

    public Date getReleased_at() {
        return released_at;
    }

    public void setReleased_at(Date released_at) {
        this.released_at = released_at;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getShare_banner() {
        return share_banner;
    }

    public void setShare_banner(String share_banner) {
        this.share_banner = share_banner;
    }
}
