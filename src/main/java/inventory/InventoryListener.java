package inventory;


import java.util.ArrayList;
import java.util.List;

public interface InventoryListener {
    void onInventoryChanged(InventoryEvent event);
}

// Enhanced Inventory with event system
class EnhancedInventory extends Inventory {
    private List<InventoryListener> listeners;

    public EnhancedInventory(int maxSlots) {
        super(maxSlots);
        this.listeners = new ArrayList<>();
    }

    public void addListener(InventoryListener listener) {
        listeners.add(listener);
    }

    public void removeListener(InventoryListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(InventoryEvent event) {
        for (InventoryListener listener : listeners) {
            listener.onInventoryChanged(event);
        }
    }

    @Override
    public boolean addItem(Item item, int quantity) {
        boolean result = super.addItem(item, quantity);
        if (result) {
            notifyListeners(new InventoryEvent(InventoryEvent.Type.ITEM_ADDED,
                    item.getId(), quantity));
        }
        return result;
    }

    @Override
    public int removeItem(String itemId, int quantity) {
        int removed = super.removeItem(itemId, quantity);
        if (removed > 0) {
            notifyListeners(new InventoryEvent(InventoryEvent.Type.ITEM_REMOVED,
                    itemId, removed));
        }
        return removed;
    }

    @Override
    public boolean addCurrency(int amount) {
        boolean result = super.addCurrency(amount);
        if (result) {
            notifyListeners(new InventoryEvent(InventoryEvent.Type.CURRENCY_CHANGED,
                    getCurrency()));
        }
        return result;
    }

    @Override
    public boolean removeCurrency(int amount) {
        boolean result = super.removeCurrency(amount);
        if (result) {
            notifyListeners(new InventoryEvent(InventoryEvent.Type.CURRENCY_CHANGED,
                    getCurrency()));
        }
        return result;
    }
}