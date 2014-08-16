package cluedo.controller;

import java.util.Random;

import org.omg.CORBA.INITIALIZE;

import cluedo.controller.connection.MasterConnection;
import cluedo.exception.IllegalRequestException;
import cluedo.model.Board;
import cluedo.model.Card;
import cluedo.model.Player;

public class Round {
	
	public enum State{
		INITIALIZING,
		WAITING,
		INTURN,
		ENDTURN,
	}
	
	private Random random = new Random();
	
	private MasterConnection[] connections;
	
	private MasterConnection playerInTurn;
	
	private State status = State.INITIALIZING;
	
	public Round(MasterConnection[] con){
		connections = con;
	}
	
	public void tick(Board game){
		if(status.equals(State.INITIALIZING)){
			for(MasterConnection connection:connections){
				try {
					Player player = game.getPlayer(connection.uid());
					
					//TODO
					//initialize the status with initializing, not null
					if(player.getStatus() == null){
						return;
					}

				} catch (IllegalRequestException e) {
					e.printStackTrace();
				}
			}
			
			//all the player finished initialize, 
			//initialize the solution, deal cards, 
			//select one player to start the turn
			//change the state of round to INTURN
			
			//broadcast the board state in order to start the game
		}else if(status.equals(State.INTURN)){
			//check if all the player is watching or eliminated, means this round is finished
			//if all the player is eliminated, stop the game,
		}
	}
	
	private void initCards(Board game){
		Card.CHARACTER sCharacter = Card.CHARACTER.values()[random.nextInt(Card.CHARACTER.values().length+1)];
		Card.ROOM sRoom = Card.ROOM.values()[random.nextInt(Card.ROOM.values().length+1)];
		Card.WEAPON sWeapon = Card.WEAPON.values()[random.nextInt(Card.WEAPON.values().length+1)];
		
		Card[] solution = {
				new Card(Card.TYPE.CHARCTER,sCharacter.toString()),
				new Card(Card.TYPE.ROOM,sRoom.toString()),
				new Card(Card.TYPE.WEAPON,sWeapon.toString()),
		};
		
		//game.setSolution(solution);
		//set the solution on board
		
		//deal the rest of cards to players
	}
}
