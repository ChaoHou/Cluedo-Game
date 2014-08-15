package cluedo.controller;

import org.omg.CORBA.INITIALIZE;

import cluedo.controller.connection.MasterConnection;
import cluedo.exception.IllegalRequestException;
import cluedo.model.Board;
import cluedo.model.Player;

public class Round {
	
	public enum State{
		INITIALIZING,
		WAITING,
		INTURN,
		ENDTURN,
	}
	
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
					
					//if(player.)
					//check All player's status,
					//if all the play finished initialize

				} catch (IllegalRequestException e) {
					e.printStackTrace();
				}
			}
			
			//
		}
		if(status.equals(State.INTURN)){
			
		}
	}
}
