import Singleton.BotSingleton;
import Utility.UserDetails;
import com.github.lalyos.jfiglet.FigletFont;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        BotSingleton singleton = BotSingleton.getInstance();
        singleton.setDriver(setupChromeDriver());
        String headerText = "";

        boolean programRunning = true;
        Scanner scan = new Scanner(System.in);

        try {
             headerText = FigletFont.convertOneLine("autogram");
        } catch(IOException e) {
            e.printStackTrace();
        }

        //start the initial
        System.out.println(headerText);
        System.out.println("Welcome to Instagram Automation Bot!");
        System.out.println("To get started, specify your username and password for Instagram.\n");

        UserDetails user = getUserCredentials();
        singleton.setupBotCredentials(user);

        System.out.println("username of object: " + user.getUsername());
        System.out.println("password of object: " + user.getPassword());


    }

    public static WebDriver setupChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "/Users/723352/Downloads/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options); //re-add options as parameter
        return driver;
    }

    public static UserDetails getUserCredentials() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scan.next();

        System.out.print("Password: ");
        String password = scan.next();

        return new UserDetails(username, password);
    }

}
