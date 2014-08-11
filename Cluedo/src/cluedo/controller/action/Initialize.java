package cluedo.controller.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cluedo.controller.Round;
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
	 */
	protected void serverAction(){
		List<Master> unInit = new ArrayList<Master>(Arrays.asList(connections));
		List<Master> Inited = new ArrayList<Master>();
		
		while(!unInit.isEmpty()){
			
			try {
				
				
				Thread.sleep(broadcastClock);
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void clientAction(){
		
	}
}
