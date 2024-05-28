package Main.Acts;

import Main.Characters.PlayerHero;
import Main.Event;
import Main.GameManager;

import java.awt.event.KeyEvent;
import java.util.Random;

public class SecondAct {

    static GameManager window = GameManager.getInstance();
    static PlayerHero  mainHero = PlayerHero.getInstance();

    static Random r = new Random();

    public static void startSecondActLoop() {
        window.clearLog();
        showSecondActPrologue();
        window.print("Начало втрого акта\n");
        while (!mainHero.checkDeath()){
            townActions();
            window.endDay();
            Event.executeRandomEvent();
            mainHero.setEnergy(mainHero.getEnergy() + 10);
            mainHero.setHealth(mainHero.getHealth() + 10);
        }
    }

    public static void showSecondActPrologue() {
        window.clearConsole();
        window.printStrict(" ╔===========================================╗\n" +
                " ║          Начало новой истории             ║\n" +
                " ╠===========================================╣\n" +
                " ║     После успешного захвата корабля       ║\n" +
                " ║  ты вместе со своими товарищами приплыл   ║\n" +
                " ║     в небольшой город на побережье.       ║\n" +
                " ║  Вместе с твоей новобретённой свободой    ║\n" +
                " ║   у тебя появилось много неприятностей,   ║\n" +
                " ║   теперь за тобой охитится вся империя.   ║\n" +
                " ║      От тебя потребуется недюжинная       ║\n" +
                " ║    стойкость, сила духа, хитроумие и      ║\n" +
                " ║   немалая доля отваги, чтобы преодолеть   ║\n" +
                " ║  эти испытания, но я не сомневаюсь, что   ║\n" +
                " ║           у тебя всё получится.           ║\n" +
                " ║          (нажмите любую клавишу)          ║\n" +
                " ╚===========================================╝\n");

        window.getLastKeyEvent(false);

    }

    public static void townActions() {
        Random r = new Random();
        int duration = r.nextInt(3) + 1;
        for (int i = 0; i < duration; i++) {
            window.print("Вы находитесь в городе, доступные места для посещения: \n" +
                        "1. Ферма  2. Церковь  3. Библиотека \n" +
                        "4. Арена  5. Лазарет \n");
            window.printStrict(" Количество действий: " + (duration - i) +"\n");
            int choice = window.getLastResponse(false, new int[]{KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5});
            switch (choice) {
                case KeyEvent.VK_1:
                    farmAction();
                    break;
                case KeyEvent.VK_2:
                    churchAction();
                    break;
                case KeyEvent.VK_3:
                    libraryAction();
                    break;
                case KeyEvent.VK_4:
                    arenaAction();
                    break;
                case KeyEvent.VK_5:
                    hospitalAction();
                    break;
            }
            mainHero.setExpirience(mainHero.getExpirience() + 50 + mainHero.getWisdom()/6);
        }

    }

    private static void farmAction() {
        window.print("Вы провели время работая на ферме за деньги.");
        mainHero.setEnergy(mainHero.getEnergy() - r.nextInt(15) - 5 + mainHero.getEndurance()/4);
        mainHero.setMoney(mainHero.getMoney() + r.nextInt(15) + mainHero.getCharisma()/4);
    }

    private static void churchAction() {
        window.print("Вы провели время поучавствовав в несении службы в церкви.");
        mainHero.setEnergy(mainHero.getEnergy() - r.nextInt(10));
        mainHero.setHealth(mainHero.getMoney() + r.nextInt(5));
        mainHero.setWisdom(mainHero.getWisdom() + (mainHero.getWisdomMod() >= r.nextInt(10)? 1: 0));
    }

    private static void libraryAction() {
        window.print("Вы провели время читая в библиотеке.");
        mainHero.setEnergy(mainHero.getEnergy() - r.nextInt(10));
        mainHero.setHealth(mainHero.getMoney() + r.nextInt(5));
        mainHero.setIntelligence(mainHero.getIntelligence() + (mainHero.getIntelligenceMod() >= r.nextInt(10)? 1: 0));
    }

    private static void arenaAction() {
        window.print("Вы провели время сражаясь на арене.");
        mainHero.setEnergy(mainHero.getEnergy() - r.nextInt(25) - 5 + mainHero.getEndurance()/3 + mainHero.getAgility()/5);
        mainHero.setHealth(mainHero.getHealth() - r.nextInt(25) - 5 + mainHero.getStrength()/3 + mainHero.getAgility()/5);
        mainHero.setStrength(mainHero.getStrength() + (mainHero.getStrengthMod() >= r.nextInt(10)? 1: 0));
        mainHero.setMoney(mainHero.getMoney() + r.nextInt(40) + 20 + mainHero.getCharisma()/2 + mainHero.getIntelligence()/5);
    }

    private static void hospitalAction() {
        window.print("Вы пришли в лазарет, здесь вы можете заплатить, чтобы восстановить часть здоровья и запаса сил. \n" +
                    "1. Малое лечение - бесплатно  \n" +
                    "2. Среднее лечение - 20 золота  \n" +
                    "3. Большое лечение - 50 золота\n" );
        int choice = window.getLastResponse(false, new int[]{KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3});
        switch (choice) {
            case KeyEvent.VK_1:
                mainHero.setEnergy(mainHero.getEnergy() + 5);
                mainHero.setHealth(mainHero.getHealth() + 5);
                break;
            case KeyEvent.VK_2:
                mainHero.setEnergy(mainHero.getEnergy() + 20);
                mainHero.setHealth(mainHero.getHealth() + 20);
                mainHero.setMoney(mainHero.getMoney() - 20);
                break;
            case KeyEvent.VK_3:
                mainHero.setEnergy(mainHero.getEnergy() + 40);
                mainHero.setHealth(mainHero.getHealth() + 40);
                mainHero.setMoney(mainHero.getMoney() - 50);
                break;
        }
    }


}

