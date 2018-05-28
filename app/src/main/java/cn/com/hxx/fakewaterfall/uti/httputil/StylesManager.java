package cn.com.hxx.fakewaterfall.uti.httputil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.hxx.fakewaterfall.uti.httputil.data.StyleData;
import cn.com.hxx.fakewaterfall.uti.httputil.data.StyleDataEntity;
import cn.com.hxx.fakewaterfall.uti.httputil.data.StyleProductData;
import cn.com.hxx.fakewaterfall.uti.httputil.data.StyleTypeData;

/**
 * Created by apple on 2018/5/28.
 */

public class StylesManager {

    private static StylesManager instance = null;
    private List<StyleData> mData;
    private Map<Integer, StyleProductData> map = new HashMap();
    private List<StyleDataEntity> entityList = new ArrayList<>();

    private StylesManager() {

    }

    public static StylesManager getInstance() {
        if (instance == null) {
            instance = new StylesManager();
        }
        return instance;
    }

    public void saveStylesData(List<StyleData> data) {
        mData = data;
        setColorsMap();
    }

    public List<StyleData> getSytleData() {
        return mData;
    }

    private void setColorsMap() {
        if (EmptyUtils.isNotEmpty(mData)) {
            for (StyleData styleData : mData) {
                entityList.add(new StyleDataEntity(true, styleData.getName(), styleData.getSize_description_url()));
                List<StyleTypeData> styles = styleData.getStyles();
                for (StyleTypeData styleTypeData : styles) {
                    List<StyleProductData> colors = styleTypeData.getColors();
                    for (StyleProductData styleProductData : colors) {
                        styleProductData.setParent_id(styleTypeData.getId());
                        styleProductData.setParent_name(styleTypeData.getName());
                        styleProductData.setParent_cost_price(styleTypeData.getCost_price());
                        styleProductData.setParent_cost_price2(styleTypeData.getCost_price());
                        styleProductData.setParent_parent_id(styleData.getId());
                        styleProductData.setParent_parent_name(styleData.getName());
                        styleProductData.setSize_description_url(styleData.getSize_description_url());
                        //当后端数据没有传image_to_show_in_list时，使用这里的
                        styleProductData.setImage_to_show_in_list(getImageToShowInList(styleData, styleProductData));
                        map.put(styleProductData.getId(), styleProductData);

                        //封装diy款式数据
                        entityList.add(new StyleDataEntity(styleProductData));
                    }
                }
            }
        }
    }

    private String getImageToShowInList(StyleData styleData, StyleProductData styleProductData) {
        if (styleData.isShow_image1_for_paid()) {
            return "show_image1";
        } else if (styleData.isShow_image2_for_paid()) {
            return "show_image2";
        } else if (styleData.isShow_image3_for_paid()) {
            return "show_image3";
        } else if (styleData.isShow_image4_for_paid()) {
            return "show_image4";
        }
        return "show_image1";
    }

    public Map<Integer, StyleProductData> getColorMap() {
        return map;
    }

    public StyleProductData getStyleProductDataById(int id) {
        if (EmptyUtils.isNotEmpty(map)) {
            return map.get(id);
        } else
            return null;
    }

    public List<StyleDataEntity> getStyleDataEntity() {
        return entityList;
    }
}
