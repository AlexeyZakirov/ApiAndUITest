package api.models.bookStoreModels;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import api.models.BooksItem;

@Data
public class ArrayOfBooksStoreModel {

    @JsonProperty("books")
    private List<BooksItem> books;

}