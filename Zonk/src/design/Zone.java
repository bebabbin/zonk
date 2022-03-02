package design;

import java.util.ArrayList;
import java.util.List;

import items.Gherkin;

/**
 * @author MK
 *
 */
public abstract class Zone {
	
	private List<Item> items;
	private List<Entity> entities;
	
	private String description;
	
	public Zone(String description) {
		this.description = description;
		this.items = new ArrayList<Item>();
		this.entities = new ArrayList<Entity>();
		this.items.add(new Gherkin());
	}

	public List<Item> getItems() {
		return items;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}