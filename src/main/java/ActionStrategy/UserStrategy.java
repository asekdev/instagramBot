package ActionStrategy;

import BotActions.BotNavigation;
import Interfaces.TypeStrategy;
import Utility.GridCalculator;
import Utility.Utils;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

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

    private int checkDivCount() {
        List<WebElement> divsOnPage = new ArrayList<>();
        try {
            divsOnPage = this.driver.findElements(By.xpath("//*[@id=\"react-root\"]/section/main/div/*"));
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return divsOnPage.size() - 1;
    }

    public ArrayList getImageLinks(int numPhotos) {
        boolean pageExsits = this.botNav.goToUserPage(this.getUsername(), "like");

        if (!pageExsits) {
            return this.imageLinks;
        }

        JavascriptExecutor jse = (JavascriptExecutor) this.driver;
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
                    WebElement photo = this.driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/div[" + checkDivCount() + "]/article/div[1]/div/div[" + i + "]/div[" + j + "]/a"));
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
//                    System.out.println("cause stale");
                }
            }
        }
        return this.imageLinks;
    }
}
