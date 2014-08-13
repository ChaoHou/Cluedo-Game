package cluedo.controller.action.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cluedo.controller.Round;
import cluedo.controller.action.AbstractAction;
import cluedo.controller.connection.Master;
import cluedo.controller.connection.Slave;
import cluedo.model.Board;

public class Initialize extends AbstractAction {
	
	private Board game;
	private Round round;
	private int broadcastClock;
	private Master[] connections;
	private Slave connection;
	
	public Initialize(Master[] con, Board game, Round round, int clock) {
		connections = con;
		server = true;
		this.game = game;
		this.round = round;
		broadcastClock = clock;
	}
	
	public Initialize(Slave slave){
		connection = slave;
		server = false;
	}

	/**
	 * Initialize the game and round, initialize all the clients
	 * TODO //to be modified
	 */
	protected void serverAction(){
		List<Master> unInit = new ArrayList<Master>(Arrays.asList(connections));
		List<Master> inited = new ArrayList<Master>();
		
		while(!unInit.isEmpty()){
			
			try {
				
				//get input from client
				//remove the client from unInit
				//add to the inited
				//boardcast to inited with current status
				
				for(Master master:unInit){
					try {
						if(master.getInput().available() != 0){
							//read input from user
							//update the board, player
							
							inited.add(master);
							
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				for(Master master:inited){
					//boardcast the current state
					
					//remove the object from unInit
					unInit.remove(master);
				}
				
				
				Thread.sleep(broadcastClock);
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	
	
}
