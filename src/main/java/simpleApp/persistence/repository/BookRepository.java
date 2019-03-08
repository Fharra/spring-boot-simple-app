package simpleApp.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import simpleApp.persistence.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
	List<Book> findByTitle(String title);
}
