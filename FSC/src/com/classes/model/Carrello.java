package com.classes.model;

import com.classes.model.bean.products.ProductBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Carrello {

    HashMap<ProductBean, Integer> items;

    public Carrello() {
        items = new HashMap<>();
    }

    public void addItem(ProductBean item, Integer quantity) {
        if(items.containsKey(item)){
            Integer oldquantity = items.get(item);
            Integer newquantity = oldquantity + quantity;
            items.put(item, newquantity);
        }
        else{
            items.put(item, quantity);
        }
    }

    public void deleteItem(ProductBean item) {
        items.remove(item);
    }

    public HashMap<ProductBean, Integer> getItems() {
        return items;
    }

    public void deleteAll() {
        items.clear();
    }

}
