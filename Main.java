import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final int N_THREADS = 1000;
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < N_THREADS; i++) {
            Thread thread = new Thread(() -> {
                String text = generateRoute("RLRFR", 100);
                int count = 0;
                for (int j = 0; j < text.length(); j++) {
                    if (text.charAt(j) == 'R') {
                        count++;
                    }
                }
                synchronized (sizeToFreq) {
                    updateMap(count);
                }
            });
            thread.start();
        }
        printResult();
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void updateMap(int num) {
        sizeToFreq.put(num, sizeToFreq.getOrDefault(num, 0) + 1);
    }

    public static void printResult() {
        int maxValue = 0;
        int maxKey = 0;
        int i;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if ((i = entry.getValue()) > maxValue) {
                maxKey = entry.getKey();
                maxValue = i;
            }
        }
        System.out.println("Самое частое количество повторений " + maxKey + " (встретилось " + maxValue + " раз)");
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if ((i = entry.getKey()) != maxKey) {
                System.out.println("- " + i + " (" + entry.getValue() + " раз)");
            }
        }
    }
}