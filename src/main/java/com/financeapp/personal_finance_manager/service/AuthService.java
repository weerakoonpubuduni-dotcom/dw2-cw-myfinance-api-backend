package com.financeapp.personal_finance_manager.service;

import com.financeapp.personal_finance_manager.entity.Category;
import com.financeapp.personal_finance_manager.entity.CategoryRepository;
import com.financeapp.personal_finance_manager.entity.User;
import com.financeapp.personal_finance_manager.entity.UserRepository;
import com.financeapp.personal_finance_manager.model.JwtResponse;
import com.financeapp.personal_finance_manager.model.LoginRequest;
import com.financeapp.personal_finance_manager.model.MessageResponse;
import com.financeapp.personal_finance_manager.model.RegisterRequest;
import com.financeapp.personal_finance_manager.security.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Transactional
    public MessageResponse registerUser(RegisterRequest request) {
        // Check if username exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        // Check if email exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        // Create default categories
        createDefaultCategories(savedUser.getUserId());

        return new MessageResponse("User registered successfully!");
    }

    public JwtResponse authenticateUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new JwtResponse(
                jwt,
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName()
        );
    }

    private void createDefaultCategories(Long userId) {
        List<Category> defaultCategories = new ArrayList<>();

        // Expense categories
        defaultCategories.add(createCategory(userId, "Food & Dining", Category.CategoryType.EXPENSE));
        defaultCategories.add(createCategory(userId, "Transportation", Category.CategoryType.EXPENSE));
        defaultCategories.add(createCategory(userId, "Shopping", Category.CategoryType.EXPENSE));
        defaultCategories.add(createCategory(userId, "Entertainment", Category.CategoryType.EXPENSE));
        defaultCategories.add(createCategory(userId, "Bills & Utilities", Category.CategoryType.EXPENSE));
        defaultCategories.add(createCategory(userId, "Healthcare", Category.CategoryType.EXPENSE));

        // Income categories
        defaultCategories.add(createCategory(userId, "Salary", Category.CategoryType.INCOME));
        defaultCategories.add(createCategory(userId, "Freelance", Category.CategoryType.INCOME));
        defaultCategories.add(createCategory(userId, "Investment", Category.CategoryType.INCOME));

        categoryRepository.saveAll(defaultCategories);
    }

    private Category createCategory(Long userId, String name, Category.CategoryType type) {
        Category category = new Category();
        category.setUserId(userId);
        category.setCategoryName(name);
        category.setCategoryType(type);
        category.setIsSynced(false);
        return category;
    }
}
