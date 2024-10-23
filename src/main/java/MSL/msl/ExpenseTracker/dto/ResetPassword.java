package MSL.msl.ExpenseTracker.dto;

import lombok.Data;

@Data
public class ResetPassword {
    private String newPassword;
    private String token;
}
