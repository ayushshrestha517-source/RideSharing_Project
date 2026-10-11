package org.example.ridesharingproject.dao;

import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dto.request.LoginRequestDto;
import org.example.ridesharingproject.dto.request.SelectRoleRequestDto;
import org.example.ridesharingproject.dto.request.SignupRequestDto;
import org.example.ridesharingproject.dto.response.LoginResponseDto;
import org.example.ridesharingproject.dto.response.SelectRoleResponseDto;
import org.example.ridesharingproject.dto.response.SignupResponseDto;
import org.example.ridesharingproject.entity.Passenger;
import org.example.ridesharingproject.entity.Role;
import org.example.ridesharingproject.entity.Users;
import org.example.ridesharingproject.enums.UserRole;
import org.example.ridesharingproject.exception.AlreadyExistsException;
import org.example.ridesharingproject.exception.BadRequestException;
import org.example.ridesharingproject.exception.ResourceNotFoundException;
import org.example.ridesharingproject.mapper.UsersMapper;
import org.example.ridesharingproject.repository.PassengerRepository;
import org.example.ridesharingproject.repository.RoleRepository;
import org.example.ridesharingproject.repository.UsersRepository;
import org.example.ridesharingproject.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersDaoImpl implements UsersDao {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final RoleRepository roleRepository;
    private final PassengerRepository passengerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public SignupResponseDto register(SignupRequestDto dto) {

        if (usersRepository.findByUsername(dto.username()).isPresent()) {
            throw new AlreadyExistsException("Username already exists");
        }

        if (usersRepository.findByEmail(dto.email()).isPresent()) {
            throw new AlreadyExistsException("Email already exists");
        }
        Users user = usersMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.password()));

        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        user.setRoles(new HashSet<>());
        user.getRoles().add(role);
        Users savedUser = usersRepository.save(user);


        if (passengerRepository.existsByUserId(user.getId())) {
            throw new AlreadyExistsException("Passenger profile already exists");
        }

        Passenger passenger = new Passenger();
        passenger.setUser(user);
        passenger.setActive(true);

        passengerRepository.save(passenger);

        return usersMapper.toResponse(savedUser);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.username(),
                        loginRequestDto.password()
                )
        );

        Users users = usersRepository.findByUsername(loginRequestDto.username()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Set<String> roles = users.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        String token = jwtUtil.generateToken(
                users.getUsername(),
                roles
        );

        return new LoginResponseDto(token, roles);
    }

    @Override
    public SelectRoleResponseDto selectRole(SelectRoleRequestDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (dto.role() == UserRole.PASSENGER) {
            Role passengerRole = roleRepository.findByName("ROLE_PASSENGER").orElseThrow(() -> new ResourceNotFoundException("Passenger role not found"));

            user.getRoles().add(passengerRole);
            usersRepository.save(user);


            return new SelectRoleResponseDto(
                    "ROLE_PASSENGER",
                    "Passenger role assigned successfully."
            );
        }

        if (dto.role() == UserRole.DRIVER) {
            return new SelectRoleResponseDto(
                    "DRIVER_APPLICATION",
                    "Please submit your driver documents."
            );
        }

        throw new BadRequestException("Invalid role selection.");

    }
}