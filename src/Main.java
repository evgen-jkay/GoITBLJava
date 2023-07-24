import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static final ArrayList<String> cities = new ArrayList<>();
    private static final Set<String> usedCities = new HashSet<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static String lastCityByProgram = null;

    public static void main(String[] args) {
        fillCitiesFromFile();
        gameLoop();
        scanner.close();
    }

    private static void gameLoop() {
        System.out.println("Почнімо гру! Введіть перше місто:");

        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("exit")) {
            String city = validateCity(input);

            if (city != null) {
                usedCities.add(city);
                char lastChar = city.charAt(city.length() - 1);

                String nextCity = findNextCity(lastChar);
                if (nextCity != null) {
                    lastCityByProgram = nextCity;
                    usedCities.add(nextCity);
                    System.out.println("Моє місто: " + nextCity);
                } else {
                    System.out.println("Ви Перемогли! Я не знаю більше міст на (" + lastCityByProgram.charAt(lastCityByProgram.length() - 1) + ")");
                    break;
                }
            }
            input = scanner.nextLine();
        }

        System.out.println("Дякую за гру!");
    }

    private static String validateCity(String input) {
        String city = input.trim();
        if (city.length() < 2 || !Character.isLetter(city.charAt(0))) {
            System.out.println("Введіть дійсне назву міста, що складається з більше однієї букви!");
            return null;
        }

        if (usedCities.contains(city)) {
            System.out.println("Не намагайся надурити... Ти ж не москаль?");
            System.out.println("Це місто вже було назване. Введіть інше місто!");
            return null;
        }

        if (lastCityByProgram != null && Character.toLowerCase(city.charAt(0)) != Character.toLowerCase(lastCityByProgram.charAt(lastCityByProgram.length() - 1))) {
            System.out.println("Місто має починатися на останню літеру міста, яким відповіла програма (" + lastCityByProgram.charAt(lastCityByProgram.length() - 1) + ")");
            return null;
        }

        if (city.matches(".*[ЫЁЪыёъ].*")) {
            System.out.println("З москалями не граю!!!");
            for (int i = 0; i < 5; i++) {
                noMoskal();
            }
            System.exit(0);
        }

        return city;
    }

    private static String findNextCity(char lastChar) {
        for (String city : cities) {
            char firstChar = city.charAt(0);
            if (Character.toLowerCase(lastChar) == Character.toLowerCase(firstChar) && !usedCities.contains(city)) {
                return city;
            }
        }
        return null;
    }

    private static void fillCitiesFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("cities.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                cities.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void noMoskal() {
        String content = readTextFromFile("ua.txt");

        if (content != null) {
            String[] lines = content.split("\n");
            for (String line : lines) {
                System.out.println(line);
                try {
                    Thread.sleep(1000); // Затримка на 1 секунду
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String readTextFromFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}