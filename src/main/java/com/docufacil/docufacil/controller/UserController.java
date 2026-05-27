package com.docufacil.docufacil.controller;

import com.docufacil.docufacil.dto.UserDTO;
import com.docufacil.docufacil.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200") 
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }


    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<UserDTO>> getUsersByOrganization(@PathVariable Long orgId) {
        List<UserDTO> users = userService.getUsersByOrganization(orgId);
        return ResponseEntity.ok(users);
    }
}