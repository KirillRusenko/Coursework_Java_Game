package Main.Acts;

import Main.Characters.PlayerHero;
import Main.Main;
import Main.GlobalFlags;
import Main.ConsoleWindowManager;

import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Random;

public class FirstAct {

    private static int mutiny_progress = 0;
    static ConsoleWindowManager window = ConsoleWindowManager.getInstance();

    public static void startFirstActLoop(PlayerHero mainHero) {
        while (mainHero.getHealth() > 0 && mainHero.getEnergy() > 0) {
            window.clearConsole();
            Main.printTopHUD();
            mainHero.printStats();
            window.print("День №" + GlobalFlags.getInstance().currentDay + "\n");

            galleyWork(mainHero);

            mainHero.setEnergy(mainHero.getEnergy() + 10);
            mainHero.setHealth(mainHero.getHealth() + 10);
            GlobalFlags.getInstance().currentDay++;
            if (!GlobalFlags.getInstance().hasPath){
                if (mainHero.getInfamy() >= 20) {
                    window.print("Ваше бесчестие поднялось достаточно высоко, чтобы другие рабы\n" +
                                 "позволили вам помогать им готовить бунт на корабле\n" +
                                 "нажмите любую клавишу\n");
                    GlobalFlags.getInstance().chosenPath = "бесчестие";
                    GlobalFlags.getInstance().hasPath = true;
                    window.getLastKeyEvent(true);
                }
                else if (mainHero.getReputation() >= 20) {
                    window.print("На данный момент этот путь ещё не проработан.\n" +
                            " Нажмите любую клавишу чтобы выйти в главное меню\n");
                    window.getLastKeyEvent(true);
                    Main.showMainMenu();
                }
            }
            if (FirstAct.mutiny_progress == 10) {
                actEnd(mainHero);
                return;
            }

            window.print("Ваш день закончился, нажмите любую клавишу, \n" +
                         " чтобы начать следующий день\n");
            window.getLastKeyEvent(true);

        }
        window.clearConsole();
        window.print("Вы умерли! Нажмите любую клавишу чтобы выйти\n");
        window.getLastKeyEvent(true);
        Main.exitGame();
    }

    public static void showPrologue() {
        window.clearConsole();
        window.print(" ╔===========================================╗\n" +
                     " ║          Приветствую, путник              ║\n" +
                     " ╠===========================================╣\n" +
                     " ║    В данной игре тебе выпала суровая      ║\n" +
                     " ║  доля, тебе предстоит играть за недавно   ║\n" +
                     " ║  захваченного в результате войны раба.    ║\n" +
                     " ║    Тебя увезли далеко от твоего дома,     ║\n" +
                     " ║     в самое сердце молодой Османской      ║\n" +
                     " ║     империи, где ты будешь вынужден       ║\n" +
                     " ║   заниматся греблёй на галере до конца    ║\n" +
                     " ║  своих дней. Но даже из такой ситуации    ║\n" +
                     " ║    можно выкрутится, поэтому поведай      ║\n" +
                     " ║  мне о своём прошлом, которое определило  ║\n" +
                     " ║  твоё настоящие и поможет в формировании  ║\n" +
                     " ║  твоего будущего (нажмите любую цифру)    ║\n" +
                     " ╚===========================================╝\n");

        window.getLastKeyEvent(false);

    }

