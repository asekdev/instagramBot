package BotActions;

import Interfaces.IAuth;
import Utility.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BotAuthentication implements IAuth {

    private WebDriver driver;
    private UserDetails userDetails;

    public BotAuthentication(WebDriver driver, UserDetails userDetails) {
        this.driver = driver;
        this.userDetails = userDetails;
    }

    public boolean isLoggedIn() {
        try {
            this.driver.navigate().to("https://www.instagram.com/" + userDetails.getUsername() + "/");
            WebElement loginButton = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/nav/div[2]/div/div/div[3]/div/span/a[1]/button"));
        } catch (NoSuchElementException e) {
            return true;
        }
        System.out.println("You are not logged in. Try login again.");
        return false;
    }

    public boolean login() {
        //navigate to login page
        this.driver.get("https://www.instagram.com/accounts/login/?source=auth_switcher");
        Utils.wait(5);
        try {

            WebElement usernameField = this.driver.findElement(By.cssSelector("input[name='username']"));
            usernameField.click();
            usernameField.sendKeys(this.userDetails.getUsername());

            Utils.wait(7);

            WebElement passwordField = this.driver.findElement(By.cssSelector("input[name='password']"));
            passwordField.click();
            passwordField.sendKeys(this.userDetails.getPassword());

            Utils.wait(8);

            WebElement loginButton = this.driver.findElement(By.cssSelector("button[type='submit']"));
            loginButton.click();

            Utils.wait(3);

            WebElement removeModal = this.driver.findElement(By.xpath("//button[text() = 'Not Now']"));
            removeModal.click();

        } catch (Exception e) {
            return false;
        }
        return loginSuccess();
    }

    public boolean loginSuccess() {
        try {
            WebElement errorMessage = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/article/div/div[1]/div/form/div[7]"));
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public boolean logout() {
        try {
            WebElement profile = this.driver.findElement(By.xpath("//a[@href='/" + this.userDetails.getUsername() + "/']"));
            profile.click();
            Utils.wait(4);

            WebElement settingsGear = this.driver.findElement(By.xpath("//span[@aria-label='Options']"));
            settingsGear.click();
            Utils.wait(5);

            WebElement logoutButton = this.driver.findElement(By.xpath("/html/body/div[3]/div/div/div/button[6]"));
            logoutButton.click();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
