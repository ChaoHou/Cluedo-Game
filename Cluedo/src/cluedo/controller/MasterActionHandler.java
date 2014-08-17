package cluedo.controller;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import cluedo.controller.action.Action;
import cluedo.controller.action.ActionHelper;
import cluedo.controller.action.server.MasterAction;
import cluedo.controller.connection.MasterConnection;
import cluedo.model.Board;
import cluedo.model.Player;

public class MasterActionHandler extends Thread implements ActionHandler{
	
	private MasterConnection[] connections;
	
	private Queue<MasterAction> actionQueue = new ConcurrentLinkedQueue<MasterAction>();
	
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
		
		
		//initial broadcast
		ActionHelper.broadcast(con, game);
	}
	
	@Override
	public void run(){
		System.out.println("MASTER RUNNING");
		while(allConnectionAlive()){
			try {
				if(!actionQueue.isEmpty()){
					MasterAction action = actionQueue.poll();
					action.execute(connections,game);
					
					round.tick(game);
				}
				
				
				Thread.sleep(gameClock);
			} catch (InterruptedException e) {
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
		assert(action instanceof MasterAction);
		actionQueue.offer((MasterAction)action);
	}
}
