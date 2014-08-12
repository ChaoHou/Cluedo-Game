package cluedo.controller;

import cluedo.controller.connection.Master;

public class Round {
	
	public enum State{
		INITIALIZING,
		WAITING,
		INTURN,
		ENDTURN,
	}
	
	private Master[] connections;
	
	private Master playerInTurn;
	
	public Round(Master[] con){
		connections = con;
	}
	
	public void tick(){
		
	}
}
