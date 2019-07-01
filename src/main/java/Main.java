import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

    public static void main(String[] args){

        //setup the singleton

        boolean programRunning = true;

        //start the initial




    }

    public static WebDriver setupChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "/Users/723352/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver(); //re-add options as parameter
        return driver;
    }

}
