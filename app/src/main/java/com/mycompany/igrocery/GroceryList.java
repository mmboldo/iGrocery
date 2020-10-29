package com.mycompany.igrocery;

public class GroceryList {

    String itemTitle, itemDescription, itemQuantity;

    public GroceryList(){
    }

    public GroceryList(String itemTitle, String itemDescription, String itemQuantity) {
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemQuantity = itemQuantity;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
