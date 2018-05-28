package cn.com.hxx.fakewaterfall.uti.httputil.data;

import java.util.List;

/**
 * Created by apple on 2018/5/28.
 */

public class DesignerData {
    private int id;
    private String name;
    private String avatar;
    private String slogan;
    private int relationship;
    private String verification_type;

    private int designer_id;
    private String designer_name;
    private int published_tshirts_count;
    private int liked_count;
    private int followers_count;
    private List<TshirtData> hot_tshirts;

    public List<TshirtData> getHot_tshirts() {
        return hot_tshirts;
    }

    public void setHot_tshirts(List<TshirtData> hot_tshirts) {
        this.hot_tshirts = hot_tshirts;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getRelationship() {
        return relationship;
    }

    public void setRelationship(int relationship) {
        this.relationship = relationship;
    }

    public String getVerification_type() {
        return verification_type;
    }

    public void setVerification_type(String verification_type) {
        this.verification_type = verification_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getDesigner_id() {
        return designer_id;
    }

    public void setDesigner_id(int designer_id) {
        this.designer_id = designer_id;
    }

    public String getDesigner_name() {
        return designer_name;
    }

    public void setDesigner_name(String designer_name) {
        this.designer_name = designer_name;
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
