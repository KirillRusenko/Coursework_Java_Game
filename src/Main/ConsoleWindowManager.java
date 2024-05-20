package Main;

import java.awt.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConsoleWindowManager extends JFrame {
    private static ConsoleWindowManager instance = null;

    private Clip clip;
    private JTextArea textArea;
    private Object keyLock = new Object();
    private KeyEvent lastKeyEvent;
    private static final int FRAME_WIDTH = 1024;
    private static final int FRAME_HEIGHT = 1024;

    public static ConsoleWindowManager getInstance() {
        if (instance == null) {
            instance = new ConsoleWindowManager();
        }
        return instance;
    }

    public ConsoleWindowManager() {
        // Настройка окна
        setTitle("Консольное окно");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);

        // Создание текстовой области
        textArea = new JTextArea();
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
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

    public int getLastKeyEvent(boolean strict) {
        // ждём пока игрок не нажмёт клавишу
        synchronized (keyLock) {
            try {
                keyLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int keyCode = lastKeyEvent.getKeyCode();
        textArea.append(KeyEvent.getKeyText(keyCode) + "\n");
        textArea.append("======================================================================\n\n");
        if (!strict) {
            if (keyCode == KeyEvent.VK_C && (lastKeyEvent.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
                clearConsole();
            } else {
                switch (keyCode) {
                    case KeyEvent.VK_8:
                        Main.showMainMenu();
                        break;
                    case KeyEvent.VK_9:
                        Main.openSettings();
                        break;
                    case KeyEvent.VK_0:
                        Main.exitGame();
                        break;
                }
            }
        }
        return keyCode;
    }

    public void print(String text) {
        textArea.append(text);
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

}