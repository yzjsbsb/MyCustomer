package cn.com.hxx.fakewaterfall.uti.httputil.data;

import java.util.List;

/**
 * Created by apple on 2018/5/28.
 */

public class StyleData {
    private String color_menu_text;
    private String cover_image;
    private String crowd_menu_text;
    private String diy_back_image_field;
    private String diy_image_field;
    private int id;
    private String name;
    private String product_description_url;
    private String show_image1_field;
    private boolean show_image1_for_paid;
    private String show_image2_field;
    private boolean show_image2_for_paid;
    private boolean show_image3_for_paid;
    private boolean show_image4_for_paid;
    private String size_cover_image;
    private String size_description_url;
    private String style_menu_text;
    private int[] interchangeable_crowds;
    private List<StyleTypeData> styles;

    public int[] getInterchangeable_crowds() {
        return interchangeable_crowds;
    }

    public void setInterchangeable_crowds(int[] interchangeable_crowds) {
        this.interchangeable_crowds = interchangeable_crowds;
    }

    public String getColor_menu_text() {
        return color_menu_text;
    }

    public void setColor_menu_text(String color_menu_text) {
        this.color_menu_text = color_menu_text;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getCrowd_menu_text() {
        return crowd_menu_text;
    }

    public void setCrowd_menu_text(String crowd_menu_text) {
        this.crowd_menu_text = crowd_menu_text;
    }

    public String getDiy_back_image_field() {
        return diy_back_image_field;
    }

    public void setDiy_back_image_field(String diy_back_image_field) {
        this.diy_back_image_field = diy_back_image_field;
    }

    public String getDiy_image_field() {
        return diy_image_field;
    }

    public void setDiy_image_field(String diy_image_field) {
        this.diy_image_field = diy_image_field;
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

    public String getProduct_description_url() {
        return product_description_url;
    }

    public void setProduct_description_url(String product_description_url) {
        this.product_description_url = product_description_url;
    }

    public String getShow_image1_field() {
        return show_image1_field;
    }

    public void setShow_image1_field(String show_image1_field) {
        this.show_image1_field = show_image1_field;
    }


    public String getShow_image2_field() {
        return show_image2_field;
    }

    public void setShow_image2_field(String show_image2_field) {
        this.show_image2_field = show_image2_field;
    }

    public boolean isShow_image1_for_paid() {
        return show_image1_for_paid;
    }

    public void setShow_image1_for_paid(boolean show_image1_for_paid) {
        this.show_image1_for_paid = show_image1_for_paid;
    }

    public boolean isShow_image2_for_paid() {
        return show_image2_for_paid;
    }

    public void setShow_image2_for_paid(boolean show_image2_for_paid) {
        this.show_image2_for_paid = show_image2_for_paid;
    }

    public boolean isShow_image3_for_paid() {
        return show_image3_for_paid;
    }

    public void setShow_image3_for_paid(boolean show_image3_for_paid) {
        this.show_image3_for_paid = show_image3_for_paid;
    }

    public boolean isShow_image4_for_paid() {
        return show_image4_for_paid;
    }

    public void setShow_image4_for_paid(boolean show_image4_for_paid) {
        this.show_image4_for_paid = show_image4_for_paid;
    }

    public String getSize_cover_image() {
        return size_cover_image;
    }

    public void setSize_cover_image(String size_cover_image) {
        this.size_cover_image = size_cover_image;
    }

    public String getSize_description_url() {
        return size_description_url;
    }

    public void setSize_description_url(String size_description_url) {
        this.size_description_url = size_description_url;
    }

    public String getStyle_menu_text() {
        return style_menu_text;
    }

    public void setStyle_menu_text(String style_menu_text) {
        this.style_menu_text = style_menu_text;
    }

    public List<StyleTypeData> getStyles() {
        return styles;
    }

    public void setStyles(List<StyleTypeData> styles) {
        this.styles = styles;
    }
}
