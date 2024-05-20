package Main.Characters;

import Main.Acts.FirstAct;
import Main.Item;
import Main.Main;
import Main.ConsoleWindowManager;

import java.awt.event.KeyEvent;
import java.util.*;

public class PlayerHero extends Character {

    // Характеристики персонажа
    private int strength;
    private int agility;
    private int intelligence;
    private int charisma;
    private int wisdom;
    private int endurance;

    // Характеристики Взаимоотношения
    private int reputation;
    private int infamy;

    // Модификаторы характеристик персонажа
    private int strength_mod;
    private int agility_mod;
    private int intelligence_mod;
    private int charisma_mod;
    private int wisdom_mod;
    private int endurance_mod;

    // Остальное
    private Item[] items;
    private int expirience;
    private int level;

    static ConsoleWindowManager window = ConsoleWindowManager.getInstance();



    public PlayerHero() {
        this.health = 100;
        this.energy = 100;
        this.money = 0;

        this.strength = 10;
        this.agility = 10;
        this.intelligence = 10;
        this.charisma = 10;
        this.wisdom = 10;

        this.endurance = 10;
        this.reputation = 0;
        this.infamy = 0;

        this.strength_mod = 1;
        this.agility_mod = 1;
        this.intelligence_mod = 1;
        this.charisma_mod = 1;
        this.wisdom_mod = 1;
        this.endurance_mod = 1;

        this.expirience = 0;
        this.level = 0;
        this.items = new Item[3];
    }

    public TreeMap<String, Integer> getStatsAsMap() {
        TreeMap<String, Integer> playerHeroMap = new TreeMap<String, Integer>(buildStatsComparator());
        playerHeroMap.put("здоровье", this.health);
        playerHeroMap.put("запас сил", this.energy);
        playerHeroMap.put("деньги", this.money);
        playerHeroMap.put("сила", this.strength);
        playerHeroMap.put("ловкость", this.agility);
        playerHeroMap.put("интеллект", this.intelligence);
        playerHeroMap.put("харизма", this.charisma);
        playerHeroMap.put("мудрость", this.wisdom);
        playerHeroMap.put("выносливость", this.endurance);
        playerHeroMap.put("репутация", this.reputation);
        playerHeroMap.put("бесчестие", this.infamy);
        playerHeroMap.put("уровень", this.level);
        return playerHeroMap;
    }

    public Object[] getCharacterCharacteristics() {
        return new Object[] {
                this.health,
                this.energy,
                this.money,
                this.strength,
                this.agility,
                this.intelligence,
                this.charisma,
                this.wisdom,
                this.endurance,
                this.reputation,
                this.infamy,
                this.level
        };
    }

