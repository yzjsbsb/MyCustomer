package cn.com.hxx.fakewaterfall.uti.httputil.data;

import java.util.Date;
import java.util.List;

/**
 * Created by apple on 2018/5/28.
 */

public class CommodityData {
    private int id;
    private String description;
    private String image;
    private String back_image;
    private int designer_id;
    private int style_color_id;
    private Date created_at;
    private boolean published;
    private Date published_at;
    private Double price;
    private double cost_price;
    private int favorites_count;
    private String[] tag_list;
    private Boolean liked_by_current;
    private DesignerData designer;
    private List<CommentData> comments;
    private List<TshirtData> recommended_tshirts;
    private String[] crowd_info_image;
    private String color_value;
    private int comment_counts;
    private boolean disabled_publish;
    private String show_image1;
    private String show_image2;
    private String show_image3;
    private String show_image4;
    private String show_image5;
    private String image_to_show_in_list;
    private int relationship;
    private int tshirt_id;
    private boolean fromLocal;
    private int positionInSaved;

    public int getPositionInSaved() {
        return positionInSaved;
    }

    public void setPositionInSaved(int positionInSaved) {
        this.positionInSaved = positionInSaved;
    }

    public double getCost_price() {
        return cost_price;
    }

    public void setCost_price(double cost_price) {
        this.cost_price = cost_price;
    }

    public String getShow_image5() {
        return show_image5;
    }

    public void setShow_image5(String show_image5) {
        this.show_image5 = show_image5;
    }

    public int getTshirt_id() {
        return tshirt_id;
    }

    public void setTshirt_id(int tshirt_id) {
        this.tshirt_id = tshirt_id;
    }

    public boolean isFromLocal() {
        return fromLocal;
    }

    public void setFromLocal(boolean fromLocal) {
        this.fromLocal = fromLocal;
    }

    public int getRelationship() {
        return relationship;
    }

    public void setRelationship(int relationship) {
        this.relationship = relationship;
    }

    public String getBack_image() {
        return back_image;
    }

    public void setBack_image(String back_image) {
        this.back_image = back_image;
    }

    public String getImage_to_show_in_list() {
        return image_to_show_in_list;
    }

    public void setImage_to_show_in_list(String image_to_show_in_list) {
        this.image_to_show_in_list = image_to_show_in_list;
    }

    public String[] getCrowd_info_image() {
        return crowd_info_image;
    }

    public void setCrowd_info_image(String[] crowd_info_image) {
        this.crowd_info_image = crowd_info_image;
    }

    public String getColor_value() {
        return color_value;
    }

    public void setColor_value(String color_value) {
        this.color_value = color_value;
    }

    public int getComment_counts() {
        return comment_counts;
    }

    public void setComment_counts(int comment_counts) {
        this.comment_counts = comment_counts;
    }

    public boolean isDisabled_publish() {
        return disabled_publish;
    }

    public void setDisabled_publish(boolean disabled_publish) {
        this.disabled_publish = disabled_publish;
    }

    public String getShow_image1() {
        return show_image1;
    }

    public void setShow_image1(String show_image1) {
        this.show_image1 = show_image1;
    }

    public String getShow_image2() {
        return show_image2;
    }

    public void setShow_image2(String show_image2) {
        this.show_image2 = show_image2;
    }

    public String getShow_image3() {
        return show_image3;
    }

    public void setShow_image3(String show_image3) {
        this.show_image3 = show_image3;
    }

    public String getShow_image4() {
        return show_image4;
    }

    public void setShow_image4(String show_image4) {
        this.show_image4 = show_image4;
    }

    public List<CommentData> getComments() {
        return comments;
    }

    public void setComments(List<CommentData> comments) {
        this.comments = comments;
    }

    public List<TshirtData> getRecommended_tshirts() {
        return recommended_tshirts;
    }

    public void setRecommended_tshirts(List<TshirtData> recommended_tshirts) {
        this.recommended_tshirts = recommended_tshirts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDesigner_id() {
        return designer_id;
    }

    public void setDesigner_id(int designer_id) {
        this.designer_id = designer_id;
    }

    public int getStyle_color_id() {
        return style_color_id;
    }

    public void setStyle_color_id(int style_color_id) {
        this.style_color_id = style_color_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Date getPublished_at() {
        return published_at;
    }

    public void setPublished_at(Date published_at) {
        this.published_at = published_at;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getFavorites_count() {
        return favorites_count;
    }

    public void setFavorites_count(int favorites_count) {
        this.favorites_count = favorites_count;
    }

    public String[] getTag_list() {
        return tag_list;
    }

    public void setTag_list(String[] tag_list) {
        this.tag_list = tag_list;
    }

    public Boolean getLiked_by_current() {
        return liked_by_current;
    }

    public void setLiked_by_current(Boolean liked_by_current) {
        this.liked_by_current = liked_by_current;
    }

    public DesignerData getDesigner() {
        return designer;
    }

    public void setDesigner(DesignerData designer) {
        this.designer = designer;
    }
}
