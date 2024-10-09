package e2e.tests;

import e2e.pages.StartPage;
import e2e.pages.LoginPage;
import e2e.pages.OverviewPage;
import e2e.pages.WishListPage;
import org.testng.annotations.Test;

public class MyWishListPagePageIsDisplayed_004 extends TestBase {

    LoginPage loginPage;

    StartPage startPage;

    OverviewPage overviewPage;

    WishListPage wishListPage;

    @Test
    public void WishListIsDisplayedTest() {


        String email = "Virthunter@gmail.com";
        String password = "Virthunter@12";
        String myZoo = "Mein zooplus";
        String myWishList = "Meine Wunschliste";

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
        overviewPage.clickOnOneOfDropDownElement(myWishList);

        wishListPage = new WishListPage(app.driver);
        wishListPage.waitForLoadingWishListPage();




    }


}
