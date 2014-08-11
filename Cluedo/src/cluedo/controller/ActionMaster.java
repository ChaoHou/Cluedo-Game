package cluedo.controller;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import cluedo.controller.action.Action;
import cluedo.controller.action.Initialize;
import cluedo.controller.connection.Master;
import cluedo.model.Board;

public class ActionMaster extends Thread implements ActionHandler{
	
	private Master[] connections;
	
	private Queue<Action> actionQueue = new ConcurrentLinkedQueue<Action>();
	
	private Board game;
	private Round round;
	
	public ActionMaster(Master[] con){
		connections = con;
		
		//initialize the game
		round = new Round();
		
		Action initialize = new Initialize();
		actionQueue.offer(initialize);
	}
	
	
	public void run(){
		
	}
	
	public boolean isEmpty(){
		return actionQueue.isEmpty();
	}
	
	public Action pollAction(){
		return actionQueue.poll();
	}
}
