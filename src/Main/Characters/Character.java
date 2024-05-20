package Main.Characters;

import Main.Item;
import Main.Main;

public class Character {
    protected int health;
    protected int energy;
    protected int money;

    public Character() {
        this.health = 100;
        this.energy = 100;
        this.money = 0;
    }

    public Object[] getCharacterCharacteristics() {
        return new Object[] {
                this.health,
                this.energy,
                this.money,
        };
    }


    public void setHealth(int health) { this.health = health; }
    public void setEnergy(int energy) { this.energy = energy; }
    public void setMoney(int money) {
        this.money = money;
    }

    public int getHealth() { return health; }
    public int getEnergy() { return energy; }
    public int getMoney() { return money; }
}
