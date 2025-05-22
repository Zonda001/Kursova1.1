package inventory;

import java.util.*;

public class InventoryStats {
    private final int totalItems;
    private final int totalValue;
    private final int usedSlots;
    private final int freeSlots;
    private final Map<ItemType, Integer> itemsByType;

    public InventoryStats(int totalItems, int totalValue, int usedSlots,
                          int freeSlots, Map<ItemType, Integer> itemsByType) {
        this.totalItems = totalItems;
        this.totalValue = totalValue;
        this.usedSlots = usedSlots;
        this.freeSlots = freeSlots;
        this.itemsByType = new EnumMap<>(itemsByType);
    }

    // Getters
    public int getTotalItems() { return totalItems; }
    public int getTotalValue() { return totalValue; }
    public int getUsedSlots() { return usedSlots; }
    public int getFreeSlots() { return freeSlots; }
    public Map<ItemType, Integer> getItemsByType() { return new EnumMap<>(itemsByType); }

    public int getItemCountByType(ItemType type) {
        return itemsByType.getOrDefault(type, 0);
    }
}