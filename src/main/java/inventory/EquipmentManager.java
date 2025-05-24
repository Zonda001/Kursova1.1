package inventory;

import java.util.*;

public class EquipmentManager {
    private Map<EquipmentSlot, Equipment> equippedItems;
    private Inventory inventory;

    public EquipmentManager(Inventory inventory) {
        this.inventory = inventory;
        this.equippedItems = new EnumMap<>(EquipmentSlot.class);
    }

    // Equip item from inventory
    public boolean equipItem(String itemId) {
        ItemStack stack = inventory.findItemStack(itemId);
        if (stack == null || !(stack.getItem() instanceof Equipment)) {
            return false;
        }

        Equipment equipment = (Equipment) stack.getItem();
        EquipmentSlot slot = equipment.getSlot();

        // Unequip current item if any
        if (equippedItems.containsKey(slot)) {
            unequipItem(slot);
        }

        // Remove from inventory and equip
        inventory.removeItem(itemId, 1);
        equippedItems.put(slot, equipment);

        return true;
    }

    // Unequip item to inventory
    public boolean unequipItem(EquipmentSlot slot) {
        Equipment equipment = equippedItems.get(slot);
        if (equipment == null) return false;

        // Try to add back to inventory
        if (inventory.addItem(equipment, 1)) {
            equippedItems.remove(slot);
            return true;
        }

        return false; // Couldn't add to inventory (full)
    }

    // Get equipped item in slot
    public Equipment getEquippedItem(EquipmentSlot slot) {
        return equippedItems.get(slot);
    }

    // Check if slot is equipped
    public boolean isSlotEquipped(EquipmentSlot slot) {
        return equippedItems.containsKey(slot);
    }

    // Get total stats from all equipped items
    public Map<StatType, Integer> getTotalStats() {
        Map<StatType, Integer> totalStats = new EnumMap<>(StatType.class);

        for (Equipment equipment : equippedItems.values()) {
            for (Map.Entry<StatType, Integer> entry : equipment.getStats().entrySet()) {
                totalStats.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }
        }

        return totalStats;
    }

    // Get all equipped items
    public Map<EquipmentSlot, Equipment> getAllEquippedItems() {
        return new EnumMap<>(equippedItems);
    }

    // Calculate total equipment value
    public int getTotalEquipmentValue() {
        return equippedItems.values().stream()
                .mapToInt(Equipment::getValue)
                .sum();
    }
}