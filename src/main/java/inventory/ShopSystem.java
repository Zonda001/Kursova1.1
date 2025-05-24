package inventory;

import java.util.*;

public class ShopSystem {
    private Inventory shopInventory;
    private Map<String, Integer> buyPrices;
    private Map<String, Integer> sellPrices;
    private float buyPriceMultiplier;
    private float sellPriceMultiplier;

    public ShopSystem(int shopSlots) {
        this.shopInventory = new Inventory(shopSlots);
        this.buyPrices = new HashMap<>();
        this.sellPrices = new HashMap<>();
        this.buyPriceMultiplier = 1.2f; // Shop sells at 20% markup
        this.sellPriceMultiplier = 0.7f; // Shop buys at 30% discount

        initializeShopInventory();
    }

    private void initializeShopInventory() {
        // Add basic shop items
        shopInventory.addItem(ItemFactory.createItem("health_potion_small"), 20);
        shopInventory.addItem(ItemFactory.createItem("health_potion_large"), 10);
        shopInventory.addItem(ItemFactory.createItem("stamina_potion"), 15);
        shopInventory.addItem(ItemFactory.createItem("leather_chest"), 3);
        shopInventory.addItem(ItemFactory.createItem("katana_basic"), 2);

        // Set custom prices for specific items
        setBuyPrice("health_potion_small", 30);
        setBuyPrice("health_potion_large", 90);
        setSellPrice("katana_master", 800); // Shop pays less for rare items
    }

    public void setBuyPrice(String itemId, int price) {
        buyPrices.put(itemId, price);
    }

    public void setSellPrice(String itemId, int price) {
        sellPrices.put(itemId, price);
    }

    public int getBuyPrice(String itemId) {
        if (buyPrices.containsKey(itemId)) {
            return buyPrices.get(itemId);
        }

        Item item = ItemFactory.createItem(itemId);
        if (item != null) {
            return Math.round(item.getValue() * buyPriceMultiplier);
        }

        return 0;
    }

    public int getSellPrice(String itemId) {
        if (sellPrices.containsKey(itemId)) {
            return sellPrices.get(itemId);
        }

        Item item = ItemFactory.createItem(itemId);
        if (item != null) {
            return Math.round(item.getValue() * sellPriceMultiplier);
        }

        return 0;
    }

    // Player buys from shop
    public boolean buyItem(Inventory playerInventory, String itemId, int quantity) {
        if (!shopInventory.hasItem(itemId, quantity)) {
            return false;
        }

        int totalCost = getBuyPrice(itemId) * quantity;
        if (!playerInventory.hasCurrency(totalCost)) {
            return false;
        }

        ItemStack shopStack = shopInventory.findItemStack(itemId);
        if (shopStack == null) {
            return false;
        }

        // Remove from shop and add to player
        shopInventory.removeItem(itemId, quantity);
        playerInventory.addItem(shopStack.getItem(), quantity);
        playerInventory.removeCurrency(totalCost);

        return true;
    }

    // Player sells to shop
    public boolean sellItem(Inventory playerInventory, String itemId, int quantity) {
        if (!playerInventory.hasItem(itemId, quantity)) {
            return false;
        }

        ItemStack playerStack = playerInventory.findItemStack(itemId);
        if (playerStack == null) {
            return false;
        }

        int totalValue = getSellPrice(itemId) * quantity;

        // Remove from player and add currency
        playerInventory.removeItem(itemId, quantity);
        playerInventory.addCurrency(totalValue);

        // Optionally add to shop inventory
        shopInventory.addItem(playerStack.getItem(), quantity);

        return true;
    }

    // Get shop inventory for display
    public List<ItemStack> getShopItems() {
        return shopInventory.getAllItems();
    }

    // Check if shop has item
    public boolean hasItem(String itemId, int quantity) {
        return shopInventory.hasItem(itemId, quantity);
    }

    // Restock shop (called periodically)
    public void restockShop() {
        // Add basic consumables if low
        if (shopInventory.getItemCount("health_potion_small") < 5) {
            shopInventory.addItem(ItemFactory.createItem("health_potion_small"), 15);
        }

        if (shopInventory.getItemCount("stamina_potion") < 3) {
            shopInventory.addItem(ItemFactory.createItem("stamina_potion"), 10);
        }

        // Randomly add rare items
        Random random = new Random();
        if (random.nextFloat() < 0.1f) { // 10% chance
            String[] rareItems = {"katana_master", "samurai_helmet", "strength_buff"};
            String rareItem = rareItems[random.nextInt(rareItems.length)];
            shopInventory.addItem(ItemFactory.createItem(rareItem), 1);
        }
    }
}