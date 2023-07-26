import ukrainianization.NoMoskal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Головний клас, що реалізує гру "Міста".
 * Введіть назву міста, що починається на останню літеру попереднього міста.
 * Гра триває до введення користувачем слова "exit".
 * Міста завантажуються з файлу cities.txt.
 * Міста зчитуються з файлу по одному на кожному рядку.
 *
 * @author Євген Ландаренко
 * @version 4.0
 */
public class Main {
    // Колекція міст для гри
    private static final ArrayList<String> cities = new ArrayList<>();
    // Колекція міст, які вже були використані
    private static final Set<String> usedCities = new HashSet<>();
    // Об'єкт Scanner для зчитування введення користувача
    private static final Scanner scanner = new Scanner(System.in);
    // Змінна для збереження останнього міста, що було названо програмою
    private static String lastCityByProgram = null;

    /**
     * Головний метод програми.
     * Заповнює колекцію міст, починає гру та закриває Scanner.
     *
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        fillCitiesFromFile();
        gameLoop();
        scanner.close(); // закриваємо зчитування з файлу
    }

    /**
     * Головний цикл гри "Міста".
     * Виводить вітання, починає гру та обробляє введення користувача.
     */
    private static void gameLoop() {
        System.out.println("Патриотична гра в Міста.");
        System.out.println("Программа зроблена в рамках Битви мов програмування GoIT");
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
                    System.out.println("Моє місто: " + nextCity.substring(0, 1).toUpperCase() + nextCity.substring(1));
                } else {
                    System.out.println("Ви перемогли! Я не знаю більше міст на (" + lastCityByProgram.charAt(lastCityByProgram.length() - 1) + ")");
                    break;
                }
            }
            input = scanner.nextLine();
        }

        System.out.println("Дякую за гру!");
    }

    /**
     * Метод для перевірки та валідації назви міста.
     *
     * @param input введене користувачем місто
     * @return назва міста після перевірок, або null, якщо місто не пройшло валідацію
     * TODO: 26.07.2023 Додати перевірку на закінчення назви міста на Й, И, Ь та в таких випадках дозволити використовувати попередню літеру для продовження гри
     */
    private static String validateCity(String input) {
        // Обрізуємо введену строку
        String city = input.trim();

        // Якщо введенно одну букву
        if (city.length() < 2 || !Character.isLetter(city.charAt(0))) {
            System.out.println("Введіть дійсне назву міста, що складається з більше однієї букви!");
            return null;
        }

        // Перевіряємо чи місто існує у списку міст
        if (!cities.contains(city.toLowerCase())) {
            System.out.println("Такого міста в списку не знайдено. Спробуйте ввести інше місто!");
            return null;
        }

        // Якщо місто вже було використанне
        if (usedCities.contains(city)) {
            System.out.println("Це місто вже було назване. Не намагайтеся надурити...");
            System.out.println("Введіть інше місто!");
            return null;
        }

        // Перевіряємо чи починається введене місто правилам.
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

    /**
     * Метод для пошуку наступного міста для гри.
     *
     * @param lastChar остання літера попереднього міста
     * @return назва міста для гри, або null, якщо місто не знайдено
     */
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

    /**
     * Метод для заповнення колекції міст із файлу "cities.txt".
     * При виникненні помилки виводить повідомлення про помилку.
     */
    private static void fillCitiesFromFile() {
        // підключаємо файл зі списком міст та проходимося по кожному рядку
        try (BufferedReader br = new BufferedReader(new FileReader("resource/cities.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                cities.add(line.trim().toLowerCase());
            }
            System.out.println("Завантажено з бази " + cities.size() + " міста для гри...");
        } catch (IOException e) {
            System.out.println("Помилка при підключенні бази міст: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
