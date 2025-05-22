package inventory;

import java.util.*;

public class ItemFactory {
    private static final Map<String, Item> itemPrototypes = new HashMap<>();

    static {
        initializeItems();
    }

    private static void initializeItems() {
        // Weapons
        registerWeapon("katana_basic", "Steel Katana", "A well-crafted steel katana",
                ItemRarity.COMMON, 100, WeaponType.KATANA, 25, 1.2f, 5, 100, 1);

        registerWeapon("katana_master", "Master's Katana", "A legendary blade forged by a master smith",
                ItemRarity.LEGENDARY, 1000, WeaponType.KATANA, 60, 1.5f, 15, 200, 15);

        registerWeapon("wakizashi_iron", "Iron Wakizashi", "A shorter companion blade",
                ItemRarity.COMMON, 75, WeaponType.WAKIZASHI, 18, 1.5f, 8, 80, 1);

        // Armor
        registerArmor("leather_chest", "Leather Chestplate", "Basic leather protection",
                ItemRarity.COMMON, 50, EquipmentSlot.CHEST, 10, 5, 120, 1);

        registerArmor("steel_chest", "Steel Chestplate", "Heavy steel protection",
                ItemRarity.UNCOMMON, 200, EquipmentSlot.CHEST, 25, 10, 150, 5);

        registerArmor("samurai_helmet", "Samurai Helmet", "Traditional samurai headgear",
                ItemRarity.RARE, 300, EquipmentSlot.HEAD, 15, 20, 100, 8);

        // Consumables
        registerConsumable("health_potion_small", "Small Health Potion", "Restores 50 HP",
                ItemRarity.COMMON, 25, ConsumableType.HEALTH_POTION, 50, 0, 10);

        registerConsumable("health_potion_large", "Large Health Potion", "Restores 150 HP",
                ItemRarity.UNCOMMON, 75, ConsumableType.HEALTH_POTION, 150, 0, 5);

        registerConsumable("stamina_potion", "Stamina Elixir", "Restores 100 stamina",
                ItemRarity.COMMON, 30, ConsumableType.STAMINA_POTION, 100, 0, 8);

        registerConsumable("strength_buff", "Warrior's Brew", "Increases attack damage for 60 seconds",
                ItemRarity.RARE, 100, ConsumableType.BUFF_POTION, 20, 60, 3);
    }

    private static void registerWeapon(String id, String name, String description,
                                       ItemRarity rarity, int value, WeaponType type,
                                       int damage, float speed, int crit, int durability, int level) {
        itemPrototypes.put(id, new Weapon(id, name, description, rarity, value,
                type, damage, speed, crit, durability, level));
    }

    private static void registerArmor(String id, String name, String description,
                                      ItemRarity rarity, int value, EquipmentSlot slot,
                                      int defense, int magicRes, int durability, int level) {
        itemPrototypes.put(id, new Armor(id, name, description, rarity, value,
                slot, defense, magicRes, durability, level));
    }

    private static void registerConsumable(String id, String name, String description,
                                           ItemRarity rarity, int value, ConsumableType type,
                                           int effectValue, int duration, int stackSize) {
        itemPrototypes.put(id, new Consumable(id, name, description, rarity, value,
                type, effectValue, duration, stackSize));
    }

    // Create item by ID
    public static Item createItem(String itemId) {
        Item prototype = itemPrototypes.get(itemId);
        return prototype != null ? prototype.copy() : null;
    }

    // Get all available item IDs
    public static Set<String> getAllItemIds() {
        return new HashSet<>(itemPrototypes.keySet());
    }

    // Get items by type
    public static List<String> getItemIdsByType(ItemType type) {
        return itemPrototypes.entrySet().stream()
                .filter(entry -> entry.getValue().getType() == type)
                .map(Map.Entry::getKey)
                .collect(java.util.stream.Collectors.toList());
    }

    // Check if item exists
    public static boolean itemExists(String itemId) {
        return itemPrototypes.containsKey(itemId);
    }
}
