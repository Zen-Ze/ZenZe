package com.ucd.comp41690.team21.zenze.backend.database.models;

/**
 * Created by timothee on 18/10/17.
 */

public class Item extends BaseModel {

    private String name;
    private String desc;
    private String imgPath;

    public Item(int id, String name, String desc, String imgPath) {
        super(id);
        this.name = name;
        this.desc = desc;
        this.imgPath = imgPath;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getName() {
        return name;
    }
}
