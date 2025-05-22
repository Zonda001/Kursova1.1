package inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;

public class GameInventorySystem {
    private EnhancedInventory playerInventory;
    private EquipmentManager equipmentManager;
    private ShopSystem shopSystem;
    private InventorySaveManager saveManager;

    public GameInventorySystem(int inventorySlots, int shopSlots) {
        this.playerInventory = new EnhancedInventory(inventorySlots);
        this.equipmentManager = new EquipmentManager(playerInventory);
        this.shopSystem = new ShopSystem(shopSlots);
        this.saveManager = new InventorySaveManager();

        setupInventoryListeners();
    }

    private void setupInventoryListeners() {
        playerInventory.addListener(event -> {
            switch (event.getType()) {
                case ITEM_ADDED:
                    System.out.println("Added " + event.getQuantity() + "x " + event.getItemId());
                    break;
                case ITEM_REMOVED:
                    System.out.println("Removed " + event.getQuantity() + "x " + event.getItemId());
                    break;
                case CURRENCY_CHANGED:
                    System.out.println("Currency changed to: " + event.getNewCurrency());
                    break;
            }
        });
    }

    // Initialize with starting items
    public void initializeStartingInventory() {
        // Give player starting items
        playerInventory.addItem(ItemFactory.createItem("katana_basic"), 1);
        playerInventory.addItem(ItemFactory.createItem("leather_chest"), 1);
        playerInventory.addItem(ItemFactory.createItem("health_potion_small"), 5);
        playerInventory.addCurrency(100);

        // Equip starting gear
        equipmentManager.equipItem("katana_basic");
        equipmentManager.equipItem("leather_chest");
    }

    // Use consumable item
    public boolean useConsumable(String itemId) {
        ItemStack stack = playerInventory.findItemStack(itemId);
        if (stack == null || !(stack.getItem() instanceof Consumable)) {
            return false;
        }

        Consumable consumable = (Consumable) stack.getItem();

        // Apply consumable effect (this would integrate with your game's stat system)
        applyConsumableEffect(consumable);

        // Remove one from inventory
        playerInventory.removeItem(itemId, 1);

        return true;
    }

    private void applyConsumableEffect(Consumable consumable) {
        // This would integrate with your player stats system
        switch (consumable.getConsumableType()) {
            case HEALTH_POTION:
                System.out.println("Restored " + consumable.getEffectValue() + " health");
                // player.healHealth(consumable.getEffectValue());
                break;
            case STAMINA_POTION:
                System.out.println("Restored " + consumable.getEffectValue() + " stamina");
                // player.restoreStamina(consumable.getEffectValue());
                break;
            case BUFF_POTION:
                System.out.println("Applied buff for " + consumable.getDuration() + " seconds");
                // player.applyBuff(consumable.getEffectValue(), consumable.getDuration());
                break;
        }
    }

    // Loot enemy
    public void lootEnemy(String enemyType) {
        ItemLootTable lootTable = switch (enemyType.toLowerCase()) {
            case "basic" -> LootTableFactory.createBasicEnemyLoot();
            case "elite" -> LootTableFactory.createEliteEnemyLoot();
            case "boss" -> LootTableFactory.createBossLoot();
            default -> LootTableFactory.createBasicEnemyLoot();
        };

        List<ItemStack> loot = lootTable.generateLoot();
        for (ItemStack stack : loot) {
            playerInventory.addItem(stack.getItem(), stack.getQuantity());
        }

        // Add currency loot
        Random random = new Random();
        int currencyDrop = switch (enemyType.toLowerCase()) {
            case "basic" -> random.nextInt(20) + 5;
            case "elite" -> random.nextInt(50) + 25;
            case "boss" -> random.nextInt(200) + 100;
            default -> random.nextInt(10) + 1;
        };

        playerInventory.addCurrency(currencyDrop);
        System.out.println("Looted " + currencyDrop + " currency from " + enemyType);
    }

    // Save game
    public boolean saveGame(String filename) {
        return saveManager.saveInventory(playerInventory, equipmentManager, filename);
    }

    // Load game
    public boolean loadGame(String filename) {
        return saveManager.loadInventory(playerInventory, equipmentManager, filename);
    }

    // Get player stats from equipment
    public Map<StatType, Integer> getPlayerStats() {
        return equipmentManager.getTotalStats();
    }

    // Getters
    public EnhancedInventory getPlayerInventory() { return playerInventory; }
    public EquipmentManager getEquipmentManager() { return equipmentManager; }
    public ShopSystem getShopSystem() { return shopSystem; }
}