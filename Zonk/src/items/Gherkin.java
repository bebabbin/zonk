package items;

public class Gherkin extends design.Item {
	
	public Gherkin() {
		this("You look at the gherkin and think about how tasty it is. "
				+ "You think about how delicious it would taste if you ate it raw. "
				+ "You think about how delicious it would be if you ate it cooked. "
				+ "You think about how delicious it would be if you ate it boiled");
	}
	
	public Gherkin(String description) {
		super(description);
	}
	
	// All Gherkins are created equal.
	public boolean equals(Object other) {
		if (other instanceof Gherkin) {
			return true;
		}
		return false;
	}
	
	public String take() {
		return inspect();
	}
	
	public String equip() {
		return inspect();
	}
	
	public String hurt(int dmg) {
		return inspect();
	}
	
	public String destroy() {
		return inspect();
	}
	
	public String chuck(Object other) {
		return inspect();
	}
	
	public String chuck() {
		return inspect();
	}
}