package inventory;

import java.util.*;
import java.util.stream.Collectors;

public class Inventory {
    private List<ItemStack> items;
    private int maxSlots;
    private int currency;

    public Inventory(int maxSlots) {
        this.maxSlots = maxSlots;
        this.items = new ArrayList<>();
        this.currency = 0;
    }

    // Add item to inventory
    public boolean addItem(Item item, int quantity) {
        if (quantity <= 0) return false;

        int remainingQuantity = quantity;

        // Try to stack with existing items first
        if (item.isStackable()) {
            for (ItemStack stack : items) {
                if (stack.canStack(item)) {
                    int added = stack.addItems(remainingQuantity);
                    remainingQuantity -= added;
                    if (remainingQuantity <= 0) {
                        return true;
                    }
                }
            }
        }

        // Create new stacks for remaining items
        while (remainingQuantity > 0 && items.size() < maxSlots) {
            int stackSize = Math.min(remainingQuantity, item.getMaxStackSize());
            items.add(new ItemStack(item.copy(), stackSize));
            remainingQuantity -= stackSize;
        }

        return remainingQuantity <= 0;
    }

    // Remove item from inventory
    public int removeItem(String itemId, int quantity) {
        int toRemove = quantity;
        Iterator<ItemStack> iterator = items.iterator();

        while (iterator.hasNext() && toRemove > 0) {
            ItemStack stack = iterator.next();
            if (stack.getItem().getId().equals(itemId)) {
                int removed = stack.removeItems(toRemove);
                toRemove -= removed;

                if (stack.isEmpty()) {
                    iterator.remove();
                }
            }
        }

        return quantity - toRemove; // return actual amount removed
    }

    // Get total quantity of specific item
    public int getItemCount(String itemId) {
        return items.stream()
                .filter(stack -> stack.getItem().getId().equals(itemId))
                .mapToInt(ItemStack::getQuantity)
                .sum();
    }

    // Check if inventory has specific item with quantity
    public boolean hasItem(String itemId, int quantity) {
        return getItemCount(itemId) >= quantity;
    }

    // Get all items of specific type
    public List<ItemStack> getItemsByType(ItemType type) {
        return items.stream()
                .filter(stack -> stack.getItem().getType() == type)
                .collect(Collectors.toList());
    }

    // Get all consumables
    public List<ItemStack> getConsumables() {
        return getItemsByType(ItemType.CONSUMABLE);
    }

    // Get all weapons
    public List<ItemStack> getWeapons() {
        return getItemsByType(ItemType.WEAPON);
    }

    // Get all armor
    public List<ItemStack> getArmor() {
        return getItemsByType(ItemType.ARMOR);
    }

    // Sort inventory by various criteria
    public void sortByName() {
        items.sort(Comparator.comparing(stack -> stack.getItem().getName()));
    }

    public void sortByType() {
        items.sort(Comparator.comparing(stack -> stack.getItem().getType()));
    }

    public void sortByRarity() {
        items.sort(Comparator.comparing(stack -> stack.getItem().getRarity()));
    }

    public void sortByValue() {
        items.sort(Comparator.comparing(stack -> stack.getItem().getValue(),
                Comparator.reverseOrder()));
    }

    // Currency management
    public boolean addCurrency(int amount) {
        if (amount < 0) return false;
        currency += amount;
        return true;
    }

    public boolean removeCurrency(int amount) {
        if (amount < 0 || currency < amount) return false;
        currency -= amount;
        return true;
    }

    public boolean hasCurrency(int amount) {
        return currency >= amount;
    }

    // Inventory space management
    public boolean isFull() {
        return items.size() >= maxSlots;
    }

    public int getFreeSlots() {
        return maxSlots - items.size();
    }

    public int getUsedSlots() {
        return items.size();
    }

    // Get total inventory value
    public int getTotalValue() {
        return items.stream()
                .mapToInt(stack -> stack.getItem().getValue() * stack.getQuantity())
                .sum() + currency;
    }

    // Clear inventory
    public void clear() {
        items.clear();
        currency = 0;
    }

    // Find item stack by item ID
    public ItemStack findItemStack(String itemId) {
        return items.stream()
                .filter(stack -> stack.getItem().getId().equals(itemId))
                .findFirst()
                .orElse(null);
    }

    // Get all items as list
    public List<ItemStack> getAllItems() {
        return new ArrayList<>(items);
    }

    // Getters
    public int getMaxSlots() { return maxSlots; }
    public int getCurrency() { return currency; }
    public void setCurrency(int currency) { this.currency = currency; }
}
