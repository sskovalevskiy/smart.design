package smart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import static smart.Constants.APPLICATION_URL;
import static smart.Constants.HOST_URL;

public class CalcPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public CalcPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 3);
    }

    @FindBy(css = "pre")
    public WebElement result;

    public void openCalcPage(String arithmeticOperationsPath) {
        driver.get(HOST_URL + APPLICATION_URL + arithmeticOperationsPath);
    }
}
