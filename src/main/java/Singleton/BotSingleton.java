package Singleton;

import BotActions.BotAuthentication;
import BotActions.BotFollower;
import BotActions.BotLiker;
import BotActions.BotNavigation;
import Interfaces.TypeStrategy;
import Utility.UserDetails;
import org.openqa.selenium.WebDriver;

public class BotSingleton {

    private static BotSingleton instance = null;

    private WebDriver driver;
    private BotAuthentication botAuth;
    private BotNavigation botNav;
    private BotFollower botFollower;
    private BotLiker botLiker;

    private BotSingleton(){}

    public static BotSingleton getInstance() {
        //if no singleton has been initialised, create a new one
        if(instance == null) {
            instance = new BotSingleton();
        }
        return instance;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void setupBotCredentials(UserDetails details) {
        this.botAuth = new BotAuthentication(this.driver, details);
        this.botNav = new BotNavigation(this.driver);
        this.botFollower = new BotFollower(this.driver);
        this.botLiker = new BotLiker(this.driver);
    }

    public void likePhotos(TypeStrategy type, int numPhotos) {
        checkAuthorised();
        boolean likePhotos = this.botLiker.likePhotos(type, numPhotos);

        if(likePhotos) {
            System.out.println("Liking completed successfully!");
        } else {
            System.out.println("Something went wrong... Try again");
        }
    }

    public void followUsers(TypeStrategy type, int numUsers) {
        checkAuthorised();
        boolean followUsers = this.botFollower.followUsers(type, numUsers);

        if(followUsers) {
            System.out.println("Followed users successfully!");
        } else {
            System.out.println("Something went wrong... Try again");
        }
    }

    public void followUser(String username) {
        checkAuthorised();
        this.botFollower.followerUser(username);
    }

    public void unfollowUser(String username) {
        checkAuthorised();
        this.botFollower.unfollowUser(username);
    }

    public void unfollowUsers(int numUsers) {
        checkAuthorised();
        this.botFollower.unfollowUsers(numUsers);
    }

    private void checkAuthorised() {
        this.botNav.goHome();
        if(!this.botAuth.isLoggedIn()) {
            this.botAuth.login();
        }
    }

}