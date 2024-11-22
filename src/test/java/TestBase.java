import api.bookStore.BookStoreApi;
import api.models.login.ResponseLoginModel;
import api.profile.ProfileApi;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.ProfilePage;

import java.util.Map;

import static api.authorization.AuthorizationApi.getAuthorization;

public class TestBase {
    private static ResponseLoginModel loginModel;
    protected String token = loginModel.getToken();
    protected String userId = loginModel.getUserId();
    protected String expires = loginModel.getExpires();

    protected final ProfileApi profileApi = new ProfileApi();
    protected final BookStoreApi bookStoreApi = new BookStoreApi();
    protected final ProfilePage profilePage = new ProfilePage();

    @BeforeAll
    public static void setUp() {
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.baseURI = "https://demoqa.com";
        loginModel = getAuthorization();

        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("version");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
        if (System.getProperty("remoteUrl") != null) {
            Configuration.remote = String.format("https://%s@%s/wd/hub",
                    System.getProperty("auth"), System.getProperty("remoteUrl"));
        }
    }

    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    public void tearDown() {
        if (Configuration.browser.equals("firefox")) {
            byte[] lastScreenshots = Attach.screenshotAs("Last screenshot");
            Attach.pageSource();
            Attach.addVideo();
        } else {
            Attach.screenshotAs("Last screenshot");
            Attach.pageSource();
            Attach.browserConsoleLogs();
            Attach.addVideo();
        }
        Selenide.closeWebDriver();
    }
}
