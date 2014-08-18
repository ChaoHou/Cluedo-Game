package cluedo.controller.action.server;

import java.io.DataInputStream;
import java.io.IOException;

import cluedo.controller.connection.MasterConnection;
import cluedo.exception.IllegalRequestException;
import cluedo.model.Board;
import cluedo.model.Card;
import cluedo.model.Card.TYPE;
import cluedo.model.Player;
import cluedo.model.Player.STATUS;
import cluedo.model.Room;


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
		
		try {
			Player player = game.getPlayer(connection.uid());
			
			Room[] rooms = game.getRooms();
			for(Room r:rooms){
				if(r.getName().equals(room)){
					if(!r.getCharactersInside().contains(player)){
						player.setString("Player should in the room you want to make the announcement");
						System.out.println("not in Room");
						return;
					}
				}
			}
			
			Card[] suggestion = {
					new Card(TYPE.CHARCTER, character.toString()),
					new Card(TYPE.WEAPON,weapon.toString()),
					new Card(TYPE.ROOM,room.toString()),
			};
			
			game.setSuggestion(suggestion);
			
			player.setStatus(STATUS.WAITING);
			
			
		} catch (IllegalRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//set the suggestion to board
		//update player's status
		
	}


}
