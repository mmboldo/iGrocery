package com.mycompany.igrocery;

import androidx.annotation.NonNull;

public class Stores {
    private int id;
    private String storePicName;
    private int storePicDrawable;
    private String storeName;
    private String storeAddress;
    private String storePicMapName;
    private int storePicMapDrawable;

    public Stores(int id, String storePicName, int storePicDrawable, String storeName,
                  String storeAddress, String storePicMapName, int storePicMapDrawable) {
        this.id = id;
        this.storePicName = storePicName;
        this.storePicDrawable = storePicDrawable;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storePicMapName = storePicMapName;
        this.storePicMapDrawable = storePicMapDrawable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStorePicName() {
        return storePicName;
    }

    public void setStorePicName(String storePicName) {
        this.storePicName = storePicName;
    }

    public int getStorePicDrawable() {
        return storePicDrawable;
    }

    public void setStorePicDrawable(int storePicDrawable) {
        this.storePicDrawable = storePicDrawable;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStorePicMapName() {
        return storePicMapName;
    }

    public void setStorePicMapName(String storePicMapName) {
        this.storePicMapName = storePicMapName;
    }

    public int getStorePicMapDrawable() {
        return storePicMapDrawable;
    }

    public void setStorePicMapDrawable(int storePicMapDrawable) {
        this.storePicMapDrawable = storePicMapDrawable;
    }

    @NonNull
    @Override
    public String toString() {
        return storeName;
    }
}
