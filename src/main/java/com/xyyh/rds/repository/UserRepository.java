package com.xyyh.rds.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyyh.rds.entity.User;

public interface UserRepository  extends JpaRepository<User, Long> {

}
