package LikeStrategy;

import Interfaces.LikeStrategy;
import Utility.GridCalculator;
import Utility.Utils;
import org.openqa.selenium.*;

import java.util.ArrayList;

public class UserLiker implements LikeStrategy {

    private WebDriver driver;
    private ArrayList<String> imageLinks = new ArrayList<String>();

    public UserLiker(WebDriver driver) {
        this.driver = driver;
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
                try {
                    Utils.wait(3);
                    WebElement photo = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/div[3]/article/div[1]/div/div[" + i + "]/div[" + j + "]/a"));
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
