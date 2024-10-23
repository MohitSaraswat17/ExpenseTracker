package MSL.msl.ExpenseTracker.service;

import MSL.msl.ExpenseTracker.dto.IncomeResponse;
import MSL.msl.ExpenseTracker.dto.Response;
import MSL.msl.ExpenseTracker.enums.ServiceCode;
import MSL.msl.ExpenseTracker.model.Income;
import MSL.msl.ExpenseTracker.repo.IncomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    @Autowired
    IncomeRepo repo;


    public ResponseEntity<Response> addIncome(Income income){
        if(income!=null){
            income.setReceivedAt(LocalDateTime.now());
            income.setCreatedAt(LocalDateTime.now());
            repo.save(income);
            return ResponseEntity.ok().body(new Response(ServiceCode.INC01.getCode(),ServiceCode.INC01.getMessage(),null));
        }
        return ResponseEntity.badRequest().body(new Response(ServiceCode.INC01.getCode(),ServiceCode.INC01.getMessage(),null));
    }
    public ResponseEntity<List<IncomeResponse>> getIncomeByUserId(int userId) {
     List<Income> incomes = repo.getIncomeDetails(userId);
    if (incomes.isEmpty()) {
        return ResponseEntity.badRequest().body(null);
    }
    
    // Map Income entities to IncomeResponseDto
    List<IncomeResponse> incomeResponseDtos = incomes.stream()
            .map(income -> {
                IncomeResponse dto = new IncomeResponse();
                dto.setIncomeId(income.getIncomeId());
                dto.setIncomeSource(income.getIncomeSource());
                dto.setIncomeAmount(income.getIncomeAmount());
                dto.setReceivedAt(income.getReceivedAt());
                dto.setDescription(income.getDescription());
                dto.setCreatedAt(income.getCreatedAt());
                return dto;
            })
            .collect(Collectors.toList());

    return ResponseEntity.ok().body(incomeResponseDtos);
    }

    public ResponseEntity<Double> getTotalIncomeAmountByUserId(int userId){
        Double totalIncomeAmount = repo.getTotalIncomeAmountByUserId(userId);
        if (totalIncomeAmount == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(totalIncomeAmount);
    }
}
