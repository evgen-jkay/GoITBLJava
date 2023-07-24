package ukrainianization;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;

public class ScreenFlipper {
    public static void flipScreen() throws AWTException {
        GraphicsDevice screenDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Robot robot = new Robot(screenDevice);

        // Завершення поточного виводу екрана
        robot.keyPress(0x91); // VK_SCROLL_LOCK (для деяких ОС може бути VK_SCROLL)

        // Очікування 1 секунди (замість цього можна використати Thread.sleep)
        robot.delay(1000);

        // Запуск перевернення екрана
        robot.keyPress(0x91); // VK_SCROLL_LOCK (для деяких ОС може бути VK_SCROLL)
    }
}
