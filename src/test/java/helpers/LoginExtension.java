package helpers;

import api.models.login.ResponseLoginModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import static api.authorization.AuthorizationApi.getAuthorization;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginExtension implements BeforeEachCallback {


    @Override
    public void beforeEach(ExtensionContext context){
        ResponseLoginModel loginModel = getAuthorization();
        String token = loginModel.getToken(),
        userId = loginModel.getUserId(),
        expires = loginModel.getExpires();

        open("/images/Toolsqa.jpg");
        getWebDriver().manage().addCookie(new Cookie("userID", userId));
        getWebDriver().manage().addCookie(new Cookie("expires", expires));
        getWebDriver().manage().addCookie(new Cookie("token", token));
    }
}
