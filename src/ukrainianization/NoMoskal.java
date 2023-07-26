package ukrainianization;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Клас для українізації програми.
 * Тут підключаємо файл з Гімном України та виводимо його построково з затримкою.
 *
 * @author Євген Ландаренко
 * @version 1.0
 */
public class NoMoskal {
    /**
     * Метод українізації програми.
     * Зчитує вміст файлу "ua.txt" та виводить його построково з затримкою в 1 секунду.
     */
    public static void noMoskal() {

        String content = readTextFromFile("resource/ua.txt");

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

    /**
     * Метод для зчитування тексту з файлу.
     *
     * @param filename назва файлу для зчитування
     * @return вміст файлу у вигляді рядка, або null, якщо сталася помилка під час зчитування
     */
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
