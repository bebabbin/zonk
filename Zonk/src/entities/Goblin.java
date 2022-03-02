package entities;

import design.Entity;

public class Goblin extends Entity {
	
	public Goblin() {
		this("It's just a little guy.");
	}
	
	public Goblin(String description) {
		super(description);
	}

}
