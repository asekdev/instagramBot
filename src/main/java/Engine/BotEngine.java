package Engine;

import ActionStrategy.ExploreStrategy;
import ActionStrategy.HashtagStrategy;
import ActionStrategy.UserStrategy;
import Singleton.BotSingleton;
import Utility.ChromePreferences;
import Utility.UserDetails;
import com.github.lalyos.jfiglet.FigletFont;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import javax.xml.ws.WebEndpoint;
import java.io.IOException;
import java.util.Scanner;
import java.util.prefs.Preferences;

public class BotEngine {

    public static void main(String[] args){

        BotSingleton singleton = BotSingleton.getInstance();
        setupChromeDriver(true);
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
                    int followOption = scan.nextInt();
                    followOptions(followOption);
                    break;
                case 2:
                    showLikeOptions();
                    System.out.print("Your option: ");
                    int likeOption = scan.nextInt();
                    likeOptions(likeOption);
                    break;
                case 3:
                    singleton.botAuth.logout();
                    System.out.println("\nLogin with a different account\n");
                    singleton.getDriver().quit();
                    userInputLogin();
                    break;
                case 4:
                    System.out.println("Please select your ChromeDriver");
                    singleton.botAuth.logout();
                    singleton.getDriver().quit();
                    setupChromeDriver(true);
                    break;
                case 5:
                    System.out.println("Closing application...");
                    singleton.botAuth.logout();
                    singleton.getDriver().quit();
                    System.out.println("Application closed... Goodbye!");
                    System.exit(0);
                    break;
            }
        }

    }

    public static void setupChromeDriver(boolean modify) {
        BotSingleton singleton = BotSingleton.getInstance();
        ChromePreferences prefs = new ChromePreferences();
        JFileChooser fileChooser = null;
        WebDriver driver = null;
//        System.out.println("modify = " + modify);
//        System.out.println("prefernce from before = " + prefs.getChromedriverpath());
        if(prefs.getChromedriverpath() == "" || modify) {
            System.out.println("Getting to open dialog area....");
            System.out.println("filechooser > " + fileChooser);
            fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(null);
            fileChooser.setVisible(true);
            prefs.setChromedriverPath(fileChooser.getSelectedFile().getAbsolutePath());
        }

       try {
           System.setProperty("webdriver.chrome.driver", prefs.getChromedriverpath());
           System.setProperty("webdriver.chrome.args", "--disable-logging --log-level=3");
           System.setProperty("webdriver.chrome.silentOutput", "true");
           ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
           options.addArguments("--silent");
           options.addArguments("--log-level=OFF");
           driver = new ChromeDriver(options); //re-add options as parameter
       }catch (IllegalStateException e) {
           System.out.println("Please choose a chromedriver exexutable file.");

           setupChromeDriver(true);
       }
        System.out.println("did we get here");
        singleton.setDriver(driver);
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
        boolean authorised = false;

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
        System.out.println("2) Like Photos");
        System.out.println("3) Login as a different user\n");
        System.out.println("4) Change ChromeDriver");
        System.out.println("5) Quit\n");
    }

    public static void showFollowOptions() {
        System.out.println("Follow or Unfollow Users");
        System.out.println("--------------------------------------\n");
        System.out.println("1) Follow users by hashtag");
        System.out.println("2) Follow users on explore page");
        System.out.println("3) Follow a single user\n");
        System.out.println("4) Unfollow a single user");
        System.out.println("5) Unfollow multiple users\n");
        System.out.println("6) Back to Engine.BotEngine Menu");
    }

    public static void showLikeOptions() {
        System.out.println("Like Photos");
        System.out.println("--------------------------------------\n");
        System.out.println("1) Like Photos by hashtag");
        System.out.println("2) Like Photos on explore page");
        System.out.println("3) Like a users photos\n");
        System.out.println("4) Back to Engine.BotEngine Menu");
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
                case 4:
                    validOption = true;
                    break;
                default:
                    System.out.println("Please specify a valid option.");
                    validOption = true;
                    break;
            }
        }

    }

    public static void followOptions(int option) {
        BotSingleton singleton = BotSingleton.getInstance();
        Scanner scan = new Scanner(System.in);
        int numUsers = 0;

        boolean validOption = false;

        while(!validOption){
            switch(option) {
                case 1:
                    System.out.print("Specify a hashtag: ");
                    String hashtag = scan.next();
                    System.out.print("\nNumber of users to follow: ");
                    numUsers = scan.nextInt();
                    singleton.followUsers(new HashtagStrategy(singleton.getDriver(), hashtag), numUsers );
                    validOption = true;
                    break;
                case 2:
                    System.out.print("\nNumber of users to follow on Explore page: ");
                    numUsers = scan.nextInt();
                    singleton.followUsers(new ExploreStrategy(singleton.getDriver()), numUsers );
                    validOption = true;
                    break;
                case 3:
                    System.out.print("Specify a user: ");
                    String username = scan.next();
                    singleton.followUser(username);
                    validOption = true;
                    break;
                case 4:
                    System.out.print("Specify a user to unfollow: ");
                    username = scan.next();
                    singleton.unfollowUser(username);
                    validOption = true;
                    break;
                case 5:
                    System.out.print("Number of users to unfollow: ");
                    numUsers = scan.nextInt();
                    singleton.unfollowUsers(numUsers);
                    validOption = true;
                    break;
                case 6:
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