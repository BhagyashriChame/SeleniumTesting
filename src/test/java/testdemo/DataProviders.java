package testdemo;


import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {

    // Login Data Provider
    @DataProvider(name = "logInTestData", parallel = true)
    public static Object[][] logInData() throws IOException {
        return ExcelProvider1.getTestData("src/test/resources/RegisterData.xlsx", "LoginSheet");
    }

    // Register Data Provider
    @DataProvider(name = "registerData", parallel = false)
    public static Object[][] registerData() throws IOException {
        return ExcelProvider1.getTestData("src/test/resources/RegisterData.xlsx", "RegisterSheet");
    }
}

	