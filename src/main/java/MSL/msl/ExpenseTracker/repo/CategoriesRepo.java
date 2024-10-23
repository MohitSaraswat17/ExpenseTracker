package MSL.msl.ExpenseTracker.repo;

import MSL.msl.ExpenseTracker.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepo extends JpaRepository<Categories,Integer> {
}
