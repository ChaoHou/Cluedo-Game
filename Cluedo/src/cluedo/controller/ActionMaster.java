package cluedo.controller;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import cluedo.controller.action.Action;
import cluedo.controller.action.Initialize;
import cluedo.controller.action.Notify;
import cluedo.controller.connection.Master;
import cluedo.model.Board;

public class ActionMaster extends Thread implements ActionHandler{
	
	private Master[] connections;
	
	private Queue<Action> actionQueue = new ConcurrentLinkedQueue<Action>();
	
	private Board game;
	private Round round;
	private int broadcastClock;
	
	public ActionMaster(Master[] con, int clock){
		connections = con;
		broadcastClock = clock;
		
		//initialize the game
		round = new Round();
		game = new Board(0, 0);
		//Action initialize = new Initialize(connections,game,round,broadcastClock);
		//actionQueue.offer(initialize);
	}
	
	@Override
	public void run(){
		System.out.println("MASTER RUNNING");
		while(allConnectionAlive()){
			try {
				Thread.sleep(1000);
				actionQueue.add(new Notify(connections));
				
				System.out.println("Queue an action");
				
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
		for(Master master:connections){
			if(master.isClosed()) return false;
		}
		return true;
	}
}
