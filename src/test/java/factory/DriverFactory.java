
package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    public static WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();

            case "yandex":
                ChromeOptions options = new ChromeOptions();

                options.setBinary(
                        "/Applications/Yandex.app/Contents/MacOS/Yandex"
                );

                WebDriverManager.chromedriver()
                        .browserVersion("152")
                        .setup();

                return new ChromeDriver(options);

            default:
                throw new IllegalArgumentException(
                        "Неизвестный браузер: " + browser
                );
        }
    }
}
