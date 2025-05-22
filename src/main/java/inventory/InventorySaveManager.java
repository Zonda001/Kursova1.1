package inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;

public class InventorySaveManager {
    private ObjectMapper objectMapper;

    public InventorySaveManager() {
        this.objectMapper = new ObjectMapper();
    }

    // Save inventory to file
    public boolean saveInventory(Inventory inventory, EquipmentManager equipmentManager, String filename) {
        try {
            InventorySaveData saveData = createSaveData(inventory, equipmentManager);
            objectMapper.writeValue(new File(filename), saveData);
            return true;
        } catch (IOException e) {
            System.err.println("Failed to save inventory: " + e.getMessage());
            return false;
        }
    }

    // Load inventory from file
    public boolean loadInventory(Inventory inventory, EquipmentManager equipmentManager, String filename) {
        try {
            InventorySaveData saveData = objectMapper.readValue(new File(filename), InventorySaveData.class);
            return applySaveData(inventory, equipmentManager, saveData);
        } catch (IOException e) {
            System.err.println("Failed to load inventory: " + e.getMessage());
            return false;
        }
    }

    // Convert inventory to save data
    private InventorySaveData createSaveData(Inventory inventory, EquipmentManager equipmentManager) {
        InventorySaveData saveData = new InventorySaveData();

        // Save inventory items
        List<InventorySaveData.ItemStackData> itemsData = new ArrayList<>();
        for (ItemStack stack : inventory.getAllItems()) {
            int durability = -1;
            if (stack.getItem() instanceof Equipment equipment) {
                durability = equipment.getDurability();
            }
            itemsData.add(new InventorySaveData.ItemStackData(
                    stack.getItem().getId(), stack.getQuantity(), durability));
        }
        saveData.setItems(itemsData);

        // Save currency and slots
        saveData.setCurrency(inventory.getCurrency());
        saveData.setMaxSlots(inventory.getMaxSlots());

        // Save equipped items
        Map<String, InventorySaveData.EquipmentData> equippedData = new HashMap<>();
        for (Map.Entry<EquipmentSlot, Equipment> entry : equipmentManager.getAllEquippedItems().entrySet()) {
            String slotName = entry.getKey().name();
            Equipment equipment = entry.getValue();
            equippedData.put(slotName, new InventorySaveData.EquipmentData(
                    equipment.getId(), equipment.getDurability()));
        }
        saveData.setEquippedItems(equippedData);

        return saveData;
    }

    // Apply save data to inventory
    private boolean applySaveData(Inventory inventory, EquipmentManager equipmentManager, InventorySaveData saveData) {
        try {
            // Clear existing inventory
            inventory.clear();

            // Restore items
            for (InventorySaveData.ItemStackData itemData : saveData.getItems()) {
                Item item = ItemFactory.createItem(itemData.getItemId());
                if (item != null) {
                    // Restore durability for equipment
                    if (item instanceof Equipment equipment && itemData.getDurability() >= 0) {
                        equipment.takeDamage(equipment.getMaxDurability() - itemData.getDurability());
                    }
                    inventory.addItem(item, itemData.getQuantity());
                }
            }

            // Restore currency
            inventory.setCurrency(saveData.getCurrency());

            // Restore equipped items
            for (Map.Entry<String, InventorySaveData.EquipmentData> entry : saveData.getEquippedItems().entrySet()) {
                try {
                    EquipmentSlot slot = EquipmentSlot.valueOf(entry.getKey());
                    InventorySaveData.EquipmentData equipData = entry.getValue();

                    Equipment equipment = (Equipment) ItemFactory.createItem(equipData.getItemId());
                    if (equipment != null) {
                        // Restore durability
                        equipment.takeDamage(equipment.getMaxDurability() - equipData.getDurability());

                        // Add to inventory first, then equip
                        inventory.addItem(equipment, 1);
                        equipmentManager.equipItem(equipment.getId());
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid equipment slot: " + entry.getKey());
                }
            }

            return true;
        } catch (Exception e) {
            System.err.println("Failed to apply save data: " + e.getMessage());
            return false;
        }
    }

    // Save to JSON string (for debugging or cloud saves)
    public String saveToString(Inventory inventory, EquipmentManager equipmentManager) {
        try {
            InventorySaveData saveData = createSaveData(inventory, equipmentManager);
            return objectMapper.writeValueAsString(saveData);
        } catch (Exception e) {
            System.err.println("Failed to save to string: " + e.getMessage());
            return null;
        }
    }

    // Load from JSON string
    public boolean loadFromString(Inventory inventory, EquipmentManager equipmentManager, String jsonData) {
        try {
            InventorySaveData saveData = objectMapper.readValue(jsonData, InventorySaveData.class);
            return applySaveData(inventory, equipmentManager, saveData);
        } catch (Exception e) {
            System.err.println("Failed to load from string: " + e.getMessage());
            return false;
        }
    }
}