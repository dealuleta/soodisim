public class Main {
    public static void main(String[] args) {
        //addPoints
        System.out.println("---- Test Dea (addPoints + getUserTotalPoints) ----");
        System.out.println("Pikët fillestare të Dea-s: " + UserService.getUserTotalPoints("dea"));
        UserService.addPoints("dea", 10); // Dea merr +10 pikë
        System.out.println("Pikët pas shtimit për Dea: " + UserService.getUserTotalPoints("dea"));
        System.out.println();

        // visitedByUser
        System.out.println("---- Test Erla (visitedByUser) ----");
        System.out.println("A e ka vizituar Erla Beratin? " +
                UserService.visitedByUser("erla", "Berat"));
        System.out.println("A e ka vizituar Ergisa Thethin? " +
                UserService.visitedByUser("ergisa", "Theth"));
        System.out.println();

        // calculateRewardDiscount me piket ekzistuese
        System.out.println("---- Test zbritjesh normale (calculateRewardDiscount) ----");
        System.out.println("Zbritje për Dea: " +
                UserService.calculateRewardDiscount("dea"));
        System.out.println("Zbritje për Ergisën: " +
                UserService.calculateRewardDiscount("ergisa"));
        System.out.println("Zbritje për Erlën: " +
                UserService.calculateRewardDiscount("erla"));
        System.out.println("Zbritje për Ersin: " +
                UserService.calculateRewardDiscount("ersi"));
        System.out.println();


        System.out.println("---- Test 111 pikë ----");

        UserData.userPoints.put("ergisa", 111);
        System.out.println("Pikët e Ergisës përpara ofertës: " +
                UserService.getUserTotalPoints("ergisa"));
        System.out.println("Oferta për Ergisën: " +
                UserService.calculateRewardDiscount("ergisa"));
        System.out.println("Pikët e Ergisës pas përdorimit të ofertës: " +
                UserService.getUserTotalPoints("ergisa"));
    }
}