    public static void actEnd(PlayerHero mainHero) {
        window.clearConsole();
        window.print(" ╔===========================================╗\n" +
                     " ║            Бунт на корабле!               ║\n" +
                     " ╠===========================================╣\n" +
                     " ║    Дождавшись ночи ты и твои братья       ║\n" +
                     " ║ по несчастью привели ваш план в действие. ║\n" +
                     " ║   Вы планировали дождатся когда стража    ║\n" +
                     " ║    Уснёт на посту и пробратся в каюту     ║\n" +
                     " ║    капитана, чтобы расправится с ним      ║\n" +
                     " ║    и его подчинёнными, а потом получив    ║\n" +
                     " ║   контроль над кораблём, уплыть в море.   ║\n" +
                     " ║  Но когда вы были уже в шаге от победы    ║\n" +
                     " ║    вас заметила стража, и завязался бой,  ║\n" +
                     " ║              ваши действия?               ║\n" +
                     " ╠===========================================╣\n" +
                     " ║ 1)  Попробую отвлечь их на себя, чтобы    ║\n" +
                     " ║     остальные могли пробратся в каюту     ║\n" +
                     " ╠===========================================╣\n" +
                     " ║ 2)  Попытаюсь оглушить и обезаружить      ║\n" +
                     " ║     одного из них, пока он не успел       ║\n" +
                     " ║     выхватить оружие                      ║\n" +
                     " ╠===========================================╣\n" +
                     " ║ 3)  Попытаюсь скрытно пробратся в каюту   ║\n" +
                     " ║     чтобы расправится с капитаном самому  ║\n" +
                     " ╚===========================================╝\n");

        int choice = window.getLastKeyEvent(false);
        Random r = new Random();
        switch (choice) {
            case KeyEvent.VK_1:
                window.print("Вы успешно отвлекли стражу на себя, благодаря вам корабль был успешно захвачен.\n");
                mainHero.setEnergy(mainHero.getEnergy() - r.nextInt(40 - mainHero.getEndurance()));
                mainHero.setHealth(mainHero.getHealth() - r.nextInt(40 - mainHero.getAgility()));
                break;
            case KeyEvent.VK_2:
                window.print("Вы успешно обезаружили одного из стражников, благодаря вам корабль был успешно захвачен.\n");
                mainHero.setEnergy(mainHero.getEnergy() - r.nextInt(40 - mainHero.getAgility()));
                mainHero.setHealth(mainHero.getHealth() - r.nextInt(40 - mainHero.getStrength()));
                break;
            case KeyEvent.VK_3:
                window.print("Вы успешно пробрались в каюту капитана и взяли его в заложники.\n" +
                             "Благодаря вам корабль был успешно захвачен.\n");
                mainHero.setEnergy(mainHero.getEnergy() - r.nextInt(40 - mainHero.getWisdom()));
                mainHero.setHealth(mainHero.getHealth() - r.nextInt(40 - mainHero.getIntelligence()));
                break;
            default:
                actEnd(mainHero);
                break;
        }

    }

    public static void galleyWork(PlayerHero mainHero) {
        Random r = new Random();
        int duration = r.nextInt(4) + 1;
        for (int i = 0; i < duration; i++) {
            String extra_options = "";
            if (Objects.equals(GlobalFlags.getInstance().chosenPath, "бесчестие")) {
                extra_options = " 3- готовить бунт (-здорвье -запас сил) \n";
            }
            window.print("Гребите! 1-грести(-запаса сил), 2-не грести(-здоровья) \n" + extra_options);
            int choice = window.getLastKeyEvent(false);
            switch (choice) {
                case KeyEvent.VK_1:
                    mainHero.setEnergy(mainHero.getEnergy() - r.nextInt(20));
                    mainHero.setReputation(mainHero.getReputation() + 2);
                    mainHero.setInfamy(mainHero.getInfamy() - 1);
                    window.print("+репутация -бесчестие\n" +
                                 "Ваш запас сил = " + mainHero.getEnergy() + "\n");
                    mainHero.setExpirience(mainHero.getExpirience() + r.nextInt(10));
                    break;
                case KeyEvent.VK_2:
                    mainHero.setHealth(mainHero.getHealth() - r.nextInt(20));
                    mainHero.setReputation(mainHero.getReputation() - 1);
                    mainHero.setInfamy(mainHero.getInfamy() + 2);
                    window.print("-репутация +бесчестиe\n" +
                                 "Ваше здоровье = " + mainHero.getHealth() + "\n");
                    mainHero.setExpirience(mainHero.getExpirience() + r.nextInt(10));
                    break;
                case KeyEvent.VK_3:
                    if (Objects.equals(GlobalFlags.getInstance().chosenPath, "бесчестие") && FirstAct.mutiny_progress <10){
                        mainHero.setHealth(mainHero.getHealth() - r.nextInt(10));
                        mainHero.setEnergy(mainHero.getEnergy() - r.nextInt(10));
                        FirstAct.mutiny_progress++;
                        window.print("Подготовка к бунту= " + FirstAct.mutiny_progress + "/10\n" +
                                     "Ваш запас сил= " + mainHero.getEnergy() + ". Ваше здоровье = " + mainHero.getHealth() + "\n");
                        if (FirstAct.mutiny_progress >= 10) {
                            window.print("Всё готово, дождитесь ночи и вы вместе с остальными рабами устроите бунт\n");
                            return;
                        }
                        mainHero.setMoney(mainHero.getMoney() + r.nextInt(5));
                        mainHero.setExpirience(mainHero.getExpirience() + 10 + r.nextInt(10));
                        break;
                    }
                default:
                    window.print("Неверный ввод, попробуйте ещё раз\n");
                    i--;
                    break;
            }
        }

    }


}
