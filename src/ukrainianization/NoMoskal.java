package ukrainianization;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Тут Підключаємо файл з Гімном України
 * Зчитуємо кожну строку, та ввиводимо по строці в секунду
 */
public class NoMoskal {
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
