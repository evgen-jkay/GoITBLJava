import ukrainianization.NoMoskal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final ArrayList<String> cities = new ArrayList<>();
    private static final Set<String> usedCities = new HashSet<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static String lastCityByProgram = null;

    public static void main(String[] args) {
        fillCitiesFromFile();
        gameLoop();
        scanner.close(); // закриваємо зчитування з файлу
    }

    private static void gameLoop() {
        System.out.println("Патриотична гра в Міста.");
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
        // Обрізуємо введену строку
        String city = input.trim();

        // Якщо введенно одну букву
        if (city.length() < 2 || !Character.isLetter(city.charAt(0))) {
            System.out.println("Введіть дійсне назву міста, що складається з більше однієї букви!");
            return null;
        }

        // Перевірка на початок міста з букви "И"
        if (city.startsWith("И") || city.startsWith("и")) {
            System.out.println("Немає міста на 'И'. Введіть інше місто!");
            return null;
        }

        // Якщо місто вже було використанне
        if (usedCities.contains(city)) {
            System.out.println("Це місто вже було назване. Не намагайся надурити...");
            System.out.println("Введіть інше місто!");
            return null;
        }

        // Перевіряємо чи починаєтся введенне місто правилам.
        if (lastCityByProgram != null && Character.toLowerCase(city.charAt(0)) != Character.toLowerCase(lastCityByProgram.charAt(lastCityByProgram.length() - 1))) {
            System.out.println("Місто має починатися на останню літеру міста, яким відповіла програма (" + lastCityByProgram.charAt(lastCityByProgram.length() - 1) + ")");
            return null;
        }

        // Ловимо букви ЫЁЪыёъ та опрацьовуємо
        if (city.matches(".*[ЫЁЪыёъ].*")) {
            System.out.println("З москалями не граю!!!");
            System.out.println("Режим українізації запущений!");
            System.out.println("Слава Україні!");

            NoMoskal.noMoskal(); // запускаємо легку українізацію

            // ShutdownComputer.shutdown(); // для закріплення

            System.exit(0); // завершаємо программу
        }

        return city;
    }

    private static String findNextCity(char lastChar) {
        // Створюємо копію списку міст, щоб не змінювати оригінальний список
        ArrayList<String> availableCities = new ArrayList<>(cities);

        // Перемішуємо список, щоб міста обирались рандомно
        Collections.shuffle(availableCities);

        // Проходимося по перемішаному списку і знаходимо місто, що відповідає правилам гри
        for (String city : availableCities) {
            char firstChar = city.charAt(0);
            if (Character.toLowerCase(lastChar) == Character.toLowerCase(firstChar) && !usedCities.contains(city)) {
                return city;
            }
        }
        return null;
    }

    private static void fillCitiesFromFile() {
        // підключаємо файл з списком міст та проходимось по кожному рядку
        try (BufferedReader br = new BufferedReader(new FileReader("resource/cities.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                cities.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}