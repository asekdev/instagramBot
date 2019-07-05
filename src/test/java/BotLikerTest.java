import BotActions.BotAuthentication;
import BotActions.BotLiker;
import BotActions.BotNavigation;
import ActionStrategy.ExploreStrategy;
import ActionStrategy.HashtagStrategy;
import ActionStrategy.UserStrategy;
import Utility.Constants;
import Utility.UserDetails;
import Utility.Utils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BotLikerTest {

    static WebDriver driver;
    static BotNavigation botNav;
    static BotLiker botLiker;
    static BotAuthentication botAuthentication;
    static UserDetails userDetails = new UserDetails(Constants.USERNAME, Constants.PASSWORD);

    @BeforeAll
    public static void executeBefore() {
        //System.setProperty("webdriver.chrome.driver", "/Users/723352/Downloads/chromedriver");
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        String chromeProfile = "/Users/723352/Library/Application Support/Google/Chrome";
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--user-data-dir=" + chromeProfile);
//        options.addArguments("--disable-infobars");
//        options.addArguments("--start-maximized");
        driver = new ChromeDriver(); //add options if don't want to always log in
        driver.navigate().to("https://www.instagram.com/grandkosmetics");
        botNav = new BotNavigation(driver, userDetails);
        botAuthentication = new BotAuthentication(driver, userDetails);
        botLiker = new BotLiker(driver);
    }

    @Test
    @Order(1)
    void likeHashtagPhotosTest() {
        if (!botAuthentication.isLoggedIn()) {
            botAuthentication.login();
        }
        Utils.wait(3);
        boolean likeHashtagPhotos = botLiker.likePhotos(new HashtagStrategy(driver, "botox"), 2);
        assertTrue(likeHashtagPhotos);
    }

    @Test
    @Order(2)
    void likeHashtagThatDoesntExistTest() {
        if (!botAuthentication.isLoggedIn()) {
            botAuthentication.login();
        }
        Utils.wait(3);
        boolean likeHashtagThatDoesntExist= botLiker.likePhotos(new HashtagStrategy(driver, "asdfasdfas#1313!!@#!@"), 2);
        assertFalse(likeHashtagThatDoesntExist);
    }

    @Test
    @Order(3)
    void likePublicUserPhotosTest() {
        if (!botAuthentication.isLoggedIn()) {
            botAuthentication.login();
        }
        Utils.wait(3);
        boolean likePublicUserPhotos = botLiker.likePhotos(new UserStrategy(driver, "justinbieber"), 2);
        Utils.wait(3);
        assertTrue(likePublicUserPhotos);
    }

    @Test
    @Order(4)
    void likePrivateUserPhotosTest() {
        if (!botAuthentication.isLoggedIn()) {
            botAuthentication.login();
        }
        Utils.wait(3);
        boolean likePrivateUserPhotos = botLiker.likePhotos(new UserStrategy(driver, "codev.ski"), 3);
        assertFalse(likePrivateUserPhotos);
    }

    @Test
    @Order(5)
    void likeExplorePhotosTest() {
        if (!botAuthentication.isLoggedIn()) {
            botAuthentication.login();
        }
        botNav.goToExplorePage();
        Utils.wait(3);
        boolean likeExplorePhotos = botLiker.likePhotos(new ExploreStrategy(driver), 3);
        assertTrue(likeExplorePhotos);
        driver.quit();
    }
}