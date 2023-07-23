import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<String> cities = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        fillCities();
        gameLoop();
    }

    private static void gameLoop() {
        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Дякую за гру!");
                System.exit(0);
            }

            char lastChar = Character.toLowerCase(input.charAt(input.length() - 1));
            String nextCity = findCityWithFirstChar(lastChar);

            if (nextCity != null) {
                System.out.println("Моє місто: " + nextCity);
            } else {
                System.out.println("Нажаль, не знайшли місто з відповідним першим символом. Введіть інше місто:");
            }
        }
    }

    private static String findCityWithFirstChar(char firstChar) {
        for (String city : cities) {
            if (Character.toLowerCase(city.charAt(0)) == firstChar) {
                cities.remove(city); // Оптимізація: Видаляємо місто зі списку, щоб уникнути повторного використання.
                return city;
            }
        }
        return null;
    }

    private static void fillCities() {
        cities.add("Київ");
        cities.add("Вінниця");
        cities.add("Луганськ");
        cities.add("Донецьк");
        cities.add("Харків");
        cities.add("Херсон");
        cities.add("Маріупіль");
        cities.add("Запоріжжя");
    }
}
