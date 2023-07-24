package ukrainianization;

public class WallpaperChanger {
    // Завантаження бібліотеки Windows зміни заставки (використовуємо JNI)
    static {
        System.loadLibrary("User32");
    }

    // Оголошення функції бібліотеки Windows, яка змінює заставку
    private static native int SystemParametersInfo(int uiAction, int uiParam, String filePath, int fWinIni);

    // Метод для зміни заставки
    public static void changeWallpaper(String filePath) {
        // Шлях до зображення, яке ви хочете встановити як заставку

        // Виклик функції зміни заставки
        int result = SystemParametersInfo(20, 0, filePath, 3);

        if (result != 0) {
            System.out.println("Заставку успішно змінено.");
        } else {
            System.out.println("Не вдалося змінити заставку.");
        }
    }
}
