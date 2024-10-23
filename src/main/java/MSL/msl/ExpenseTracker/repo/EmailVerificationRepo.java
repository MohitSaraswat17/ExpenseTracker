package MSL.msl.ExpenseTracker.repo;

import MSL.msl.ExpenseTracker.model.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationRepo extends JpaRepository<EmailVerificationToken,Long> {
//    @Query("SELECT t from EmailVerificationToken t WHERE t.token=token")
    EmailVerificationToken findByToken(String token);
}
