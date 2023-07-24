package ukrainianization;

/**
 * Це знайшов в Google
 * Виключаємо ПК при спрацюванні
 */
public class ShutdownComputer {
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
            e.printStackTrace();
        }
    }
}
