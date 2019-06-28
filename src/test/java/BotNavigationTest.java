import BotActions.BotAuthentication;
import BotActions.BotNavigation;
import Utility.UserDetails;
import Utility.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BotNavigationTest {

    static WebDriver driver;
    static BotNavigation botNav;
    static BotAuthentication botAuthentication;
    static UserDetails userDetails = new UserDetails("grandkosmetics", "ruska2019!");
    @BeforeAll
    public static void executeBefore() {
        System.setProperty("webdriver.chrome.driver", "/Users/723352/Downloads/chromedriver");
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
        if (botAuthentication.isLoggedIn()) {
            System.out.println("we need to log in ");
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
        boolean goProfile = this.botNav.goToProfile();
        assertEquals(true, goProfile);
        assertEquals(true, this.driver.getCurrentUrl()
                .equalsIgnoreCase("https://www.instagram.com/" + this.botNav.userDetails.getUsername()+"/"));
        Utils.wait(3);
    }

    @Test
    @Order(3)
    void goToExplorePageTest() {
        boolean goExplore = this.botNav.goToExplorePage();
        Utility.Utils.wait(2);
        assertEquals(true, goExplore);
        assertEquals(true, this.driver.getCurrentUrl()
                .equalsIgnoreCase("https://www.instagram.com/explore/"));
        Utility.Utils.wait(3);
    }

    @Test
    @Order(4)
    void goToUserPageTest() {
        boolean findUser = this.botNav.goToUserPage("kyliejenner");
        Utils.wait(3);
        assertEquals(true, findUser);
        assertEquals(this.driver.getCurrentUrl(), "https://www.instagram.com/kyliejenner/");
        Utils.wait(3);
    }

    @Test
    @Order(5)
    void goToUserPageTestFailure() {
        boolean findUser = this.botNav.goToUserPage("kylieasdfasdfasdfjenner");
        Utils.wait(3);
        assertEquals(false, findUser);
        Utils.wait(3);
    }

    @Test
    @Order(6)
    void goToHashtagTest() {
        this.botNav.goHome();
        boolean hashTag = this.botNav.goToHashtag("#test");
        Utils.wait(3);
        assertEquals(true, hashTag);
        Utils.wait(3);
    }

    @Test
    @Order(7)
    void goToHashtagNotFormattedTest() {
        boolean hashTag = this.botNav.goToHashtag("test");
        Utils.wait(3);
        assertEquals(true, hashTag);
        Utils.wait(3);

    }

    @Test
    @Order(8)
    void goToHashtagFailure() {
        boolean hashTag = this.botNav.goToHashtag("testasdfasdfasdfa");
        Utils.wait(3);
        assertEquals(false, hashTag);
        Utils.wait(3);
        this.driver.quit();
    }
}