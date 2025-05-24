package inventory;

public class Armor extends Equipment {
    private int defense;
    private int magicResistance;

    public Armor(String id, String name, String description, ItemRarity rarity,
                 int value, EquipmentSlot slot, int defense, int magicResistance,
                 int durability, int levelRequirement) {
        super(id, name, description, ItemType.ARMOR, rarity, value,
                slot, durability, levelRequirement);
        this.defense = defense;
        this.magicResistance = magicResistance;

        // Add armor stats
        addStat(StatType.DEFENSE, defense);
        addStat(StatType.MAGIC_RESISTANCE, magicResistance);
    }

    @Override
    public Item copy() {
        Armor copy = new Armor(id, name, description, rarity, value, slot,
                defense, magicResistance, maxDurability, levelRequirement);
        copy.durability = this.durability;
        return copy;
    }

    // Getters
    public int getDefense() { return defense; }
    public int getMagicResistance() { return magicResistance; }
}