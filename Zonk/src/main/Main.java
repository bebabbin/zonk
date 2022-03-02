package main;

import java.io.IOException;

import design.*;
import entities.Goblin;
import gameEngine.Game;
import items.*;
import test.TestAreaOne;

public class Main {

	public static void main(String[] args) {
		try {
			Game game = new Game();
			Screen screen = new Screen(game);
			
			screen.open(0, 0, 700, 700);
			
			Zone zone = new TestAreaOne("You are in Test Area One. It has a Goblin, and a Rock.");
			zone.getEntities().add(new Goblin());
			zone.getItems().add(new Rock());
			screen.restart(Player.getInstance().setCurrentArea(zone));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
