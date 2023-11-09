//Conny.fu@iths.se

public class Product {                                                                                                // Conny Fu conny.fu@iths.se
    private String name;
    private double price;
    private String productGroup;
    private double priceKG;

    private double salePrice;
    private int saleQuantity;

    public Product(String name, double price, double priceKG, String productGroup) {
        this.name = name;
        this.price = price;
        this.priceKG = priceKG;
        this.productGroup = productGroup;
    }

    public Product(String name, double price, String productGroup, double priceKG) {
        this.name = name;
        this.price = price;
        this.productGroup = productGroup;
        this.priceKG = priceKG;
    }

    // Getters - Returnerar på det name, price, productGroup och priceKG
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }

    public String getProductGroup() {
        return productGroup;
    }
    public double getPriceKG() {
        return priceKG;
    }
    // Setters - Sätter värden till name, price, productGroup och priceKG
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public void setPriceKG(double priceKG) {
        this.priceKG = priceKG;
    }

    public void setSale(double salePrice, int saleQuantity){
        this.salePrice = salePrice;
        this.saleQuantity = saleQuantity;

    }
    public double getSalePrice() {
        return salePrice;
    }

    public int getSaleQuantity() {
        return saleQuantity;
    }

    @Override
    public String toString() {
        return "Product [name=" + name + ", price=" + price + ", productGroup=" + productGroup + "]";
    }
}
