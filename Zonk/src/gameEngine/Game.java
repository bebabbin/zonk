/**
 * 
 */
package gameEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;

import design.*;
import items.Gherkin;

/**
 * @author MK
 *
 */
public class Game {
	
	//private WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE; // A simpler tokenizer
	private Tokenizer tokenizer;
	//private POSTaggerME tagger;
	
	private Map<String, List<String>> verbMap;
	
	private StringBuilder log;
	
	public Game() throws IOException {
		log = new StringBuilder();
		/*
		InputStream posTrainingData = new FileInputStream("en-pos-maxent.bin");
		POSModel posModel = new POSModel(posTrainingData);
		this.tagger = new POSTaggerME(posModel);
		*/
		
		//tokenizer = WhitespaceTokenizer.INSTANCE; // A simpler tokenizer. Not smart enough.
		
		InputStream tokenTrainingData = new FileInputStream("en-token.bin");
		TokenizerModel tokenModel = new TokenizerModel(tokenTrainingData);
		this.tokenizer = new TokenizerME(tokenModel);
		
		loadVerbMap();
	}
	
	private void loadVerbMap() {
		verbMap = new HashMap<String, List<String>>();
		verbMap.put("inspect", Arrays.asList("look"));
		verbMap.put("chuck", Arrays.asList("throw", "lob", "toss", "hurl"));
		verbMap.put("take", Arrays.asList("pick", "grab"));
		verbMap.put("equip", Arrays.asList("hold", "wear", "wield"));
	}
	
	public String parse(String command) {
		// Tokenize the command
		String[] tokens = tokenizer.tokenize(command);
		
		// Tag the tokens with what they probably are (NN means noun, for example)
		//String[] tags = tagger.tag(tokens);
		
		// Look for special commands
		if (command.trim().equalsIgnoreCase("clear")) {
			log = new StringBuilder();
			return Player.getInstance().getCurrentArea().getDescription();
		}
		
		for (String token : tokens) {
			if (token.equalsIgnoreCase("say")) {
				String message = command.substring(command.toLowerCase().indexOf("say ") + 4);
				return say(message);
			}
			
			// etc.
		}
		
		// From here, assume they want to do something to an item or entity
		
		List<String> verbs = new ArrayList<>();
		List<Item> items = new ArrayList<>();
		List<Entity> entities = new ArrayList<>();
		
		// For each word
		for (String word : tokens) {
			// Look for verbs
			for (String verb : verbMap.keySet()) {
				if (word.equalsIgnoreCase(verb) || verbMap.get(verb).contains(word.toLowerCase())) {
					verbs.add(verb);
				}
			}
			
			// Look for items
			for (Item item : Player.getInstance().getEquipped()) {
				if (item.getName().equalsIgnoreCase(word)) {
					items.add(item);
				}
			}
			
			for (Item item : Player.getInstance().getInventory()) {
				if (item.getName().equalsIgnoreCase(word)) {
					items.add(item);
				}
			}
			
			for (Item item : Player.getInstance().getCurrentArea().getItems()) {
				if (item.getName().equalsIgnoreCase(word)) {
					items.add(item);
				}
			}
			
			// Look for entities
			for (Entity entity : Player.getInstance().getCurrentArea().getEntities()) {
				if (entity.getName().equalsIgnoreCase(word) || entity.getNicknames().contains(word)) {
					entities.add(entity);
				}
			}
		}
		
		// Check for invalid commands
		if (verbs.size() > 1) {
			return "I can only do one thing at a time!";
		}
		
		// Check cases with 1 verb
		if (verbs.size() == 1) {
			String verb = verbs.get(0);
			// If there are no items, try stuff with entities
			if (items.size() == 0) {
				if (entities.size() == 0) {
					// Look for more special commands
					if (verb.equals("inspect")) {
						return Player.getInstance().getCurrentArea().getDescription();
					}
					
					// Nothing to do anything with
					return "What do you want to " + verb + "?";
				}
				if (entities.size() == 1) {
					return executeVerb(entities.get(0), verb);
				}
				if (entities.size() == 2) {
					return executeVerb(entities.get(0), verb, entities.get(1));
				}
				if (entities.size() > 2) {
					return "That's too complicated.";
				}
			}
			
			// If there are no entities, try stuff with items.
			if (entities.size() == 0) {
				if (items.size() == 0) {
					// Nothing to do anything with
					return "What do you want to " + verb + "?";
				}
				if (items.size() == 1) {
					return executeVerb(items.get(0), verb);
				}
				if (items.size() == 2) {
					return executeVerb(items.get(0), verb, items.get(1));
				}
				if (items.size() > 2) {
					return "That's too complicated.";
				}
			}
			
			// Exactly 1 item and 1 entity, assume use item on entity.
			if (items.size() == 1 && entities.size() == 1) {
				return executeVerb(items.get(0), verb, entities.get(0));
			}
			
			// Too many things
			return "That's too complicated.";
		}
		
		// Check if they said Gherkin:
		Gherkin gherkin = new Gherkin();
		if (items.contains(gherkin)) {
			return gherkin.inspect();
		}
		
		// Didn't find anything to do.
		return "I don't know what that means.";
	}

	private String say(String message) {
		return "Not implemented";
	}

	public String executeVerb(Object indirectObject, String verb, Object directObject) {
		// indirectObject.verb(directObject);
		try {
			if (directObject == null) {
				Method method = indirectObject.getClass().getMethod(verb);
				
				return method.invoke(indirectObject).toString();
			}
			else {
				Method method = indirectObject.getClass().getMethod(verb, Object.class);
				
				return method.invoke(indirectObject, directObject).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "You can't do that!";
	}
	
	public String executeVerb(Object indirectObject, String verb) {
		// Implement later: indirectObject.verb(directObject);
		return executeVerb(indirectObject, verb, null);
	}

	public StringBuilder getLog() {
		return log;
	}

	public void setLog(StringBuilder log) {
		this.log = log;
	}
}
