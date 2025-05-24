package inventory;

import java.util.*;


public class ItemLootTable {
    private List<LootEntry> entries;
    private Random random;

    public ItemLootTable() {
        this.entries = new ArrayList<>();
        this.random = new Random();
    }

    public void addEntry(String itemId, float dropChance, int minQuantity, int maxQuantity) {
        entries.add(new LootEntry(itemId, dropChance, minQuantity, maxQuantity));
    }

    public List<ItemStack> generateLoot() {
        List<ItemStack> loot = new ArrayList<>();

        for (LootEntry entry : entries) {
            if (random.nextFloat() < entry.dropChance) {
                int quantity = random.nextInt(entry.maxQuantity - entry.minQuantity + 1) + entry.minQuantity;
                Item item = ItemFactory.createItem(entry.itemId);
                if (item != null) {
                    loot.add(new ItemStack(item, quantity));
                }
            }
        }

        return loot;
    }

    private static class LootEntry {
        String itemId;
        float dropChance;
        int minQuantity;
        int maxQuantity;

        LootEntry(String itemId, float dropChance, int minQuantity, int maxQuantity) {
            this.itemId = itemId;
            this.dropChance = dropChance;
            this.minQuantity = minQuantity;
            this.maxQuantity = maxQuantity;
        }
    }
}