package com.my_finance.my_finance.Controller;

import com.my_finance.my_finance.DTO.UserDTO;
import com.my_finance.my_finance.Entity.User;
import com.my_finance.my_finance.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    UserService userService;


//get all users
    @GetMapping (path = "/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //create user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    //get user by id
    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId) {
        UserDTO user = userService.getUserById(userId);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }


    @DeleteMapping(path="/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();

    }
}
