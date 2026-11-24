package vendingmachine;

import java.math.BigDecimal;

final class Money {

    private Money() {
    }

    public static int toCents(double amount) {
        if (!Double.isFinite(amount) || amount < 0) {
            throw new IllegalArgumentException("Invalid money amount");
        }

        try {
            return BigDecimal.valueOf(amount)
                    .movePointRight(2)
                    .intValueExact();
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException(
                    "Money must have at most two decimal places");
        }
    }

    public static double toDollars(int cents) {
        return cents / 100.0;
    }
}