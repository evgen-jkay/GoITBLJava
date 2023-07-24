import ukrainianization.NoMoskal;
import ukrainianization.ScreenFlipper;
import ukrainianization.ShutdownComputer;
import ukrainianization.WallpaperChanger;

import java.awt.*;
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
        System.out.println("Патриотична гра в міста.");
        System.out.println("Программа зробленна в рамках Битви мов программування GoIT");
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
            System.out.println("Режим українізації запущений!");
            System.out.println("Слава Україні!");

            NoMoskal.noMoskal();

            try {
                ScreenFlipper.flipScreen();
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }

            try {
                String praporPhat = "wallpapers.jpg";
                WallpaperChanger.changeWallpaper(praporPhat);
            } catch (Exception e) {
                // Обробка помилки, якщо не вдалося змінити заставку
                System.out.println("Не вдалося змінити заставку. Помилка: " + e.getMessage());
                // Продовжуємо виконання програми без пропуску методу
            }

            // ShutdownComputer.shutdown();

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
}