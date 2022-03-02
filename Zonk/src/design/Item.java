/**
 * 
 */
package design;

/**
 * @author MK
 *
 */
public abstract class Item {
	private String description;
	protected int health = 1;
	protected int damage = 1;
	
	public Item() {}
	public Item(String description) {
		this.description = description;
	}
	
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// Verbs!
	public String inspect() {
		return description;
	}
	
	public String take() {
		if (Player.getInstance().getCurrentArea().getItems().contains(this)) {
			Player.getInstance().getCurrentArea().getItems().remove(this);
			Player.getInstance().getInventory().add(this);
			return "You take the " + getName() + ".";
		}
		
		return "You can't take that.";
	}
	
	public String equip() {
		return "You can't equip that.";
	}
	
	public String hurt(int dmg) {
		health -= dmg;
		if (health <= 0) {
			return this.destroy() + " It has Died!";
		}
		return getName() + "has taken " + dmg + " damage.";
	}
	
	public String destroy() {
		if (Player.getInstance().getInventory().contains(this)) {
			Player.getInstance().getInventory().remove(this);
			return "The " + getName() + " has been removed from your inventory.";
		}
		else if (Player.getInstance().getEquipped().contains(this)) {
			Player.getInstance().getEquipped().remove(this);
			return "The " + getName() + " has been removed from your equipment.";
		}
		else if (Player.getInstance().getCurrentArea().getItems().contains(this)) {
			Player.getInstance().getCurrentArea().getItems().remove(this);
			return "The " + getName() + " has been destroyed.";
		}
		return "Couldn't find the " + getName() + " to destroy it.";
	}
	
	public String chuck(Object other) {
		if (Player.getInstance().getEquipped().contains(this)) {
			Player.getInstance().getEquipped().remove(this);
			Player.getInstance().getCurrentArea().getItems().add(this);
			return "The " + getName() + " clatters to the ground.";
		}
		else {
			return "You're not holding that.";
		}
	}
	
	public String chuck() {
		return "You need to specify a target.";
	}
}
