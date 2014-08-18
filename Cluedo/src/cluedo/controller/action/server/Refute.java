package cluedo.controller.action.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cluedo.controller.action.ActionHelper;
import cluedo.controller.connection.MasterConnection;
import cluedo.exception.IllegalRequestException;
import cluedo.model.Board;
import cluedo.model.Card;
import cluedo.model.Player;

/**
 * Server side Action
 * 
 * Will read the refute info from the connection
 * When executing
 * 
 * @author C
 *
 */
public class Refute implements MasterAction{

	private MasterConnection connection;
	
	private boolean hasCard;
	private Card.TYPE type;
	private byte[] nameBytes;
	
	public Refute(MasterConnection master){
		connection = master;
		
		try {
			
			System.out.println("Server Refute recieved");
			DataInputStream input = connection.getInput();
			//read the player info from client
			
			hasCard = input.readBoolean();
			
			if(hasCard){
				type = Card.TYPE.values()[input.readInt()];
				int length;
				length = input.readInt();
				nameBytes = new byte[length];
				input.read(nameBytes);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void execute(MasterConnection[] connections,Board game) {
		
		try {
			Player player = game.getPlayer(connection.uid());
			
			if(hasCard){
				//if the user didn't pass
				String name = new String(nameBytes,"UTF-8");
				System.out.println("User intend to refute");
				//Update player's message.
				//update player's status
				Card card = new Card(type,name);

				Card[] suggestion = game.getSuggestion();
				
				if((suggestion[0].getType().equals(card.getType())
						&& suggestion[0].getName().equals(card.getName()))
						|| (suggestion[0].getType().equals(card.getType())
						&& suggestion[0].getName().equals(card.getName()))
						||(suggestion[0].getType().equals(card.getType())
						&& suggestion[0].getName().equals(card.getName()))){
						
						//check if user can refute with the card
						
						player.setStatus(Player.STATUS.WATCHING);
						player.setString("You refute with:"+name);
						for(Player p:game.getPlayers()){
							if(p.getStatus().equals(Player.STATUS.WAITING)){
								p.setStatus(Player.STATUS.FINISHTURN);
							}
						}
					}
				
				
				
			}else{
				//if the user choose to pass
				if(player.getStatus().equals(Player.STATUS.REFUTING)){
					player.setStatus(Player.STATUS.FINISHREFUTE);
				}else{
					//should be eliminated refute
					player.setStatus(Player.STATUS.ELIMINATEDFINISHREFUTE);
				}
				
			}
			
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalRequestException e) {
			e.printStackTrace();
		} 
		
		ActionHelper.broadcast(connections, game);
	}

}
