import static org.junit.jupiter.api.Assertions.*;

import BotActions.BotAuthentication;
import BotActions.BotFollower;
import BotActions.BotLiker;
import BotActions.BotNavigation;
import LikeStrategy.ExploreStrategy;
import LikeStrategy.HashtagStrategy;
import Utility.UserDetails;
import Utility.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

class BotFollowerTest {

    static WebDriver driver;
    static BotNavigation botNav;
    static BotFollower botFollower;
    static BotLiker botLiker;
    static BotAuthentication botAuthentication;
    static UserDetails userDetails = new UserDetails("grandkosmetics", "ruska2019!");

    @BeforeAll
    public static void executeBefore() {
        System.setProperty("webdriver.chrome.driver", "/Users/723352/Downloads/chromedriver");
        String chromeProfile = "/Users/723352/Library/Application Support/Google/Chrome";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=" + chromeProfile);
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.navigate().to("https://www.instagram.com/grandkosmetics");
        botNav = new BotNavigation(driver);
        botAuthentication = new BotAuthentication(driver, userDetails);
        botLiker = new BotLiker(driver);
        botFollower = new BotFollower(driver);
    }

//    @Test
//    @Order(1)
//    void followUserTest() {
//        if (botAuthentication.isLoggedIn()) {
//            System.out.println("we need to log in ");
//            botAuthentication.login();
//        }
//
//        boolean followUser = botFollower.followerUser("thenotoriousmma");
//
//        assertEquals(true, followUser);
//    }

//    @Test
//    @Order(1)
//    void followUserThatDoesntExistTest() {
//        if (botAuthentication.isLoggedIn()) {
//            System.out.println("we need to log in ");
//            botAuthentication.login();
//        }
//
//        boolean followUser = botFollower.followerUser("useruserusuus");
//
//        assertEquals(false, followUser);
//    }

    @Test
    @Order(1)
    void followUsersFromHashtag() {
        if (botAuthentication.isLoggedIn()) {
            System.out.println("we need to log in ");
            botAuthentication.login();
        }

        botNav.goToHashtag("lipstick");

        boolean followUser = botFollower.followUsers(new HashtagStrategy(driver), 10);

//        assertEquals(true, followUser);
    }
}