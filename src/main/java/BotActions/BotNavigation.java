package BotActions;

import Interfaces.INavigation;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import Utility.*;

import java.util.List;

public class BotNavigation implements INavigation {

    WebDriver driver;
    public UserDetails userDetails;

    public BotNavigation(WebDriver driver, UserDetails userDetails) {
        this.driver = driver;
        this.userDetails = userDetails;
    }

    public BotNavigation(WebDriver driver){
        this.driver = driver;
    }

    public boolean goHome() {
        this.driver.navigate().to("https://www.instagram.com/");
        Utils.wait(3);
        return this.driver.getCurrentUrl()
                .equalsIgnoreCase("https://www.instagram.com/");
    }

    public boolean goToProfile() {
        this.driver.navigate().to("https://www.instagram.com/" + userDetails.getUsername()+"/");
        return this.driver.getCurrentUrl()
                .equalsIgnoreCase("https://www.instagram.com/" + userDetails.getUsername() + "/");
    }

    public boolean goToExplorePage() {
        this.driver.navigate().to("https://www.instagram.com/explore/");
        return this.driver.getCurrentUrl()
                .equalsIgnoreCase("https://www.instagram.com/explore/");
    }

    public boolean goToUserPageToLike(String username) {
        this.driver.get("https://www.instagram.com/" + username);
        Utils.wait(4);

        try {
            WebElement notFoundConatiner = this.driver.findElement(By.xpath("/html/body/div/div[1]/div/div"));
        } catch (Exception e) {
            boolean exists = isPublicAccount(username);
            System.out.println("does page exist = " + exists);
            return exists;
        }
        System.out.println(username + "'s page doesnt exist!");
        return false;
    }

    public boolean goToUserPageToFollow(String username) {
        this.driver.get("https://www.instagram.com/" + username);
        Utils.wait(4);

        try {
            WebElement notFoundConatiner = this.driver.findElement(By.xpath("/html/body/div/div[1]/div/div"));
        } catch (Exception e) {
            return true;
        }
        System.out.println(username + "'s page doesnt exist!");
        return false;
    }

    public boolean isPublicAccount(String username) {
        try {
            List<WebElement> privateAccountConatiner = this.driver.findElements(By.xpath("//*[@id=\"react-root\"]/section/main/div/div/article/div[1]/div/*"));
            for(WebElement el : privateAccountConatiner) {
                String notFound = "This Account is Private";
                if(el.getText().equalsIgnoreCase(notFound)) {
                    System.out.println(username + "'s account is private. Request a follow before attempting to like their photos");
                    return false;
                }
            }
        } catch (NoSuchElementException e) {
            return true;
        }
        return true;
    }

    public boolean goToHashtag(String hashtag) {
        String formattedHashtag = "";

        if(hashtag.contains("#")) {
            formattedHashtag = hashtag.substring(1);
        } else {
            formattedHashtag = hashtag;
            hashtag = "#" + hashtag;
        }

        try {
            WebElement input = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/nav/div[2]/div/div/div[2]/input"));
            Utils.wait(3);
            input.sendKeys(hashtag);

            Utils.wait(2);

            WebElement found = this.driver.findElement(By.cssSelector("a[href='/explore/tags/"+ formattedHashtag + "/'"));
            found.click();
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(hashtag + "does not exist.");
            return false;
        }
        return true;
    }
}
