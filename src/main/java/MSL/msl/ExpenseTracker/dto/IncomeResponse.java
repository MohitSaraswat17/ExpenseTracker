package MSL.msl.ExpenseTracker.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class IncomeResponse {
    private int incomeId;
    private String incomeSource;
    private BigDecimal incomeAmount;
    private LocalDateTime receivedAt;
    private String description;
    private LocalDateTime createdAt;
}
