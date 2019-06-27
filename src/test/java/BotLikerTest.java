import BotActions.BotAuthentication;
import BotActions.BotLiker;
import BotActions.BotNavigation;
import LikeStrategy.ExploreStrategy;
import Utility.UserDetails;
import Utility.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

class BotLikerTest {

    static WebDriver driver;
    static BotNavigation botNav;
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
        botNav = new BotNavigation(driver, userDetails);
        botAuthentication = new BotAuthentication(driver, userDetails);
        botLiker = new BotLiker(driver);
    }
//
//    @Test
//    void likeHashtagPhotosTest() {
//        if (botAuthentication.isLoggedIn()) {
//            System.out.println("we need to log in ");
//            botAuthentication.login();
//        }
//
//        botNav.goToHashtag("lipstick");
//        Utils.wait(3);
//
//        boolean likeHashtagPhotos = botLiker.likePhotos(new HashtagStrategy(driver), 10);
//
//        System.out.println("liked photos outcome = " + likeHashtagPhotos);
//
//        Utils.wait(4);
//
//        driver.quit();
//
//    }

//    @Test
//    void likeHashtagPhotosTest() {
//        if (botAuthentication.isLoggedIn()) {
//            System.out.println("we need to log in ");
//            botAuthentication.login();
//        }
//
//        botNav.goToUserPage("kyliejenner");
//        Utils.wait(3);
//
//        boolean likeHashtagPhotos = botLiker.likePhotos(new UserStrategy(driver), 10);
//
//        System.out.println("liked photos outcome = " + likeHashtagPhotos);
//
//        Utils.wait(4);
//
//        driver.quit();
//
//    }

    @Test
    void likeHashtagPhotosTest() {
        if (botAuthentication.isLoggedIn()) {
            System.out.println("we need to log in ");
            botAuthentication.login();
        }

        botNav.goToExplorePage();
        Utils.wait(3);

        boolean likeHashtagPhotos = botLiker.likePhotos(new ExploreStrategy(driver), 10);

        System.out.println("liked explorer photos outcome = " + likeHashtagPhotos);

        Utils.wait(4);

        driver.quit();

    }


//    @Test
//    void likeHashtag Test() {
//        HashtagStrategy botLiker2 = new HashtagStrategy();
//        boolean loop = botLiker2.likePhotos(19);
////        assertEquals(2, cols);
//    }
}