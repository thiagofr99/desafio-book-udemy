package com.devthiagofurtado.repository;

import com.devthiagofurtado.data.model.Book;
import com.devthiagofurtado.data.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


}
