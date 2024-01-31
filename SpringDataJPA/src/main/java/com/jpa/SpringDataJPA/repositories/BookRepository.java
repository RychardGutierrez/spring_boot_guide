package com.jpa.SpringDataJPA.repositories;

import com.jpa.SpringDataJPA.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
