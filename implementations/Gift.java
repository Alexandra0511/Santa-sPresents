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

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }
}
