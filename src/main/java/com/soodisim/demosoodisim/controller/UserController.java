package com.soodisim.demosoodisim.controller;

import com.soodisim.demosoodisim.dto.UserDTO;
import com.soodisim.demosoodisim.model.User;
import com.soodisim.demosoodisim.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return service.getAllUsers()
                .stream()
                .map(u -> new UserDTO(
                        u.getUserId(),
                        u.getName(),
                        u.getEmail(),
                        u.getRole().name(),
                        u.getPoints()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        User u = service.getUserById(id);
        return new UserDTO(
                u.getUserId(),
                u.getName(),
                u.getEmail(),
                u.getRole().name(),
                u.getPoints()
        );
    }
}
