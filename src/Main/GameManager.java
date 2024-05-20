package Main;

import Main.Acts.FirstAct;
import Main.Characters.PlayerHero;

import java.awt.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

// todo ивенты
// todo предметы
// todo сохранения
// todo настройки

public class GameManager extends JFrame {
    private static GameManager instance = null;

    private Clip clip;
    private JTextArea textArea;
    private Object keyLock = new Object();
    private String[] currentLog = {};
    private KeyEvent lastKeyEvent;
    private int keyCode;
//    private static final int FRAME_WIDTH = 1024;
//    private static final int FRAME_HEIGHT = 1024;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public static void main(String[] args) {
        getInstance().showMainMenu();
    }

    public GameManager() {
        // Настройка окна
        setTitle("Консольное окно");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Создание текстовой области
        textArea = new JTextArea();
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
        textArea.setEditable(false);
        add(textArea, BorderLayout.CENTER);

        // Добавление обработчика клавиатурных событий
        textArea.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // Игнорируем события keyTyped
            }

            @Override
            public void keyPressed(KeyEvent e) {
                lastKeyEvent = e;
                synchronized (keyLock) {
                    keyLock.notify();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Игнорируем события keyReleased
            }
        });



        // Отображение окна
        setVisible(true);
    }

    public void clearConsole() {
        textArea.setText("");
    }

    public int getLastKeyEvent(boolean strict, int[] options) {
        boolean keyCorrect = false;
        // ждём пока игрок не нажмёт клавишу
        synchronized (keyLock) {
            try {
                keyLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        keyCode = lastKeyEvent.getKeyCode();
        if (!strict) {
            switch (keyCode) {
                case KeyEvent.VK_8:
                    showMainMenu();
                    break;
                case KeyEvent.VK_9:
                    openSettings();
                    break;
                case KeyEvent.VK_0:
                    exitGame();
                    break;
            }
        }

        while (!keyCorrect){
            if (Arrays.stream(options).anyMatch(x -> x == keyCode)) {
                keyCorrect = true;
                break;
            }
            textArea.append("НЕВЕРНЫЙ ВВОД! Попробуйте ещё раз\n");
            getLastKeyEvent(strict, options);
        }
        return keyCode;
    }

    public int getLastKeyEvent(boolean strict) {
        // ждём пока игрок не нажмёт клавишу
        synchronized (keyLock) {
            try {
                keyLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        keyCode = lastKeyEvent.getKeyCode();
        if (!strict) {
            switch (keyCode) {
                case KeyEvent.VK_8:
                    showMainMenu();
                    break;
                case KeyEvent.VK_9:
                    openSettings();
                    break;
                case KeyEvent.VK_0:
                    exitGame();
                    break;
            }
        }
        return keyCode;
    }

    public void print(String text) {
        clearConsole();
        printTopHUD();
        PlayerHero.getInstance().printStats();

        // Разделить входную строку по символу "\n" и получить массив строк
        String[] newLines = (KeyEvent.getKeyText(keyCode) + "\n" + text).split("(?<=\n)");

        if (currentLog.length + newLines.length > 14) {
            currentLog = Arrays.copyOfRange(currentLog, newLines.length, currentLog.length);
        }

        // Добавить новые строки в конец массива currentLog
        currentLog = Arrays.copyOf(currentLog, currentLog.length + newLines.length);
        System.arraycopy(newLines, 0, currentLog, currentLog.length - newLines.length, newLines.length);

        textArea.append(String.join("", currentLog));
        if (GlobalFlags.getInstance().inGame && PlayerHero.getInstance().checkDeath()) {
            clearConsole();
            clearLog();
            printStrict("Вы умерли! Нажмите любую клавишу чтобы выйти\n");
            getLastKeyEvent(true);
            exitGame();
        }
    }

    public void printStrict(String text) {
        textArea.append(text);
    }


    public void clearLog() {
        currentLog = new String[]{};
    }

    public void showMainMenu() {

        clearConsole();
        printStrict(
                "+----------------------------+\n" +
                        "|       Главное меню         |\n" +
                        "+----------------------------+\n" +
                        "|  1. Начать игру            |\n" +
                        "|  2. Настройки              |\n" +
                        "|  3. Выход                  |\n" +
                        "+----------------------------+\n" +
                        "Введите ваш выбор: \n"
        );
        playClip("main_theme.wav");
        int response = getLastKeyEvent(true);

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

    public void startGame() {
        clearConsole();
        GlobalFlags.getInstance().currentAct = 1;
        PlayerHero.getInstance().createMainHero();
        clearLog();
        FirstAct.startFirstActLoop();
        printStrict("Конец первого акта\n");
        getLastKeyEvent(false, new int[]{});
    }

    public void exitGame() {
        playClip("escape.wav");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ignored) {
        }
        dispose();
        System.exit(1);
    }

    public void openSettings() {
        clearConsole();
        printTopHUD();
        printStrict(
                "+----------------------------+\n" +
                        "|         Настройки          |\n" +
                        "+----------------------------+\n"
        );
        getLastKeyEvent(false, new int[]{});
    }

    public void printTopHUD() {
        textArea.append(
                "+-----------------------------------------------------------+\n" +
                "| 8. выйти в главное меню | 9. Настройки | 0. Выйти из игры |\n" +
                "+-----------------------------------------------------------+\n"
        );
    }

    public void playClip(String clipName) {
        // Проверяем, играет ли в данный момент музыка
        if (clip != null && clip.isRunning()) {
            // Если музыка играет, останавливаем её
            clip.stop();
            if (!clip.isOpen()) {
                // Если клип уже закрыт, пропускаем закрытие
                clip.close();
            }
        }

        try {
            InputStream inputStream = getClass().getResourceAsStream("/music/" + clipName);
            if (inputStream == null) {
                // Если файл не найден, выводим сообщение об ошибке
                print("Не удалось найти файл аудио: " + clipName + "\n");
                return;
            }
            InputStream bufferedIn = new BufferedInputStream(inputStream);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Установка громкости на 30%
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-6.0f); // Значение -10.0f соответствует 50% громкости

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // Если возникла ошибка при загрузке файла, выводим сообщение об ошибке
            print("Не удалось загрузить файл аудио: " + clipName + "\n");
            e.printStackTrace();
        }
    }

    public void stopClip() {
        if (clip != null){
            clip.stop();
        }
    }

    public void endDay() {
       print("Ваш день закончился, нажмите любую клавишу, \n" +
                " чтобы начать следующий день\n");
        getLastKeyEvent(true);
        clearConsole();
        clearLog();

        GlobalFlags.getInstance().currentDay++;
    }

}