package inventory;

public class Consumable extends Item {
    private ConsumableType consumableType;
    private int effectValue;
    private int duration; // in seconds, 0 for instant effects

    public Consumable(String id, String name, String description, ItemRarity rarity,
                      int value, ConsumableType consumableType, int effectValue,
                      int duration, int maxStackSize) {
        super(id, name, description, ItemType.CONSUMABLE, rarity, value, true, maxStackSize);
        this.consumableType = consumableType;
        this.effectValue = effectValue;
        this.duration = duration;
    }

    @Override
    public Item copy() {
        return new Consumable(id, name, description, rarity, value,
                consumableType, effectValue, duration, maxStackSize);
    }

    // Getters
    public ConsumableType getConsumableType() { return consumableType; }
    public int getEffectValue() { return effectValue; }
    public int getDuration() { return duration; }
}