package inventory;

public class ItemStack {
    private Item item;
    private int quantity;

    public ItemStack(Item item, int quantity) {
        this.item = item;
        this.quantity = Math.min(quantity, item.getMaxStackSize());
    }

    public boolean canStack(Item otherItem) {
        return item.isStackable() &&
                item.getId().equals(otherItem.getId()) &&
                quantity < item.getMaxStackSize();
    }

    public int addItems(int amount) {
        int canAdd = Math.min(amount, item.getMaxStackSize() - quantity);
        quantity += canAdd;
        return amount - canAdd; // return remaining items that couldn't be added
    }

    public int removeItems(int amount) {
        int removed = Math.min(amount, quantity);
        quantity -= removed;
        return removed;
    }

    public boolean isEmpty() {
        return quantity <= 0;
    }

    public ItemStack split(int amount) {
        int splitAmount = Math.min(amount, quantity);
        quantity -= splitAmount;
        return new ItemStack(item.copy(), splitAmount);
    }

    // Getters
    public Item getItem() { return item; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}