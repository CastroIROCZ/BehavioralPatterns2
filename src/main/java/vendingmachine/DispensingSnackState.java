package vendingmachine;

public class DispensingSnackState implements StateOfVendingMachine {

    @Override
    public void selectSnack(VendingMachine machine, String snackName) {
        System.out.println("Transaction in progress");
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        System.out.println("Transaction in progress");
    }

    @Override
    public void dispenseSnack(VendingMachine machine) {
        Snack selectedSnack = machine.getSelectedSnack();

        Snack dispensedSnack = machine.getSnackDispenser().dispense(
                selectedSnack.getName(),
                machine.getInsertedMoney()
        );

        machine.completeTransaction(dispensedSnack);
    }

    @Override
    public String getName() {
        return "Dispensing Snack";
    }
}