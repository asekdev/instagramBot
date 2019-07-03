package ActionStrategy;

import BotActions.BotNavigation;
import Interfaces.TypeStrategy;
import Utility.GridCalculator;
import Utility.Utils;
import org.openqa.selenium.*;

import java.util.ArrayList;

public class ExploreStrategy implements TypeStrategy {

    private WebDriver driver;
    private ArrayList<String> imageLinks = new ArrayList<String>();
    private BotNavigation botNavigation;

    public ExploreStrategy(WebDriver driver) {
        this.driver = driver;
        this.botNavigation = new BotNavigation(this.driver);
    }

    public void addLink(String link) {
        this.imageLinks.add(link);
    }

    public ArrayList getImageLinks(int numPhotos) {
        this.botNavigation.goToExplorePage();
        JavascriptExecutor jse = (JavascriptExecutor) this.driver;
        int lengthToScroll = numPhotos * 180;
        String scrollLength =  String.valueOf(lengthToScroll);
        System.out.println("scrolling by " + scrollLength + "pixels");
        jse.executeScript("window.scrollBy(0,"+scrollLength+")", "");

        for(int i=1; i < numPhotos / 2; i++) {
            Utils.wait(4);
            jse.executeScript("window.scrollBy(0,"+scrollLength+")", "");
        }

        int rows = GridCalculator.determineRows(numPhotos);
        int cols = 3;

        for (int i = 1; i <= rows; i++) {
            if (numPhotos / 3 < 1) {
                cols = GridCalculator.determineCols(rows);
            }

            if(i % 10 == 0){
                jse.executeScript("window.scrollBy(0,1500)", "");
            }

            for (int j = 1; j <= cols; j++) {
                try {
//                    Utils.wait(3);
                    WebElement photo = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/article/div[1]/div/div["+i+"]/div["+j+"]/a"));
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
