package ActionStrategy;

import BotActions.BotNavigation;
import Interfaces.TypeStrategy;
import org.openqa.selenium.*;

import java.util.ArrayList;

import Utility.*;


public class HashtagStrategy implements TypeStrategy {

    private WebDriver driver;
    private ArrayList<String> imageLinks = new ArrayList<String>();
    private BotNavigation botNav;
    private String hashtag;

    public HashtagStrategy(WebDriver driver, String hashtag) {
        this.driver = driver;
        this.botNav = new BotNavigation(this.driver);
        this.hashtag = hashtag;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public void addLink(String link) {
        this.imageLinks.add(link);
    }

    public ArrayList getImageLinks(int numPhotos) {
        JavascriptExecutor jse = (JavascriptExecutor) this.driver;
        boolean searchHashtag = this.botNav.goToHashtag(this.getHashtag());

        if (!searchHashtag) {
            return this.imageLinks;
        }

        Utils.scrollWindowDown(this.driver, numPhotos);

        int rows = GridCalculator.determineRows(numPhotos);
        int cols = 3;

        for (int i = 1; i <= rows; i++) {
            if (numPhotos / 3 < 1) {
                cols = GridCalculator.determineCols(rows);
            }

            for (int j = 1; j <= cols; j++) {
                try {
                    Utils.wait(3);
                    WebElement photo = this.driver.findElement(By.xpath(" //*[@id=\"react-root\"]/section/main/article/div[2]/div/div[" + i + "]/div[" + j + "]/a"));
                    String imageLink = photo.getAttribute("href");
                    this.addLink(imageLink);
                    numPhotos -= 1;
                    photo = null;
                    if (numPhotos == 0) {
                        break;
                    }
                } catch (NoSuchElementException e) {
                    //e.printStackTrace();
                    System.out.println("Cannot find image");
                } catch (StaleElementReferenceException e) {
                }
            }
        }
        return this.imageLinks;
    }
}