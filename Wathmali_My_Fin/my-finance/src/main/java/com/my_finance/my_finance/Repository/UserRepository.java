package com.my_finance.my_finance.Repository;

import com.my_finance.my_finance.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

}
