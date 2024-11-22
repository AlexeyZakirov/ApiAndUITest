package api.models.profileModels;

import java.util.List;

import api.models.BooksItem;
import lombok.Data;

@Data
public class AllBooksInProfileModel{

    private List<BooksItem> books;

    private String userId;

    private String username;

}