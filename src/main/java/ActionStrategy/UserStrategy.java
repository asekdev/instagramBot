package ActionStrategy;

import BotActions.BotNavigation;
import Interfaces.TypeStrategy;
import Utility.GridCalculator;
import Utility.Utils;
import org.openqa.selenium.*;

import java.util.ArrayList;

public class UserStrategy implements TypeStrategy {

    private WebDriver driver;
    private ArrayList<String> imageLinks = new ArrayList<String>();
    private BotNavigation botNav;
    private String username;

    public UserStrategy(WebDriver driver, String username) {
        this.driver = driver;
        this.botNav = new BotNavigation(this.driver);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addLink(String link) {
        this.imageLinks.add(link);
    }

    public ArrayList getImageLinks(int numPhotos) {
        boolean pageExsits = this.botNav.goToUserPage(this.getUsername(), "like");

//        System.out.println("page exists for image link = " + pageExsits);
        if (!pageExsits) {
            return this.imageLinks;
        }

        JavascriptExecutor jse = (JavascriptExecutor) this.driver;
//        int lengthToScroll = numPhotos * 160;
//        String scrollLength =  String.valueOf(lengthToScroll);
//
//        for(int i=1; i < numPhotos / 2; i++) {
//            Utils.wait(4);
//            jse.executeScript("window.scrollBy(0,"+scrollLength+")", "");
//        }

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
                    WebElement photo = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/div[2]/article/div[1]/div/div[" + i + "]/div[" + j + "]/a"));
                    String imageLink = photo.getAttribute("href");
                    this.addLink(imageLink);
//                    System.out.println("image link = " + imageLink);
                    numPhotos -= 1;
                    photo = null;
                    if (numPhotos == 0) {
                        break;
                    }
                } catch (NoSuchElementException e) {
                    e.printStackTrace();

                } catch (StaleElementReferenceException e) {
//                    System.out.println("cause stale");
                }
            }
        }
//        System.out.println("image link size = " + this.imageLinks.size());
        return this.imageLinks;
    }
}
