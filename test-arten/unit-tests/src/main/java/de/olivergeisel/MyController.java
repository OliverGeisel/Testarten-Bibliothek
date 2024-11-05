package de.olivergeisel;


public class MyController {

	private final MyService service;

	public MyController(MyService service) {this.service = service;}

	public String createEntity(MyEntity entity) {
		service.save(entity);
		return "entity created: ";
	}
}
