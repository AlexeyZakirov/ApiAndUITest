package api.bookStore;

import api.models.BooksItem;
import api.models.bookStoreModels.AddListOfBooksModel;
import api.models.bookStoreModels.ArrayOfBooksStoreModel;
import api.models.bookStoreModels.CollectionOfIsbnsItem;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;

import java.util.List;

import static api.specs.BaseSpecs.requestWithToken;
import static api.specs.BaseSpecs.requestWithoutToken;
import static io.restassured.RestAssured.given;

public class BookStoreApi {

    @Step("Получить все доступные книги из каталога, отправив API запрос")
    public ArrayOfBooksStoreModel getAllBooksInStore() {
        return given(requestWithoutToken)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .log().all()
                .extract().as(ArrayOfBooksStoreModel.class);
    }
    @Step("Выбрать случайную книгу из каталога")
    public BooksItem selectRandomBook(){
        ArrayOfBooksStoreModel arrayOfBooksStore = getAllBooksInStore();
        int bookIndex = new Faker().number().numberBetween(0, arrayOfBooksStore.getBooks().size() - 1);
        return arrayOfBooksStore.getBooks().get(bookIndex);
    }
    @Step("Добавить случайную книгу в профиль, отправив API запрос")
    public void addRandomBookToProfile(String userId, BooksItem book){
        CollectionOfIsbnsItem collectionOfIsbnsItem = new CollectionOfIsbnsItem(book.getIsbn());
        List<CollectionOfIsbnsItem> collectionOfIsbnsItemList = List.of(collectionOfIsbnsItem);
        AddListOfBooksModel addListOfBooksModel = new AddListOfBooksModel(userId, collectionOfIsbnsItemList);

        given(requestWithToken)
                .body(addListOfBooksModel)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .log().all()
                .statusCode(201);
    }
}
