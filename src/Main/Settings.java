package Main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Settings {

    private static Settings instance = null;

    private int fontSize;
    private int volume;
    private int textColor;

    private Settings() { }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
            instance.loadSettings();
        }
        return instance;
    }

    public int getFontSize() {
        return fontSize;
    }

    public int getVolume() {
        return volume;
    }
    public int getTextColor() {
        return textColor;
    }
    public Color getTextColorAsColor() {
        Color color = Color.GREEN;
        switch (textColor) {
            case 2:
                color = Color.WHITE;
            case 3:
                color = Color.GRAY;
            case 4:
                color = Color.RED;
            case 5:
                color = Color.BLUE;
            case 6:
                color = Color.ORANGE;
            case 7:
                color = Color.YELLOW;
        }
        return color;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }




    private void loadSettings() {
        try (InputStream inputStream = getClass().getResourceAsStream("/settings");
             InputStreamReader reader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            Gson gson = new Gson();
            Settings settings = gson.fromJson(bufferedReader, Settings.class);
            this.fontSize = settings.getFontSize();
            this.volume = settings.getVolume();
            this.textColor = settings.getTextColor();
        } catch (IOException e) {
            System.err.println("Не удалось загрузить настройки: " + e.getMessage());
        }
    }

    public void saveSettings() {
        try {
            String filePath = "resources/settings";
            OutputStream outputStream = new FileOutputStream(filePath);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            outputStream.write(gson.toJson(this).getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            System.err.println("Не удалось сохранить настройки: " + e.getMessage());
        }
    }
}
