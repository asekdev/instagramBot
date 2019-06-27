package LikeStrategy;

import Interfaces.LikeStrategy;
import org.openqa.selenium.*;

import java.util.ArrayList;

import Utility.*;


public class HashtagLiker implements LikeStrategy {

    private WebDriver driver;
    private ArrayList<String> imageLinks = new ArrayList<String>();

    public HashtagLiker(WebDriver driver) {
        this.driver = driver;
    }

    public HashtagLiker() {
    }

    public void addLink(String link) {
        this.imageLinks.add(link);
    }

    public ArrayList getImageLinks(int numPhotos) {
        JavascriptExecutor jse = (JavascriptExecutor) this.driver;
        jse.executeScript("window.scrollBy(0,3000)", "");

        int rows = GridCalculator.determineRows(numPhotos);
        int cols = 3;

        for (int i = 1; i <= rows; i++) {
            if (numPhotos / 3 < 1) {
                cols = GridCalculator.determineCols(rows);
            }

            for (int j = 1; j <= cols; j++) {
//                System.out.println("i is now = " + i);
//                System.out.println("j is now = " + j);
                try {
                    Utils.wait(3);
                    WebElement photo = this.driver.findElement(By.xpath(" //*[@id=\"react-root\"]/section/main/article/div[2]/div/div[" + i + "]/div[" + j + "]/a"));
                    String imageLink = photo.getAttribute("href");
                    this.addLink(imageLink);
                    numPhotos -= 1;
                    photo = null;
//                  System.out.println("num photos left " + numPhotos);
                    if (numPhotos == 0) {
                        break;
                    }
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
//                    return false;
                }
            }
        }
        return this.imageLinks;
    }
}