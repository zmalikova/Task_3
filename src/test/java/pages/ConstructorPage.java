
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

    private final By bunsHeading = By.xpath(
            "//main//h2[normalize-space()='Булки']"
    );

    private final By saucesHeading = By.xpath(
            "//main//h2[normalize-space()='Соусы']"
    );

    private final By fillingsHeading = By.xpath(
            "//main//h2[normalize-space()='Начинки']"
    );

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );
    }

    private void clickTab(By locator) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", element
        );
    }

    @Step("Выбрать раздел «Булки»")
    public void clickBunsTab() {
        clickTab(bunsTab);
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(bunsHeading)
        );
    }

    @Step("Выбрать раздел «Соусы»")
    public void clickSaucesTab() {
        clickTab(saucesTab);
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(saucesHeading)
        );
    }

    @Step("Выбрать раздел «Начинки»")
    public void clickFillingsTab() {
        clickTab(fillingsTab);
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(fillingsHeading)
        );
    }


    @Step("Получить название активного раздела конструктора")
    public String getActiveTabText() {
        By activeTab = By.cssSelector(
                "div[class*='tab_tab_type_current']"
        );

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(activeTab)
        ).getText().trim();
    }


    private boolean isHeadingVisible(By locator) {
        for (WebElement element : driver.findElements(locator)) {
            if (element.isDisplayed()) {
                return true;
            }
        }
        return false;
    }
}
