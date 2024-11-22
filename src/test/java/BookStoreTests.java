import api.models.*;
import helpers.WithLogin;
import org.junit.jupiter.api.Test;

public class BookStoreTests extends TestBase {
    @WithLogin
    @Test
    public void deleteBookTest() {

        profileApi.deleteAllBooksInProfile(userId);
        BooksItem book = bookStoreApi.selectRandomBook();
        bookStoreApi.addRandomBookToProfile(userId, book);
        profileApi.checkIsbnInProfileAndStoreIsEquals(userId, book);

        profilePage.openProfilePage()
                .checkBookInProfileByTitle(book.getTitle())
                .deleteBookInProfileByTitle(book.getTitle())
                .checkProfileHasNotBookByTitle(book.getTitle());
        profileApi.checkBookProfileIsEmpty(userId);
    }
}
