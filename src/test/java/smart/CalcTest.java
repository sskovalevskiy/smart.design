package smart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static smart.Constants.WEB_DRIVER_CHROME;
import static smart.Constants.WEB_DRIVER_PATH_CHROME;

public class CalcTest {
    public WebDriver driver;
    private CalcPage calcPage;


    @BeforeSuite
    public void setUp() {
        System.setProperty(WEB_DRIVER_CHROME, WEB_DRIVER_PATH_CHROME);
    }

    @BeforeTest
    public void initializeWebDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
        calcPage = PageFactory.initElements(driver, CalcPage.class);
    }

    @DataProvider
    public static Object[][] checkOperations() {
        return new Object[][]{
                {"/+1/+2", 3},
                {"/+1/-2", -1},
                {"/+1/*2", 2}
        };
    }

    @Test(dataProvider = "checkOperations")
    public void TC1(String input, int result) {
//        Проверить операций + / - / *
        calcPage.openCalcPage(input);
        assertEquals(calcPage.result.getText(), String.valueOf(result));
    }

    @DataProvider
    public static Object[][] checkOperationsWithNull() {
        return new Object[][]{
                {"/+1/+0", 1},
                {"/+1/-0", 1},
                {"/+1/*0", 0}
        };
    }

    @Test(dataProvider = "checkOperationsWithNull")
    public void TC2(String input, int result) {
//        Проверить операции с 0
        calcPage.openCalcPage(input);
        assertEquals(calcPage.result.getText(), String.valueOf(result));
    }

    @DataProvider
    public static Object[][] checkOperationsWithNegative() {
        return new Object[][]{
                {"/-1/+2", 1},
                {"/-1/-2", -3},
                {"/-1/*2", -2},
                {"/-1/+-2", -3},
                {"/-1/--2", 1},
                {"/-1/*-2", 2}
        };
    }

    @Test(dataProvider = "checkOperationsWithNegative")
    public void TC3(String input, int result) {
//        Проверить операции с отрицательными числами
        calcPage.openCalcPage(input);
        assertEquals(calcPage.result.getText(), String.valueOf(result));
    }


    @DataProvider
    public static Object[][] checkForOverflow() {
        return new Object[][]{
                {"/-128/-1", "-129"},
                {"/+127/+1", "128"},
                {"/+111/*111", "12321"},
                {"/-32768/-1", "-32769"},
                {"/+32767/+1", "32768"},
                {"/+1111/*1111", "1234321"},
                {"/-2147483648/-1", "-2147483649"},
                {"/+2147483647/+1", "2147483648"},
                {"/+111111/*111111", "12345654321"}
        };
    }

    @Test(dataProvider = "checkForOverflow")
    public void TC4(String input, String result) {
//        Проверить на переполнение
        calcPage.openCalcPage(input);
        assertEquals(calcPage.result.getText(), result);
    }

    @Test
    public void TC5() {
//        Проверка порядка операций
        calcPage.openCalcPage("/+1/-2/*5");
        assertEquals(calcPage.result.getText(), -9);
    }

    @DataProvider
    public static Object[][] checkIncorrectValues() {
        return new Object[][]{
                {"/+1/+1.1", "unsupported operation"},
                {"/+1/+1,1", "unsupported operation"},
                {"/+a/+b", "unsupported operation"},
                {"/+1//+2", "unsupported operation"}
        };
    }

    @Test(dataProvider = "checkIncorrectValues")
    public void TC6(String input, String result) {
//        Ввод некорректных значений
        calcPage.openCalcPage(input);
        assertEquals(calcPage.result.getText(), result);
    }

    @Test
    public void TC7() {
//        Проверка ответа при превышении длины URL запроса (Всего символов: 7676)
        calcPage.openCalcPage("/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1/+1");
        assertEquals(calcPage.result.getText(), "unsupported operation");
    }

    @AfterTest
    public void close() {
        driver.close();
    }
}