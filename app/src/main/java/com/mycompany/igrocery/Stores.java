package com.mycompany.igrocery;

import androidx.annotation.NonNull;

public class Stores {
    String storeName;
    int storePic;

    public Stores(String storeName, int storePic) {
        this.storeName = storeName;
        this.storePic = storePic;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getStorePic() {
        return storePic;
    }

    public void setStorePic(int storePic) {
        this.storePic = storePic;
    }

    @NonNull
    @Override
    public String toString() {
        return storeName;
    }
}
