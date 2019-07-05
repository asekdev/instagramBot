import BotActions.BotAuthentication;
import BotActions.BotNavigation;
import Utility.Constants;
import Utility.UserDetails;
import Utility.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BotNavigationTest {

    static WebDriver driver;
    static BotNavigation botNav;
    static BotAuthentication botAuthentication;
    static UserDetails userDetails = new UserDetails(Constants.USERNAME, Constants.PASSWORD);

    @BeforeAll
    public static void executeBefore() {
        System.setProperty("webdriver.chrome.driver", "/Users/723352/Downloads/chromedriver");
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
//        String chromeProfile = "/Users/723352/Library/Application Support/Google/Chrome";
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--user-data-dir="+chromeProfile);
//        options.addArguments("--disable-infobars");
//        options.addArguments("--start-maximized");
        driver = new ChromeDriver();
        botAuthentication = new BotAuthentication(driver, userDetails);
        driver.navigate().to("https://www.instagram.com/grandkosmetics");
        botNav = new BotNavigation(driver, userDetails);
    }

    @Test
    @Order(1)
    void goHomeTest() {
        if (!botAuthentication.isLoggedIn()) {
            botAuthentication.login();
        }

        boolean goHome = this.botNav.goHome();
        assertEquals(true, goHome);
        assertEquals(true, this.driver.getCurrentUrl()
                .equalsIgnoreCase("https://www.instagram.com/"));
        Utils.wait(4);
    }

    @Test
    @Order(2)
    void goToProfileTest() {
        boolean goProfile = botNav.goToProfile();
        assertTrue(goProfile);
        assertEquals("https://www.instagram.com/grandkosmetics/", driver.getCurrentUrl());
        Utils.wait(3);
    }

    @Test
    @Order(3)
    void goToExplorePageTest() {
        boolean goToExplore = botNav.goToExplorePage();
        Utils.wait(2);
        assertTrue(goToExplore);
        assertEquals("https://www.instagram.com/explore/", driver.getCurrentUrl());
        Utils.wait(3);
    }

    @Test
    @Order(4)
    void goToUserPageTest() {
        boolean goToUserPage = botNav.goToUserPage("kyliejenner", "like");
        Utils.wait(3);
        assertTrue(goToUserPage);
        assertEquals("https://www.instagram.com/kyliejenner/", driver.getCurrentUrl());
        Utils.wait(3);
    }

    @Test
    @Order(5)
    void goToUserPageThatDoesntExistTest() {
        boolean goToUserPageThatDoesntExist = botNav.goToUserPage("kylieasdfasdfasdfjenner", "like");
        Utils.wait(3);
        assertFalse(goToUserPageThatDoesntExist);
        Utils.wait(3);
    }

    @Test
    @Order(6)
    void goToHashtagTest() {
        botNav.goHome();
        boolean hashTagFormatted = botNav.goToHashtag("#test");
        Utils.wait(3);
        assertTrue(hashTagFormatted);
        Utils.wait(3);
    }

    @Test
    @Order(7)
    void goToHashtagNotFormattedTest() {
        boolean hashTagNotFormatted = botNav.goToHashtag("test");
        Utils.wait(3);
        assertTrue(hashTagNotFormatted);
        Utils.wait(3);
    }

    @Test
    @Order(8)
    void goToHashtagDoesntExistTest() {
        boolean hashtagThatDoesntExist = botNav.goToHashtag("testasdfasdfasdfa");
        Utils.wait(3);
        assertFalse(hashtagThatDoesntExist);
        Utils.wait(3);
        this.driver.quit();
    }
}