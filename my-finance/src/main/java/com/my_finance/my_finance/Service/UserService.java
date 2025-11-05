package com.my_finance.my_finance.Service;

import com.my_finance.my_finance.DTO.UserDTO;
import com.my_finance.my_finance.Entity.User;
import com.my_finance.my_finance.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Integer userId) {
        return userRepository.findById(userId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public UserDTO createUser(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setLastSync(null);

        User saved = userRepository.save(user);
        return convertToDTO(saved);
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    public UserDTO loginUser(String username, String email, String passwordHash) {
        Optional<User> userOptional = userRepository.findByUsernameAndEmailAndPasswordHash(username, email, passwordHash);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return convertToDTO(user);
        } else {
            return null; // login failed
        }
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setLastSync(user.getLastSync());
        return dto;
    }
}
