package e2e.tests;

import e2e.enums.SubMenu;
import e2e.enums.SubSubMenu;
import e2e.pages.*;
import org.testng.annotations.Test;

public class UserCanChooseProductsFromAnyCategory extends TestBase {

    LoginPage loginPage;

    StartPage startPage;

    OverviewPage overviewPage;

    ProductsPage productsPage;

    @Test
    public void UseCanClickOnOneElementOfMainNavBarTest() throws InterruptedException {


        String email = "Virthunter@gmail.com";
        String password = "Virthunter@12";
        String myZoo = "Mein zooplus";
        String katzen = "Hundefutter";
        String subSubMenu = "Rocco";

        startPage = new StartPage(app.driver);
        startPage.clickOnCookies();
        startPage.waitForLoadingStartPage();
        startPage.moveMouseToDropDownMyZoo();
        Thread.sleep(3000);
        startPage.clickOnOneOfDropDownElement(myZoo);

        loginPage = new LoginPage(app.driver);
        loginPage.waitForLoadingLoginPage();
        loginPage.setDateLoginPage(email, password);

        overviewPage = new OverviewPage(app.driver);
        overviewPage.waitForLoadingOverviewPage();
        overviewPage.moveToOneOfMainNavBarElement(katzen);
        overviewPage.waitForSubMenuContainer(SubMenu.HUNDER.getValue());
        overviewPage.clickOnOneOfMainContainerElement(SubMenu.HUNDER.getValue(), SubSubMenu.SECTIONTWO.getValue(), subSubMenu);
        productsPage = new ProductsPage(app.driver);
        productsPage.waitForLoadingProductsPage();
        productsPage.checkIfAllProductsHaveCorrectName("Rocco");


    }


}
