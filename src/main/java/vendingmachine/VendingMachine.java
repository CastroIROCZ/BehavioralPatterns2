package vendingmachine;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class VendingMachine {

    private StateOfVendingMachine currentState;
    private final SnackDispenseHandler snackDispenser;
    private final Map<String, Snack> snacks;

    private Snack selectedSnack;
    private Snack lastDispensedSnack;

    private int moneyInCents;
    private int lastReturnedMoneyInCents;

    public VendingMachine(SnackDispenseHandler snackDispenser) {
        if (snackDispenser == null) {
            throw new IllegalArgumentException("Invalid dispenser");
        }

        this.snackDispenser = snackDispenser;
        this.snacks = new LinkedHashMap<>();
        this.currentState = new IdleState();
    }

    public void addSnack(Snack snack) {
        if (snack == null) {
            throw new IllegalArgumentException("Invalid snack");
        }

        String key = snack.getName().toLowerCase(Locale.ROOT);

        if (snacks.containsKey(key)) {
            throw new IllegalArgumentException("Snack already exists");
        }

        snacks.put(key, snack);
    }

    public Snack getSnack(String snackName) {
        if (snackName == null) {
            return null;
        }

        return snacks.get(snackName.toLowerCase(Locale.ROOT));
    }

    public Collection<Snack> getSnacks() {
        return Collections.unmodifiableCollection(snacks.values());
    }

    public StateOfVendingMachine getState() {
        return currentState;
    }

    public Snack getSelectedSnack() {
        return selectedSnack;
    }

    public Snack getLastDispensedSnack() {
        return lastDispensedSnack;
    }

    public double getInsertedMoney() {
        return Money.toDollars(moneyInCents);
    }

    public double getLastReturnedMoney() {
        return Money.toDollars(lastReturnedMoneyInCents);
    }

    int getInsertedMoneyInCents() {
        return moneyInCents;
    }

    SnackDispenseHandler getSnackDispenser() {
        return snackDispenser;
    }

    void setState(StateOfVendingMachine state) {
        currentState = state;
    }

    void startTransaction(Snack snack) {
        selectedSnack = snack;
        lastDispensedSnack = null;
        lastReturnedMoneyInCents = 0;
        moneyInCents = 0;

        setState(new WaitingForMoneyState());
    }

    void addMoney(double amount) {
        moneyInCents = Math.addExact(
                moneyInCents, Money.toCents(amount));
    }

    void completeTransaction(Snack dispensedSnack) {
        lastDispensedSnack = dispensedSnack;

        if (dispensedSnack == null) {
            lastReturnedMoneyInCents = moneyInCents;
        } else {
            lastReturnedMoneyInCents =
                    moneyInCents - dispensedSnack.getPriceInCents();
        }

        selectedSnack = null;
        moneyInCents = 0;

        setState(new IdleState());
    }

    public void selectSnack(String snackName) {
        currentState.selectSnack(this, snackName);
    }

    public void insertMoney(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Money must be greater than zero");
        }

        Money.toCents(amount);
        currentState.insertMoney(this, amount);
    }

    public void dispenseSnack() {
        currentState.dispenseSnack(this);
    }
}