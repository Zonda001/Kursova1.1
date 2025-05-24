package inventory;

import java.util.*;

public class InventoryUtils {

    // Transfer items between inventories
    public static boolean transferItem(Inventory from, Inventory to, String itemId, int quantity) {
        if (!from.hasItem(itemId, quantity)) {
            return false;
        }

        ItemStack sourceStack = from.findItemStack(itemId);
        if (sourceStack == null) {
            return false;
        }

        Item item = sourceStack.getItem();
        int removed = from.removeItem(itemId, quantity);

        if (removed > 0) {
            if (to.addItem(item, removed)) {
                return true;
            } else {
                // Rollback if couldn't add to destination
                from.addItem(item, removed);
                return false;
            }
        }

        return false;
    }

    // Transfer currency between inventories
    public static boolean transferCurrency(Inventory from, Inventory to, int amount) {
        if (from.removeCurrency(amount)) {
            to.addCurrency(amount);
            return true;
        }
        return false;
    }

    // Calculate item stack weight (for future weight system)
    public static int calculateWeight(ItemStack stack) {
        // Base weight calculation - can be extended
        int baseWeight = switch (stack.getItem().getType()) {
            case WEAPON -> 10;
            case ARMOR -> 15;
            case CONSUMABLE -> 1;
            case MATERIAL -> 2;
            case QUEST_ITEM -> 1;
            default -> 1;
        };

        return baseWeight * stack.getQuantity();
    }

    // Find cheapest item of type
    public static ItemStack findCheapestItem(Inventory inventory, ItemType type) {
        return inventory.getItemsByType(type).stream()
                .min(Comparator.comparing(stack -> stack.getItem().getValue()))
                .orElse(null);
    }

    // Find most expensive item of type
    public static ItemStack findMostExpensiveItem(Inventory inventory, ItemType type) {
        return inventory.getItemsByType(type).stream()
                .max(Comparator.comparing(stack -> stack.getItem().getValue()))
                .orElse(null);
    }

    // Count items by rarity
    public static Map<ItemRarity, Integer> countItemsByRarity(Inventory inventory) {
        Map<ItemRarity, Integer> counts = new EnumMap<>(ItemRarity.class);

        for (ItemStack stack : inventory.getAllItems()) {
            ItemRarity rarity = stack.getItem().getRarity();
            counts.merge(rarity, stack.getQuantity(), Integer::sum);
        }

        return counts;
    }

    // Get inventory statistics
    public static InventoryStats getInventoryStats(Inventory inventory) {
        int totalItems = inventory.getAllItems().stream()
                .mapToInt(ItemStack::getQuantity)
                .sum();

        int totalValue = inventory.getTotalValue();
        int usedSlots = inventory.getUsedSlots();
        int freeSlots = inventory.getFreeSlots();

        Map<ItemType, Integer> itemsByType = new EnumMap<>(ItemType.class);
        for (ItemStack stack : inventory.getAllItems()) {
            ItemType type = stack.getItem().getType();
            itemsByType.merge(type, stack.getQuantity(), Integer::sum);
        }

        return new InventoryStats(totalItems, totalValue, usedSlots, freeSlots, itemsByType);
    }

    // Repair all equipment in inventory
    public static void repairAllEquipment(Inventory inventory, int repairAmount) {
        for (ItemStack stack : inventory.getAllItems()) {
            if (stack.getItem() instanceof Equipment equipment) {
                equipment.repair(repairAmount);
            }
        }
    }

    // Find items that need repair
    public static List<ItemStack> findDamagedEquipment(Inventory inventory) {
        return inventory.getAllItems().stream()
                .filter(stack -> stack.getItem() instanceof Equipment)
                .filter(stack -> ((Equipment) stack.getItem()).isDamaged())
                .collect(java.util.stream.Collectors.toList());
    }
}