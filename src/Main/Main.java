package Main;

import Main.Acts.FirstAct;
import Main.Characters.PlayerHero;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

// todo ивенты
// todo предметы
// todo сохранения
// todo настройки



public class Main {

    static ConsoleWindowManager window = ConsoleWindowManager.getInstance();
    public static void main(String[] args) {
        showMainMenu();
    }

    public static void showMainMenu() {

        window.clearConsole();
        window.print(
                "+----------------------------+\n" +
                "|       Главное меню         |\n" +
                "+----------------------------+\n" +
                "|  1. Начать игру            |\n" +
                "|  2. Настройки              |\n" +
                "|  3. Выход                  |\n" +
                "+----------------------------+\n"
        );
        window.print("Введите ваш выбор: \n");
        window.playClip("main_theme.wav");
        int response = window.getLastKeyEvent(true);

        switch (response) {
            case KeyEvent.VK_1:
                startGame();
                break;
            case KeyEvent.VK_2:
                openSettings();
                break;
            case KeyEvent.VK_3:
                exitGame();
                break;
            default:
                showMainMenu();
                break;
        }
    }

    public static void startGame() {
        window.clearConsole();
        PlayerHero mainHero = new PlayerHero().createMainHero();
        GlobalFlags.getInstance().currentAct = 1;
        FirstAct.startFirstActLoop(mainHero);
        window.print("Конец первого акта\n");
        mainHero.printStats();
    }

    public static void openSettings() {
        window.clearConsole();
        printTopHUD();

        window.print(
                "+----------------------------+\n" +
                "|         Настройки          |\n" +
                "+----------------------------+\n"
        );
        int response = window.getLastKeyEvent(false);
        showMainMenu();
    }

    public static void exitGame() {
        window.playClip("escape.wav");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ignored) {
        }
        window.dispose();
        System.exit(1);
    }

    public static void printTopHUD() {
        window.print(
                "+--------------------------------------------------------------------------------------+\n" +
                "| 8. выйти в главное меню | 9. Настройки | 0. Выйти из игры | Ctrl+C. очистить консоль |\n" +
                "+--------------------------------------------------------------------------------------+\n"
        );
    }

}


