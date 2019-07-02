package LikeStrategy;

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
        this.botNav.goToHashtag(this.getHashtag());

        int lengthToScroll = numPhotos * 150;
        String scrollLength = String.valueOf(lengthToScroll);

        for (int i = 1; i < numPhotos / 3; i++) {
            Utils.wait(4);
            jse.executeScript("window.scrollBy(0," + scrollLength + ")", "");
        }

        int rows = GridCalculator.determineRows(numPhotos);
        int cols = 3;

        for (int i = 1; i <= rows; i++) {
            if (numPhotos / 3 < 1) {
                cols = GridCalculator.determineCols(rows);
            }

            if (i % 10 == 0) {
                jse.executeScript("window.scrollBy(0,1500)", "");
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
                    e.printStackTrace();
                } catch (StaleElementReferenceException e) {
                    System.out.println("cause stale");
                }
            }
        }
        return this.imageLinks;
    }
}