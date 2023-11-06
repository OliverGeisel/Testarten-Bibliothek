/*
 * Copyright 2023 Oliver Geisel
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.olivergeisel.teddjbrary.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;

import java.util.UUID;

public interface BuchRepository extends CrudRepository<Buch, UUID> {

	@Override
	Streamable<Buch> findAll();

	Page<Buch> findAll (Pageable pageable);

	Streamable<Buch> findByIsbn (ISBN isbn);

	@Query("SELECT count (DISTINCT b.isbn) FROM Buch b")
	long countDistinctBucherByIsbn ();

	Streamable<Buch> findByAutorContains (String autor);

	@Query("SELECT count(distinct b.isbn) FROM Buch b WHERE ?1 like b.autor")
	long countBuecherFromContainingAutor (String autor);

	Streamable<Buch> findByTitelContains (String titel);

	Streamable<Buch> findByAusgeliehenAndIsbn (boolean ausgeliehen, ISBN isbn);

	@Query("SELECT b FROM Buch b WHERE b.autor = :autor GROUP BY b.isbn having count (distinct b.isbn) >=1"
		   + "ORDER BY b.titel")
	Streamable<Buch> findAlleDistinctBuecherVon_Autor_Titel_Sortiert (@Param("autor") String autor);
}

