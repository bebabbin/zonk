package items;

import design.Entity;
import design.Item;
import design.Player;

public class Rock extends design.Item {
	
	public Rock() {
		this("It's a rock.");
		this.health = 5;
		this.damage = 2;
	}

	public Rock(String description) {
		super(description);
	}

	public String equip() {
		return Player.getInstance().equipItem(this);
	}
	
	public String chuck(Object other) {
		if (Player.getInstance().getEquipped().contains(this)) {
			Player.getInstance().getEquipped().remove(this);
			
			Player.getInstance().getCurrentArea().getItems().add(this);
			
			String message = "";
			// Check what the target is
			if (other instanceof Item) {
				Item otherItem = (Item) other;
				message += "You throw the rock.\n" + otherItem.hurt(damage);
			}
			else if (other instanceof Entity) {
				Entity entity = (Entity) other;
				message += "You throw the rock.\n" + entity.hurt(damage);
			}
			return message + " The " + getName() + " clatters to the ground.";
		}
		else {
			return "You're not holding that.";
		}
	}
}
