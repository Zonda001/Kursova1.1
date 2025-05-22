package inventory;

public class InventoryEvent {
    public enum Type {
        ITEM_ADDED, ITEM_REMOVED, CURRENCY_CHANGED, ITEM_EQUIPPED, ITEM_UNEQUIPPED
    }

    private Type type;
    private String itemId;
    private int quantity;
    private int newCurrency;

    public InventoryEvent(Type type, String itemId, int quantity) {
        this.type = type;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public InventoryEvent(Type type, int newCurrency) {
        this.type = type;
        this.newCurrency = newCurrency;
    }

    // Getters
    public Type getType() { return type; }
    public String getItemId() { return itemId; }
    public int getQuantity() { return quantity; }
    public int getNewCurrency() { return newCurrency; }
}
