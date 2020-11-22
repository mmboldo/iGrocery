package com.mycompany.igrocery;

public class GroceryList {

    String itemTitle, itemDescription, itemQuantity, itemKey;

    public GroceryList(){
    }

    public GroceryList(String itemTitle, String itemDescription, String itemQuantity, String itemKey) {
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemQuantity = itemQuantity;
        this.itemKey = itemKey;
    }

    public String getItemKey() { return itemKey; }

    public void setItemKey(String itemKey) { this.itemKey = itemKey; }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) { this.itemDescription = itemDescription; }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
