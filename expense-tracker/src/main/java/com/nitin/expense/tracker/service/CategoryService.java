package com.nitin.expense.tracker.service;

import com.nitin.expense.tracker.entity.Category;
import com.nitin.expense.tracker.entity.User;
import com.nitin.expense.tracker.entity.Expense;
import com.nitin.expense.tracker.repository.CategoryRepository;
import com.nitin.expense.tracker.repository.ExpenseRepository;
import com.nitin.expense.tracker.repository.UserRepository;
import com.nitin.expense.tracker.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    public Category createCategory(Long userId, Category category) {
        User user = resolveUserForOperation(userId);
        category.setUser(user);
        return categoryRepository.save(category);
    }

    public List<Category> getUserCategories(Long userId) {
        requireOwnerOrAdmin(userId);
        return categoryRepository.findByUserId(userId);
    }

    public Category updateCategory(Long categoryId, Category updated) {
        Category existing = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found!"));
        requireOwnerOrAdmin(existing.getUser() != null ? existing.getUser().getId() : null);

        if (updated.getName() != null) {
            existing.setName(updated.getName());
        }
        if (updated.getDescription() != null) {
            existing.setDescription(updated.getDescription());
        }

        return categoryRepository.save(existing);
    }

    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found!"));
        requireOwnerOrAdmin(category.getUser() != null ? category.getUser().getId() : null);

        List<Expense> expenses = expenseRepository.findByCategoryId(categoryId);
        if (!expenses.isEmpty()) {
            for (Expense expense : expenses) {
                expense.setCategory(null);
            }
            expenseRepository.saveAll(expenses);
        }

        categoryRepository.delete(category);
    }

    private User resolveUserForOperation(Long userId) {
        User current = getCurrentUser();
        if (SecurityUtil.hasRole("ROLE_ADMIN")) {
            return userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found!"));
        }
        if (!current.getId().equals(userId)) {
            throw new AccessDeniedException("Access denied");
        }
        return current;
    }

    private void requireOwnerOrAdmin(Long userId) {
        if (SecurityUtil.hasRole("ROLE_ADMIN")) {
            return;
        }
        if (userId == null) {
            throw new AccessDeniedException("Access denied");
        }
        User current = getCurrentUser();
        if (!current.getId().equals(userId)) {
            throw new AccessDeniedException("Access denied");
        }
    }

    private User getCurrentUser() {
        String username = SecurityUtil.currentUsername();
        if (username == null) {
            throw new AccessDeniedException("Not authenticated");
        }
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }
}
