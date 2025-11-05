package com.my_finance.my_finance.Repository;

import com.my_finance.my_finance.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsernameAndEmailAndPasswordHash(String username, String email, String passwordHash);
}
