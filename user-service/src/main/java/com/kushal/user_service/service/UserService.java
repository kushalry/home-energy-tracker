package com.kushal.user_service.service;

import org.springframework.stereotype.Service;

import com.kushal.user_service.dto.UserDto;
import com.kushal.user_service.entity.User;
import com.kushal.user_service.repository.UserRespository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

    private final UserRespository userRespository;

    public UserService(UserRespository userRespository) {
        this.userRespository = userRespository;
    }

    public UserDto createUser(UserDto input) {

        final User createdUser = User.builder()
                .id(input.getId())
                .name(input.getName())
                .surname(input.getSurname())
                .email(input.getEmail())
                .address(input.getAddress())
                .alerting(input.isAlerting())
                .energyAlertingThreshold(input.getEnergyAlertingThreshold())
                .build();

        User savedUser = userRespository.save(createdUser);

        return toDto(savedUser);
    }

    public UserDto getUserById(Long id) {
        return userRespository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    public void updateUser(Long id, UserDto dto) {
        User user = userRespository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setAlerting(dto.isAlerting());
        user.setEnergyAlertingThreshold(dto.getEnergyAlertingThreshold());

        userRespository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRespository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRespository.delete(user);
    }

    private UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .address(user.getAddress())
                .alerting(user.isAlerting())
                .energyAlertingThreshold(user.getEnergyAlertingThreshold())
                .build();
    }
}
