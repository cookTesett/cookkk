package model;

public class PurchaseOrder {
    private Ingredient ingredient;
    private int quantity;

    public PurchaseOrder(Ingredient ingredient, int quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getQuantity() {
        return quantity;
    }
}
