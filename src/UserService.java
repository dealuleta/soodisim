import java.util.List;

public class UserService {

    public static void addPoints(String username, int points) {
        if (username == null || username.isBlank() || points <= 0) {
            return;
        }

        username = username.toLowerCase();

        int current = UserData.userPoints.getOrDefault(username, 0);
        UserData.userPoints.put(username, current + points);
    }

    public static int getUserTotalPoints(String username) {
        if (username == null || username.isBlank()) {
            return 0;
        }

        username = username.toLowerCase();

        return UserData.userPoints.getOrDefault(username, 0);
    }

    public static boolean visitedByUser(String username, String destinationName) {
        if (username == null || destinationName == null) {
            return false;
        }

        username = username.toLowerCase();

        List<String> visited = UserData.userVisited.get(username);
        if (visited == null) return false;

        for (String d : visited) {
            if (d.equalsIgnoreCase(destinationName)) {
                return true;
            }
        }
        return false;
    }

    public static String calculateRewardDiscount(String username) {
        int total = UserService.getUserTotalPoints(username);

        if (total == 111) {
            UserData.userPoints.put(username.toLowerCase(), 0);
            return "Urime! Ju keni fituar 50% zbritje, si dhe produkte FALAS të përzgjedhura (pikët aktuale pas përdorimit të ofertës: 0)";
        }

        if (total < 20) return "0%";
        if (total < 50) return "5%";
        if (total < 80) return "10%";
        if (total < 100) return "15%";

        return "15%";
    }
}
