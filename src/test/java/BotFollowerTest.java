import static org.junit.jupiter.api.Assertions.*;

import BotActions.BotAuthentication;
import BotActions.BotFollower;
import BotActions.BotLiker;
import BotActions.BotNavigation;
import ActionStrategy.ExploreStrategy;
import ActionStrategy.HashtagStrategy;
import Utility.Constants;
import Utility.UserDetails;
import Utility.Utils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BotFollowerTest {

    static WebDriver driver;
    static BotNavigation botNav;
    static BotFollower botFollower;
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
        driver = new ChromeDriver(); //re-add options as parameter
        driver.navigate().to("https://www.instagram.com/grandkosmetics");
        botNav = new BotNavigation(driver, userDetails);
        botAuthentication = new BotAuthentication(driver, userDetails);
        botLiker = new BotLiker(driver);
        botFollower = new BotFollower(driver);
    }

    @Test
    @Order(1)
    void followPublicUserTest() {
        if (!botAuthentication.isLoggedIn()) {
            botAuthentication.login();
        }
        boolean followUser = botFollower.followerUser("justinbieber");
        assertTrue(followUser);
        Utils.wait(3);
    }

    @Test
    @Order(2)
    void requestFollowPrivateUserTest() {
        if (!botAuthentication.isLoggedIn()) {
            botAuthentication.login();
        }
        boolean followPrivateUser = botFollower.followerUser("jesstoj_");
        assertTrue(followPrivateUser);
        Utils.wait(3);
    }

    @Test
    @Order(3)
    void followUserThatAlreadyFollowingTest() {
        if (botAuthentication.isLoggedIn()) {
            botAuthentication.login();
        }
        boolean followUserThatAlreadyFollowing = botFollower.followerUser("therock");
        assertFalse(followUserThatAlreadyFollowing);
    }

    @Test
    @Order(4)
    void followUserThatDoesntExistTest() {
        if (botAuthentication.isLoggedIn()) {
            botAuthentication.login();
        }
        boolean followUserThatDoesntExist = botFollower.followerUser("useruserusuus");
        assertFalse(followUserThatDoesntExist);
    }

    @Test
    @Order(5)
    void followExploreImageUsersTest() {
        if (botAuthentication.isLoggedIn()) {
            botAuthentication.login();
        }
        botNav.goToExplorePage();
        boolean followExploreUsers = botFollower.followUsers(new ExploreStrategy(driver), 3);
        assertTrue(followExploreUsers);
    }

    @Test
    @Order(6)
    void followUsersByHashtagTest() {
        if (botAuthentication.isLoggedIn() == true) {
            botAuthentication.login();
        }
        Utils.wait(3);
        boolean followUsersByHashtag = botFollower.followUsers(new HashtagStrategy(driver, "sports"), 4);
        assertTrue(followUsersByHashtag);
    }

    @Test
    @Order(7)
    void unfollowUserTest() {
        if (botAuthentication.isLoggedIn()) {
            botAuthentication.login();
        }
        boolean unfollowUser = botFollower.unfollowUser("therock");
        assertTrue(unfollowUser);
    }

    @Test
    @Order(8)
    void unfollowUserThatDoesntExistTest() {
        if (botAuthentication.isLoggedIn()) {
            botAuthentication.login();
        }
        boolean unfollowUserThatDoesntExist = botFollower.unfollowUser("asdfasdfasdfasdxxxvxvxvxvxv");
       assertFalse(unfollowUserThatDoesntExist);
    }

    @Test
    @Order(9)
    void unfollowUsersTest() {
        if (botAuthentication.isLoggedIn()) {
            botAuthentication.login();
        }
        botNav.goToProfile();
        boolean unfollowUsers = botFollower.unfollowUsers(5);
        assertTrue(unfollowUsers);
        this.driver.quit();
    }
}