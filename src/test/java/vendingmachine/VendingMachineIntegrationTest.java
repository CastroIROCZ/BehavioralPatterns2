package vendingmachine;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VendingMachineIntegrationTest {

    private VendingMachine machine;

    @BeforeEach
    public void setUp() {
        Snack[] snacks = {
            new Snack("Coke", 1.50, 2),
            new Snack("Pepsi", 1.50, 2),
            new Snack("Cheetos", 1.25, 2),
            new Snack("Doritos", 1.75, 2),
            new Snack("KitKat", 1.25, 2),
            new Snack("Snickers", 2.00, 1)
        };

        SnackDispenseHandler first = new SnackHandler(snacks[0]);
        SnackDispenseHandler previous = first;

        for (int i = 1; i < snacks.length; i++) {
            SnackDispenseHandler next = new SnackHandler(snacks[i]);
            previous.setNextHandler(next);
            previous = next;
        }

        machine = new VendingMachine(first);

        for (Snack snack : snacks) {
            machine.addSnack(snack);
        }
    }

    @Test
    public void testLastHandlerInChain() {
        machine.selectSnack("Snickers");
        machine.insertMoney(2.00);

        assertEquals("Snickers",
                machine.getLastDispensedSnack().getName());

        assertEquals(0,
                machine.getSnack("Snickers").getQuantity());
    }

    @Test
    public void testSnickersOutOfStockAndRefund() {
        machine.selectSnack("Snickers");
        machine.insertMoney(2.00);

        assertEquals(0,
                machine.getSnack("Snickers").getQuantity());

        machine.selectSnack("Snickers");
        machine.insertMoney(2.00);

        assertNull(machine.getLastDispensedSnack());

        assertEquals(2.00,
                machine.getLastReturnedMoney(), 0.001);

        assertEquals("Idle", machine.getState().getName());
    }

    @Test
    public void testMultiplePaymentsAndChange() {
        machine.selectSnack("Coke");
        machine.insertMoney(0.50);

        assertEquals("Waiting for Money",
                machine.getState().getName());

        machine.insertMoney(2.00);

        assertEquals("Coke",
                machine.getLastDispensedSnack().getName());

        assertEquals(1.00,
                machine.getLastReturnedMoney(), 0.001);

        assertEquals("Idle", machine.getState().getName());
    }

    @Test
    public void testAllSixSnacks() {
        String[] names = {
            "Coke", "Pepsi", "Cheetos",
            "Doritos", "KitKat", "Snickers"
        };

        double[] prices = {
            1.50, 1.50, 1.25, 1.75, 1.25, 2.00
        };

        for (int i = 0; i < names.length; i++) {
            machine.selectSnack(names[i]);
            machine.insertMoney(prices[i]);

            assertNotNull(machine.getLastDispensedSnack());

            assertEquals(names[i],
                    machine.getLastDispensedSnack().getName());

            assertEquals("Idle", machine.getState().getName());
        }
    }

    @Test
    public void testDriverOutput() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try (PrintStream capture = new PrintStream(output)) {
            System.setOut(capture);
            VendingMachineDriver.main(new String[0]);
        } finally {
            System.setOut(original);
        }

        String result = output.toString();

        assertTrue(result.contains("Snickers: 0 remaining"));
        assertTrue(result.contains("Refunded: $2.00"));
        assertTrue(result.contains("Dispensed: Coke"));
        assertTrue(result.contains("Dispensed: KitKat"));
    }
}