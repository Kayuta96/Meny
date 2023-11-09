//Conny.fu@iths.se

public class VarukorgArtikel {
    private Product product;
    private int quantity;

    public VarukorgArtikel(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return quantity * calculatePrice();
    }

    public double calculatePrice() {
        if (product.getSalePrice() > 0 && quantity >= product.getSaleQuantity()) {
            return product.getSalePrice();
        } else {
            return product.getPrice();
        }
    }

    @Override
    public String toString() {
        return String.format("%s | Antal: %d | Pris: %.2f kr", product.getName(), quantity, getTotalPrice());
    }
}