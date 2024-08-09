package com.project.springmysql.springmysqlproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.springmysql.springmysqlproject.domain.Books;

public interface BooksRepository extends JpaRepository<Books,Long>{

}
