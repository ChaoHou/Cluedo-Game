package cluedo.controller.action.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import cluedo.controller.action.Action;
import cluedo.controller.action.ActionHelper;
import cluedo.controller.connection.MasterConnection;
import cluedo.exception.IllegalRequestException;
import cluedo.model.Board;
import cluedo.model.Card;
import cluedo.model.Player;

/**
 * Server side Action.
 * will read the infomation from the connection
 * When executing, 
 * 
 * @author C
 *
 */
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
			try {
				ArrayList<Player> players = game.getPlayers();
				for(Player p:players){
					p.setStatus(Player.STATUS.WATCHING);
				}
				
				Player winner = game.getPlayer(connection.uid());
				winner.setStatus(Player.STATUS.WIN);
				winner.setString("You are winner");
			} catch (IllegalRequestException e) {
				e.printStackTrace();
			}
		}else{
			//update player's status to eliminated, and update player's message
			try {
							
				Player eliminate = game.getPlayer(connection.uid());
				eliminate.setStatus(Player.STATUS.ELIMINATED);
				
			} catch (IllegalRequestException e) {
				e.printStackTrace();
			}
		}
		
		ActionHelper.broadcast(connections, game);
	}

}
