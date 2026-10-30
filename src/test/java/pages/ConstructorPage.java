//
//package pages;
//
//import io.qameta.allure.Step;
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//public class ConstructorPage {
//
//    private final WebDriver driver;
//    private final WebDriverWait wait;
//
//    private final By bunsTab = By.xpath(
//            "//div[contains(@class, 'tab')][.//*[normalize-space()='Булки']]"
//    );
//
//    private final By saucesTab = By.xpath(
//            "//div[contains(@class, 'tab')][.//*[normalize-space()='Соусы']]"
//    );
//
//    private final By fillingsTab = By.xpath(
//            "//div[contains(@class, 'tab')][.//*[normalize-space()='Начинки']]"
//    );
//
//    private final By activeTab = By.cssSelector(
//            "div[class*='tab_tab_type_current']"
//    );
//
//    public ConstructorPage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(
//                driver, Duration.ofSeconds(10)
//        );
//    }
//
//    private void selectTab(By tabLocator, String tabName) {
//        WebElement element = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(tabLocator)
//        );
//
//        ((JavascriptExecutor) driver).executeScript(
//                "arguments[0].scrollIntoView({block: 'center'});",
//                element
//        );
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(tabLocator)
//        );
//
//        element = driver.findElement(tabLocator);
//
//        ((JavascriptExecutor) driver).executeScript(
//                "arguments[0].click();",
//                element
//        );
//
//        wait.until(
//                ExpectedConditions.textToBePresentInElementLocated(
//                        activeTab, tabName
//                )
//        );
//    }
//
//    @Step("Выбрать раздел «Булки»")
//    public void clickBunsTab() {
//        selectTab(bunsTab, "Булки");
//    }
//
//    @Step("Выбрать раздел «Соусы»")
//    public void clickSaucesTab() {
//        selectTab(saucesTab, "Соусы");
//    }
//
//    @Step("Выбрать раздел «Начинки»")
//    public void clickFillingsTab() {
//        selectTab(fillingsTab, "Начинки");
//    }
//
//    @Step("Получить название активного раздела конструктора")
//    public String getActiveTabText() {
//        return wait.until(
//                ExpectedConditions.visibilityOfElementLocated(activeTab)
//        ).getText().trim();
//    }
//}


package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConstructorPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By bunsTab = By.xpath(
            "//div[contains(@class, 'tab')][.//*[normalize-space()='Булки']]"
    );

    private final By saucesTab = By.xpath(
            "//div[contains(@class, 'tab')][.//*[normalize-space()='Соусы']]"
    );

    private final By fillingsTab = By.xpath(
            "//div[contains(@class, 'tab')][.//*[normalize-space()='Начинки']]"
    );

    private final By activeTab = By.cssSelector(
            "div[class*='tab_tab_type_current']"
    );

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(15)
        );
    }

    private void selectTab(By tabLocator, String tabName) {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(tabLocator)
        );

        // Прокручиваем страницу к нужной вкладке
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );

        // Получаем актуальный элемент после прокрутки
        element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(tabLocator)
        );

        // Выполняем клик через JavaScript
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                element
        );

        // Ждём переключения активной вкладки
        wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                        activeTab, tabName
                )
        );
    }

    @Step("Выбрать раздел «Булки»")
    public void clickBunsTab() {
        selectTab(bunsTab, "Булки");
    }

    @Step("Выбрать раздел «Соусы»")
    public void clickSaucesTab() {
        selectTab(saucesTab, "Соусы");
    }

    @Step("Выбрать раздел «Начинки»")
    public void clickFillingsTab() {
        selectTab(fillingsTab, "Начинки");
    }

    @Step("Получить название активного раздела конструктора")
    public String getActiveTabText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(activeTab)
        ).getText().trim();
    }
}
