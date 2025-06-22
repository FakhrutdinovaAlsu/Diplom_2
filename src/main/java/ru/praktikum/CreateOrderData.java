package ru.praktikum;

public class CreateOrderData {
    private String[] ingredients;

    public CreateOrderData(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
