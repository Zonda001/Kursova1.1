package inventory;

public abstract class Item {
    protected String id;
    protected String name;
    protected String description;
    protected ItemType type;
    protected ItemRarity rarity;
    protected int value;
    protected boolean stackable;
    protected int maxStackSize;

    public Item(String id, String name, String description, ItemType type,
                ItemRarity rarity, int value, boolean stackable, int maxStackSize) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.rarity = rarity;
        this.value = value;
        this.stackable = stackable;
        this.maxStackSize = maxStackSize;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ItemType getType() { return type; }
    public ItemRarity getRarity() { return rarity; }
    public int getValue() { return value; }
    public boolean isStackable() { return stackable; }
    public int getMaxStackSize() { return maxStackSize; }

    public abstract Item copy();
}
