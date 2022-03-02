package test;

import java.io.IOException;

import design.Player;
import design.Zone;
import entities.Goblin;
import gameEngine.Game;
import items.Rock;

public class TestAreaOne extends Zone {

	public TestAreaOne(String description) {
		super(description);
	}

	public static void main(String[] args) {
		Zone zone = new TestAreaOne("You are in Test Area One. It has a Goblin, and a Rock.");
		zone.getEntities().add(new Goblin());
		zone.getItems().add(new Rock());
		System.out.println(Player.getInstance().setCurrentArea(zone));
		
		Game game;
		try {
			game = new Game();
			System.out.println(game.parse("Look at the rock."));
			System.out.println(game.parse("Take the rock."));
			System.out.println(game.parse("Hold the rock."));
			System.out.println(game.parse("Throw the rock at the goblin."));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}