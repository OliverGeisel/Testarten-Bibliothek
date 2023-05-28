package de.olivergeisel.teddjbrary.core;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import org.springframework.lang.Nullable;

import java.util.UUID;

public interface BuchRepository extends CrudRepository<Buch, UUID> {
	@Override
	Streamable<Buch> findAll();

	boolean existsByIsbn(ISBN isbn);

	long countDistinctByIsbnNot(@Nullable ISBN isbn);

	default long countDistinctBooks(){
		return countDistinctByIsbnNot(null);
	}

	@Query("SELECT COUNT(DISTINCT b.isbn) FROM Buch b")
	long countDistinctIsbn();

}
