package com.financeapp.personal_finance_manager.entity;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserId(Long userId);
    List<Category> findByUserIdAndCategoryType(Long userId, Category.CategoryType categoryType);
    Optional<Category> findByUserIdAndCategoryName(Long userId, String categoryName);
    List<Category> findByUserIdAndIsSynced(Long userId, Boolean isSynced);
}
