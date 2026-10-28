
import java.util.ArrayList;
import java.util.List;

class OutOfStockException extends Exception {
    private final int shortfall;

    public OutOfStockException(int shortfall) {
        super("Insufficient stock. Shortfall: " + shortfall);
        this.shortfall = shortfall;
    }

    public int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {
    private int stock;

    public Warehouse(int stock) {
        this.stock = stock;
    }

    public void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {
        if (qty <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than zero.");
        }

        if (qty > stock) {
            throw new OutOfStockException(qty - stock);
        }

        stock -= qty;
        System.out.println("Issued " + qty + " units of " + item);
        System.out.println("Remaining stock: " + stock);
    }
}

class StockRequest {
    String item;
    int qty;

    StockRequest(String item, int qty) {
        this.item = item;
        this.qty = qty;
    }
}

public class StockIssue {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse(20);

        List<StockRequest> requests = new ArrayList<>();
        requests.add(new StockRequest("Keyboard", 5));
        requests.add(new StockRequest("Mouse", 25));
        requests.add(new StockRequest("Monitor", 0));
        requests.add(new StockRequest("Cable", 10));

        for (StockRequest request : requests) {
            try {
                warehouse.issue(request.item, request.qty);
            } catch (OutOfStockException e) {
                System.out.println("Issue failed for " + request.item
                        + ": short by " + e.getShortfall());
            } catch (InvalidQuantityException e) {
                System.out.println("Issue failed for " + request.item
                        + ": " + e.getMessage());
            }
        }

        System.out.println("All stock requests processed.");
    }
}
