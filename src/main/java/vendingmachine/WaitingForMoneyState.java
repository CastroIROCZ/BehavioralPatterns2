package vendingmachine;

public class WaitingForMoneyState implements StateOfVendingMachine {

    @Override
    public void selectSnack(VendingMachine machine, String snackName) {
        System.out.println("Complete current transaction first");
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        machine.addMoney(amount);

        Snack snack = machine.getSelectedSnack();

        if (machine.getInsertedMoneyInCents() >=
                snack.getPriceInCents()) {

            machine.setState(new DispensingSnackState());
            machine.dispenseSnack();
        }
    }

    @Override
    public void dispenseSnack(VendingMachine machine) {
        System.out.println("Not enough money");
    }

    @Override
    public String getName() {
        return "Waiting for Money";
    }
}