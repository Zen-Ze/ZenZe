package com.ucd.comp41690.team21.zenze.backend.database.models;

/**
 * Created by timothee on 25/10/17.
 */

public class BaseModel {

    int id;

    public BaseModel(int id) { this.id = id; }

    public void setId(int id) { this.id = id; }

    public int getId() { return id; }
}
