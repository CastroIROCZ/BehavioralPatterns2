package vendingmachine;

public class VendingMachineDriver {

    public static void main(String[] args) {

        Snack coke = new Snack("Coke", 1.50, 2);
        Snack pepsi = new Snack("Pepsi", 1.50, 2);
        Snack cheetos = new Snack("Cheetos", 1.25, 2);
        Snack doritos = new Snack("Doritos", 1.75, 2);
        Snack kitkat = new Snack("KitKat", 1.25, 2);
        Snack snickers = new Snack("Snickers", 2.00, 1);

        SnackDispenseHandler cokeHandler = new SnackHandler(coke);
        SnackDispenseHandler pepsiHandler = new SnackHandler(pepsi);
        SnackDispenseHandler cheetosHandler = new SnackHandler(cheetos);
        SnackDispenseHandler doritosHandler = new SnackHandler(doritos);
        SnackDispenseHandler kitkatHandler = new SnackHandler(kitkat);
        SnackDispenseHandler snickersHandler = new SnackHandler(snickers);

        cokeHandler.setNextHandler(pepsiHandler);
        pepsiHandler.setNextHandler(cheetosHandler);
        cheetosHandler.setNextHandler(doritosHandler);
        doritosHandler.setNextHandler(kitkatHandler);
        kitkatHandler.setNextHandler(snickersHandler);

        VendingMachine machine = new VendingMachine(cokeHandler);

        machine.addSnack(coke);
        machine.addSnack(pepsi);
        machine.addSnack(cheetos);
        machine.addSnack(doritos);
        machine.addSnack(kitkat);
        machine.addSnack(snickers);

        System.out.println("Vending Machine");
        System.out.println("-------------------------");

        System.out.println("\nInitial Inventory:");
        printInventory(machine);

        System.out.println("\nBuying Coke with multiple payments");
        machine.selectSnack("Coke");
        machine.insertMoney(0.50);

        System.out.println("State: " + machine.getState().getName());
        System.out.printf("Money inserted: $%.2f%n",
                machine.getInsertedMoney());

        machine.insertMoney(1.00);
        printResult(machine);

        buySnack(machine, "Pepsi", 2.00);
        buySnack(machine, "Cheetos", 1.25);
        buySnack(machine, "Doritos", 1.75);
        buySnack(machine, "KitKat", 1.25);

        System.out.println("\nSnickers Stock Test");

        buySnack(machine, "Snickers", 2.00);

        System.out.println("Snickers quantity: " +
                snickers.getQuantity());

        buySnack(machine, "Snickers", 2.00);

        System.out.println("\nFinal Inventory:");
        printInventory(machine);
    }

    private static void buySnack(VendingMachine machine,
                                 String snackName, double amount) {

        System.out.println("\nBuying " + snackName);

        machine.selectSnack(snackName);
        machine.insertMoney(amount);

        printResult(machine);
    }

    private static void printResult(VendingMachine machine) {

        Snack result = machine.getLastDispensedSnack();

        if (result != null) {
            System.out.println("Dispensed: " + result.getName());

            System.out.printf("Change: $%.2f%n",
                    machine.getLastReturnedMoney());
        } else {
            System.out.println("Snack unavailable");

            System.out.printf("Refunded: $%.2f%n",
                    machine.getLastReturnedMoney());
        }

        System.out.println("State: " + machine.getState().getName());
    }

    private static void printInventory(VendingMachine machine) {

        for (Snack snack : machine.getSnacks()) {

            System.out.printf("%s: %d remaining%n",
                    snack.getName(), snack.getQuantity());
        }
    }
}