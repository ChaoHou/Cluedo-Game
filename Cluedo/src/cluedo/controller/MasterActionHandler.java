package cluedo.controller;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import cluedo.controller.action.Action;
import cluedo.controller.connection.MasterConnection;
import cluedo.model.Board;
import cluedo.model.Player;

public class MasterActionHandler extends Thread implements ActionHandler{
	
	private MasterConnection[] connections;
	
	private Queue<Action> actionQueue = new ConcurrentLinkedQueue<Action>();
	
	private Board game;
	private Round round;
	private int gameClock;
	
	public MasterActionHandler(MasterConnection[] con, int clock){
		connections = con;
		gameClock = clock;
		
		//initialize the round
		round = new Round(connections);
		//initialize the players with uid
		ArrayList<Player> players = new ArrayList<Player>();
		for(int i=0;i<connections.length;i++){
			players.add(new Player(connections[i].uid()));
		}
		//initialize the board
		game = new Board(players);
		
		//initialize and start the connections
		for(MasterConnection master:connections){
			master.setActionHandler(this);
			master.initialize(connections, game);
			
			master.start();
		}
	}
	
	@Override
	public void run(){
		System.out.println("MASTER RUNNING");
		while(1 == 1){
			try {
				if(!actionQueue.isEmpty()){
					Action action = actionQueue.poll();
					action.execute();
					
					round.tick();
				}
				
				
				Thread.sleep(gameClock);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean isEmpty(){
		return actionQueue.isEmpty();
	}
	
	public Action pollAction(){
		return actionQueue.poll();
	}
	
	private boolean allConnectionAlive(){
		for(MasterConnection master:connections){
			if(master.isClosed()) return false;
		}
		return true;
	}

	@Override
	public void offerAction(Action action) {
		actionQueue.offer(action);
	}
}
