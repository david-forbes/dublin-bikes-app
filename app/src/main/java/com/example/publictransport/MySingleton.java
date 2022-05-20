package com.example.publictransport;

import org.json.JSONArray;

import java.util.ArrayList;

public class MySingleton {
    private JSONArray jsonArray = new JSONArray();
    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }



    // Getter/setter

    private static MySingleton instance;

    public static MySingleton getInstance() {
        if (instance == null)
            instance = new MySingleton();
        return instance;
    }



    private MySingleton() { }
}