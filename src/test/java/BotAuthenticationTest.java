import BotActions.BotAuthentication;
import Utility.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BotAuthenticationTest {

    static WebDriver driver;
    static BotAuthentication botAuth;

    @BeforeEach
    public void executeBefore() {
        //System.setProperty("webdriver.chrome.driver", "/Users/723352/Downloads/chromedriver");
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver = new ChromeDriver();
    }

    @Test
    @Order(1)
    void loginSuccessTest() {
        //provide correct credentials
        UserDetails userDetails = new UserDetails(Constants.USERNAME, Constants.PASSWORD);
        botAuth = new BotAuthentication(driver, userDetails);
        Utils.wait(4);
        boolean loginAttempt = botAuth.login();
        assertTrue(loginAttempt);
        driver.quit();
    }

    @Test
    @Order(2)
    void loginFailedTest() {
        //provide invalid credentials
        UserDetails userDetails = new UserDetails("ahhhahhh", "2333forrty!");
        botAuth = new BotAuthentication(driver, userDetails);
        boolean loginAttempt = botAuth.login();
        assertFalse(loginAttempt);
        driver.quit();
    }

    @Test
    @Order(3)
    void isLoggedInTest() {
        //login successfully, then check if logged in
        UserDetails userDetails = new UserDetails(Constants.USERNAME, Constants.PASSWORD);
        botAuth = new BotAuthentication(driver, userDetails);
        Utils.wait(4);
        botAuth.login();
        Utils.wait(4);
        boolean loggedIn = botAuth.isLoggedIn();
        assertTrue(loggedIn);
        driver.quit();
    }

    @Test
    @Order(4)
    void notLoggedInTest() {
        //create the user details & bot auth
        UserDetails userDetails = new UserDetails("ahhhahhh", "2333forrty!");
        botAuth = new BotAuthentication(driver, userDetails);
        Utils.wait(4);
        boolean isLoggedIn = botAuth.isLoggedIn();
        assertFalse(isLoggedIn);
        driver.quit();
    }

    @Test
    @Order(5)
    void logoutTest() {
        UserDetails userDetails = new UserDetails(Constants.USERNAME, Constants.PASSWORD);
        botAuth = new BotAuthentication(driver, userDetails);
        Utils.wait(4);
        botAuth.login();
        boolean logout = botAuth.logout();
        assertTrue(logout);
        driver.quit();
    }

}