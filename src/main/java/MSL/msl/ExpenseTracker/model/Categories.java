package MSL.msl.ExpenseTracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Categories {
    @Id
    private  int categoryId;
    private String categoryName;
}
