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
					
					if(player.getStatus() == null){
						return;
					}

				} catch (IllegalRequestException e) {
					e.printStackTrace();
				}
			}
			
			
		}
		if(status.equals(State.INTURN)){
			
		}
	}
	
	private void initCards(Board game){
		Card.CHARACTER sCharacter = Card.CHARACTER.values()[random.nextInt(7)];
		Card.ROOM sRoom = Card.ROOM.values()[random.nextInt(10)];
		Card.WEAPON sWeapon = Card.WEAPON.values()[random.nextInt(7)];
	}
}
