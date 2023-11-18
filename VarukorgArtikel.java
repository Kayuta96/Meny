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
        return quantity * product.getPrice();
    }

    public double getTotalPriceWithDiscount() {
        if (product.getSalePrice() > 0 && quantity >= product.getSaleQuantity()) {
            int discountQuantity = quantity / product.getSaleQuantity();
            int regularQuantity = quantity % product.getSaleQuantity();
            return (discountQuantity * product.getSalePrice()) + (regularQuantity * product.getPrice());
        } else {
            return getTotalPrice(); // Ingen discount ge de normala värdet.
        }
    }}