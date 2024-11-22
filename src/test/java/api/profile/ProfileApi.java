package api.profile;

import api.models.BooksItem;
import api.models.profileModels.AllBooksInProfileModel;
import io.qameta.allure.Step;

import static api.specs.BaseSpecs.requestWithToken;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProfileApi {


    @Step("Удалить все книги из профиля, отправив API запрос")
    public void deleteAllBooksInProfile(String userId){
        given(requestWithToken)
                .queryParam("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Step("Получить все книги, находящиеся в профиле, отправив API запрос")
    public AllBooksInProfileModel getAllBooksInProfile(String userId){
        return given(requestWithToken)
                .pathParam("userId", userId)
                .when()
                .get("/Account/v1/User/{userId}")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(AllBooksInProfileModel.class);
    }
    @Step("Проверить, что ISBN добавляемой книги в профиле соответствует ISBN книги из каталога, отправив API запрос")
    public void checkIsbnInProfileAndStoreIsEquals(String userId, BooksItem book){
        AllBooksInProfileModel addedBookInProfile = getAllBooksInProfile(userId);
        assertThat(addedBookInProfile.getBooks().get(0).getIsbn()).isEqualTo(book.getIsbn());
    }
    @Step("Проверить, что в профиле отсутствуют книги, отправив API запрос")
    public void checkBookProfileIsEmpty(String userId){
        AllBooksInProfileModel allBooksInProfile = getAllBooksInProfile(userId);
        assertThat(allBooksInProfile.getBooks().size()).isEqualTo(0);
    }
}
