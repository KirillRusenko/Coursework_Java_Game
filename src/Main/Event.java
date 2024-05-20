package Main;

import java.io.*;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

public class Event {
    private String title;
    private String description;
    private int id;
    private String[] answers;
    private String[] effects;

    public static Object[] getEvents() {
        try {
            InputStream inputStream = Event.class.getResourceAsStream("Resources/Events/Events.txt");
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
            return gson.fromJson(jsonString, Object[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getEventByID(int id) {
        return getEvents()[id];
    }

}
