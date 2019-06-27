package BotActions;

import Interfaces.IFollower;
import Interfaces.TypeStrategy;
import Utility.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

public class BotFollower implements IFollower {

    private WebDriver driver;
    private ArrayList<String> userLinks = new ArrayList<String>();
    private BotNavigation botNav;

    public BotFollower(WebDriver driver) {
        this.driver = driver;
        this.botNav = new BotNavigation(this.driver);
    }

    public void addUserLinks(ArrayList a){
        this.userLinks = a;
    }

    public boolean followerUser(String username) {
        this.botNav.goToUserPage(username);

        try {
            Utils.wait(3);
            WebElement followBtn = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/header/section/div[1]/div[1]/span/span[1]/button"));
            System.out.println("button text follow -> " + followBtn.getText());
            if(followBtn.getText().equalsIgnoreCase("Follow")){
                followBtn.click();
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean unfollowUsers(int numUsers) {
        return false;
    }

    public boolean followUsers(TypeStrategy type, int numUsers) {
        this.addUserLinks(type.getImageLinks(10));

        for (String link : this.userLinks) {
            try {
                this.driver.navigate().to(link);
                System.out.println("Link = " + link);
                Utils.wait(3);
                WebElement followBtn = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/div/article/header/div[2]/div[1]/div[2]/button"));
                WebElement username = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/div/article/header/div[2]/div[1]/div[1]/h2/a"));

                System.out.println("getting here 11");
                System.out.println("follow button text = " + followBtn.getText());

                if (followBtn.getText().equalsIgnoreCase("follow")) {
                    followBtn.click();
                    Utils.wait(3);
                    System.out.println("Followed " + username.getText());
                }
            } catch (Exception e) {
                e.getMessage();
                return false;
            }
        }

        return true;
    }
}
