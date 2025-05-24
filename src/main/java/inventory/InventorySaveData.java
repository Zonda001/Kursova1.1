package inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventorySaveData {
    private List<ItemStackData> items;
    private int currency;
    private int maxSlots;
    private Map<String, EquipmentData> equippedItems;

    @JsonCreator
    public InventorySaveData(@JsonProperty("items") List<ItemStackData> items,
                             @JsonProperty("currency") int currency,
                             @JsonProperty("maxSlots") int maxSlots,
                             @JsonProperty("equippedItems") Map<String, EquipmentData> equippedItems) {
        this.items = items != null ? items : new ArrayList<>();
        this.currency = currency;
        this.maxSlots = maxSlots;
        this.equippedItems = equippedItems != null ? equippedItems : new HashMap<>();
    }

    public InventorySaveData() {
        this(new ArrayList<>(), 0, 30, new HashMap<>());
    }

    // Getters and setters
    public List<ItemStackData> getItems() { return items; }
    public void setItems(List<ItemStackData> items) { this.items = items; }
    public int getCurrency() { return currency; }
    public void setCurrency(int currency) { this.currency = currency; }
    public int getMaxSlots() { return maxSlots; }
    public void setMaxSlots(int maxSlots) { this.maxSlots = maxSlots; }
    public Map<String, EquipmentData> getEquippedItems() { return equippedItems; }
    public void setEquippedItems(Map<String, EquipmentData> equippedItems) { this.equippedItems = equippedItems; }

    public static class ItemStackData {
        private String itemId;
        private int quantity;
        private int durability; // For equipment

        @JsonCreator
        public ItemStackData(@JsonProperty("itemId") String itemId,
                             @JsonProperty("quantity") int quantity,
                             @JsonProperty("durability") int durability) {
            this.itemId = itemId;
            this.quantity = quantity;
            this.durability = durability;
        }

        public ItemStackData() {}

        public String getItemId() { return itemId; }
        public void setItemId(String itemId) { this.itemId = itemId; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public int getDurability() { return durability; }
        public void setDurability(int durability) { this.durability = durability; }
    }

    public static class EquipmentData {
        private String itemId;
        private int durability;

        @JsonCreator
        public EquipmentData(@JsonProperty("itemId") String itemId,
                             @JsonProperty("durability") int durability) {
            this.itemId = itemId;
            this.durability = durability;
        }

        public EquipmentData() {}

        public String getItemId() { return itemId; }
        public void setItemId(String itemId) { this.itemId = itemId; }
        public int getDurability() { return durability; }
        public void setDurability(int durability) { this.durability = durability; }
    }
}