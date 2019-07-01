import Singleton.BotSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        //setup the singleton
        BotSingleton singleton = BotSingleton.getInstance();
        singleton.setDriver(setupChromeDriver());

        boolean programRunning = true;
        Scanner scan = new Scanner(System.in);

        //start the initial

        System.out.println("Welcome to Instagram Automation Bot!");
        System.out.println("To get started, specify your username and password for Instagram.\n");






    }

    public static WebDriver setupChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "/Users/723352/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver(); //re-add options as parameter
        return driver;
    }

}
