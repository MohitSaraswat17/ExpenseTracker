package MSL.msl.ExpenseTracker.controller;


import MSL.msl.ExpenseTracker.model.Expenses;
import MSL.msl.ExpenseTracker.service.ExpensesService;
import MSL.msl.ExpenseTracker.dto.ExpenseResponse;
import MSL.msl.ExpenseTracker.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eta")
public class ExpensesController {
    @Autowired
    ExpensesService service;

    @PostMapping("/expenses")
    public ResponseEntity<Response> addExpenses(@RequestBody Expenses expenses){
        return service.addExpenses(expenses);
    }

   @GetMapping("/expenses/userExpenses/{userId}")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByUser(@PathVariable int userId) {
        return service.getExpensesByUser(userId);
    }

    // @GetMapping("/expenses/category/{categoryId}")
    // public ResponseEntity<List<Expenses>> getExpensesByCategory(@PathVariable int categoryId) {
    //     return service.getExpensesByCategory(categoryId);
    // }

    @GetMapping("/expenses/total/{userId}")
    public ResponseEntity<Double> getTotalExpenseAmountByUserId(@PathVariable int userId) {
        return service.getTotalExpenseAmountByUserId(userId);
    }
}
