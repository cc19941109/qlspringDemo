package com.chen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chen.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
