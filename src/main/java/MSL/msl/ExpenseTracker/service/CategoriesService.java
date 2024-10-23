package MSL.msl.ExpenseTracker.service;

import MSL.msl.ExpenseTracker.model.Categories;
import MSL.msl.ExpenseTracker.repo.CategoriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoriesService {
    @Autowired
    CategoriesRepo repo;
    public ResponseEntity<List<Categories>> getCategories() {
        List<Categories> categories=repo.findAll();
        if(categories.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    public Categories getCategoriesById(int id){
        return repo.findById(id).orElse(null);
    }
}
