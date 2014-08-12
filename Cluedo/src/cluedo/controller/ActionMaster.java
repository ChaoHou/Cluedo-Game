package cluedo.controller;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import cluedo.controller.action.Action;
import cluedo.controller.connection.Master;
import cluedo.model.Board;

public class ActionMaster extends Thread implements ActionHandler{
	
	private Master[] connections;
	
	private Queue<Action> actionQueue = new ConcurrentLinkedQueue<Action>();
	
	private Board game;
	private Round round;
	private int gameClock;
	
	public ActionMaster(Master[] con, int clock){
		connections = con;
		gameClock = clock;
		
		//initialize the game
		
		game = new Board(0, 0);
		round = new Round(connections);
		//Action initialize = new Initialize(connections,game,round,broadcastClock);
		//actionQueue.offer(initialize);
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
		for(Master master:connections){
			if(master.isClosed()) return false;
		}
		return true;
	}

	@Override
	public void offerAction(Action action) {
		actionQueue.offer(action);
	}
}
