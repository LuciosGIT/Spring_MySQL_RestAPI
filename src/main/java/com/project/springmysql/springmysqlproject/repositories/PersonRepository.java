package com.project.springmysql.springmysqlproject.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.springmysql.springmysqlproject.domain.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person,Long>{
	boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);


    @Modifying
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id =:id")
    void disablePerson(@Param("id") Long id);

    @Query("SELECT p FROM Person p WHERE p.name LIKE LOWER(CONCAT ('%',:name,'%'))")
    Page<Person> findPersonsByName(@Param("name") String name, Pageable pageable);

}
