package MSL.msl.ExpenseTracker.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponse {
    private int expenseId;
    private BigDecimal expenseAmount;
    private LocalDateTime expenseDate;
    private String description;
    private int categoryId;
    private String categoryName; 

}
