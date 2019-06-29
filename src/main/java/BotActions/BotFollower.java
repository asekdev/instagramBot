package BotActions;

import Interfaces.IFollower;
import Interfaces.TypeStrategy;
import Utility.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BotFollower implements IFollower {

    private WebDriver driver;
    private ArrayList<String> userLinks = new ArrayList<String>();
    private BotNavigation botNav;

    public BotFollower(WebDriver driver) {
        this.driver = driver;
        this.botNav = new BotNavigation(this.driver);
    }

    public void addUserLinks(ArrayList a) {
        this.userLinks = a;
    }

    public boolean followerUser(String username) {
        this.botNav.goToUserPage(username);
        this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        try {
            Utils.wait(2);
            WebElement followBtn = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/header/section/div[2]/button"));
            System.out.println("button text follow -> " + followBtn.getText());
            if (followBtn.getText().equalsIgnoreCase("Follow")) {
                followBtn.click();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean unfollowUser(String username) {
        this.botNav.goToUserPage(username);

        try {
            Utils.wait(3);
            WebElement followBtn = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/header/section/div[1]/div[1]/span/span[1]/button"));
            System.out.println("button text follow -> " + followBtn.getText());
            if (followBtn.getText().equalsIgnoreCase("following")) {
                followBtn.click();
                Utils.wait(3);
                WebElement confirmUnfollow = this.driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/button[1]"));
                confirmUnfollow.click();
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean unfollowUsers(int numUsers) {
        try {
            WebElement followersButton = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/header/section/ul/li[3]/a"));
            followersButton.click();

            Utils.wait(3);

            for(int i=1; i <= numUsers; i++) {
                if(i % 2 == 0) {
                    JavascriptExecutor jse = (JavascriptExecutor) this.driver;
                    jse.executeScript("window.scrollBy(0,1000)", "");
                    System.out.println("should be scrolling...");
                }
                Utils.wait(2);
                WebElement followerName = this.driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/ul/div/li["+i+"]/div/div[1]/div[2]/div[1]/a"));
                WebElement followingButton = this.driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/ul/div/li["+i+"]/div/div[2]/button"));
                WebElement confirmUnfollow;

                followingButton.click();
                Utils.wait(3);

                confirmUnfollow = this.driver.findElement(By.xpath("/html/body/div[4]/div/div/div[3]/button[1]"));
                confirmUnfollow.click();

                System.out.println("Unfollowed " + followerName.getText());
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean followUsers(TypeStrategy type, int numUsers) {
        this.addUserLinks(type.getImageLinks(numUsers));

        for (String link : this.userLinks) {
            try {
                this.driver.navigate().to(link);
                System.out.println("Link = " + link);
                Utils.wait(3);
                WebElement followBtn = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/div/article/header/div[2]/div[1]/div[2]/button"));
                WebElement username = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/div/article/header/div[2]/div[1]/div[1]/h2/a"));

                if (followBtn.getText().equalsIgnoreCase("follow")) {
                    followBtn.click();
                    Utils.wait(3);
                    System.out.println("Followed " + username.getText());
                }
            } catch (Exception e) {
                //e.getMessage();
                return false;
            }
        }
        return true;
    }
}
