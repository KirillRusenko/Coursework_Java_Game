package Main;

import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import Main.Characters.PlayerHero;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.ArrayUtils;

public class Event {
    private String title;
    private String description;
    private int id;
    private List<String> answers;
    private List<String> effects;
    private List<String> effectAmount;
    static GameManager window = GameManager.getInstance();
    static PlayerHero mainHero = PlayerHero.getInstance();

    public Event(String title, String description, int id, List<String> answers, List<String> effects, List<String> effectAmount) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.answers = answers;
        this.effects = effects;
        this.effectAmount = effectAmount;
    }

    public static JsonObject[] getEvents() {
        try {
            InputStream inputStream = Event.class.getResourceAsStream("/events/events.txt");
            assert inputStream != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String jsonString = "";
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = reader.readLine();
            }
            jsonString = sb.toString();

            reader.close();
            Gson gson = new Gson();
            JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            JsonObject[] events = new JsonObject[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                events[i] = jsonArray.get(i).getAsJsonObject();
            }
            return events;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getEventByID(int id) {
        return getEvents()[id];
    }

    public static Event parseEvent(Object eventObj) {
        JsonObject eventJson = (JsonObject) eventObj;

        String title = eventJson.get("title").getAsString();
        String description = eventJson.get("description").getAsString();
        int id = eventJson.get("id").getAsInt();

        JsonArray answersJson = eventJson.getAsJsonArray("answers");
        List<String> answers = new ArrayList<>();
        for (JsonElement answerElement : answersJson) {
            answers.add(answerElement.getAsString());
        }

        JsonArray effectsJson = eventJson.getAsJsonArray("effects");
        List<String> effects = new ArrayList<>();
        for (JsonElement effectElement : effectsJson) {
            effects.add(effectElement.getAsString());
        }

        JsonArray effectAmountJson = eventJson.getAsJsonArray("effect_amount");
        List<String> effectAmount = new ArrayList<>();
        for (JsonElement effectAmountElement : effectAmountJson) {
            effectAmount.add(effectAmountElement.getAsString());
        }

        return new Event(title, description, id, answers, effects, effectAmount);
    }



    public static void executeEvent(int eventId) {
        Object eventObj = getEventByID(eventId);
        Event event = parseEvent(eventObj);
        window.print(event.title + "\n" );
        window.printStrict(event.description + "\n");
        int[] choice_options = new int[]{};
        for (int i = 0; i < event.answers.size(); i++) {
            window.printStrict((i + 1) + ") " + event.answers.get(i) + "\n");
            choice_options = ArrayUtils.addAll(choice_options, KeyEvent.VK_1 + i);
        }
        int response = window.getLastResponse(false, choice_options);

        int chosenIndex = response - 49;
        String chosenEffect = event.effects.get(chosenIndex);
        int effectAmount = Integer.parseInt(event.effectAmount.get(chosenIndex));

        executeEffect(chosenEffect, effectAmount);

    }

    public static void executeRandomEvent() {
        int randomEventId = (int) (Math.random() * GlobalFlags.getInstance().eventAmount);
        executeEvent(randomEventId);
    }


    public static void executeEffect(String chosenEffect, int effectAmount) {
        switch (chosenEffect) {
            case "health_inc":
                mainHero.setHealth(mainHero.getHealth() + effectAmount);
                break;
            case "health_dec":
                mainHero.setHealth(mainHero.getHealth() - effectAmount);
                break;
            case "energy_inc":
                mainHero.setEnergy(mainHero.getEnergy() + effectAmount);
                break;
            case "energy_dec":
                mainHero.setEnergy(mainHero.getEnergy() - effectAmount);
                break;
            case "money_inc":
                mainHero.setMoney(mainHero.getMoney() + effectAmount);
                break;
            case "money_dec":
                mainHero.setMoney(mainHero.getMoney() - effectAmount);
                break;
            case "strength_inc":
                mainHero.setStrength(mainHero.getStrength() + (effectAmount * mainHero.getStrengthMod()));
                break;
            case "strength_dec":
                mainHero.setStrength(mainHero.getStrength() - effectAmount);
                break;
            case "agility_inc":
                mainHero.setAgility(mainHero.getAgility() + (effectAmount * mainHero.getAgilityMod()));
                break;
            case "agility_dec":
                mainHero.setAgility(mainHero.getAgility() - effectAmount);
                break;
            case "intelligence_inc":
                mainHero.setIntelligence(mainHero.getIntelligence() + (effectAmount * mainHero.getIntelligenceMod()));
                break;
            case "intelligence_dec":
                mainHero.setIntelligence(mainHero.getIntelligence() - effectAmount);
                break;
            case "charisma_inc":
                mainHero.setCharisma(mainHero.getCharisma() + (effectAmount * mainHero.getCharismaMod()));
                break;
            case "charisma_dec":
                mainHero.setCharisma(mainHero.getCharisma() - effectAmount);
                break;
            case "wisdom_inc":
                mainHero.setWisdom(mainHero.getWisdom() + (effectAmount * mainHero.getWisdomMod()));
                break;
            case "wisdom_dec":
                mainHero.setWisdom(mainHero.getWisdom() - effectAmount);
                break;
            case "endurance_inc":
                mainHero.setEndurance(mainHero.getEndurance() + (effectAmount * mainHero.getEnduranceMod()));
                break;
            case "endurance_dec":
                mainHero.setEndurance(mainHero.getEndurance() - effectAmount);
                break;
            case "reputation_inc":
                mainHero.setReputation(mainHero.getReputation() + effectAmount);
                break;
            case "reputation_dec":
                mainHero.setReputation(mainHero.getReputation() - effectAmount);
                break;
            case "infamy_inc":
                mainHero.setInfamy(mainHero.getInfamy() + effectAmount);
                break;
            case "infamy_dec":
                mainHero.setInfamy(mainHero.getInfamy() - effectAmount);
                break;
        }
    }

}
