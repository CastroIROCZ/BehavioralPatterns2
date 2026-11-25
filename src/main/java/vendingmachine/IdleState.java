package vendingmachine;

public class IdleState implements StateOfVendingMachine {

    @Override
    public void selectSnack(VendingMachine machine, String snackName) {
        Snack snack = machine.getSnack(snackName);

        if (snack == null) {
            System.out.println("Snack not found");
            return;
        }

        machine.startTransaction(snack);
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        System.out.println("Select a snack first");
    }

    @Override
    public void dispenseSnack(VendingMachine machine) {
        System.out.println("No snack selected");
    }

    @Override
    public String getName() {
        return "Idle";
    }
}