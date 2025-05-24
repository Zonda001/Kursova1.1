package inventory;

public class LootTableFactory {

    public static ItemLootTable createBasicEnemyLoot() {
        ItemLootTable table = new ItemLootTable();
        table.addEntry("health_potion_small", 0.3f, 1, 2);
        table.addEntry("stamina_potion", 0.2f, 1, 1);
        return table;
    }

    public static ItemLootTable createEliteEnemyLoot() {
        ItemLootTable table = new ItemLootTable();
        table.addEntry("health_potion_large", 0.4f, 1, 2);
        table.addEntry("stamina_potion", 0.3f, 1, 3);
        table.addEntry("katana_basic", 0.1f, 1, 1);
        table.addEntry("leather_chest", 0.15f, 1, 1);
        return table;
    }

    public static ItemLootTable createBossLoot() {
        ItemLootTable table = new ItemLootTable();
        table.addEntry("katana_master", 0.2f, 1, 1);
        table.addEntry("samurai_helmet", 0.25f, 1, 1);
        table.addEntry("steel_chest", 0.3f, 1, 1);
        table.addEntry("health_potion_large", 0.8f, 2, 5);
        table.addEntry("strength_buff", 0.4f, 1, 3);
        return table;
    }

    public static ItemLootTable createTreasureChestLoot() {
        ItemLootTable table = new ItemLootTable();
        table.addEntry("katana_basic", 0.4f, 1, 1);
        table.addEntry("wakizashi_iron", 0.3f, 1, 1);
        table.addEntry("health_potion_large", 0.6f, 1, 3);
        table.addEntry("strength_buff", 0.2f, 1, 2);
        return table;
    }
}
