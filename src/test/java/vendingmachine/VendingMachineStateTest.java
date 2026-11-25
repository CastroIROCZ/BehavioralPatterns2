package vendingmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VendingMachineStateTest {

    private VendingMachine machine;
    private Snack coke;

    @BeforeEach
    public void setUp() {
        coke = new Snack("Coke", 1.50, 2);

        SnackDispenseHandler handler = new SnackHandler(coke);

        machine = new VendingMachine(handler);
        machine.addSnack(coke);
    }

    @Test
    public void testInitialState() {
        assertEquals("Idle", machine.getState().getName());
        assertNull(machine.getSelectedSnack());
    }

    @Test
    public void testSelectSnack() {
        machine.selectSnack("Coke");

        assertEquals("Waiting for Money",
                machine.getState().getName());

        assertSame(coke, machine.getSelectedSnack());
    }

    @Test
    public void testInvalidSnackSelection() {
        machine.selectSnack("Pepsi");

        assertEquals("Idle", machine.getState().getName());
        assertNull(machine.getSelectedSnack());
    }

    @Test
    public void testMoneyBeforeSelection() {
        machine.insertMoney(2.00);

        assertEquals("Idle", machine.getState().getName());
        assertEquals(0.00, machine.getInsertedMoney(), 0.001);
    }

    @Test
    public void testInsufficientMoney() {
        machine.selectSnack("Coke");
        machine.insertMoney(1.00);

        assertEquals("Waiting for Money",
                machine.getState().getName());

        assertEquals(1.00, machine.getInsertedMoney(), 0.001);
        assertEquals(2, coke.getQuantity());
    }

    @Test
    public void testCannotDispenseWithoutEnoughMoney() {
        machine.selectSnack("Coke");
        machine.insertMoney(0.50);
        machine.dispenseSnack();

        assertEquals("Waiting for Money",
                machine.getState().getName());

        assertEquals(2, coke.getQuantity());
    }

    @Test
    public void testDispenseWithExactMoney() {
        machine.selectSnack("Coke");
        machine.insertMoney(1.50);

        assertEquals("Idle", machine.getState().getName());
        assertSame(coke, machine.getLastDispensedSnack());
        assertEquals(1, coke.getQuantity());
        assertEquals(0.00, machine.getLastReturnedMoney(), 0.001);
    }

    @Test
    public void testOutOfStockRefund() {
        coke.decreaseQuantity();
        coke.decreaseQuantity();

        machine.selectSnack("Coke");
        machine.insertMoney(1.50);

        assertEquals("Idle", machine.getState().getName());
        assertNull(machine.getLastDispensedSnack());
        assertEquals(0, coke.getQuantity());
        assertEquals(1.50, machine.getLastReturnedMoney(), 0.001);
    }

    @Test
    public void testInvalidMoney() {
        machine.selectSnack("Coke");

        assertThrows(IllegalArgumentException.class,
                () -> machine.insertMoney(-1.00));

        assertThrows(IllegalArgumentException.class,
                () -> machine.insertMoney(0.00));

        assertThrows(IllegalArgumentException.class,
                () -> machine.insertMoney(1.555));

        assertEquals(0.00, machine.getInsertedMoney(), 0.001);
    }
}