package cn.com.hxx.fakewaterfall.uti.httputil.data;

import java.util.List;

/**
 * Created by apple on 2018/5/28.
 */

public class StyleTypeData {

    private double cost_price;
    private int id;
    private String name;
    private List<StyleProductData> colors;

    public double getCost_price() {
        return cost_price;
    }

    public void setCost_price(double cost_price) {
        this.cost_price = cost_price;
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

    public List<StyleProductData> getColors() {
        return colors;
    }

    public void setColors(List<StyleProductData> colors) {
        this.colors = colors;
    }
}
