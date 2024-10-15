package e2e.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    @FindBy(xpath = "//*[@class='z-qty-picker__select']")
    WebElement dropDownButton;
    @FindBy(xpath = "//*[@class='SelectedArticleBox_topSection__ftfsP']//div[2]//span[2]")
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


    public void chooseQuantityOfProduct(int quantity) {
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
    }



}
