package BotActions;

import Interfaces.ILiker;
import Interfaces.TypeStrategy;
import Utility.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

public class BotLiker implements ILiker {

    private WebDriver driver;
    private ArrayList<String> imageLinks = new ArrayList<String>();

    public BotLiker(WebDriver driver) {
        this.driver = driver;
    }

    public void addImageLinks(ArrayList a) {
        this.imageLinks = a;
    }

    public int getImageLinkSize() {
        return this.imageLinks.size();
    }

    public boolean likePhotos(TypeStrategy strategy, int numPhotos) {
        this.addImageLinks(strategy.getImageLinks(numPhotos));

        if (this.getImageLinkSize() == 0) {
            return false;
        }

        for (String link : this.imageLinks) {
            try {
                this.driver.navigate().to(link);
                Utils.wait(3);
                WebElement like = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/div/article/div[2]/section[1]/span[1]/button"));
                WebElement likeSpan = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/div/article/div[2]/section[1]/span[1]/button/span"));
                WebElement username = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/div/article/header/div[2]/div[1]/div[1]/h2/a"));

                if (likeSpan.getAttribute("aria-label").equalsIgnoreCase("like")) {
                    like.click();
                    System.out.println("Liked " + username.getText() + "'s photo");
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }

        return true;
    }

}
