package cluedo.controller;

import org.omg.CORBA.INITIALIZE;

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
	
	private State status = State.INITIALIZING;
	
	public Round(MasterConnection[] con){
		connections = con;
	}
	
	public void tick(){
		if(status.equals(State.INITIALIZING)){
			
		}
	}
}
