package com.my_finance.my_finance.Repository;

import com.my_finance.my_finance.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findByUserUserId(Integer userId);

}
