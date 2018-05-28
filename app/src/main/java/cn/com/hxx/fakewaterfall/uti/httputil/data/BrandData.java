package cn.com.hxx.fakewaterfall.uti.httputil.data;

import java.util.List;

/**
 * Created by apple on 2018/5/28.
 */

public class BrandData {
    private String brand_name;
    private int brand_user_id;
    private String slogan;
    private String verification_type;
    private String avatar;
    private String cover_image;
    private int relationship;
    private int published_tshirts_count;
    private int liked_count;
    private int followers_count;
    private List<TshirtData> hot_tshirts;
    private String brand_cover_image;

    public String getBrand_cover_image() {
        return brand_cover_image;
    }

    public void setBrand_cover_image(String brand_cover_image) {
        this.brand_cover_image = brand_cover_image;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public int getBrand_user_id() {
        return brand_user_id;
    }

    public void setBrand_user_id(int brand_user_id) {
        this.brand_user_id = brand_user_id;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getVerification_type() {
        return verification_type;
    }

    public void setVerification_type(String verification_type) {
        this.verification_type = verification_type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public List<TshirtData> getHot_tshirts() {
        return hot_tshirts;
    }

    public void setHot_tshirts(List<TshirtData> hot_tshirts) {
        this.hot_tshirts = hot_tshirts;
    }

    public int getRelationship() {
        return relationship;
    }

    public void setRelationship(int relationship) {
        this.relationship = relationship;
    }

    public int getPublished_tshirts_count() {
        return published_tshirts_count;
    }

    public void setPublished_tshirts_count(int published_tshirts_count) {
        this.published_tshirts_count = published_tshirts_count;
    }

    public int getLiked_count() {
        return liked_count;
    }

    public void setLiked_count(int liked_count) {
        this.liked_count = liked_count;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }
}
