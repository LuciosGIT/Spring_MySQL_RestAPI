package com.project.springmysql.springmysqlproject.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.springmysql.springmysqlproject.domain.User;

public interface UserRepository extends JpaRepository<User,Long>{
	boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
