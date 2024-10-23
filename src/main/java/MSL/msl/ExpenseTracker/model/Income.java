package MSL.msl.ExpenseTracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int incomeId;  // Primary Key with Auto Increment

    // Foreign key to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    private String incomeSource;

    private BigDecimal incomeAmount;

    private LocalDateTime receivedAt;

    private String description;

    private LocalDateTime createdAt;

}
