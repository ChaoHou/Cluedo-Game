package cluedo.controller.action.server;

import java.io.DataInputStream;
import java.io.IOException;

import cluedo.controller.connection.MasterConnection;
import cluedo.model.Board;
import cluedo.model.Card;


public class Suggestion implements MasterAction{

	private MasterConnection connection;
	
	private Card.CHARACTER character;
	private Card.WEAPON weapon;
	private Card.ROOM room;
	
	public Suggestion(MasterConnection master){
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

		assert(connections != null);
		assert(game != null);
		
		//set the suggestion to board
		//update player's status
		
	}


}
