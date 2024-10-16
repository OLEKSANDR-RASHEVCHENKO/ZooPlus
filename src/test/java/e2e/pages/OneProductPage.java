package e2e.pages;

import e2e.enums.PageType;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.NoSuchElementException;

public class OneProductPage extends BasePage{
    public OneProductPage(WebDriver driver) {
        super(driver);
    }
    @FindBy(xpath = "//*[@data-zta='ProductTitle']//h1")
    WebElement title;
    @FindBy(xpath = "//*[@data-zta='quantityStepperIncrementButton']")
    WebElement plusButton;
    @FindBy(xpath = "//select[@class='z-qty-picker__select']")
    WebElement dropDownButton;
    @FindBy(xpath = "//*[@class='z-price ActiveVariantPrice_container__vdGaz Price_price__9QP3p']//div[2]//span[2]")
    WebElement price;
    @FindBy(xpath = "//span[normalize-space()='Zum Warenkorb hinzufügen']")
    WebElement addToCartButton;
    @FindBy(xpath = "//span[@data-testid='buttonText']")
    WebElement goToShoppingCartButton;

    public void waitForLoadingProductPage(){
        getWait().forVisibility(title);
        Assert.assertTrue(title.isDisplayed());
        getWait().forVisibility(price);
        Assert.assertTrue(price.isDisplayed());
        getWait().forVisibility(addToCartButton);
        Assert.assertTrue(addToCartButton.isDisplayed());
        getWait().forVisibility(goToShoppingCartButton);
        Assert.assertTrue(goToShoppingCartButton.isDisplayed());
    }
    public String getProductName(){
        String productName = title.getText();
        return productName;
    }
    public double getPrice(){
        String priceString = price.getText();
        String cleanedPrice = priceString.replaceAll("[^\\d,]", "");
        cleanedPrice = cleanedPrice.replace(",", ".");
        return  Double.parseDouble(cleanedPrice);
    }


    public void chooseQuantityOfProducts(int quantity) {
        PageType pageType = getPageType();

        switch (pageType) {
            case PLUS_BUTTON_PAGE:
                handlePlusButton(quantity);
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

    // Метод для работы с кнопкой плюс
    private void handlePlusButton(int quantity) {
        try {
            WebElement plusButton = driver.findElement(By.xpath("//*[@data-zta='quantityStepperIncrementButton']"));
            for (int i = 0; i < quantity; i++) {
                plusButton.click();
            }
        } catch (NoSuchElementException e) {
            System.out.println("Error with plus button.");
        }
    }

    private void handleDropDownButton(int quantity) {
            WebElement dropDownButton = driver.findElement(By.xpath("//*[@class='z-qty-picker__select']"));
            getWait().forVisibility(dropDownButton);
            Select select = new Select(dropDownButton);
            select.selectByIndex(quantity);
    }







   /* public void chooseQuantityOfProduct(int quantity) {
        boolean clickedPlusButton = false;

        for (int i = 0; i < quantity; i++) {
            if (plusButton.isDisplayed()) {
                plusButton.click();
                clickedPlusButton = true;
            } else {
                throw new NoSuchElementException("Plus button is not displayed.");
            }
        }

        if (!clickedPlusButton) {
            if (dropDownButton.isDisplayed()) {
                try {
                    dropDownButton.click();
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                } catch (NoAlertPresentException e) {
                }
                Select select = new Select(dropDownButton);
                select.selectByValue(String.valueOf(quantity));
            }
        }
    }*/













}
