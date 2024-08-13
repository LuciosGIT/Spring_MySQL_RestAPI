package com.project.springmysql.springmysqlproject.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.springmysql.springmysqlproject.domain.Person;

public interface PersonRepository extends JpaRepository<Person,Long>{
	boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
