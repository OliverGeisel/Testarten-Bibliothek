package de.olivergeisel;

import java.util.Collection;

public class MyService {

	private final MyRepository repository;


	public MyService(MyRepository repository) {
		this.repository = repository;
	}

	public MyEntity save(MyEntity entity) {
		return repository.save(entity);
	}

	public Collection<MyEntity> saveAll(Collection<MyEntity> entities) {
		return repository.saveAll(entities);
	}


	public MyEntity findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity not found"));
	}

	public void delete(MyEntity entity) {
		repository.delete(entity);
	}


}
