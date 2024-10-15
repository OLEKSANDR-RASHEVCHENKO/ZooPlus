package e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeoutException;

public class OverviewPage extends StartPage {
    public OverviewPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h1[normalize-space()='Hallo Anatolii']")
    WebElement customerName;
    @FindBy(xpath = "//div[@class='TopBar_headerAccountFlyoutWrapper__zTQP1']//div[@id='shopHeaderAccountLink']")
    WebElement dropDownMeinZoo;
    @FindBy(xpath = "//*[@class='loy-bppo-bannerContainer__title']")
    WebElement titleOnBonusPage;

    @FindBy(xpath = "//*[@id='header-category-links']")
    WebElement container;


    public void waitForLoadingOverviewPage() {
        getWait().forVisibility(customerName);
        Assert.assertTrue(customerName.isDisplayed());
        getWait().forVisibility(dropDownMeinZoo);
        Assert.assertTrue(dropDownMeinZoo.isDisplayed());
        getWait().forVisibility(container);
        Assert.assertTrue(container.isDisplayed());
    }

    public void moveMouseToDropDownMyZoo() {
        Actions actions = new Actions(driver);
        actions.moveToElement(dropDownMeinZoo).perform();
    }


    public void clickOnAllElementsOfDropDownMenu(List<String> expectedTitles) {
        moveMouseToDropDownMyZoo();
        List<WebElement> menus = driver.findElements(By.xpath("//*[@class='Flyout-module_flyoutVisible__u9qJE Flyout-module_flyout__qLvdx']//*[@class='Flyout-module_linkText__OZGD8']"));
        int menuItemsCount = menus.size();
        System.out.println(menuItemsCount);

        // Проходим по каждому элементу меню
        for (int i = 0; i < menuItemsCount; i++) {
            // Снова наводим курсор на меню, чтобы оно оставалось открытым
            moveMouseToDropDownMyZoo();

            // Повторно находим все элементы меню, так как после клика они могут быть пересозданы
            menus = driver.findElements(By.xpath("//*[@class='Flyout-module_flyoutVisible__u9qJE Flyout-module_flyout__qLvdx']//*[@class='Flyout-module_linkText__OZGD8']"));

            // Ждём, пока все элементы меню станут видимыми
            getWait().forAllVisibility(menus);

            // Проверяем, что текущий элемент меню существует и можем кликнуть по нему
            if (menus.size() > i) {
                // Получаем элемент меню по индексу
                WebElement menuItem = menus.get(i);

                // Кликаем по элементу
                menuItem.click();

                try {
                    // Ожидаем 1 секунду после клика, чтобы страница могла загрузиться
                    Thread.sleep(1000);

                    // Пытаемся найти заголовок страницы, который может быть в тегах <h1> или <h2>
                    WebElement titleForAllPages = driver.findElement(By.xpath("//h1[contains(@class, 'title')] | //h2[contains(@class, 'title')]"));

                    // Проверяем, что заголовок виден на странице
                    if (titleForAllPages.isDisplayed()) {
                        // Ждём, пока заголовок страницы станет видимым
                        getWait().forVisibility(titleForAllPages);

                        // Получаем текст заголовка и выводим его в консоль
                        String actualPageTitle = titleForAllPages.getText();
                        System.out.println(actualPageTitle);

                        // Сравниваем фактический заголовок страницы с ожидаемым
                        Assert.assertEquals(actualPageTitle, expectedTitles.get(i));
                    }

                    // Обрабатываем исключения в блоке try
                } catch (Exception e) {
                    // Если заголовок страницы отличается на бонусной странице, ищем заголовок бонусной страницы
                    if (titleOnBonusPage.isDisplayed()) {
                        // Получаем текст заголовка бонусной страницы и выводим его в консоль
                        String actualBonusPageTitle = titleOnBonusPage.getText();
                        System.out.println(actualBonusPageTitle);

                        // Сравниваем фактический заголовок бонусной страницы с ожидаемым
                        Assert.assertEquals(actualBonusPageTitle, expectedTitles.get(i));
                    } else {
                        // Если заголовок не найден, выводим сообщение об ошибке
                        System.out.println("Error ebat ego rot");
                    }
                }
            }
        }
    }


    public void moveToOneOfMainNavBarElement(String element) {
        List<WebElement> elements = driver.findElements(By.xpath("//*[@id='header-category-links']//*[@class='LowerBar_categoryItem__5VHca']"));
        getWait().forAllVisibility(elements);
        for (WebElement element1 : elements) {
            if (element1.getText().contains(element)) {
                Actions actions = new Actions(driver);
                actions.moveToElement(element1).perform();
                break;
            }
        }
    }

    public void waitForSubMenuContainer(int numberOfContainer) {
        WebElement element = driver.findElement(By.xpath("//*[@id='header-category-flyout'][" + numberOfContainer + "]"));
        getWait().forVisibility(element);
        Assert.assertTrue(element.isDisplayed());
    }

    public void clickOnOneOfMainContainerElement(int category, int column, String element) {
        WebElement ctg = driver.findElement(By.xpath("//*[@id='header-category-flyout'][" + category + "]//*[@class='CategoryFlyout_animalPanelGroup___E_yP'][" + column + "]//*[text()='" + element + "']"));
        getWait().forVisibility(ctg);
        ctg.click();

    }
}
