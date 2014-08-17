package cluedo.controller.action.server;

import java.io.DataInputStream;
import java.io.IOException;

import cluedo.controller.action.Action;
import cluedo.controller.connection.MasterConnection;
import cluedo.model.Board;
import cluedo.model.Card;

public class Accusation implements MasterAction{

private MasterConnection connection;
	
	private Card.CHARACTER character;
	private Card.WEAPON weapon;
	private Card.ROOM room;
	
	public Accusation(MasterConnection master){
		connection = master;
		
		try {
			System.out.println("Server Suggestion recieved");
			DataInputStream input = connection.getInput();
			
			character = Card.CHARACTER.values()[input.readInt()];
			weapon = Card.WEAPON.values()[input.readInt()];
			room = Card.ROOM.values()[input.readInt()];
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void execute(MasterConnection[] connections,Board game) {
		Card[] solution = game.getSolution();
		
		Card.CHARACTER sCharacter = Card.CHARACTER.valueOf(solution[0].getName());
		Card.WEAPON sWeapon = Card.WEAPON.valueOf(solution[1].getName());
		Card.ROOM sRoom = Card.ROOM.valueOf(solution[2].getName());
		
		if(sCharacter.equals(character) && sWeapon.equals(weapon) && sRoom.equals(room)){
			//update current user's status to win
			//show popups(client side)
		}else{
			//update player's status to eliminated, and update player's message
		}
		
		
	}

}
