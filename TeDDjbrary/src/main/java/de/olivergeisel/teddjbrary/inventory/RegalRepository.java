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

package de.olivergeisel.teddjbrary.inventory;

import de.olivergeisel.teddjbrary.core.ISBN;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

import java.util.Optional;
import java.util.UUID;

public interface RegalRepository extends CrudRepository<Regal, UUID> {
	@Override
	Streamable<Regal> findAll();


	Optional<Regal> findByInhalt_Buecher_Isbn (ISBN isbn);

//region setter/getter
	@Query("""
			select r from Regal r where (
			(SELECT SUM(size(rb.buecher)) FROM RegalBrett rb WHERE rb MEMBER OF r.inhalt) < r.kapazitaet )""")
	Streamable<Regal> getNichtVolleRegale ();
//endregion

	@Query("select r from Regal r inner join r.inhalt.buecher buecher where buecher.id = ?1")
	Regal findByBuchId (UUID id);

}
