package com.ucd.comp41690.team21.zenze.backend.database.models;

/**
 * Created by timothee on 18/10/17.
 */

public class BaseModel {

    private int id;

    public BaseModel(int id) { this.id = id; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
