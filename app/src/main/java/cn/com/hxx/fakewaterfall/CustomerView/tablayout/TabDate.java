package cn.com.hxx.fakewaterfall.CustomerView.tablayout;

/**
 * Created by apple on 2018/7/31.
 */

public class TabDate {

    private int dra_selected;
    private int dra_un_selected;
    private String title;
    private boolean isSelected;

    public TabDate(int dra_selected, int dra_un_selected, String title) {
        this.dra_selected = dra_selected;
        this.dra_un_selected = dra_un_selected;
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getDra_selected() {
        return dra_selected;
    }

    public void setDra_selected(int dra_selected) {
        this.dra_selected = dra_selected;
    }

    public int getDra_un_selected() {
        return dra_un_selected;
    }

    public void setDra_un_selected(int dra_un_selected) {
        this.dra_un_selected = dra_un_selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
