package MSL.msl.ExpenseTracker.repo;

import MSL.msl.ExpenseTracker.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepo extends JpaRepository<Income,Integer> {
    List<Income> findByUserUserId(int userId);

    @Query("SELECT i FROM Income i WHERE i.user.userId = :userId")
    List<Income> getIncomeDetails(@Param("userId") int userId); 

    @Query("SELECT SUM(i.incomeAmount) FROM Income i WHERE i.user.userId = :userId")
    Double getTotalIncomeAmountByUserId(@Param("userId") int userId);
    
}
