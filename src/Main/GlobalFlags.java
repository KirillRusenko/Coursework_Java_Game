package Main;

public class GlobalFlags {
    private static GlobalFlags instance = null;

    // Глобальные переменные
    public boolean isDebugMode = false;
    public int currentDay = 1;
    public int currentAct = 0;
    public String chosenPath = null;
    public boolean hasPath = false;
    public boolean inGame = false;



    private GlobalFlags() { }

    public static GlobalFlags getInstance() {
        if (instance == null) {
            instance = new GlobalFlags();
        }
        return instance;
    }
}