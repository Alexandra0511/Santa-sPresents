package utils;

import enums.Category;
import enums.Cities;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

public final class Utils {
    private Utils() {
        //constructor pentru checkstyle
    }

    /**
     * Converteste orasul in string
     * @param city orasul ce trebuie convertit
     * @return string-ul corespunzator orasului
     */
    public static String convertCityToString(final Cities city) {
        return switch (city) {
            case BUCURESTI -> "Bucuresti";
            case TIMISOARA -> "Timisoara";
            case CONSTANTA -> "Constanta";
            case ORADEA -> "Oradea";
            case BUZAU -> "Buzau";
            case IASI -> "Iasi";
            case CRAIOVA -> "Craiova";
            case BRASOV -> "Brasov";
            case BRAILA -> "Braila";
            case CLUJ -> "Cluj-Napoca";
            default -> null;
        };
    }

    /**
     * Converteste un string intr-unul din elementele clasei enum Cities
     * @param city string-ul initial
     * @return orasul corespunzator
     */
    public static Cities convertStringToCity(final String city) {
        return switch (city.toLowerCase()) {
            case "bucuresti" -> Cities.BUCURESTI;
            case "timisoara" -> Cities.TIMISOARA;
            case "constanta" -> Cities.CONSTANTA;
            case "oradea" -> Cities.ORADEA;
            case "buzau" -> Cities.BUZAU;
            case "iasi" -> Cities.IASI;
            case "craiova" -> Cities.CRAIOVA;
            case "brasov" -> Cities.BRASOV;
            case "braila" -> Cities.BRAILA;
            case "cluj-napoca" -> Cities.CLUJ;
            default -> null;
        };
    }

    /**
     * Converteste o categorie in string
     * @param category categoria initiala
     * @return string-ul corespunzator categoriei
     */
    public static String convertCategoryToString(final Category category) {
        return switch (category) {
            case BOOKS -> "Books";
            case BOARD_GAMES -> "Board Games";
            case CLOTHES -> "Clothes";
            case  SWEETS -> "Sweets";
            case  TECHNOLOGY -> "Technology";
            case TOYS -> "Toys";
            default -> null;
        };
    }

    /**
     * Converteste un string intr-unul din elementele clasei enum Category
     * @param category string-ul initial
     * @return categoria corespunzatoare
     */
    public static Category convertStringToCategory(final String category) {
        return switch (category.toLowerCase()) {
            case "books" -> Category.BOOKS;
            case "board games" -> Category.BOARD_GAMES;
            case "clothes" -> Category.CLOTHES;
            case "sweets" -> Category.SWEETS;
            case "technology" -> Category.TECHNOLOGY;
            case "toys" -> Category.TOYS;
            default -> null;
        };
    }

    /**
     * Converteste un JSONArray intr-o lista de categorii, folosindu-se de functia anterioara
     * @param json array-ul initial
     * @return lista de categorii
     */
    public static List<Category> convertJsonArrayToListCategory(final JSONArray json) {
        List<Category> category = new ArrayList<>();
        for (Object iter : json) {
            category.add(convertStringToCategory((String) iter));
        }
        return category;
    }
}
