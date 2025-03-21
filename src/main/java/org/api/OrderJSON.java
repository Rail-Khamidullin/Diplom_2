package org.api;

import java.util.List;

// Тело запроса для создания заказа
public class OrderJSON {

    List<String> ingredients;

    public OrderJSON(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public OrderJSON() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
