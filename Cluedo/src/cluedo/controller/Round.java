package cluedo.controller;

import cluedo.controller.connection.MasterConnection;

public class Round {
	
	public enum State{
		INITIALIZING,
		WAITING,
		INTURN,
		ENDTURN,
	}
	
	private MasterConnection[] connections;
	
	private MasterConnection playerInTurn;
	
	public Round(MasterConnection[] con){
		connections = con;
	}
	
	public void tick(){
		
	}
}
