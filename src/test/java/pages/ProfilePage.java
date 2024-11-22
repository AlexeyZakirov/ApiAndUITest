package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProfilePage {
    public final String profileEndpoint = "/profile";
    private final SelenideElement profileTable = $(".rt-tbody"),
            smallModalOkButton = $("#closeSmallModal-ok");
    private final String profileTableRow = ".rt-tr",
            deleteButton = "#delete-record-undefined";

    @Step("Открыть страницу профиля")
    public ProfilePage openProfilePage(){
        open(profileEndpoint);
        return this;
    }

    @Step("Проверить, что в профиле находится книга с названием {0}")
    public ProfilePage checkBookInProfileByTitle(String title) {
        profileTable.shouldHave(text(title));
        return this;
    }

    @Step("Удалить книгу с названием {0}")
    public ProfilePage deleteBookInProfileByTitle(String title) {
        profileTable.$(byText(title)).closest(profileTableRow).$(deleteButton).click();
        smallModalOkButton.click();
        return this;
    }

    @Step("Проверить, что в профиле отсутствует книга с названием {0}")
    public ProfilePage checkProfileHasNotBookByTitle(String title) {
        profileTable.shouldNotHave(text(title));
        return this;
    }
}
