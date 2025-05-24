package model;

public class Ingredient {
    private String name;
    private int stockLevel;
    private int threshold;
    private boolean criticallyLow;

    public Ingredient(String name, int stockLevel, int threshold) {
        this.name = name;
        this.stockLevel = stockLevel;
        this.threshold = threshold;
        this.criticallyLow = false;
    }

    public Ingredient(String name, int stockLevel, int threshold, boolean criticallyLow) {
        this.name = name;
        this.stockLevel = stockLevel;
        this.threshold = threshold;
        this.criticallyLow = criticallyLow;
    }

    public String getName() {
        return name;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public int getThreshold() {
        return threshold;
    }

    public boolean isLowStock() {
        return stockLevel < threshold;
    }

    public boolean isCriticallyLow() {
        return criticallyLow;
    }
}
