package vendingmachine;

public abstract class SnackDispenseHandler {

    private SnackDispenseHandler nextHandler;

    public void setNextHandler(SnackDispenseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public Snack dispense(String snackName, double moneyInserted) {
        if (canHandle(snackName)) {
            return dispenseSnack(Money.toCents(moneyInserted));
        }

        if (nextHandler != null) {
            return nextHandler.dispense(snackName, moneyInserted);
        }

        return null;
    }

    protected abstract boolean canHandle(String snackName);

    protected abstract Snack dispenseSnack(int moneyInCents);
}