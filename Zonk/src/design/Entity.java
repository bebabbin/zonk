/**
 * 
 */
package design;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MK
 */
public abstract class Entity {
	private String description;
	protected int health = 1;
	protected int damage = 1;
	private List<String> nicknames;
	
	public Entity() {
		this.nicknames = new ArrayList<String>();
	}
	
	public Entity(String description) {
		this();
		this.description = description;
	}
	
	public String getName() {
		return this.getClass().getSimpleName();
	}
	public List<String> getNicknames() {
		return nicknames;
	}
	public void setNicknames(List<String> nicknames) {
		this.nicknames = nicknames;
	}
	
	
	// Verbs!
	
	public String inspect() {
		return description;
	}
	
	public String hurt(int dmg) {
		health -= dmg;
		if (health <= 0) {
			return this.kill() + " It has Died!";
		}
		return getName() + "has taken " + dmg + " damage.";
	}
	
	public String kill() {
		if (Player.getInstance().getCurrentArea().getEntities().contains(this)) {
			Player.getInstance().getCurrentArea().getEntities().remove(this);
			return "The " + getName() + " has been removed from life.";
		}
		return "Couldn't find the " + getName() + " to kill it.";
	}

}
