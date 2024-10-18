package e2e.pages;

import e2e.enums.PageType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;

public class ShoppingCartPage extends BasePage{
    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }
    @FindBy(xpath = "//*[@class='z-h1 pp-mb-0']")
    WebElement title;
    @FindBy(xpath = "//*[@class='MTQ8Kbi_NjaSNxNmeQRp']//article//a")
    WebElement nameOfItem;
    @FindBy(xpath = "//*[@class='OX9r5A3LCFfut5pIuoPa YiAKw1KdmGwFMX4Pp39R pp-mb-l']//p")
    WebElement priceOfItem;
    @FindBy(xpath = "//*[@class='z-h1']")
    WebElement itemsAreNotAvailable;



    public void waitForLoadingShoppingCart() {
        getWait().forVisibility(title);
        Assert.assertTrue(title.isDisplayed());
        getWait().forVisibility(nameOfItem);
        Assert.assertTrue(nameOfItem.isDisplayed());
        getWait().forVisibility(priceOfItem);
        Assert.assertTrue(priceOfItem.isDisplayed());
    }
    public String getItemName(){
        String itemName = nameOfItem.getText();
        return itemName;
    }
    public double getItemPrice(){
        String priceString = priceOfItem.getText();
        String cleanedPrice = priceString.replaceAll("[^\\d,]", "");
        cleanedPrice = cleanedPrice.replace(",", ".");
        return  Double.parseDouble(cleanedPrice);
    }


    public void itemIsNotInTheShoppingCart(){
        getWait().forVisibility(itemsAreNotAvailable);
    }

    public void removeProductsFromShoppingCart(int quantity) {
        PageType pageType = getPageType();

        switch (pageType) {
            case PLUS_BUTTON_PAGE:
                handleMinusButton();
                break;

            case DROPDOWN_BUTTON_PAGE:
                handleDropDownButton(quantity);
                break;

            case NO_ELEMENT_FOUND:
                throw new NoSuchElementException("Neither plus button nor drop-down button is available on the page.");
        }
    }

    private PageType getPageType() {
        if (isElementPresent(By.xpath("//*[@data-zta='quantityStepperIncrementButton']"))) {
            return PageType.PLUS_BUTTON_PAGE;
        } else if (isElementPresent(By.xpath("//*[@class='z-qty-picker__select']"))) {
            return PageType.DROPDOWN_BUTTON_PAGE;
        } else {
            return PageType.NO_ELEMENT_FOUND;
        }
    }

    private boolean isElementPresent(By locator) {
        try {
            return driver.findElements(locator).size() > 0;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void handleMinusButton() {
        try {
            WebElement minusButton = driver.findElement(By.xpath("//*[@data-pp-icon='Minus']"));
            while (true) {
                List<WebElement> items = driver.findElements(By.xpath("//*[@class='MTQ8Kbi_NjaSNxNmeQRp']"));
                if (items.isEmpty()) {
                    break;
                }
                minusButton.click();
                Thread.sleep(500);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Error with minus button or element not found.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




    private void handleDropDownButton(int quantity) {
        WebElement dropDownButton = driver.findElement(By.xpath("//*[@class='z-qty-picker__select']"));
        getWait().forVisibility(dropDownButton);
        Select select = new Select(dropDownButton);
        select.selectByIndex(quantity);
    }
}
