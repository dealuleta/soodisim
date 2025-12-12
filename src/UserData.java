import java.util.*;

public class UserData {

    public static Map<String, Integer> userPoints = new HashMap<>();
    public static Map<String, List<String>> userVisited = new HashMap<>();

    static {
        userPoints.put("dea", 120);
        userPoints.put("ergisa", 40);
        userPoints.put("erla", 85);
        userPoints.put("ersi", 200);

        userVisited.put("dea", new ArrayList<>(List.of("Berat", "Theth")));
        userVisited.put("ergisa", new ArrayList<>(List.of("Shkodër")));
        userVisited.put("erla", new ArrayList<>(List.of("Gjirokastër", "Berat")));
        userVisited.put("ersi", new ArrayList<>(List.of("Theth", "Valbonë")));
    }
}
