
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemServiceTest {

    @Test
    public void testAddingItemIncreasesCount() {
        ItemService service = new ItemService();
        service.addItem("Laptop");
        assertEquals(1, service.getCount());
    }

    @Test
    public void testInvalidActionThrowsException() {
        ItemService service = new ItemService();

        assertThrows(IllegalArgumentException.class, () -> {
            service.performAction("UPDATE", "Laptop");
        });
    }

    @Test
    public void testRemovingItemDecreasesCount() {
        ItemService service = new ItemService();
        service.addItem("Laptop");
        service.removeItem("Laptop");
        assertEquals(0, service.getCount());
    }
}
