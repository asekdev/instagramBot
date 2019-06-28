import BotActions.BotAuthentication;
import Utility.UserDetails;
import Utility.Utils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BotAuthenticationTest {

    static WebDriver driver;
    static BotAuthentication botAuth;
    static UserDetails userDetails = new UserDetails("grandkosmetics", "ruska2019!");

    @BeforeAll
    public static void executeBefore() {
        System.setProperty("webdriver.chrome.driver", "/Users/723352/Downloads/chromedriver");
        driver = new ChromeDriver();
        botAuth = new BotAuthentication(driver, userDetails);
    }

    @Test
    @Order(1)
    void notLoggedIn() {
        Utility.Utils.wait(5);
        boolean isLoggedIn = botAuth.isLoggedIn();
        assertEquals(false, isLoggedIn);
    }

    @Test
    @Order(2)
    void loginSuccess() {
        Utils.wait(4);
        boolean loginAttempt = botAuth.login();
        assertEquals(true, loginAttempt);
    }

    @Test
    @Order(3)
    void isLoggedIn() {
        boolean loggedIn = botAuth.isLoggedIn();
        assertEquals(true, loggedIn);
    }

    @Test
    @Order(4)
    void logout() {
        boolean logout = botAuth.logout();
        assertEquals(true, logout);

        Utils.wait(5);
        this.driver.quit();
    }

}