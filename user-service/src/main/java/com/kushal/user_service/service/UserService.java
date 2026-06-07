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
                .id(input.id())
                .name(input.name())
                .surname(input.surname())
                .email(input.email())
                .address(input.address())
                .alerting(input.alerting())
                .energyAlertingThreshold(input.energyAlertingThreshold())
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

        user.setName(dto.name());
        user.setSurname(dto.surname());
        user.setEmail(dto.email());
        user.setAddress(dto.address());
        user.setAlerting(dto.alerting());
        user.setEnergyAlertingThreshold(dto.energyAlertingThreshold());

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
