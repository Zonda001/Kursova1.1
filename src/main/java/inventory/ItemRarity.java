package inventory;

public enum ItemRarity {
    COMMON(1.0f, "#FFFFFF"),
    UNCOMMON(1.2f, "#00FF00"),
    RARE(1.5f, "#0080FF"),
    EPIC(2.0f, "#8000FF"),
    LEGENDARY(3.0f, "#FF8000");

    private final float valueMultiplier;
    private final String color;

    ItemRarity(float valueMultiplier, String color) {
        this.valueMultiplier = valueMultiplier;
        this.color = color;
    }

    public float getValueMultiplier() { return valueMultiplier; }
    public String getColor() { return color; }
}
