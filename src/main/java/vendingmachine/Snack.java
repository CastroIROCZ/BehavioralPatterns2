package vendingmachine;

public class Snack {

    private final String name;
    private final int priceInCents;
    private int quantity;

    public Snack(String name, double price, int quantity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid snack name");
        }

        if (price <= 0 || quantity < 0) {
            throw new IllegalArgumentException("Invalid price or quantity");
        }

        this.name = name;
        this.priceInCents = Money.toCents(price);
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return Money.toDollars(priceInCents);
    }

    int getPriceInCents() {
        return priceInCents;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean decreaseQuantity() {
        if (quantity == 0) {
            return false;
        }

        quantity--;
        return true;
    }
}