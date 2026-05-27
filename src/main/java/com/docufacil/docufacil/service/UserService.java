package com.docufacil.docufacil.service;

import com.docufacil.docufacil.dto.UserDTO;
import com.docufacil.docufacil.model.Organization;
import com.docufacil.docufacil.model.User;
import com.docufacil.docufacil.repository.UserRepository;
import com.docufacil.docufacil.repository.OrganizationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder; 

    public UserService(UserRepository userRepository, OrganizationRepository organizationRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO createUser(UserDTO dto) {
        Organization org = organizationRepository.findById(dto.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("La organización con ID " + dto.getOrganizationId() + " no existe."));

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setRole(dto.getRole().toUpperCase());
        user.setOrganization(org);

        User savedUser = userRepository.save(user);

        dto.setId(savedUser.getId());
        dto.setPassword(null);
        return dto;
    }

    public List<UserDTO> getUsersByOrganization(Long orgId) {
        return userRepository.findByOrganizationId(orgId).stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole());
            dto.setOrganizationId(user.getOrganization().getId());
            return dto;
        }).collect(Collectors.toList());
    }
}