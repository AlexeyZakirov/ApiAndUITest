package api.models.bookStoreModels;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddListOfBooksModel{

    private String userId;

    private List<CollectionOfIsbnsItem> collectionOfIsbns;

}