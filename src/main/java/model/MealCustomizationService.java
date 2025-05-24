package model;

import java.util.*;

public class MealCustomizationService {

    // ✅ مكونات غير متوفرة أو غير متوافقة
    private static final Set<String> unavailableIngredients = Set.of("Shrimp", "Shellfish", "Blue Cheese");

    private static final Map<String, List<String>> incompatibleIngredients = Map.of(
            "Shrimp", List.of("Vegan Cheese")
    );

    private static final Map<String, String> substitutionMap = Map.of(
            "Cheddar Cheese", "Vegan Cheese",
            "Wheat Bread", "Gluten-Free Bread"
    );

    private List<String> substitutionLog = new ArrayList<>();

    public boolean validateIngredients(List<String> ingredients) {
        for (String ingredient : ingredients) {
            if (unavailableIngredients.contains(ingredient)) {
                return false;
            }
        }

        for (int i = 0; i < ingredients.size(); i++) {
            String ingredient = ingredients.get(i);
            if (incompatibleIngredients.containsKey(ingredient)) {
                List<String> incompatibleWith = incompatibleIngredients.get(ingredient);
                for (int j = 0; j < ingredients.size(); j++) {
                    if (i == j) continue;
                    String other = ingredients.get(j);
                    if (incompatibleWith.contains(other)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public String suggestSubstitution(String ingredient, List<String> dietaryRestrictions) {
        if (dietaryRestrictions != null) {
            for (String restriction : dietaryRestrictions) {
                if (restriction.equalsIgnoreCase("dairy") && ingredient.toLowerCase().contains("cheese")) {
                    return substitutionMap.getOrDefault(ingredient, ingredient);
                }
                if (restriction.equalsIgnoreCase("gluten") && ingredient.toLowerCase().contains("bread")) {
                    return substitutionMap.getOrDefault(ingredient, ingredient);
                }
            }
        }

        if (unavailableIngredients.contains(ingredient)) {
            return substitutionMap.getOrDefault(ingredient, ingredient);
        }

        return ingredient;
    }

    public void notifyChefOfSubstitution(String substitution) {
        substitutionLog.add("Substitution notification sent for: " + substitution);
    }

    public void logSubstitutions(List<String> substitutions) {
        substitutionLog.addAll(substitutions);
    }

    public List<String> getSubstitutionLog() {
        return substitutionLog;
    }
}
