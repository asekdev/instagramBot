import BotActions.BotAuthentication;
import BotActions.BotLiker;
import BotActions.BotNavigation;
import LikeStrategy.ExploreStrategy;
import LikeStrategy.HashtagStrategy;
import LikeStrategy.UserStrategy;
import Utility.UserDetails;
import Utility.Utils;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BotLikerTest {

    static WebDriver driver;
    static BotNavigation botNav;
    static BotLiker botLiker;
    static BotAuthentication botAuthentication;
    static UserDetails userDetails = new UserDetails("grandkosmetics", "ruska2019!");

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
    void likeHashtagPhotosTest() {
        if (botAuthentication.isLoggedIn()) {
            System.out.println("we need to log in ");
            botAuthentication.login();
        }

        botNav.goToHashtag("lipstick");
        Utils.wait(3);

        boolean likeHashtagPhotos = botLiker.likePhotos(new HashtagStrategy(driver), 3);

        System.out.println("liked photos outcome = " + likeHashtagPhotos);

        assertEquals(true, likeHashtagPhotos);

    }

    @Test
    void likeUserPhotosTest() {
        if (botAuthentication.isLoggedIn()) {
            System.out.println("we need to log in ");
            botAuthentication.login();
        }

        botNav.goToUserPage("kimkardashian");
        Utils.wait(3);

        boolean likeUserPhotos = botLiker.likePhotos(new UserStrategy(driver), 4);

        Utils.wait(3);

        assertEquals(true, likeUserPhotos);

    }

    @Test
    void likeExplorePhotosTest() {
        if (botAuthentication.isLoggedIn()) {
            System.out.println("we need to log in ");
            botAuthentication.login();
        }

        botNav.goToExplorePage();
        Utils.wait(3);

        boolean likeExplorePhotos = botLiker.likePhotos(new ExploreStrategy(driver), 3);

        assertEquals(true, likeExplorePhotos);
    }
}