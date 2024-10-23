package MSL.msl.ExpenseTracker.repo;

import MSL.msl.ExpenseTracker.dto.ExpenseResponse;
import MSL.msl.ExpenseTracker.model.Expenses;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensesRepo extends JpaRepository<Expenses,Integer> {
    List<Expenses> findByUserUserId(int id);
    List<Expenses> findByCategoryCategoryId(int id);

   // Using the fully qualified class name in the HQL query
   @Query("SELECT new MSL.msl.ExpenseTracker.dto.ExpenseResponse(e.expenseId, e.expenseAmount, e.expenseDate, e.description ,e.category.categoryId, e.category.categoryName) " +
   "FROM Expenses e WHERE e.user.userId = :userId")
    List<ExpenseResponse> getExpenseDetails(@Param("userId") int userId);

    @Query("SELECT SUM(e.expenseAmount) FROM Expenses e WHERE e.user.userId = :userId")
    Double getTotalExpenseAmountByUserId(@Param("userId") int userId);
}
