package cn.com.hxx.fakewaterfall.uti.httputil.data;

import java.util.Date;

/**
 * Created by apple on 2018/5/28.
 */

public class StyleProductData {

    private String back_image_url;
    private String back_image_url_small;
    private String diy_image_url;
    private String diy_image_url_small;
    private String diy_image_field;
    private int id;
    private String image_offset;
    private String image_url;
    private String image_url_small;
    private String model_image_offset;
    private String name;
    private String offset_diy_back_image;
    private String offset_diy_image;
    private String offset_show_image1;
    private String offset_show_image2;
    private String offset_show_image3;
    private String offset_show_image4;
    private String preview_image_url;
    private String preview_image_url_small;
    private String show_image1_url;
    private String show_image1_url_small;
    private String show_image2_url;
    private String show_image2_url_small;
    private String show_image3_url;
    private String show_image3_url_small;
    private String show_image4_url;
    private String show_image4_url_small;
    private String sizes;
    private Date updated_at;
    private String value;
    private String size_description_url;
    private String diy_back_image_url;
    private String diy_back_image_url_small;

    //    "id": 7,
//            "name": "男款",
//            "cost_price": "119.0",
//            "cost_price2": "129.0",
    private int parent_id;
    private String parent_name;
    private double parent_cost_price;
    private double parent_cost_price2;


    //    "cover_image":
//            "size_cover_image":,
//            "product_description_url": "http:\/\/woqi.me\/articles\/crowds\/5\/description",
//            "size_description_url": "http:\/\/woqi.me\/articles\/crowds\/5\/size_description",
    private int parent_parent_id;
    private String parent_parent_name;
    private String parent_parent_cover_image;
    private String parent_parent_size_cover_image;
    private String parent_parent_product_description_url;
    private String parent_parent_size_description_url;

    private String image_to_show_in_list;

    public String getDiy_back_image_url() {
        return diy_back_image_url;
    }

    public void setDiy_back_image_url(String diy_back_image_url) {
        this.diy_back_image_url = diy_back_image_url;
    }

    public String getDiy_back_image_url_small() {
        return diy_back_image_url_small;
    }

    public void setDiy_back_image_url_small(String diy_back_image_url_small) {
        this.diy_back_image_url_small = diy_back_image_url_small;
    }

    public String getImage_to_show_in_list() {
        return image_to_show_in_list;
    }

    public void setImage_to_show_in_list(String image_to_show_in_list) {
        this.image_to_show_in_list = image_to_show_in_list;
    }

    public String getSize_description_url() {
        return size_description_url;
    }

