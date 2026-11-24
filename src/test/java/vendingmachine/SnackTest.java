package vendingmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SnackTest {

    @Test
    public void testSnackInformation() {
        Snack snack = new Snack("Coke", 1.50, 5);

        assertEquals("Coke", snack.getName());
        assertEquals(1.50, snack.getPrice(), 0.001);
        assertEquals(5, snack.getQuantity());
    }

    @Test
    public void testDecreaseQuantity() {
        Snack snack = new Snack("Pepsi", 1.50, 3);

        assertTrue(snack.decreaseQuantity());
        assertEquals(2, snack.getQuantity());
    }

    @Test
    public void testQuantityCannotBeNegative() {
        Snack snack = new Snack("Snickers", 2.00, 1);

        assertTrue(snack.decreaseQuantity());
        assertFalse(snack.decreaseQuantity());
        assertEquals(0, snack.getQuantity());
    }

    @Test
    public void testInvalidSnackInformation() {
        assertThrows(IllegalArgumentException.class,
                () -> new Snack("", 1.50, 2));

        assertThrows(IllegalArgumentException.class,
                () -> new Snack("Coke", -1.50, 2));

        assertThrows(IllegalArgumentException.class,
                () -> new Snack("Coke", 1.50, -1));

        assertThrows(IllegalArgumentException.class,
                () -> new Snack("Coke", 1.555, 2));
    }
}