package implementations;

import enums.Category;

public class Gift {
    private String productName;
    private double price;
    private Category category;

    public Gift(final String productName,
                final double price,
                final Category category) {
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

    /**
     * Getter pentru numele cadoului
     * @return nume produs
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Getter pentru pretul cadoului
     * @return pret
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter pentru tipul cadoului
     * @return categoria produsului
     */
    public Category getCategory() {
        return category;
    }
}
