package de.olivergeisel;

import java.util.Collection;
import java.util.Optional;

public interface MyRepository {

	MyEntity save(MyEntity entity);

	Collection<MyEntity> saveAll(Collection<MyEntity> entities);

	Optional<MyEntity> findById(Long id);

	void delete(MyEntity entity);

}
