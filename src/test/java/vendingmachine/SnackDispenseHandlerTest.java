package vendingmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SnackDispenseHandlerTest {

    @Test
    public void testChainPassesRequestToNextHandler() {
        Snack coke = new Snack("Coke", 1.50, 2);
        Snack pepsi = new Snack("Pepsi", 1.50, 2);
        Snack cheetos = new Snack("Cheetos", 1.25, 2);

        SnackDispenseHandler cokeHandler = new SnackHandler(coke);
        SnackDispenseHandler pepsiHandler = new SnackHandler(pepsi);
        SnackDispenseHandler cheetosHandler = new SnackHandler(cheetos);

        cokeHandler.setNextHandler(pepsiHandler);
        pepsiHandler.setNextHandler(cheetosHandler);

        Snack result = cokeHandler.dispense("Cheetos", 1.25);

        assertSame(cheetos, result);
        assertEquals(1, cheetos.getQuantity());
        assertEquals(2, coke.getQuantity());
        assertEquals(2, pepsi.getQuantity());
    }

    @Test
    public void testDispenseWithEnoughMoney() {
        Snack coke = new Snack("Coke", 1.50, 3);
        SnackDispenseHandler handler = new SnackHandler(coke);

        Snack result = handler.dispense("Coke", 2.00);

        assertSame(coke, result);
        assertEquals(2, coke.getQuantity());
    }

    @Test
    public void testDispenseWithInsufficientMoney() {
        Snack kitkat = new Snack("KitKat", 1.75, 3);
        SnackDispenseHandler handler = new SnackHandler(kitkat);

        Snack result = handler.dispense("KitKat", 1.00);

        assertNull(result);
        assertEquals(3, kitkat.getQuantity());
    }

    @Test
    public void testDispenseOutOfStock() {
        Snack snickers = new Snack("Snickers", 2.00, 0);
        SnackDispenseHandler handler = new SnackHandler(snickers);

        Snack result = handler.dispense("Snickers", 2.00);

        assertNull(result);
        assertEquals(0, snickers.getQuantity());
    }

    @Test
    public void testSnackNotFoundInChain() {
        Snack coke = new Snack("Coke", 1.50, 2);
        SnackDispenseHandler handler = new SnackHandler(coke);

        assertNull(handler.dispense("Doritos", 2.00));
        assertEquals(2, coke.getQuantity());
    }
}