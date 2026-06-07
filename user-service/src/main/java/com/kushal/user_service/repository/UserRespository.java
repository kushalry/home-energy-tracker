package com.kushal.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kushal.user_service.entity.User;

@Repository
public interface UserRespository extends JpaRepository<User, Long> {

}
