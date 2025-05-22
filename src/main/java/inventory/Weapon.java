package inventory;

public class Weapon extends Equipment {
    private WeaponType weaponType;
    private int attackDamage;
    private float attackSpeed;
    private int criticalChance;

    public Weapon(String id, String name, String description, ItemRarity rarity,
                  int value, WeaponType weaponType, int attackDamage,
                  float attackSpeed, int criticalChance, int durability, int levelRequirement) {
        super(id, name, description, ItemType.WEAPON, rarity, value,
                EquipmentSlot.MAIN_HAND, durability, levelRequirement);
        this.weaponType = weaponType;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.criticalChance = criticalChance;

        // Add weapon stats
        addStat(StatType.ATTACK_DAMAGE, attackDamage);
        addStat(StatType.CRITICAL_CHANCE, criticalChance);
    }

    @Override
    public Item copy() {
        Weapon copy = new Weapon(id, name, description, rarity, value,
                weaponType, attackDamage, attackSpeed,
                criticalChance, maxDurability, levelRequirement);
        copy.durability = this.durability;
        return copy;
    }

    // Getters
    public WeaponType getWeaponType() { return weaponType; }
    public int getAttackDamage() { return attackDamage; }
    public float getAttackSpeed() { return attackSpeed; }
    public int getCriticalChance() { return criticalChance; }
}