import Interfaces.IAuth;
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
            WebElement user = this.driver.findElement(By.xpath("//a[@href='/" + this.userDetails.getUsername() + "/']"));
            System.out.println("//a[@href='/" + this.userDetails.getUsername() + "/']");
        } catch(NoSuchElementException e) {
            System.out.println("You are not logged in. Try login again.");
            return false;
        }
        return true;
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

//            WebElement removeModal = this.driver.findElement(By.xpath("//button[text() = 'Not Now']"));
//            removeModal.click();

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("login success outcome: " + loginSuccess());

        return loginSuccess();
    }

    public boolean loginSuccess() {
       String url = driver.getCurrentUrl();
       return url.equalsIgnoreCase("https://www.instagram.com/");
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
       } catch(NoSuchElementException e) {
           return false;
       }
       return true;
    }
}
