import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Driver;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BotAuthenticationTest {

    static WebDriver driver;
    static BotAuthentication botAuth;

    @BeforeAll
    public static void executeBefore() {
        System.setProperty("webdriver.chrome.driver", "/Users/723352/Downloads/chromedriver");
        driver = new ChromeDriver();
        botAuth = new BotAuthentication(driver, "grandkosmetics", "ruska2019!");
    }

//    @Test
//    @Order(1)
//    void notLoggedIn() {
//        System.out.println("running first test");
//        Utils.wait(5);
//        boolean isLoggedIn = botAuth.isLoggedIn();
//        assertEquals(false, isLoggedIn);
//
//    }

    @Test
    @Order(2)
    void loginSuccess() {
        System.out.println("running second test");
        Utils.wait(4);
        boolean loginAttempt = botAuth.login();
        assertEquals(true, loginAttempt);
    }

    @Test
    @Order(3)
    void isLoggedIn() {
        System.out.println("running 3rd test");

        boolean loggedIn = botAuth.isLoggedIn();
        assertEquals(true, loggedIn);
    }

    @Test
    @Order(4)
    void logout() {
        System.out.println("running final test");

        boolean logout = botAuth.logout();
        assertEquals(true, logout);

        Utils.wait(5);
        this.driver.quit();
    }

}