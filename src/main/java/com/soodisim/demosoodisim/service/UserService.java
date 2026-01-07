package com.soodisim.demosoodisim.service;

import com.soodisim.demosoodisim.model.RoleType;
import com.soodisim.demosoodisim.model.User;
import com.soodisim.demosoodisim.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor injection (BEST PRACTICE)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE USER (REGISTER)
    public User createUser(User user) {
        if (user.getRole() == null) {
            user.setRole(RoleType.NORMAL);
        }
        return userRepository.save(user);
    }

    // GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET USER BY ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // UPDATE USER
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setPreferredLanguage(updatedUser.getPreferredLanguage());

        return userRepository.save(existingUser);
    }

    // DELETE USER (ADMIN USE CASE)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