    // Характеристики персонажа
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public void setAgility(int agility) {
        this.agility = agility;
    }
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }
    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }
    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    // Характеристики Взаимоотношения
    public void setReputation(int reputation) {
        this.reputation = reputation;
    }
    public void setInfamy(int infamy) {
        this.infamy = infamy;
    }

    // Модификаторы характеристик персонажа
    public void setStrengthMod(int strength_mod) {
        this.strength_mod = strength_mod;
    }
    public void setAgilityMod(int agility_mod) {
        this.agility_mod = agility_mod;
    }
    public void setIntelligenceMod(int intelligence_mod) {
        this.intelligence_mod = intelligence_mod;
    }
    public void setCharismaMod(int charisma_mod) {
        this.charisma_mod = charisma_mod;
    }
    public void setWisdomMod(int wisdom_mod) {
        this.wisdom_mod = wisdom_mod;
    }
    public void setEnduranceMod(int endurance_mod) {
        this.endurance_mod = endurance_mod;
    }

    // Остальное
    public void setItem(int index, Item item) {
        this.items[index] = item;
    }
    public void setExpirience(int expirience) {
        int expirience_cap = 100 + this.level * 25;
        if (expirience >= expirience_cap ) {
            this.expirience -= expirience_cap;
            levelUp();
        }
        else {
            this.expirience = expirience;
        }
    }
    public void setLevel(int level) {
        this.level = level;
    }

    private void levelUp() {
        this.level++;
        Random r = new Random();
        this.health += 10 * this.strength_mod + this.strength;
        this.energy += 10 * this.endurance_mod + this.endurance;
        this.strength += strength_mod >= r.nextInt(10)? 1: 0;
        this.agility += agility_mod >= r.nextInt(10)? 1: 0;
        this.intelligence += intelligence_mod >= r.nextInt(10)? 1: 0;
        this.wisdom += wisdom_mod >= r.nextInt(10)? 1: 0;
        this.charisma += charisma_mod >= r.nextInt(10)? 1: 0;
        this.endurance += endurance_mod >= r.nextInt(10)? 1: 0;
        window.print("Вы получили новый уровень!\n" +
                     "нажмите любую клавишу\n");
        window.getLastKeyEvent(false);
    }

    // Характеристики персонажа
    public int getStrength() { return strength; }
    public int getAgility() { return agility; }
    public int getIntelligence() { return intelligence; }
    public int getCharisma() { return charisma; }
    public int getWisdom() { return wisdom; }
    public int getEndurance() { return endurance; }

    // Характеристики Взаимоотношения
    public int getReputation() { return reputation; }
    public int getInfamy() { return infamy; }

    // Модификаторы характеристик персонажа
    public int getStrengthMod() { return strength_mod; }
    public int getAgilityMod() { return agility_mod; }
    public int getIntelligenceMod() { return intelligence_mod; }
    public int getCharismaMod() { return charisma_mod; }
    public int getWisdomMod() { return wisdom_mod; }
    public int getEnduranceMod() { return endurance_mod; }


    // Остальное
    public Item[] getItems() {
        return items;
    }
    public int getExpirience() { return expirience; }
    public int getLevel() { return level; }

    private Comparator buildStatsComparator() {

        return (Comparator<String>) (s1, s2) -> {
            //порядок сортировки ключей
            String[] keys = {"здоровье", "энергия", "деньги", "сила", "ловкость", "интеллект", "харизма", "мудрость", "выносливость", "репутация", "бесчестие", "уровень"};

            int firstIndex = -1;
            int secondIndex = -1;

            for (int i = 0; i < keys.length; i++) {
                if (keys[i].equals(s1)) {
                    firstIndex = i;
                }
                if (keys[i].equals(s2)) {
                    secondIndex = i;
                }
            }

            return Integer.compare(firstIndex, secondIndex);
        };
    }

    public PlayerHero createMainHero() {
        FirstAct.showPrologue();

        PlayerHero mainHero = new PlayerHero();

        chooseBakcground(mainHero);

        return mainHero;
    }

    private void chooseBakcground(PlayerHero mainHero) {

        window.clearConsole();
        Main.printTopHUD();

        // Спрашиваем игрока о его прошлом
        window.print(" ╔===========================================╗\n" +
                     " ║        Расскажи о своем прошлом           ║\n" +
                     " ╠===========================================╣\n" +
                     " ║ 1. Крестьянин из болгарской деревни       ║\n" +
                     " ║    -инт -мудр +сил ++вын                  ║\n" +
                     " ║ 2. Семья греческих торговцев              ║\n" +
                     " ║    -сил -вын +инт ++хар                   ║\n" +
                     " ║ 3. Малоизвестный венгерский род дворян    ║\n" +
                     " ║    -вын -лов +хар +сил +мудр              ║\n" +
                     " ╚===========================================╝\n");


        int choice = window.getLastKeyEvent(false);


        switch (choice) {
            case KeyEvent.VK_1: // Крестьянская семья
                mainHero.setHealth(120);
                mainHero.setEnergy(130);
                mainHero.setStrength(12);
                mainHero.setAgility(10);
                mainHero.setIntelligence(8);
                mainHero.setCharisma(10);
                mainHero.setWisdom(8);
                mainHero.setEndurance(14);
                mainHero.setReputation(-5);
                mainHero.setInfamy(5);
                mainHero.setStrengthMod(2);
                mainHero.setEnduranceMod(4);
                break;
            case KeyEvent.VK_2: // Семья ремесленников
                mainHero.setHealth(90);
                mainHero.setEnergy(90);
                mainHero.setStrength(8);
                mainHero.setAgility(10);
                mainHero.setIntelligence(12);
                mainHero.setCharisma(14);
                mainHero.setWisdom(10);
                mainHero.setEndurance(8);
                mainHero.setReputation(0);
                mainHero.setInfamy(0);
                mainHero.setCharismaMod(2);
                mainHero.setIntelligenceMod(4);
                break;
            case KeyEvent.VK_3: // Дворянская семья
                mainHero.setHealth(110);
                mainHero.setEnergy(95);
                mainHero.setStrength(12);
                mainHero.setAgility(8);
                mainHero.setIntelligence(10);
                mainHero.setCharisma(12);
                mainHero.setWisdom(12);
                mainHero.setEndurance(8);
                mainHero.setReputation(5);
                mainHero.setInfamy(-5);
                mainHero.setStrengthMod(2);
                mainHero.setCharismaMod(2);
                mainHero.setWisdomMod(2);
                break;
            default:
                chooseBakcground(mainHero);
                break;
        }

    }

    public void printStats() {
        window.print("Ваши  характеристики: \n");
        int column_count = 0;
        TreeMap<String, Integer> stats = getStatsAsMap();
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            window.print(entry.getKey() + ": " + entry.getValue() + " | ");
            column_count++;
            if(column_count == 3){
                window.print("\n");
                column_count = 0;
            }
        }
        window.print("\n");
    }
}
