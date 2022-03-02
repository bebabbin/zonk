/**
 * 
 */
package design;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MK
 *
 */
public class Player extends Entity{

	private static Player instance = null;
	
	private List<Item> inventory;
	private List<Item> equipped;
	private Zone currentArea;
	
	public List<Item> getEquipped() {
		return equipped;
	}
	public List<Item> getInventory() {
		return inventory;
	}

	// Singleton!
	public static Player getInstance() {
		if (instance == null) {
			instance = new Player();
		}
		return instance;
	}
	
	private Player () {
		equipped = new ArrayList<Item>();
		inventory = new ArrayList<Item>();
	}
	
	public String setCurrentArea(Zone zone) {
		this.currentArea = zone;
		return zone.getDescription();
	}

	public Zone getCurrentArea() {
		return currentArea;
	}
	
	public String equipItem(Item item) {
		if (equipped.contains(item)) {
			return "You are already holding the " + item.getName() + ".";
		}
		else {
			if (inventory.contains(item)) {
				equipped.add(item);
				inventory.remove(item);
				return "You are now holding the " + item.getName() + ".";
			}
			else {
				return "You don't have that.";
			}
			
		}
	}
}
