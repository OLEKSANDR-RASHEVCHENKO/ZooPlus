package e2e.tests;

import e2e.pages.*;
import org.testng.annotations.Test;

public class OrdersPageIsDisplayed_003 extends TestBase {
    LoginPage loginPage;

    StartPage startPage;

    OverviewPage overviewPage;

    OrdersPage ordersPage;

    @Test
    public void OrdersListIsDisplayedTest() {


        String email = "Virthunter@gmail.com";
        String password = "Virthunter@12";
        String myZoo = "Mein zooplus";
        String myOrders = "Meine Bestellungen";

        startPage = new StartPage(app.driver);
        startPage.clickOnCookies();
        startPage.waitForLoadingStartPage();
        startPage.moveMouseToDropDownMyZoo();
        startPage.clickOnOneOfDropDownElement(myZoo);

        loginPage = new LoginPage(app.driver);
        loginPage.waitForLoadingLoginPage();
        loginPage.setDateLoginPage(email, password);

        overviewPage = new OverviewPage(app.driver);
        overviewPage.waitForLoadingOverviewPage();
        overviewPage.moveMouseToDropDownMyZoo();
        overviewPage.clickOnOneOfDropDownElement(myOrders);

        ordersPage = new OrdersPage(app.driver);
        ordersPage.waitForLoadingOrdersPage();


    }

}
