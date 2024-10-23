package MSL.msl.ExpenseTracker.controller;

import MSL.msl.ExpenseTracker.service.IncomeService;
import MSL.msl.ExpenseTracker.dto.IncomeResponse;
import MSL.msl.ExpenseTracker.dto.Response;
import MSL.msl.ExpenseTracker.model.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/eta")
public class IncomeController {

    @Autowired
    IncomeService service;

    @PostMapping("/income")
    public ResponseEntity<Response> addIncome(@RequestBody Income income){
        return service.addIncome(income);
    }

    @GetMapping("/income/{userId}")
    public ResponseEntity<List<IncomeResponse>> getUserIncome(@PathVariable int userId) {
        return service.getIncomeByUserId(userId);
    }
    @GetMapping("/income/total/{userId}")
    public ResponseEntity<Double> getTotalIncomeAmountByUserId(@PathVariable int userId) {
        return service.getTotalIncomeAmountByUserId(userId);
    }
    
}
