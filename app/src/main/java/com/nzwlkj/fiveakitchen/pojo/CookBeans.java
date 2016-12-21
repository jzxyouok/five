package com.nzwlkj.fiveakitchen.pojo;

/**
 * Created by WuWenGuang on 2016/9/28.
 */
public class CookBeans {

    public int Id;
    public String Cook_name;
    public String Info;
    public String From_gs;
    public int Price;

    public String Img;

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCook_name() {
        return Cook_name;
    }

    public void setCook_name(String cook_name) {
        Cook_name = cook_name;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public String getFrom_gs() {
        return From_gs;
    }

    public void setFrom_gs(String from_gs) {
        From_gs = from_gs;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

}
