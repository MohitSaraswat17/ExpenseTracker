package MSL.msl.ExpenseTracker.service;

import MSL.msl.ExpenseTracker.model.Expenses;
import MSL.msl.ExpenseTracker.repo.ExpensesRepo;
import MSL.msl.ExpenseTracker.dto.ExpenseResponse;
import MSL.msl.ExpenseTracker.dto.Response;
import MSL.msl.ExpenseTracker.enums.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Slf4j
public class ExpensesService {

    @Autowired
    ExpensesRepo repo;

    public ResponseEntity<Response> addExpenses(Expenses expenses){
        log.info("{}",expenses);

        if (expenses != null) {
            repo.save(expenses);
            return ResponseEntity.ok().body(new Response(ServiceCode.EXP01.getCode(), ServiceCode.EXP01.getMessage(), null));
        }
        return ResponseEntity.badRequest().body(new Response(ServiceCode.EXP02.getCode(), ServiceCode.EXP02.getMessage(), null));
    }

    // Get expenses by user in ExpenseResponse format
    public ResponseEntity<List<ExpenseResponse>> getExpensesByUser(int userId) {
        List<ExpenseResponse> expenses = repo.getExpenseDetails(userId);
        if (expenses.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(expenses);
    }

    // Get expenses by category (Optional, for category-based fetching if needed)
    // public ResponseEntity<List<Expenses>> getExpensesByCategory(int categoryId) {
    //     List<Expenses> expenses = repo.findByCategoryCategoryId(categoryId);
    //     if (expenses.isEmpty()) {
    //         return ResponseEntity.badRequest().body(null);
    //     }
    //     return ResponseEntity.ok().body(expenses);
    // }

    public ResponseEntity<Double> getTotalExpenseAmountByUserId(int userId) {
        Double totalExpenseAmount = repo.getTotalExpenseAmountByUserId(userId);
        if (totalExpenseAmount == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(totalExpenseAmount);
    }
    
}
