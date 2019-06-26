package LikeStrategy;

import Exceptions.AlreadyLikedException;
import Interfaces.LikeStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class HashtagLiker implements LikeStrategy {

    private WebDriver driver;

    public HashtagLiker(WebDriver driver) {
        this.driver = driver;
    }

    public boolean likePhotos(int numPhotos) {
        for (int i = 1; i <= numPhotos; i++) {
            for (int j = 1; j <= 3; j++) {
                try {
                    String image = "//*[@id=\"react-root\"]/section/main/article/div[1]/div/div/div[1]/div[" + j + "]";
                    WebElement imageDiv = this.driver.findElement(By.xpath(image));
                    Actions actions = new Actions(driver);
                    actions.moveToElement(imageDiv).click().build().perform();

                    this.driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

                    //like the image
                    this.likeImage();

                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                    return false;
                } catch(AlreadyLikedException e) {
                    break;
                }
            }
        }

        return true;
    }

    private void likeImage() throws AlreadyLikedException  {
        try {
            System.out.println("tying to like photo ");
            WebElement photo = this.driver.findElement(By.xpath("//button[//span[aria-label='Like']]"));
            photo.click();
            Thread.sleep(3000);
            WebElement closeLikeModal = this.driver.findElement(By.xpath("/html/body/div[3]/button[1]"));
            closeLikeModal.click();

        } catch(Exception e) {
            throw new AlreadyLikedException("Already liked image");
        }
    }

}

//row1
//*[@id="react-root"]/section/main/article/div[1]/div/div/div[1]/div[1]
//*[@id="react-root"]/section/main/article/div[1]/div/div/div[1]/div[1]/a

//*[@id="react-root"]/section/main/article/div[1]/div/div/div[1]/div[2]
//*[@id="react-root"]/section/main/article/div[1]/div/div/div[1]/div[3]

//row2
//*[@id="react-root"]/section/main/article/div[1]/div/div/div[2]/div[1]
//*[@id="react-root"]/section/main/article/div[1]/div/div/div[2]/div[2]
//*[@id="react-root"]/section/main/article/div[1]/div/div/div[2]/div[3]

//row3
//*[@id="react-root"]/section/main/article/div[1]/div/div/div[3]/div[1]