import LikeStrategy.ExploreStrategy;
import LikeStrategy.HashtagStrategy;
import LikeStrategy.UserStrategy;
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
        //singleton.setDriver(setupChromeDriver());
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

        //Log the user in with provided credentials
        userInputLogin();

        while(programRunning) {
            showMenuOptions();
            int option = scan.nextInt();

            switch(option) {
                case 1:
                    showFollowOptions();
                    System.out.print("Your option: ");
                    return;
                case 2:
                    showLikeOptions();
                    System.out.print("Your option: ");

                    try {
                        int likeOption = scan.nextInt();
                        likeOptions(likeOption);
                    } catch (Exception e) {
                        System.out.println("Please specify a valid option.");
                    }
                    break;
                case 3:
                    singleton.botAuth.logout();
                    System.out.println("\nLogin with a different account\n");
                    singleton.getDriver().quit();
                    userInputLogin();
                    break;
                case 4:
                    singleton.botAuth.logout();
                    System.out.println("Quitting application... Goodbye!");
                    System.exit(0);
                    return;
            }
        }

    }

    public static WebDriver setupChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "/Users/723352/Downloads/chromedriver");
        System.setProperty("webdriver.chrome.args", "--disable-logging --log-level=3");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        options.addArguments("--silent");
        options.addArguments("--log-level=OFF");
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

    public static void userInputLogin() {
        BotSingleton singleton = BotSingleton.getInstance();
        singleton.setDriver(setupChromeDriver());
        boolean authorised = false;
//        UserDetails user = getUserCredentials();
//        singleton.setupBotCredentials(user);

        while(!authorised) {
            UserDetails user = getUserCredentials();
            singleton.setupBotCredentials(user);
            boolean login = singleton.botAuth.login();

            if(login) {
                System.out.println("Logged in successfully!");
                authorised = true;
            } else {
                System.out.println("Authentication failed, try again.");
            }
        }
    }

    public static void showMenuOptions() {
        System.out.println("\nPlease select an option: ");
        System.out.println("--------------------------------------");
        System.out.println("1) Follow/Unfollow Users");
        System.out.println("2) Like Photos\n");
        System.out.println("3) Login as different user");
        System.out.println("4) Quit\n");
    }

    public static void showFollowOptions() {
        System.out.println("Follow or Unfollow Users");
        System.out.println("--------------------------------------\n");
        System.out.println("1) Follow users by hashtag");
        System.out.println("2) Follow users on explore page");
        System.out.println("3) Follow a single user\n");
        System.out.println("4) Unfollow a single user");
        System.out.println("5) Unfollow multiple users\n");
        System.out.println("6) Back to Main Menu");
    }

    public static void showLikeOptions() {
        System.out.println("Like Photos");
        System.out.println("--------------------------------------\n");
        System.out.println("1) Like Photos by hashtag");
        System.out.println("2) Like Photos on explore page");
        System.out.println("3) Like a users photos\n");
        System.out.println("4) Back to Main Menu");
    }

    public static void likeOptions(int option) {
        BotSingleton singleton = BotSingleton.getInstance();
        Scanner scan = new Scanner(System.in);
        int numPhotos = 0;

        boolean validOption = false;

        while(!validOption){
            switch(option) {
                case 1:
                    System.out.print("Specify a hashtag: ");
                    String hashtag = scan.next();
                    System.out.print("\nNumber of photos to like: ");
                    numPhotos = scan.nextInt();
                    singleton.likePhotos(new HashtagStrategy(singleton.getDriver(), hashtag), numPhotos);
                    validOption = true;
                    break;
                case 2:
                    System.out.print("\nNumber of photos to like on Explore page: ");
                    numPhotos = scan.nextInt();
                    singleton.likePhotos(new ExploreStrategy(singleton.getDriver()), numPhotos);
                    validOption = true;
                    break;
                case 3:
                    System.out.print("Specify a user: ");
                    String username = scan.next();
                    System.out.print("\nNumber of photos to like: ");
                    numPhotos = scan.nextInt();
                    singleton.likePhotos(new UserStrategy(singleton.getDriver(), username), numPhotos);
                    validOption = true;
                    break;
                default:
                    System.out.println("Please specify a valid option.");
                    validOption = true;
                    break;
            }
        }

    }

}
