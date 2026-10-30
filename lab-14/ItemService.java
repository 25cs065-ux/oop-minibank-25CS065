
import java.util.ArrayList;
import java.util.List;

public class ItemService {
    private ArrayList<Item> items = new ArrayList<>();

    public void addItem(String name) {
        items.add(new Item(name));
    }

    public boolean removeItem(String name) {
        return items.removeIf(item -> item.getName().equalsIgnoreCase(name));
    }

    public int getCount() {
        return items.size();
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public void performAction(String action, String name) {
        switch (action.toUpperCase()) {
            case "ADD":
                addItem(name);
                break;
            case "REMOVE":
                removeItem(name);
                break;
            default:
                throw new IllegalArgumentException("Invalid action: " + action);
        }
    }
}
