package de.olivergeisel;


import org.junit.jupiter.api.BeforeEach;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryExampleTest {

	@Autowired
	private MyRepository repository;


	@BeforeEach
	void setup() {
		var entity1 = new MyEntity("Test1");
		var entity2 = new MyEntity("Test2");
		var entity3 = new MyEntity("Test3");
		var entities = List.of(entity1, entity2, entity3);
		repository.saveAll(entities);
	}

}
