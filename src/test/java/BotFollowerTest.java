import static org.junit.jupiter.api.Assertions.*;

import BotActions.BotAuthentication;
import BotActions.BotFollower;
import BotActions.BotLiker;
import BotActions.BotNavigation;
import ActionStrategy.ExploreStrategy;
import ActionStrategy.HashtagStrategy;
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
    static UserDetails userDetails = new UserDetails("grandkosmetics", "ruska2019!");

    @BeforeAll
    public static void executeBefore() {
        System.setProperty("webdriver.chrome.driver", "/Users/723352/Downloads/chromedriver");
        //System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        String chromeProfile = "/Users/723352/Library/Application Support/Google/Chrome";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=" + chromeProfile);
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options); //re-add options as parameter
        driver.navigate().to("https://www.instagram.com/grandkosmetics");
        botNav = new BotNavigation(driver, userDetails);
        botAuthentication = new BotAuthentication(driver, userDetails);
        botLiker = new BotLiker(driver);
        botFollower = new BotFollower(driver);
    }

    @Test
    @Order(1)
    void followUserTest() {
        if (!botAuthentication.isLoggedIn()) {
            System.out.println("we need to log in ");
            botAuthentication.login();
        }

        boolean followUser = botFollower.followerUser("codev.ski");

        assertEquals(true, followUser);
        Utility.Utils.wait(3);
        this.driver.quit();
    }

    @Test
    @Order(2)
    void followUserThatDoesntExistTest() {
        if (botAuthentication.isLoggedIn()) {
            System.out.println("we need to log in ");
            botAuthentication.login();
        }

        boolean followUser = botFollower.followerUser("useruserusuus");

        assertEquals(false, followUser);
        this.driver.quit();
    }

    @Test
    @Order(3)
    void followExploreImageUsersTest() {
        if (botAuthentication.isLoggedIn()) {
            System.out.println("we need to log in ");
            botAuthentication.login();
        }
        botNav.goToExplorePage();

        boolean followExploreUsers = botFollower.followUsers(new ExploreStrategy(driver), 3);

        assertEquals(true, followExploreUsers);
    }

    @Test
    @Order(4)
    void followUsersFromHashtag() {
        if (botAuthentication.isLoggedIn() == true) {
            System.out.println("we need to log in ");
            botAuthentication.login();
        }
        Utils.wait(3);
        boolean followUser = botFollower.followUsers(new HashtagStrategy(driver, "tan"), 4);

        assertEquals(true, followUser);
    }

    @Test
    @Order(5)
    void unfollowUserTest() {
        if (botAuthentication.isLoggedIn()) {
            System.out.println("we need to log in ");
            botAuthentication.login();
        }

        boolean unfollowUser = botFollower.unfollowUser("codev.ski");

        assertEquals(true, unfollowUser);
    }

    @Test
    @Order(6)
    void unfollowUsers() {
        if (botAuthentication.isLoggedIn()) {
            System.out.println("we need to log in ");
            botAuthentication.login();
        }

        botNav.goToProfile();
        boolean unfollowUsers = botFollower.unfollowUsers(5);

        assertEquals(true, unfollowUsers);
        this.driver.quit();
    }
}