
package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.ConstructorPage;
import io.qameta.allure.Description;

public class ConstructorTest extends BaseTest {

    private ConstructorPage constructorPage;

    @BeforeEach
    public void setUpConstructorPage() {
        constructorPage = new ConstructorPage(driver);
    }

    @Test
    @Description("Проверка переключения на раздел «Булки»")
    public void shouldOpenBunsSection() {
        constructorPage.clickBunsTab();

        assertTrue(
                constructorPage.getActiveTabText().contains("Булки"),
                "Должен быть выбран раздел «Булки»"
        );
    }

    @Test
    @Description("Проверка переключения на раздел «Соусы»")
    public void shouldOpenSaucesSection() {
        constructorPage.clickSaucesTab();

        assertTrue(
                constructorPage.getActiveTabText().contains("Соусы"),
                "Должен быть выбран раздел «Соусы»"
        );
    }

    @Test
    @Description("Проверка переключения на раздел «Начинки»")
    public void shouldOpenFillingsSection() {
        constructorPage.clickFillingsTab();

        assertTrue(
                constructorPage.getActiveTabText().contains("Начинки"),
                "Должен быть выбран раздел «Начинки»"
        );
    }
}
