package com.mycompany.igrocery;

import androidx.annotation.NonNull;

public class Stores {
    private int id;
    private String storePicName;
    private int storePicDrawable;
    private String storeName;
    private String storeAddress;

    public Stores(int id, String storePicName, int storePicDrawable, String storeName, String storeAddress) {
        this.id = id;
        this.storePicName = storePicName;
        this.storePicDrawable = storePicDrawable;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
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

    @NonNull
    @Override
    public String toString() {
        return storeName;
    }
}
