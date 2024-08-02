import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Apple {
    int weight;

    Apple(int weight) {
        this.weight = weight;
    }
}

public class AppleQue {

    private static boolean distributeApplesRec(List<Apple> apples, int ramTarget, int shamTarget, int rahimTarget, List<Apple> ramApples, List<Apple> shamApples, List<Apple> rahimApples, int index) {
        if (ramTarget == 0 && shamTarget == 0 && rahimTarget == 0) {
            return true; // All targets met
        }
        if (index >= apples.size()) {
            return false; // Out of apples
        }
        
        Apple apple = apples.get(index);

        // Try to put the apple in Ram's list
        if (ramTarget >= apple.weight) {
            ramApples.add(apple);
            if (distributeApplesRec(apples, ramTarget - apple.weight, shamTarget, rahimTarget, ramApples, shamApples, rahimApples, index + 1)) {
                return true;
            }
            ramApples.remove(apple);
        }

        // Try to put the apple in Sham's list
        if (shamTarget >= apple.weight) {
            shamApples.add(apple);
            if (distributeApplesRec(apples, ramTarget, shamTarget - apple.weight, rahimTarget, ramApples, shamApples, rahimApples, index + 1)) {
                return true;
            }
            shamApples.remove(apple);
        }

        // Try to put the apple in Rahim's list
        if (rahimTarget >= apple.weight) {
            rahimApples.add(apple);
            if (distributeApplesRec(apples, ramTarget, shamTarget, rahimTarget - apple.weight, ramApples, shamApples, rahimApples, index + 1)) {
                return true;
            }
            rahimApples.remove(apple);
        }

        // Try without using this apple
        return distributeApplesRec(apples, ramTarget, shamTarget, rahimTarget, ramApples, shamApples, rahimApples, index + 1);
    }

    public static void main(String[] args) {
        List<Apple> apples = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int weight;

        // Input apple weights
        System.out.println("Enter apple weight in grams (-1 to stop): ");
        while (true) {
            if (sc.hasNextInt()) {
                weight = sc.nextInt();
                if (weight == -1) break;
                apples.add(new Apple(weight));
            } else {
                System.out.println("Please enter a valid integer.");
                sc.next(); // clear the invalid input
            }
        }

        // Shares paid by Ram, Sham, and Rahim
        int ramShare = 50;
        int shamShare = 30;
        int rahimShare = 20;

        // Calculate total weight of apples
        int totalWeight = apples.stream().mapToInt(a -> a.weight).sum();

        // Calculate the target weights for each person
        int ramTarget = (ramShare * totalWeight) / 100;
        int shamTarget = (shamShare * totalWeight) / 100;
        int rahimTarget = (rahimShare * totalWeight) / 100;

        // Sort apples by weight in descending order
        apples.sort((a, b) -> b.weight - a.weight);

        // Lists to hold the result
        List<Apple> ramApples = new ArrayList<>();
        List<Apple> shamApples = new ArrayList<>();
        List<Apple> rahimApples = new ArrayList<>();

        // Start the distribution process
        boolean success = distributeApplesRec(apples, ramTarget, shamTarget, rahimTarget, ramApples, shamApples, rahimApples, 0);

        if (success) {
            // Output the distribution
            System.out.println("Distribution Result:");
            System.out.print("Ram: ");
            for (Apple apple : ramApples) {
                System.out.print(apple.weight + " ");
            }
            System.out.print("\nSham: ");
            for (Apple apple : shamApples) {
                System.out.print(apple.weight + " ");
            }
            System.out.print("\nRahim: ");
            for (Apple apple : rahimApples) {
                System.out.print(apple.weight + " ");
            }
            System.out.println();
        } else {
            System.out.println("No valid distribution found.");
        }
    }
}