    public void setSize_description_url(String size_description_url) {
        this.size_description_url = size_description_url;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public double getParent_cost_price() {
        return parent_cost_price;
    }

    public void setParent_cost_price(double parent_cost_price) {
        this.parent_cost_price = parent_cost_price;
    }

    public double getParent_cost_price2() {
        return parent_cost_price2;
    }

    public void setParent_cost_price2(double parent_cost_price2) {
        this.parent_cost_price2 = parent_cost_price2;
    }

    public int getParent_parent_id() {
        return parent_parent_id;
    }

    public void setParent_parent_id(int parent_parent_id) {
        this.parent_parent_id = parent_parent_id;
    }

    public String getParent_parent_name() {
        return parent_parent_name;
    }

    public void setParent_parent_name(String parent_parent_name) {
        this.parent_parent_name = parent_parent_name;
    }

    public String getParent_parent_cover_image() {
        return parent_parent_cover_image;
    }

    public void setParent_parent_cover_image(String parent_parent_cover_image) {
        this.parent_parent_cover_image = parent_parent_cover_image;
    }

    public String getParent_parent_size_cover_image() {
        return parent_parent_size_cover_image;
    }

    public void setParent_parent_size_cover_image(String parent_parent_size_cover_image) {
        this.parent_parent_size_cover_image = parent_parent_size_cover_image;
    }

    public String getParent_parent_product_description_url() {
        return parent_parent_product_description_url;
    }

    public void setParent_parent_product_description_url(String parent_parent_product_description_url) {
        this.parent_parent_product_description_url = parent_parent_product_description_url;
    }

    public String getParent_parent_size_description_url() {
        return parent_parent_size_description_url;
    }

    public void setParent_parent_size_description_url(String parent_parent_size_description_url) {
        this.parent_parent_size_description_url = parent_parent_size_description_url;
    }

    public String getImageToshow(String image_to_show_in_list) {
        if ("show_image1_url_small".contains(image_to_show_in_list)) {
            return show_image1_url;
        } else if ("show_image2_url_small".contains(image_to_show_in_list)) {
            return show_image2_url;
        } else if ("show_image3_url_small".contains(image_to_show_in_list)) {
            return show_image3_url;
        } else if ("show_image4_url_small".contains(image_to_show_in_list)) {
            return show_image4_url;
        }
        return null;
    }

    public String getoffsetData(String image_to_show_in_list) {
        if ("offset_show_image1".contains(image_to_show_in_list)) {
            return offset_show_image1;
        } else if ("offset_show_image2".contains(image_to_show_in_list)) {
            return offset_show_image2;
        } else if ("offset_show_image3".contains(image_to_show_in_list)) {
            return offset_show_image3;
        } else if ("offset_show_image4".contains(image_to_show_in_list)) {
            return offset_show_image4;
        }
        return null;
    }

    public String getShow_image4_url() {
        return show_image4_url;
    }

    public void setShow_image4_url(String show_image4_url) {
        this.show_image4_url = show_image4_url;
    }

    public String getShow_image4_url_small() {
        return show_image4_url_small;
    }

    public void setShow_image4_url_small(String show_image4_url_small) {
        this.show_image4_url_small = show_image4_url_small;
    }

    public String getBack_image_url() {
        return back_image_url;
    }

    public void setBack_image_url(String back_image_url) {
        this.back_image_url = back_image_url;
    }

    public String getBack_image_url_small() {
        return back_image_url_small;
    }

    public void setBack_image_url_small(String back_image_url_small) {
        this.back_image_url_small = back_image_url_small;
    }

    public String getDiy_image_url() {
        return diy_image_url;
    }

    public void setDiy_image_url(String diy_image_url) {
        this.diy_image_url = diy_image_url;
    }

    public String getDiy_image_url_small() {
        return diy_image_url_small;
    }

    public void setDiy_image_url_small(String diy_image_url_small) {
        this.diy_image_url_small = diy_image_url_small;
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

    public String getImage_offset() {
        return image_offset;
    }

    public void setImage_offset(String image_offset) {
        this.image_offset = image_offset;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_url_small() {
        return image_url_small;
    }

    public void setImage_url_small(String image_url_small) {
        this.image_url_small = image_url_small;
    }

    public String getModel_image_offset() {
        return model_image_offset;
    }

    public void setModel_image_offset(String model_image_offset) {
        this.model_image_offset = model_image_offset;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffset_diy_back_image() {
        return offset_diy_back_image;
    }

    public void setOffset_diy_back_image(String offset_diy_back_image) {
        this.offset_diy_back_image = offset_diy_back_image;
    }

    public String getOffset_diy_image() {
        return offset_diy_image;
    }

    public void setOffset_diy_image(String offset_diy_image) {
        this.offset_diy_image = offset_diy_image;
    }

    public String getOffset_show_image1() {
        return offset_show_image1;
    }

    public void setOffset_show_image1(String offset_show_image1) {
        this.offset_show_image1 = offset_show_image1;
    }

    public String getOffset_show_image2() {
        return offset_show_image2;
    }

    public void setOffset_show_image2(String offset_show_image2) {
        this.offset_show_image2 = offset_show_image2;
    }

    public String getOffset_show_image3() {
        return offset_show_image3;
    }

    public void setOffset_show_image3(String offset_show_image3) {
        this.offset_show_image3 = offset_show_image3;
    }

    public String getOffset_show_image4() {
        return offset_show_image4;
    }

    public void setOffset_show_image4(String offset_show_image4) {
        this.offset_show_image4 = offset_show_image4;
    }

    public String getPreview_image_url() {
        return preview_image_url;
    }

    public void setPreview_image_url(String preview_image_url) {
        this.preview_image_url = preview_image_url;
    }

    public String getPreview_image_url_small() {
        return preview_image_url_small;
    }

    public void setPreview_image_url_small(String preview_image_url_small) {
        this.preview_image_url_small = preview_image_url_small;
    }

    public String getShow_image1_url() {
        return show_image1_url;
    }

    public void setShow_image1_url(String show_image1_url) {
        this.show_image1_url = show_image1_url;
    }

    public String getShow_image1_url_small() {
        return show_image1_url_small;
    }

    public void setShow_image1_url_small(String show_image1_url_small) {
        this.show_image1_url_small = show_image1_url_small;
    }

    public String getShow_image2_url() {
        return show_image2_url;
    }

    public void setShow_image2_url(String show_image2_url) {
        this.show_image2_url = show_image2_url;
    }

    public String getShow_image2_url_small() {
        return show_image2_url_small;
    }

    public void setShow_image2_url_small(String show_image2_url_small) {
        this.show_image2_url_small = show_image2_url_small;
    }

    public String getShow_image3_url() {
        return show_image3_url;
    }

    public void setShow_image3_url(String show_image3_url) {
        this.show_image3_url = show_image3_url;
    }

    public String getShow_image3_url_small() {
        return show_image3_url_small;
    }

    public void setShow_image3_url_small(String show_image3_url_small) {
        this.show_image3_url_small = show_image3_url_small;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
