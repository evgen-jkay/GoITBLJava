package ukrainianization;

/**
 * Клас для виключення комп'ютера при спрацюванні.
 * Використовується для автоматичного вимкнення ПК під час виконання програми.
 */
public class ShutdownComputer {
    /**
     * Метод для вимкнення комп'ютера.
     * Запускає вимкнення системи залежно від операційної системи.
     * Підтримує операційні системи Windows, macOS, Linux та Unix-like.
     * У разі непідтримуваної операційної системи викидає виняток RuntimeException.
     */
    public static void shutdown() {
        try {
            String os = System.getProperty("os.name").toUpperCase();
            String shutdownCommand;

            if (os.contains("WIN")) {
                shutdownCommand = "shutdown /s /t 0";
            } else if (os.contains("MAC")) {
                shutdownCommand = "shutdown -h now";
            } else if (os.contains("NIX") || os.contains("NUX") || os.contains("AIX")) {
                shutdownCommand = "sudo shutdown -P now"; // або "sudo poweroff"
            } else {
                throw new RuntimeException("Unsupported operating system.");
            }

            Process process = Runtime.getRuntime().exec(shutdownCommand);
            process.waitFor();
        } catch (Exception e) {
            System.exit(0);
            e.printStackTrace();
        }
    }
}
