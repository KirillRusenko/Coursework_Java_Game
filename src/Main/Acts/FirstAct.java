package Main.Acts;

import Main.Characters.PlayerHero;
import Main.GlobalFlags;
import Main.GameManager;
import org.apache.commons.lang3.ArrayUtils;

import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Random;

public class FirstAct {

    private static int mutiny_progress = 0;
    static GameManager window = GameManager.getInstance();

    static PlayerHero  mainHero = PlayerHero.getInstance();

    public static void startFirstActLoop() {
        while (!mainHero.checkDeath()){
            galleyWork();
            if (!GlobalFlags.getInstance().hasPath){
                if (mainHero.getInfamy() >= 10) {
                    window.print("Ваше бесчестие поднялось достаточно высоко, чтобы другие рабы\n" +
                            "позволили вам помогать им готовить бунт на корабле\n" +
                            "нажмите любую клавишу\n");
                    GlobalFlags.getInstance().chosenPath = "бесчестие";
                    GlobalFlags.getInstance().hasPath = true;
                    window.getLastKeyEvent(true);
                }
                else if (mainHero.getReputation() >= 10) {
                    window.print("На данный момент этот путь ещё не проработан.\n" +
                            " Нажмите любую клавишу чтобы выйти в главное меню\n");
                    window.getLastKeyEvent(true);
                    window.showMainMenu();
                }
            }
            if (FirstAct.mutiny_progress == 10) {
                actEnd();
                return;
            }
            window.endDay();
            mainHero.setEnergy(mainHero.getEnergy() + 10);
            mainHero.setHealth(mainHero.getHealth() + 10);
        }
    }

    public static void showPrologue() {
        window.clearConsole();
        window.printStrict(" ╔===========================================╗\n" +
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

    public static void actEnd() {
        window.clearConsole();
        window.printStrict(" ╔===========================================╗\n" +
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

        int choice = window.getLastKeyEvent(false, new int[]{KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3});
        Random r = new Random();
        switch (choice) {
            case KeyEvent.VK_1:
                window.printStrict("Вы успешно отвлекли стражу на себя, благодаря вам корабль был успешно захвачен.\n");
                mainHero.setEnergy(mainHero.getEnergy() - r.nextInt(40 - mainHero.getEndurance()));
                mainHero.setHealth(mainHero.getHealth() - r.nextInt(40 - mainHero.getAgility()));
                break;
            case KeyEvent.VK_2:
                window.printStrict("Вы успешно обезаружили одного из стражников, благодаря вам корабль был успешно захвачен.\n");
                mainHero.setEnergy(mainHero.getEnergy() - r.nextInt(40 - mainHero.getAgility()));
                mainHero.setHealth(mainHero.getHealth() - r.nextInt(40 - mainHero.getStrength()));
                break;
            case KeyEvent.VK_3:
                window.printStrict("Вы успешно пробрались в каюту капитана и взяли его в заложники.\n" +
                             "Благодаря вам корабль был успешно захвачен.\n");
                mainHero.setEnergy(mainHero.getEnergy() - r.nextInt(40 - mainHero.getWisdom()));
                mainHero.setHealth(mainHero.getHealth() - r.nextInt(40 - mainHero.getIntelligence()));
                break;
        }

    }

    public static void galleyWork() {
        Random r = new Random();
        int duration = r.nextInt(4) + 1;
        for (int i = 0; i < duration; i++) {
            String extra_options = "";
            int[] choice_options = new int[]{KeyEvent.VK_1, KeyEvent.VK_2};
            if (Objects.equals(GlobalFlags.getInstance().chosenPath, "бесчестие")) {
                extra_options = " 3- готовить бунт (-здорвье -запас сил) \n";
                choice_options = ArrayUtils.addAll(choice_options, KeyEvent.VK_3);
            }
            window.print("Гребите! 1-грести(-запаса сил), 2-не грести(-здоровья) \n" + extra_options);

            int choice = window.getLastKeyEvent(false, choice_options);
            switch (choice) {
                case KeyEvent.VK_1:
                    mainHero.setEnergy(mainHero.getEnergy() - r.nextInt(15) - 5);
                    mainHero.setReputation(mainHero.getReputation() + 2);
                    mainHero.setInfamy(mainHero.getInfamy() - 1);
                    window.print("+репутация -бесчестие\n" +
                                 "Ваш запас сил = " + mainHero.getEnergy() + "\n");
                    mainHero.setExpirience(mainHero.getExpirience() + r.nextInt(10));
                    break;
                case KeyEvent.VK_2:
                    mainHero.setHealth(mainHero.getHealth() - r.nextInt(15) - 5);
                    mainHero.setReputation(mainHero.getReputation() - 1);
                    mainHero.setInfamy(mainHero.getInfamy() + 2);
                    window.print("-репутация +бесчестиe\n" +
                                 "Ваше здоровье = " + mainHero.getHealth() + "\n");
                    mainHero.setExpirience(mainHero.getExpirience() + r.nextInt(10));
                    break;
                case KeyEvent.VK_3:
                    if (Objects.equals(GlobalFlags.getInstance().chosenPath, "бесчестие") && FirstAct.mutiny_progress <10){
                        mainHero.setHealth(mainHero.getHealth() - r.nextInt(7) - 5);
                        mainHero.setEnergy(mainHero.getEnergy() - r.nextInt(7) - 5);
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
            }
        }

    }


}
