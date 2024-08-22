package com.project.springmysql.springmysqlproject.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.springmysql.springmysqlproject.domain.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person,Long>{
	boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    @Query("UPDATE Person p p.enabled = false WHERE p.id =:id")

    @Modifying
    void disablePerson(@Param("id") Long id);

}
