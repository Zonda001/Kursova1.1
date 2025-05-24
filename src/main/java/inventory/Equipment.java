package inventory;

import java.util.HashMap;
import java.util.Map;

public abstract class Equipment extends Item {
    protected int durability;
    protected int maxDurability;
    protected Map<StatType, Integer> stats;
    protected EquipmentSlot slot;
    protected int levelRequirement;

    public Equipment(String id, String name, String description, ItemType type,
                     ItemRarity rarity, int value, EquipmentSlot slot,
                     int durability, int levelRequirement) {
        super(id, name, description, type, rarity, value, false, 1);
        this.slot = slot;
        this.durability = durability;
        this.maxDurability = durability;
        this.levelRequirement = levelRequirement;
        this.stats = new HashMap<>();
    }

    public void addStat(StatType stat, int value) {
        stats.put(stat, value);
    }

    public int getStat(StatType stat) {
        return stats.getOrDefault(stat, 0);
    }

    public boolean isDamaged() {
        return durability < maxDurability;
    }

    public boolean isBroken() {
        return durability <= 0;
    }

    public void takeDamage(int damage) {
        durability = Math.max(0, durability - damage);
    }

    public void repair(int amount) {
        durability = Math.min(maxDurability, durability + amount);
    }

    // Getters
    public int getDurability() { return durability; }
    public int getMaxDurability() { return maxDurability; }
    public EquipmentSlot getSlot() { return slot; }
    public int getLevelRequirement() { return levelRequirement; }
    public Map<StatType, Integer> getStats() { return new HashMap<>(stats); }
}