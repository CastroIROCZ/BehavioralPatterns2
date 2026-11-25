package vendingmachine;

public class SnackHandler extends SnackDispenseHandler {

    private final Snack snack;

    public SnackHandler(Snack snack) {
        this.snack = snack;
    }

    @Override
    protected boolean canHandle(String snackName) {
        return snack.getName().equalsIgnoreCase(snackName);
    }

    @Override
    protected Snack dispenseSnack(int moneyInCents) {
        if (moneyInCents < snack.getPriceInCents()) {
            return null;
        }

        if (!snack.decreaseQuantity()) {
            return null;
        }

        return snack;
    }
}