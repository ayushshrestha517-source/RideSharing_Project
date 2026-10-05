package org.example.ridesharingproject.dao;

import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dto.request.SignupRequestDto;
import org.example.ridesharingproject.dto.response.SignupResponseDto;
import org.example.ridesharingproject.entity.Role;
import org.example.ridesharingproject.entity.Users;
import org.example.ridesharingproject.mapper.UsersMapper;
import org.example.ridesharingproject.repository.RoleRepository;
import org.example.ridesharingproject.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsersDaoImpl implements UsersDao {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignupResponseDto register(SignupRequestDto dto) {


        if (usersRepository.findByUsername(dto.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Convert DTO to entity
        Users user = usersMapper.toEntity(dto);

        // Encode password
        user.setPassword(
                passwordEncoder.encode(dto.password())
        );

        // Find default role
        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() ->
                        new RuntimeException("Role not found")
                );


        user.setRoles(Set.of(role));
        Users savedUser = usersRepository.save(user);
        return usersMapper.toResponse(savedUser);
    }


}